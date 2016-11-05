package org.devathon.contest2016.model;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

/**
 * Created by lukas on 05.11.16.
 */
public class TestBasicBlock implements BasicBlockModel {

    private final Location location;

    private boolean spawned;

    public TestBasicBlock(Location location) {
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

        this.spawned = true;
    }

    @Override
    public boolean isSpawned() {
        return spawned;
    }
}
