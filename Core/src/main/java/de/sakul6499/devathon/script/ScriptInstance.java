package de.sakul6499.devathon.script;

import de.sakul6499.devathon.api.Script;
import de.sakul6499.devathon.api.Shutdown;
import de.sakul6499.devathon.api.Startup;
import de.sakul6499.devathon.util.BiObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * Created by lukas on 06.11.16.
 */
public final class ScriptInstance {

    private final BiObject<Script, Class<?>> pluginBundle;
    private final BiObject<Startup, Method> startupBundle;
    private final BiObject<Shutdown, Method> shutdownBundle;

    private final Object instance;

    private UUID unique;

    private ScriptState scriptState;

    {
        scriptState = ScriptState.INVALID;
    }

    ScriptInstance(BiObject<Script, Class<?>> pluginBundle, BiObject<Startup, Method> startupBundle, BiObject<Shutdown, Method> shutdownBundle) {
        this.pluginBundle = pluginBundle;
        this.startupBundle = startupBundle;
        this.shutdownBundle = shutdownBundle;

        if(!this.pluginBundle.isValid())
            throw new IllegalStateException("Plugin bundle invalid");
        if(!this.startupBundle.isValid())
            throw new IllegalStateException("Startup bundle invalid!");
        if(!this.shutdownBundle.isValid())
            throw new IllegalStateException("Shutdown bundle invalid!");

        try {
            this.instance = this.pluginBundle.getSecond().newInstance();
        } catch(InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }

        this.scriptState = ScriptState.READY;
    }

    void init(UUID uuid) {
        this.unique = uuid;
    }

    public void startup() {
        System.out.println("State: " + scriptState);
        if (!this.scriptState.canLaunch())
            throw new IllegalStateException("Script can not be launched!");

        try {
            startupBundle.getSecond().invoke(instance);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new IllegalStateException(e);
        }

        this.scriptState = ScriptState.STARTED;
    }

    public void shutdown() {
        if (!this.scriptState.isRunning())
            throw new IllegalStateException("Script can not be stopped!");

        try {
            shutdownBundle.getSecond().invoke(instance);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new IllegalStateException(e);
        }

        this.scriptState = ScriptState.STOPPED;
    }

    public boolean isValid() {
        return unique != null;
    }

    public UUID getUnique() {
        return unique;
    }

    public String getName() {
        return pluginBundle.getFirst().name();
    }

    public String getAuthor() {
        return pluginBundle.getFirst().author();
    }

    public String getVersion() {
        return pluginBundle.getFirst().version();
    }

    public Class<?> getAssocClass() {
        return pluginBundle.getSecond();
    }

    public ScriptState getScriptState() {
        return scriptState;
    }
}
