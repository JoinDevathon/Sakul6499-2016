package org.devathon.contest2016.model;

import org.bukkit.Location;
import org.bukkit.Material;

import java.util.UUID;

/**
 * Created by lukas on 05.11.16.
 */
public interface BasicBlockModel {

    UUID modelID = UUID.randomUUID();

    default UUID getModelID() {
        return modelID;
    }

    Location getCenterBlockLocation();

    Material getBlockMaterial();

    void SpawnTask();

    boolean isSpawned();

}
