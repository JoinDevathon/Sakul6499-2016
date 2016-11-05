package org.devathon.contest2016.model;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

/**
 * Created by lukas on 05.11.16.
 */
public class TestBlock implements BlockModel {

    private final Location location;

    public TestBlock(Location location) {
        this.location = location;
    }

    @Override
    public Location getCenterBlockLocation() {
        return location;
    }

    @Override
    public Material getBlockMaterial() {
        return Material.CAKE_BLOCK;
    }

    @Override
    public void SpawnTask() {
        location.getBlock().setType(getBlockMaterial());
    }
}
