package org.devathon.contest2016.model;

import org.bukkit.Location;
import org.bukkit.Material;

/**
 * Created by lukas on 05.11.16.
 */
public interface BlockModel {

    public Location getCenterBlockLocation();

    public Material getBlockMaterial();

    public void SpawnTask();

}
