package org.devathon.contest2016.cart;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftMinecartChest;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.devathon.contest2016.JSONLocation;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.UUID;

/**
 * Created by lukas on 05.11.16.
 */
public final class BasicCart {

    public static BasicCart FromJSON(String json) throws ParseException {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse(json);

        String name = (String) jsonObject.get("name");

        Object object = jsonObject.get("location");
        System.out.println(object.getClass().getName());
        System.out.println((String) object);

        JSONLocation location = JSONLocation.FromJSON((String) object);

        System.out.println(name + " " + location);

        return null;
    }

    private final UUID cartID;

    {
        cartID = UUID.randomUUID();
    }

    private final String name;
    private final JSONLocation location;

    private CraftMinecartChest cart;
    private ArmorStand itemHolder;

    public BasicCart(String name, JSONLocation location) {
        this.name = name;
        this.location = location;
    }
    
    public BasicCart(String name, Location location) {
        this.name = name;
        this.location = new JSONLocation(location);
    }

    public final void spawn() {
        cart = (CraftMinecartChest) location.getWorld().spawnEntity(new Location(location.getWorld(), location.getNormalX(), location.getNormalY(), location.getNormalZ()), EntityType.MINECART_CHEST);
        cart.setInvulnerable(true);
        cart.setGravity(false);
        cart.setSilent(true);
        cart.setMaxSpeed(0);
        cart.setSlowWhenEmpty(false);
        cart.setCustomName(name);
        cart.setCustomNameVisible(true);

        itemHolder = (ArmorStand) location.getWorld().spawnEntity(new Location(location.getWorld(), location.getNormalX(), location.getNormalY() - 1.75, location.getNormalZ() + 0.8), EntityType.ARMOR_STAND);
        itemHolder.setHelmet(new ItemStack(Material.DIAMOND_PICKAXE));
        itemHolder.setInvulnerable(true);
        itemHolder.setGravity(false);
        itemHolder.setBasePlate(false);
        itemHolder.setVisible(false);
    }

    // TODO: update

    public final boolean spawned() {
        return cart != null && itemHolder != null;
    }

    public final UUID getCartID() {
        return cartID;
    }

    public final String getName() {
        return name;
    }

    public final JSONLocation getJSONLocation() {
        return location;
    }

    public final Location getBukkitLocation() {
        return location.toBukkitLocation();
    }

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        
        jsonObject.put("name", name);
        jsonObject.put("location", location.toString());
        
        return jsonObject.toJSONString();
    }
}
