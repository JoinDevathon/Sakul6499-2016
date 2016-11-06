package de.sakul6499.devathon.script;

import de.sakul6499.devathon.api.Script;
import de.sakul6499.devathon.api.Shutdown;
import de.sakul6499.devathon.api.Startup;
import de.sakul6499.devathon.util.BiObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.UUID;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

/**
 * Created by lukas on 06.11.16.
 */
final class ScriptLoader extends ClassLoader {

    private final UUID unique;

    private final HashMap<String, byte[]> undefinedMap;
    private final HashMap<String, Class<?>> definedMap;
    private final HashMap<String, byte[]> resourcesMap;

    {
        this.unique = UUID.randomUUID();

        this.undefinedMap = new HashMap<>();
        this.definedMap = new HashMap<>();
        this.resourcesMap = new HashMap<>();
    }

    ScriptLoader(ClassLoader parentLoader, byte[] pluginBytes) {
        super(parentLoader);

        try (JarInputStream jarInputStream = new JarInputStream(new ByteArrayInputStream(pluginBytes))) {
            JarEntry jarEntry;
            while ((jarEntry = jarInputStream.getNextJarEntry()) != null) {
                if (jarEntry.isDirectory()) continue;

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                int i;
                while ((i = jarInputStream.read()) != -1) {
                    byteArrayOutputStream.write(i);
                }
                byteArrayOutputStream.flush();

                if (jarEntry.getName().endsWith(".java")) {
                    undefinedMap.put(jarEntry.getName().replaceAll(".java", "").replaceAll("/", "."), byteArrayOutputStream.toByteArray());
                } else if (jarEntry.getName().endsWith(".class")) {
                    undefinedMap.put(jarEntry.getName().replaceAll(".class", "").replaceAll("/", "."), byteArrayOutputStream.toByteArray());
                } else {
                    resourcesMap.put(jarEntry.getName(), byteArrayOutputStream.toByteArray());
                }

                byteArrayOutputStream.close();
                jarInputStream.closeEntry();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public UUID createScriptInstance() throws Exception {
        for (String name : undefinedMap.keySet()) {
            try {
                Class<?> cls = findClass(name);
                if (cls == null) continue;
                if (cls.getMethods() == null) continue;

                Script script;
                if ((script = cls.getAnnotation(Script.class)) == null) {
                    continue;
                }

                BiObject<Script, Class<?>> scriptBundle = new BiObject<>(script, cls);
                BiObject<Startup, Method> startupBundle = new BiObject<>();
                BiObject<Shutdown, Method> shutdownBundle = new BiObject<>();
                for (Method method : cls.getMethods()) {
                    Startup startup;
                    if ((startup = method.getAnnotation(Startup.class)) != null) {
                        startupBundle.set(startup, method);
                    }

                    Shutdown shutdown;
                    if ((shutdown = method.getAnnotation(Shutdown.class)) != null) {
                        shutdownBundle.set(shutdown, method);
                    }
                }

                ScriptInstance scriptInstance = new ScriptInstance(scriptBundle, startupBundle, shutdownBundle);

                ScriptHeap.GetInstance().registerScript(scriptInstance, this);

                return scriptInstance.getUnique();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        throw new IllegalStateException();
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        if (definedMap.containsKey(name)) {
            return definedMap.get(name);
        }

        if (undefinedMap.containsKey(name)) {
            byte[] buffer = undefinedMap.get(name);
            Class<?> cls = defineClass(name, buffer, 0, buffer.length);

            definedMap.put(name, cls);
            undefinedMap.remove(name);

            return cls;
        }

        return null;
    }

    public InputStream getResourceAsStream(String name) {
        byte[] buffer = resourcesMap.get(name);
        if (buffer == null || buffer.length <= 0)
            return null;

        return new ByteArrayInputStream(buffer);
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass().isInstance(this.getClass()) && ((ScriptLoader) obj).unique.equals(unique);
    }

    public final UUID getUnique() {
        return unique;
    }

    public void clear() {
        undefinedMap.clear();
        definedMap.clear();
        resourcesMap.clear();
    }
}
