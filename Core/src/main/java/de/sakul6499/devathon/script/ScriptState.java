package de.sakul6499.devathon.script;

/**
 * Created by lukas on 06.11.16.
 */
public enum ScriptState {
    INVALID (false, false),
    READY (true, false),
    STARTED (false, true),
    STOPPED (true, false);

    private final boolean launch;
    private final boolean running;

    ScriptState(boolean launch, boolean running) {
        this.launch = launch;
        this.running = running;
    }

    public boolean canLaunch() {
        return launch;
    }

    public boolean isRunning() {
        return running;
    }
}
