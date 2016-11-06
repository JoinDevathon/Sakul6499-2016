package de.sakul6499.devathon.cart;

import com.google.common.collect.Lists;
import de.sakul6499.devathon.Devathon;
import de.sakul6499.devathon.JSONLocation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitTask;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.UUID;

/**
 * Created by lukas on 05.11.16.
 */
public final class CartModel {

    public final static String ITEM_NAME;

    static {
        ITEM_NAME = "Devathon Cart";
    }

    public static CartModel FromJSON(String json) throws ParseException {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse(json);

        return new CartModel(
                (String) jsonObject.get("name"),
                JSONLocation.FromJSON((String) jsonObject.get("location")),
                Facing.values()[(int) (long) jsonObject.get("facing")]
        );
    }

    private final UUID cartID;

    {
        cartID = UUID.randomUUID();
    }

    private final String name;

    private JSONLocation location;
    private Facing facing;

    private ArmorStand baseHolder;
    private ArmorStand chestHolder;
    private ArmorStand itemHolder;

    public CartModel(String name, JSONLocation location, Facing facing) {
        this.name = name;
        this.location = location;
        this.facing = facing;
    }

    public CartModel(String name, JSONLocation location) {
        this(name, location, Facing.NORTH);
    }

    public final CartModel spawn() {
        if (spawned()) {
            kill();
        }

        baseHolder = (ArmorStand) location.getWorld().spawnEntity(new Location(location.getWorld(), location.getNormalX() + 0.5, location.getNormalY() - 1.3, location.getNormalZ() + 0.5, facing.getYawVal(), 0), EntityType.ARMOR_STAND);
        baseHolder.setHelmet(new ItemStack(Material.DROPPER));
        baseHolder.setCustomName(name);
        baseHolder.setCustomNameVisible(true);
        baseHolder.setInvulnerable(true);
        baseHolder.setGravity(false);
        baseHolder.setSilent(true);
        baseHolder.setBasePlate(false);
        baseHolder.setVisible(false);

        chestHolder = (ArmorStand) location.getWorld().spawnEntity(new Location(location.getWorld(), location.getNormalX() + 0.5, location.getNormalY() - 0.15, location.getNormalZ() + 0.5, facing.getYawVal(), 0), EntityType.ARMOR_STAND);
        chestHolder.setHelmet(new ItemStack(Material.CHEST));
        chestHolder.setInvulnerable(true);
        chestHolder.setGravity(false);
        chestHolder.setSilent(true);
        chestHolder.setBasePlate(false);
        chestHolder.setVisible(false);
        chestHolder.setSmall(true);

        // TODO: Rotation not working
//        int next_facing = facing.ordinal() + 1;
//        if(next_facing >= Facing.values().length) next_facing = 0;
//        itemHolder = (ArmorStand) location.getWorld().spawnEntity(new Location(location.getWorld(), location.getNormalX() + 0.55, location.getNormalY() - 1.8, location.getNormalZ() + 0.5, Facing.values()[next_facing].getYawVal(), 0), EntityType.ARMOR_STAND);
        itemHolder = (ArmorStand) location.getWorld().spawnEntity(new Location(location.getWorld(), location.getNormalX() + 0.55, location.getNormalY() - 1.8, location.getNormalZ() + 0.5, Facing.SOUTH.getYawVal(), 0), EntityType.ARMOR_STAND);
        itemHolder.setHelmet(new ItemStack(Material.DIAMOND_PICKAXE));
        itemHolder.setInvulnerable(true);
        itemHolder.setGravity(false);
        itemHolder.setBasePlate(false);
        itemHolder.setVisible(false);
        itemHolder.setSilent(true);

        return this;
    }

    public final CartModel kill() {
        if (baseHolder != null && !baseHolder.isDead()) baseHolder.remove();
        if (itemHolder != null && !itemHolder.isDead()) itemHolder.remove();
        if (chestHolder != null && !chestHolder.isDead()) chestHolder.remove();

        return this;
    }

    public final CartModel dropItem() {
        ItemStack dropStack = new ItemStack(Material.STORAGE_MINECART);
        ItemMeta dropMeta = dropStack.getItemMeta();

        dropMeta.setDisplayName(name);
        dropMeta.addEnchant(Enchantment.LURE, 10, true);
        dropMeta.setLore(Lists.newArrayList(ITEM_NAME));

        dropStack.setItemMeta(dropMeta);
        location.getWorld().dropItem(location.toBukkitLocation(), dropStack);

        return this;
    }

    public final CartModel update() {
        kill();
        spawn();

        return this;
    }

    public final void move(Direction direction) {
        Coordinate coordinate;
        switch (direction) {
            case FORWARD:
                coordinate = facing.getForward();
                break;
            case BACKWARD:
                coordinate = facing.getBackward();
                break;
            case LEFT:
                coordinate = facing.getLeft();
                break;
            case RIGHT:
                coordinate = facing.getRight();
                break;
            case UP:
                coordinate = facing.getUp();
                break;
            case DOWN:
                coordinate = facing.getDown();
                break;
            default:
                coordinate = null;
                System.out.println("Invalid #1 [" + direction + " -> " + facing + "]");
                break;
        }

        switch (coordinate) {
            case X_POSITIVE:
                location.addX(1);
                break;
            case X_NEGATIVE:
                location.addX(-1);
                break;
            case Y_POSITIVE:
                location.addY(1);
                break;
            case Y_NEGATIVE:
                location.addY(-1);
                break;
            case Z_POSITIVE:
                location.addZ(1);
                break;
            case Z_NEGATIVE:
                location.addZ(-1);
                break;
            default:
                System.out.println("Invalid #2 [" + direction + " -> " + facing + "]");
                break;
        }

        update();
    }

    public final boolean spawned() {
        return baseHolder != null && itemHolder != null && chestHolder != null;
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

    public JSONLocation getLocation() {
        return location;
    }

    public void setLocation(JSONLocation location) {
        this.location = location;
    }

    public Facing getFacing() {
        return facing;
    }

    public void setFacing(Facing facing) {
        this.facing = facing;
    }

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        
        jsonObject.put("name", name);
        jsonObject.put("location", location.toString());
        jsonObject.put("facing", facing.ordinal());
        
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
