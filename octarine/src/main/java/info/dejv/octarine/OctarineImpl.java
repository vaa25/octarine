package info.dejv.octarine;

import info.dejv.common.ui.ZoomableScrollPane;
import info.dejv.octarine.actionhandler.ActionHandler;
import info.dejv.octarine.command.CommandStack;
import info.dejv.octarine.controller.Controller;
import info.dejv.octarine.layer.LayerManager;
import info.dejv.octarine.layer.LayerManagerImpl;
import info.dejv.octarine.selection.SelectionManager;
import info.dejv.octarine.selection.SelectionManagerImpl;
import info.dejv.octarine.tool.Tool;
import info.dejv.octarine.utils.FormattingUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;

public class OctarineImpl
        implements Octarine {

    private static final String ID_LAYERS = "Layers";
    private static final String ID_FEEDBACK = "Feedback";
    private static final String ID_ACTIVEFEEDBACK = "ActiveFeedback";
    private static final String ID_HANDLES = "Handles";

    private final Map<Class<? extends Tool>, List<ActionHandler>> actionHandlers = new HashMap<>();
    private final CommandStack commandStack = new CommandStack();
    private final SelectionManager selectionManager = new SelectionManagerImpl();
    private final LayerManager layerManager;

    private final ZoomableScrollPane viewer;
    private Controller rootController;
    private Tool activeTool;

    private final Group layersGroup = new Group();
    private final Group feedbackGroup = new Group();
    private final Group activeFeedbackGroup = new Group();
    private final Group handlesGroup = new Group();

    private long childIdSequence = 0;

    public OctarineImpl(final ZoomableScrollPane viewer) {
        this.viewer = viewer;
        this.layerManager = new LayerManagerImpl(layersGroup.getChildren());

        FormattingUtils.setZoomFactor(viewer.zoomFactorProperty());

        layersGroup.setId(ID_LAYERS);
        feedbackGroup.setId(ID_FEEDBACK);
        activeFeedbackGroup.setId(ID_ACTIVEFEEDBACK);
        handlesGroup.setId(ID_HANDLES);
        viewer.getContent().add(layersGroup);
        viewer.getContent().add(feedbackGroup);
        viewer.getContent().add(activeFeedbackGroup);
        viewer.getContent().add(handlesGroup);
    }

    @Override
    public ZoomableScrollPane getViewer() {
        return viewer;
    }


    @Override
    public void setActiveTool(final Tool tool) {
        notifyToolDeactivated();
        activeTool = tool;
        notifyToolActivated();
    }


    @Override
    public Tool getActiveTool() {
        return activeTool;
    }


    @Override
    public Controller getRootController() {
        return rootController;
    }


    @Override
    public void setRootController(final Controller root) {
        this.rootController = root;
        this.rootController.onAdded();
    }


    @Override
    public long getNextChildSequence() {
        return childIdSequence++;
    }


    @Override
    public <T extends Tool> void addActionHandler(Class<? extends Tool> toolClass, ActionHandler<T> handler) {
        if (!actionHandlers.containsKey(toolClass)) {
            actionHandlers.put(toolClass, new ArrayList<>());
        }
        actionHandlers.get(toolClass).add(handler);

    }


    @Override
    public <T extends Tool> void removeActionHandler(Class<? extends Tool> toolClass, ActionHandler<T> handler) {
        if (actionHandlers.containsKey(toolClass)) {
            actionHandlers.get(toolClass).remove(handler);
        }
    }


    @Override
    public CommandStack getCommandStack() {
        return commandStack;
    }


    private void notifyToolDeactivated() {
        if ((activeTool != null) && (actionHandlers.containsKey(activeTool.getClass()))) {
            actionHandlers.get(activeTool.getClass()).stream().forEach((ah) -> {
                ah.toolDeactivated(activeTool);
            });
            activeTool.deactivate();
        }
    }


    private void notifyToolActivated() {
        if ((activeTool != null) && (actionHandlers.containsKey(activeTool.getClass()))) {
            actionHandlers.get(activeTool.getClass()).stream().forEach((ah) -> {
                ah.toolActivated(activeTool);
            });
            activeTool.activate();
        }
    }


    @Override
    public SelectionManager getSelectionManager() {
        return selectionManager;
    }


    @Override
    public LayerManager getLayerManager() {
        return layerManager;
    }


    @Override
    public ObservableList<Node> getFeedback() {
        return feedbackGroup.getChildren();
    }

    @Override
    public ObservableList<Node> getActiveFeedback() {
        return feedbackGroup.getChildren();
    }

    @Override
    public ObservableList<Node> getHandle() {
        return handlesGroup.getChildren();
    }
}
