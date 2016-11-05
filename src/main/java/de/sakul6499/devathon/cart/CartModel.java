package de.sakul6499.devathon.cart;

import com.google.common.collect.Lists;
import de.sakul6499.devathon.JSONLocation;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftMinecartChest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.UUID;

/**
 * Created by lukas on 05.11.16.
 */
public final class CartModel {

    public static CartModel FromJSON(String json) throws ParseException {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse(json);

        return new CartModel(
                (String) jsonObject.get("name"),
                JSONLocation.FromJSON((String) jsonObject.get("location"))
        );
    }

    private final UUID cartID;

    {
        cartID = UUID.randomUUID();
    }

    private final String name;
    private final JSONLocation location;

    private CraftMinecartChest cart;
    private ArmorStand itemHolder;

    public CartModel(String name, JSONLocation location) {
        this.name = name;
        this.location = location;
    }

    public CartModel(String name, Location location) {
        this.name = name;
        this.location = new JSONLocation(location);
    }

    public final CartModel spawn() {
        if (spawned()) {
            update();
            return null;
        }

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

        return this;
    }

    public final CartModel kill(boolean drop) {
        if (cart != null && !cart.isDead()) cart.remove();
        if (itemHolder != null && !itemHolder.isDead()) itemHolder.remove();

        if (drop) {
            ItemStack dropStack = new ItemStack(Material.STORAGE_MINECART);
            ItemMeta dropMeta = dropStack.getItemMeta();

            dropMeta.setDisplayName(name);
            dropMeta.addEnchant(Enchantment.LURE, 10, true);
            dropMeta.setLore(Lists.newArrayList("Devathon Cart"));

            dropStack.setItemMeta(dropMeta);
            location.getWorld().dropItem(location.toBukkitLocation(), dropStack);
        }

        return this;
    }

    public final CartModel kill() {
        return kill(true);
    }

    public final CartModel update() {
        kill(false);
        spawn();

        return this;
    }

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

    @Override
    public boolean equals(Object obj) {
        if (!obj.getClass().isInstance(this)) {
            return false;
        }

        CartModel cart = (CartModel) obj;
        return cart.getCartID().equals(cartID);
    }
}
