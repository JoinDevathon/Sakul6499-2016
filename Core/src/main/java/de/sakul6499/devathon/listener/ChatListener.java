package de.sakul6499.devathon.listener;

import de.sakul6499.devathon.Devathon;
import de.sakul6499.devathon.api.Script;
import de.sakul6499.devathon.script.ScriptHeap;
import de.sakul6499.devathon.script.ScriptInstance;
import de.sakul6499.devathon.util.JSONLocation;
import de.sakul6499.devathon.cart.CartManager;
import de.sakul6499.devathon.cart.CartModel;
import de.sakul6499.devathon.cart.Direction;
import de.sakul6499.devathon.cart.Facing;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

/**
 * Created by lukas on 05.11.16.
 */
public class ChatListener implements Listener {

    @EventHandler
    public void AsyncPlayerChatEvent(AsyncPlayerChatEvent event) {
        if(!event.getMessage().startsWith("#")) return;
        event.setCancelled(true);

        String[] split = event.getMessage().split(" ");
        String name = split[0].substring(1, split[0].length());

        // TODO
        CartModel cartModel = CartManager.GetInstance().getByName(name);
        if(cartModel == null) {
            event.getPlayer().sendMessage("CartModel '" + name + "' does not exist!");
        } else {
            event.getPlayer().sendMessage("Here's your JSON: " + cartModel.toString());

//            Bukkit.getScheduler().runTask(Devathon.PLUGIN_INSTANCE, () -> {
//                for(Facing facing : Facing.values()) {
//                    cartModel.setFacing(facing);
//
//                    for(Direction direction : Direction.values()) {
//                        cartModel.move(direction);
//                        cartModel.update();
//                    }
//                }
//            });

            if(split.length > 1) {
                ScriptInstance scriptInstance = ScriptHeap.GetInstance().getPluginInstanceByName(split[1]);
                if(scriptInstance == null) {
                    event.getPlayer().sendMessage("Script invalid!");

                    return;
                }

                CartManager.GetInstance().assoc(cartModel, scriptInstance.getAssocClass());
            }
        }
    }

    @EventHandler
    public void PlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) {
        System.out.println("Command: " + event.getMessage());

        String[] commandString = event.getMessage().split(" ");
        switch (commandString[0].toLowerCase()) {
            case "/spawn":
                event.setCancelled(true);

                if(commandString.length <= 1) {
                    event.getPlayer().sendMessage("You need to provide a name!");
                    return;
                }

                if (!CartManager.GetInstance().registerCart(new CartModel(commandString[1], new JSONLocation(event.getPlayer().getLocation())).spawn())) {
                    event.getPlayer().sendMessage("Failed to register a new cart model!");
                }
                break;
            case "/remove":
                event.setCancelled(true);

                if(commandString.length <= 1) {
                    event.getPlayer().sendMessage("You need to provide a name!");
                    return;
                }

                if (!CartManager.GetInstance().remove(commandString[1])) {
                    event.getPlayer().sendMessage("Failed to remove the cart model!");
                }
                break;
            default:
                System.out.println("Unknown command!");
                event.getPlayer().sendRawMessage("Unknown command!");
                break;
        }
    }
}
