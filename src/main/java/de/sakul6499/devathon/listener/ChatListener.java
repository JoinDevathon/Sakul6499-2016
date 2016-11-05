package de.sakul6499.devathon.listener;

import de.sakul6499.devathon.cart.CartManager;
import de.sakul6499.devathon.cart.CartModel;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

/**
 * Created by lukas on 05.11.16.
 */
public class ChatListener implements Listener {
    @EventHandler
    public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) {
        System.out.println("Command: " + event.getMessage());

        String[] commandString = event.getMessage().split(" ");
        switch (commandString[0].toLowerCase()) {
            case "/spawn":
                event.setCancelled(true);
                if (!CartManager.GetInstance().registerCart(new CartModel("TestSpawnCart", event.getPlayer().getLocation()).spawn())) {
                    event.getPlayer().sendMessage("Failed to register a new cart model!");
                }
                break;
            default:
                System.out.println("Unknown command!");
                event.getPlayer().sendRawMessage("Unknown command!");
                break;
        }
    }
}
