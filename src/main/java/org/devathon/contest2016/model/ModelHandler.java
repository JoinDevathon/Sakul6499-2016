package org.devathon.contest2016.model;

import org.bukkit.Location;

import java.util.LinkedList;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by lukas on 05.11.16.
 */
public final class ModelHandler {

    private static ModelHandler INSTANCE;
    static {
        INSTANCE = new ModelHandler();
    }

    public static ModelHandler GetInstance() {
        return INSTANCE;
    }

    private final LinkedList<BlockModel> modelList;

    private ModelHandler() {
        modelList = new LinkedList<>();
    }

    public boolean register(BasicBlockModel basicBlockModel) {
        if(modelList.stream().anyMatch(Model -> Model.getModelID().equals(basicBlockModel.getModelID()))) {
            return false;
        }

        modelList.push(new BlockModel(basicBlockModel));
        return true;
    }

    public BlockModel get(UUID modelID) {
        return modelList.stream().filter(Model -> Model.getModelID().equals(modelID)).findFirst().orElse(null);
    }

    // TODO: normalize
    public BlockModel get(Location location) {
        return modelList.stream().filter(Model -> Model.getCenterBlockLocation().equals(location)).findFirst().orElse(null);
    }
}
