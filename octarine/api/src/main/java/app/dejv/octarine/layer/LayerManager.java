package app.dejv.octarine.layer;

import javafx.collections.ObservableList;
import javafx.scene.Node;

/**
 *
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public interface LayerManager {

    void addLayer(String layerId);

    void removeLayer(String layerId);

    ObservableList<Node> getAllLayers();

    ObservableList<Node> getLayer(String layerId);

    ObservableList<Node> getCurrentLayer();

    String getCurrentLayerId();
}
