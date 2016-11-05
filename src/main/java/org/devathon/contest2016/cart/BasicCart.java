package org.devathon.contest2016.cart;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftMinecartChest;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

/**
 * Created by lukas on 05.11.16.
 */
public final class BasicCart {
    
    private final Location location;

    private CraftMinecartChest cart;
    private ArmorStand itemHolder;
    
    public BasicCart(Location location) {
        this.location = location;
    }
    
    public void spawn() {
        cart = (CraftMinecartChest) location.getWorld().spawnEntity(new Location(location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ()), EntityType.MINECART_CHEST);
        cart.setInvulnerable(true);
        cart.setGravity(false);
        cart.setSilent(true);
        cart.setMaxSpeed(0);
        cart.setSlowWhenEmpty(false);
        cart.setCustomName("Cart #...");
        cart.setCustomNameVisible(true);
        
        itemHolder = (ArmorStand) location.getWorld().spawnEntity(new Location(location.getWorld(), location.getBlockX(), location.getBlockY() - 1.75, location.getBlockZ() + 0.8), EntityType.ARMOR_STAND);
        itemHolder.setHelmet(new ItemStack(Material.DIAMOND_PICKAXE));
        itemHolder.setInvulnerable(true);
        itemHolder.setGravity(false);
        itemHolder.setBasePlate(false);
        itemHolder.setVisible(false);
    }

    public boolean spawned() {
        return cart != null && itemHolder != null;
    }
}
