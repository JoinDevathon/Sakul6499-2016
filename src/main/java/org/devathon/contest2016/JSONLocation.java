package org.devathon.contest2016;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.UUID;

/**
 * Created by lukas on 05.11.16.
 */
public final class JSONLocation {

    private UUID worldID;

    private double x;
    private double y;
    private double z;

    private float yaw;
    private float pitch;

    public static JSONLocation FromJSON(String json) throws ParseException {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse(json);

        UUID worldID = UUID.fromString((String) jsonObject.get("worldID"));
        double x = (double) jsonObject.get("x");
        double y = (double) jsonObject.get("y");
        double z = (double) jsonObject.get("z");
        float yaw = (float) (double) jsonObject.get("yaw");
        float pitch = (float) (double) jsonObject.get("pitch");

        return new JSONLocation(worldID, x, y, z, yaw, pitch);
    }

    public JSONLocation() {

    }

    public JSONLocation(Location location) {
        this(location.getWorld().getUID(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }

    public JSONLocation(UUID worldID, double x, double y, double z) {
        this.worldID = worldID;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public JSONLocation(World world, double x, double y, double z) {
        this(world.getUID(), x, y, z);
    }

    public JSONLocation(UUID worldID, double x, double y, double z, float yaw, float pitch) {
        this.worldID = worldID;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public JSONLocation(World world, double x, double y, double z, float yaw, float pitch) {
        this(world.getUID(), x, y, z, yaw, pitch);
    }

    public final boolean isValid() {
        return worldID != null;
    }

    public UUID getWorldID() {
        return worldID;
    }

    public World getWorld() {
        return Bukkit.getWorld(worldID);
    }

    public void setWorldID(UUID worldID) {
        this.worldID = worldID;
    }

    public double getX() {
        return x;
    }

    public int getNormalX() {
        return (int) x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public int getNormalY() {
        return (int) y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public int getNormalZ() {
        return (int) z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public Location toBukkitLocation() {
        return new Location(getWorld(), getX(), getY(), getZ(), getYaw(), getPitch());
    }

    public Location toNormalLocation() {
        return new Location(getWorld(), getNormalX(), getNormalY(), getNormalZ(), getYaw(), getPitch());
    }

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("worldID", worldID.toString());
        jsonObject.put("x", x);
        jsonObject.put("y", y);
        jsonObject.put("z", z);
        jsonObject.put("yaw", yaw);
        jsonObject.put("pitch", pitch);

        return jsonObject.toJSONString();
    }

    public String toNormalString() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("worldID", worldID.toString());
        jsonObject.put("x", (int) x);
        jsonObject.put("y", (int) y);
        jsonObject.put("z", (int) z);
        jsonObject.put("yaw", yaw);
        jsonObject.put("pitch", pitch);

        return jsonObject.toJSONString();
    }
}
