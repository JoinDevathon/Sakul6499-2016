package org.devathon.contest2016.model;

import org.bukkit.block.Block;

/**
 * Created by lukas on 05.11.16.
 */
public interface AdvancedBlockModel extends BasicBlockModel {

    boolean isConnectedTo(Block block);
    // TODO

}
