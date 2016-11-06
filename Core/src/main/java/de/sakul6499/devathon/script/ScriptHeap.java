package de.sakul6499.devathon.script;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by lukas on 06.11.16.
 */
public class ScriptHeap {

    /* Static */
    private final static ScriptHeap INSTANCE;

    static {
        INSTANCE = new ScriptHeap();
    }

    public final static ScriptHeap GetInstance() {
        return INSTANCE;
    }

    /* Class */

    private final HashMap<ScriptInstance, ScriptLoader> heap;

    private ScriptHeap() {
        heap = new HashMap<>();
    }

    boolean registerScript(ScriptInstance scriptInstance, ScriptLoader scriptLoader) {
        if (scriptInstance.isValid()) return false;

        UUID uuid = UUID.randomUUID();
        scriptInstance.init(uuid);

        heap.put(scriptInstance, scriptLoader);

        return true;
    }

    public UUID createPlugin(File file) throws Exception {
        if (!file.exists())
            throw new IllegalStateException("File does not exists!");

        byte[] fileByteArray = new byte[(int) file.length()];
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            if (fileInputStream.read(fileByteArray) < 0)

                throw new IllegalStateException("Failed reading file!");
        } catch (IOException e) {
            e.printStackTrace();
        }

        ScriptLoader scriptLoader = new ScriptLoader(this.getClass().getClassLoader(), fileByteArray);
        return scriptLoader.createScriptInstance();
    }

    public void removePlugin(ScriptInstance scriptInstance) {
        if (scriptInstance.getScriptState().isRunning()) {
            scriptInstance.shutdown();
        }

        heap.remove(scriptInstance);
    }

    public void startup() {
        for (Map.Entry<ScriptInstance, ScriptLoader> entry : heap.entrySet()) {
            if (entry.getKey().getScriptState().canLaunch()) {
                try {
                    entry.getKey().startup();
                } catch (Exception e) {
                    throw new IllegalStateException("Exception during startup: " + e.getMessage());
                }
            }
        }
    }

    public void shutdown() {
        for (Map.Entry<ScriptInstance, ScriptLoader> entry : heap.entrySet()) {
            if (entry.getKey().getScriptState().isRunning()) {
                try {
                    entry.getKey().shutdown();
                } catch (Exception e) {
                    new IllegalStateException("Exception during startup: " + e.getMessage()).printStackTrace();
                }
            }

            entry.getValue().clear();
        }

        heap.clear();
    }

    public void startupScript(UUID uuid) {
        for (ScriptInstance scriptInstance : heap.keySet()) {
            if (scriptInstance.getUnique().equals(uuid)) {
                scriptInstance.startup();
                return;
            }
        }
    }

    public void shutdownScript(UUID uuid) {
        for (ScriptInstance scriptInstance : heap.keySet()) {
            if (scriptInstance.getUnique().equals(uuid)) {
                scriptInstance.shutdown();
                return;
            }
        }
    }

    public ScriptLoader getClassLoader(ScriptInstance scriptInstance) {
        return heap.get(scriptInstance);
    }

    public ScriptInstance getPluginInstance(ScriptLoader pluginClassLoader) {
        for (Map.Entry<ScriptInstance, ScriptLoader> entry : heap.entrySet()) {
            if (entry.getValue().equals(pluginClassLoader)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public ScriptInstance getPluginInstanceByName(String name) {
        for (ScriptInstance scriptInstance : heap.keySet()) {
            if (scriptInstance.getName().equalsIgnoreCase(name)) {
                return scriptInstance;
            }
        }
        return null;
    }
}
