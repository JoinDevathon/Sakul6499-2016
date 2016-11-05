package org.devathon.contest2016.model;

import org.bukkit.Location;
import org.bukkit.Material;

import java.util.UUID;

/**
 * Created by lukas on 05.11.16.
 */
class BlockModel {

    private final boolean isAdvanced;
    private final BasicBlockModel basicBlockModel;

    public BlockModel(BasicBlockModel basicBlockModel) {
        this.isAdvanced = basicBlockModel.getClass().isAssignableFrom(AdvancedBlockModel.class);
        this.basicBlockModel = basicBlockModel;

        System.out.println("DEBUG: isAdvanced -> " + isAdvanced);
    }

    public boolean isAdvanced() {
        return isAdvanced;
    }

    public BasicBlockModel getBasicBlockModel() {
        return basicBlockModel;
    }

    public UUID getModelID() {
        return basicBlockModel.getModelID();
    }

    public Location getCenterBlockLocation() {
        return basicBlockModel.getCenterBlockLocation();
    }

    public Material getBlockMaterial() {
        return basicBlockModel.getBlockMaterial();
    }
}
