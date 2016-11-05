package org.devathon.contest2016;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.devathon.contest2016.model.TestBasicBlock;

/**
 * Created by lukas on 05.11.16.
 */
public class CommandHandler implements Listener {
    @EventHandler
    public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) {
        System.out.println("Command: " + event.getMessage());

        String[] commandString = event.getMessage().split(" ");
        switch(commandString[0].toLowerCase()) {
            case "/test":
                TestBasicBlock testBlock = new TestBasicBlock(event.getPlayer().getLocation());
                testBlock.SpawnTask();

                System.out.println("Spawned!");
                event.getPlayer().sendRawMessage("Spawned!");
                break;
            default:
                System.out.println("Unknown command!");
                event.getPlayer().sendRawMessage("Unknown command!");
                break;
        }
    }
}
