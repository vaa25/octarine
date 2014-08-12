/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.dejv.octarine;

import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.Node;

import app.dejv.octarine.command.CommandStack;
import app.dejv.octarine.controller.ContainerController;
import app.dejv.octarine.layer.LayerManager;
import app.dejv.octarine.selection.SelectionManager;
import app.dejv.octarine.tool.EditationListener;
import app.dejv.octarine.tool.Tool;
import app.dejv.octarine.tool.ToolExtension;
import info.dejv.common.ui.ZoomableScrollPane;

/**
 *
 * @author dejv
 */
public interface Octarine {

    void setActiveTool(Tool tool);

    Tool getActiveTool();


    void setRootController(ContainerController root);

    ContainerController getRootController();

    ZoomableScrollPane getViewer();

    long getNextChildSequence();

    void addActionHandler(Class<? extends Tool> toolClass, ToolExtension handler);

    void removeActionHandler(Class<? extends Tool> toolClass, ToolExtension handler);

    List<EditationListener> getEditationListeners();

    CommandStack getCommandStack();

    SelectionManager getSelectionManager();

    LayerManager getLayerManager();

    ObservableList<Node> getFeedback();

    ObservableList<Node> getActiveFeedback();

    ObservableList<Node> getHandle();
}
