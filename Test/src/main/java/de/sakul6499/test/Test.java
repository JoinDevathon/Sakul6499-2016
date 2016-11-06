package de.sakul6499.test;

import de.sakul6499.devathon.api.Script;
import de.sakul6499.devathon.api.Shutdown;
import de.sakul6499.devathon.api.Startup;

/**
 * Created by lukas on 06.11.16.
 */
@Script(name = "test", author = "sakul6499", version = "1.0")
public class Test {

    @Startup
    public void startup() {
        System.out.println("Started!");
    }

    @Shutdown
    public void shutdown() {
        System.out.println("Stopped!");
    }
}
