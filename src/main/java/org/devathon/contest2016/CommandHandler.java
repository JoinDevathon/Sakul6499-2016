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
//                Location location = event.getPlayer().getLocation();
//                location.getBlock().setType(Material.COMMAND);
//                CommandBlock commandBlock = (CommandBlock) location.getBlock();

                Location origin = event.getPlayer().getLocation();
                Location commandLocation = new Location(origin.getWorld(), origin.getBlockX(), origin.getBlockY(), origin.getBlockZ());
//                System.out.println(origin);

                CraftMinecartChest commandStand = (CraftMinecartChest) commandLocation.getWorld().spawnEntity(commandLocation, EntityType.MINECART_CHEST);
//                commandStand.setHelmet(new ItemStack(Material.COMMAND_MINECART));
                commandStand.setInvulnerable(true);
                commandStand.setGravity(false);
                commandStand.setSilent(true);
                commandStand.setMaxSpeed(0);
                commandStand.setSlowWhenEmpty(false);

//                commandStand.setBasePlate(false);
//                commandStand.setVisible(false);

//                Location origin_aligned = origin.subtract(0, -4, 0);
//                System.out.println(origin_aligned);
                Location pickaxeLocation = new Location(origin.getWorld(), origin.getBlockX(), origin.getBlockY() - 1.75, origin.getBlockZ() + 0.8);
                ArmorStand pickaxeStand = (ArmorStand) pickaxeLocation.getWorld().spawnEntity(pickaxeLocation, EntityType.ARMOR_STAND);
                pickaxeStand.setHelmet(new ItemStack(Material.DIAMOND_PICKAXE));
                pickaxeStand.setInvulnerable(true);
                pickaxeStand.setGravity(false);
                pickaxeStand.setBasePlate(false);
                pickaxeStand.setVisible(false);
                break;
            default:
                System.out.println("Unknown command!");
                event.getPlayer().sendRawMessage("Unknown command!");
                break;
        }
    }
}
