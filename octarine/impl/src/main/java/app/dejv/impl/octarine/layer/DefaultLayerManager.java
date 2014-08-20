package app.dejv.impl.octarine.layer;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;

import app.dejv.octarine.layer.LayerManager;
import info.dejv.common.ui.ZoomableScrollPane;
import info.dejv.common.ui.logic.ZoomableScrollPaneView;

/**
 *
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class DefaultLayerManager
        implements LayerManager {

    private static final String ID_LAYERS = "Layers";
    private static final String ID_FEEDBACK_STATIC = "Feedback_Static";
    private static final String ID_FEEDBACK_DYNAMIC = "Feedback_Dynamic";
    private static final String ID_HANDLES = "Handles";

    public static final String LAYER_DEFAULT = "Default";

    private final Group groupLayers = new Group();
    private final Group groupFeedbackStatic = new Group();
    private final Group groupFeedbackDynamic = new Group();
    private final Group groupHandles = new Group();

    private final Map<String, Group> layersMap = new HashMap<>();
    private final ObservableList<Node> layers = groupLayers.getChildren();

    private boolean isAdded = false;

    public DefaultLayerManager() {
        addLayer(LAYER_DEFAULT);
    }

    public void addLayersToView(ZoomableScrollPane view) {
        if (!isAdded) {
            prepareGroup(view, groupLayers, ID_LAYERS);
            prepareGroup(view, groupFeedbackStatic, ID_FEEDBACK_STATIC);
            prepareGroup(view, groupFeedbackDynamic, ID_FEEDBACK_DYNAMIC);
            prepareGroup(view, groupHandles, ID_HANDLES);

            isAdded = true;
        }
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
    public ObservableList<Node> getStaticFeedbackLayer() {
        return groupFeedbackStatic.getChildren();
    }


    @Override
    public ObservableList<Node> getDynamicFeedbackLayer() {
        return groupFeedbackDynamic.getChildren();
    }


    @Override
    public ObservableList<Node> getHandlesLayer() {
        return groupHandles.getChildren();
    }


    @Override
    public ObservableList<Node> getAllContentLayers() {
        return layers;
    }


    @Override
    public ObservableList<Node> getContentLayer(String layerId) {
        return layersMap.get(layerId).getChildren();
    }


    @Override
    public ObservableList<Node> getCurrentContentLayer() {
        return getContentLayer(LAYER_DEFAULT);
    }


    @Override
    public String getCurrentContentLayerId() {
        return LAYER_DEFAULT;
    }

    private void prepareGroup(ZoomableScrollPaneView viewer, Group group, String id) {
        group.setId(id);
        viewer.getContent().add(group);
    }
}
