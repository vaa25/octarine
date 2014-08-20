package app.dejv.octarine.layer;

import javafx.collections.ObservableList;
import javafx.scene.Node;

import info.dejv.common.ui.ZoomableScrollPane;

/**
 *
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public interface LayerManager {

    void addLayersToView(ZoomableScrollPane view);

    void addLayer(String layerId);

    void removeLayer(String layerId);

    ObservableList<Node> getStaticFeedbackLayer();

    ObservableList<Node> getDynamicFeedbackLayer();

    ObservableList<Node> getHandlesLayer();

    ObservableList<Node> getAllContentLayers();

    ObservableList<Node> getContentLayer(String layerId);

    ObservableList<Node> getCurrentContentLayer();

    String getCurrentContentLayerId();
}
