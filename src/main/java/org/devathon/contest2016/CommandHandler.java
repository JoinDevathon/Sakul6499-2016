package org.devathon.contest2016;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.devathon.contest2016.cart.BasicCart;
import org.json.simple.parser.ParseException;

/**
 * Created by lukas on 05.11.16.
 */
public class CommandHandler implements Listener {
    @EventHandler
    public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) {
        System.out.println("Command: " + event.getMessage());

        String[] commandString = event.getMessage().split(" ");
        switch (commandString[0].toLowerCase()) {
            case "/spawn":
                BasicCart basicCart = new BasicCart("TestSpawnCart", event.getPlayer().getLocation());
                basicCart.spawn();

                System.out.println(basicCart.toString());
                try {
                    BasicCart.FromJSON(basicCart.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                event.setCancelled(true);
                break;
            default:
                System.out.println("Unknown command!");
                event.getPlayer().sendRawMessage("Unknown command!");
                break;
        }
    }
}
