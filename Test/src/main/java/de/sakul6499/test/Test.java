package de.sakul6499.test;

import de.sakul6499.devathon.api.Script;
import de.sakul6499.devathon.api.Shutdown;
import de.sakul6499.devathon.api.Startup;
import de.sakul6499.devathon.cart.CartManager;
import de.sakul6499.devathon.cart.CartModel;
import de.sakul6499.devathon.cart.Direction;
import de.sakul6499.devathon.script.ScriptHeap;

/**
 * Created by lukas on 06.11.16.
 */
// Name, Author, Version
@Script(name = "test", author = "sakul6499", version = "1.0")
public class Test {

    // define Startup
    @Startup
    public void startup() {
        // Print out startup message
        System.out.println("Script Started!");

        // Get Cart Model
        CartModel cartModel = CartManager.GetInstance().getByScriptClass(this.getClass());
        if(cartModel == null) {
            System.out.println("NULL");
            return;
        }

        // [DEBUG] print out JSON
        System.out.println("CartModel JSON: " + cartModel.toString());

        // move four blocks forward
        for(int i = 0; i < 4; i++) cartModel.move(Direction.FORWARD);

        // Set Script as "exited"
        ScriptHeap.GetInstance().ExitScript(this.getClass());
    }

    // define Shutdown
    @Shutdown
    public void shutdown() {
        System.out.println("Stopped!");
    }
}
