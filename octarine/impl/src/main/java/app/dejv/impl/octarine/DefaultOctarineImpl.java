package app.dejv.impl.octarine;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;

import app.dejv.impl.octarine.utils.FormattingUtils;
import app.dejv.octarine.Octarine;
import app.dejv.octarine.command.CommandStack;
import app.dejv.octarine.controller.ContainerController;
import app.dejv.octarine.infrastructure.Resources;
import app.dejv.octarine.layer.LayerManager;
import app.dejv.octarine.selection.SelectionManager;
import app.dejv.octarine.tool.EditationListener;
import app.dejv.octarine.tool.Tool;
import app.dejv.octarine.tool.ToolExtension;
import info.dejv.common.ui.ZoomableScrollPane;

public class DefaultOctarineImpl
        implements Octarine {

    private static final String ID_LAYERS = "Layers";
    private static final String ID_FEEDBACK_STATIC = "Feedback_Static";
    private static final String ID_FEEDBACK_DYNAMIC = "Feedback_Dynamic";
    private static final String ID_HANDLES = "Handles";

    private final Map<Class<? extends Tool>, List<ToolExtension>> toolExtensions = new HashMap<>();

    private final List<EditationListener> editationListeners = new ArrayList<>();

    private final CommandStack commandStack;
    private final SelectionManager selectionManager;
    private final LayerManager layerManager;
    private final Resources resources;

    private final ZoomableScrollPane viewer;

    private final Group groupLayers;
    private final Group groupFeedbackStatic;
    private final Group groupFeedbackDynamic;
    private final Group groupHandles;

    private ContainerController rootController;
    private Tool activeTool;

    private long childIdSequence = 0;


    public DefaultOctarineImpl(ZoomableScrollPane viewer, CommandStack commandStack, SelectionManager selectionManager, LayerManager layerManager, Resources resources,
                               Group groupLayers, Group groupFeedbackStatic, Group groupFeedbackDynamic, Group groupHandles) {
        requireNonNull(viewer, "viewer is null");
        requireNonNull(viewer.getScene(), "scene is NULL");
        requireNonNull(commandStack, "commandStack is null");
        requireNonNull(selectionManager, "selectionManager is null");
        requireNonNull(layerManager, "layerManager is null");
        requireNonNull(resources, "resources is null");

        this.viewer = viewer;
        this.commandStack = commandStack;
        this.selectionManager = selectionManager;
        this.layerManager = layerManager;
        this.resources = resources;

        this.groupLayers = prepareGroup(groupLayers, ID_LAYERS);
        this.groupFeedbackStatic = prepareGroup(groupFeedbackStatic, ID_FEEDBACK_STATIC);
        this.groupFeedbackDynamic = prepareGroup(groupFeedbackDynamic, ID_FEEDBACK_DYNAMIC);
        this.groupHandles = prepareGroup(groupHandles, ID_LAYERS);

        FormattingUtils.setZoomFactor(viewer.zoomFactorProperty());
    }


    private Group prepareGroup(Group group, String id) {
        group.setId(id);
        viewer.getContent().add(group);
        return group;
    }


    private void notifyToolDeactivated() {
        if ((activeTool != null) && (toolExtensions.containsKey(activeTool.getClass()))) {
            toolExtensions.get(activeTool.getClass()).stream().forEach((ah) -> ah.toolDeactivated(activeTool));
            activeTool.deactivate();
        }
    }


    @Override
    public ZoomableScrollPane getViewer() {
        return viewer;
    }


    private void notifyToolActivated() {
        if ((activeTool != null) && (toolExtensions.containsKey(activeTool.getClass()))) {
            toolExtensions.get(activeTool.getClass()).stream().forEach((ah) -> ah.toolActivated(activeTool));
            activeTool.activate();
        }
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
    public ContainerController getRootController() {
        return rootController;
    }


    @Override
    public void setRootController(final ContainerController root) {
        requireNonNull(root, "root is null");

        if (root.getOctarine() != this) {
            throw new IllegalArgumentException("Given root controller belongs to different Octarine instance");
        }

        this.rootController = root;

        this.rootController.onAdded();
    }


    @Override
    public long getNextChildSequence() {
        return childIdSequence++;
    }


    @Override
    public void addActionHandler(Class<? extends Tool> toolClass, ToolExtension handler) {
        if (!toolExtensions.containsKey(toolClass)) {
            toolExtensions.put(toolClass, new ArrayList<>());
        }
        toolExtensions.get(toolClass).add(handler);

    }


    @Override
    public void removeActionHandler(Class<? extends Tool> toolClass, ToolExtension handler) {
        if (toolExtensions.containsKey(toolClass)) {
            toolExtensions.get(toolClass).remove(handler);
        }
    }


    public List<EditationListener> getEditationListeners() {
        return editationListeners;
    }


    @Override
    public CommandStack getCommandStack() {
        return commandStack;
    }


    @Override
    public SelectionManager getSelectionManager() {
        return selectionManager;
    }


    @Override
    public LayerManager getLayerManager() {
        return layerManager;
    }


    public Resources getResources() {
        return resources;
    }


    @Override
    public ObservableList<Node> getGroupLayers() {
        return groupLayers.getChildren();
    }


    @Override
    public ObservableList<Node> getGroupFeedbackStatic() {
        return groupFeedbackStatic.getChildren();
    }


    @Override
    public ObservableList<Node> getGroupFeedbackDynamic() {
        return groupFeedbackDynamic.getChildren();
    }


    @Override
    public ObservableList<Node> getGroupHandles() {
        return groupHandles.getChildren();
    }
}
