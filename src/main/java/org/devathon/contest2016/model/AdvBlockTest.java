package org.devathon.contest2016.model;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

/**
 * Created by lukas on 05.11.16.
 */
public class AdvBlockTest implements AdvancedBlockModel {

    private final Location location;

    private boolean spawned;

    public AdvBlockTest(Location location) {
        this.location = location;
    }

    @Override
    public boolean isConnectedTo(Block block) {
        return false;
    }

    @Override
    public Location getCenterBlockLocation() {
        return location;
    }

    @Override
    public Material getBlockMaterial() {
        return Material.BONE_BLOCK;
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
