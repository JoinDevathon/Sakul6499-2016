package org.devathon.contest2016;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.CommandBlock;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftMinecartChest;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.minecart.CommandMinecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.devathon.contest2016.cart.BasicCart;
import org.devathon.contest2016.model.AdvBlockTest;
import org.devathon.contest2016.model.ModelHandler;
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
                ModelHandler.GetInstance().register(testBlock);
                testBlock.SpawnTask();

                AdvBlockTest advBlockTest = new AdvBlockTest(event.getPlayer().getLocation());
                ModelHandler.GetInstance().register(advBlockTest);
                advBlockTest.SpawnTask();

                System.out.println("Spawned!");
                event.getPlayer().sendRawMessage("Spawned!");
                break;
            case "/spawn":
                BasicCart basicCart = new BasicCart(event.getPlayer().getLocation());
                basicCart.spawn();

                event.setCancelled(true);
                break;
            default:
                System.out.println("Unknown command!");
                event.getPlayer().sendRawMessage("Unknown command!");
                break;
        }
    }
}
