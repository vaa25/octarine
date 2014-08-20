package app.dejv.impl.octarine;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.Group;

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

    private final Map<Class<? extends Tool>, List<ToolExtension>> toolExtensions = new HashMap<>();

    private final List<EditationListener> editationListeners = new ArrayList<>();

    private final CommandStack commandStack;
    private final SelectionManager selectionManager;
    private final LayerManager layerManager;
    private final Resources resources;

    private final ZoomableScrollPane view;

    private ContainerController rootController;
    private Tool activeTool;

    private long childIdSequence = 0;


    public DefaultOctarineImpl(ZoomableScrollPane view, CommandStack commandStack, SelectionManager selectionManager, LayerManager layerManager, Resources resources) {
        requireNonNull(view, "view is null");
        requireNonNull(view.getScene(), "scene is NULL");
        requireNonNull(commandStack, "commandStack is null");
        requireNonNull(selectionManager, "selectionManager is null");
        requireNonNull(layerManager, "layerManager is null");
        requireNonNull(resources, "resources is null");

        this.view = view;
        this.commandStack = commandStack;
        this.selectionManager = selectionManager;
        this.layerManager = layerManager;
        this.resources = resources;

        this.layerManager.addLayersToView(view);
        FormattingUtils.setZoomFactor(view.zoomFactorProperty());
    }


    private Group prepareGroup(Group group, String id) {
        group.setId(id);
        view.getContent().add(group);
        return group;
    }


    private void notifyToolDeactivated() {
        if ((activeTool != null) && (toolExtensions.containsKey(activeTool.getClass()))) {
            toolExtensions.get(activeTool.getClass()).stream().forEach((ah) -> ah.toolDeactivated(activeTool));
            activeTool.deactivate();
        }
    }


    @Override
    public ZoomableScrollPane getView() {
        return view;
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
}
