package info.dejv.octarine.layer;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;

/**
 *
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class LayerManagerImpl
        implements LayerManager {

    public static final String LAYER_DEFAULT = "Default";

    private final Map<String, Group> layersMap = new HashMap<>();
    private final ObservableList<Node> layers;

    public LayerManagerImpl(ObservableList<Node> layersGroup) {
        this.layers = layersGroup;
        addLayer(LAYER_DEFAULT);
    }


    @Override
    public final void addLayer(String layerId) {
        Objects.requireNonNull(layerId, "layerId is NULL");

        if (!layersMap.containsKey(layerId)) {
            Group newLayer = new Group();
            newLayer.setId("Layer " + layerId);
            layersMap.put(layerId, newLayer);
            layers.add(newLayer);
        }
    }

    @Override
    public void removeLayer(String layerId) {
        if (LAYER_DEFAULT.equals(layerId)) {
            return;
        }

        if (layersMap.containsKey(layerId)) {
            layers.remove(layersMap.get(layerId));
            layersMap.remove(layerId);
        }
    }


    @Override
    public ObservableList<Node> getAllLayers() {
        return layers;
    }


    @Override
    public ObservableList<Node> getLayer(String layerId) {
        return layersMap.get(layerId).getChildren();
    }


    @Override
    public ObservableList<Node> getCurrentLayer() {
        return getLayer(LAYER_DEFAULT);
    }


    @Override
    public String getCurrentLayerId() {
        return LAYER_DEFAULT;
    }
}
