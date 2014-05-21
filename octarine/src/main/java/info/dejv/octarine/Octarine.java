/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.dejv.octarine;

import javafx.collections.ObservableList;
import javafx.scene.Node;

import info.dejv.common.ui.ZoomableScrollPane;
import info.dejv.octarine.tool.ToolExtension;
import info.dejv.octarine.command.CommandStack;
import info.dejv.octarine.controller.ContainerController;
import info.dejv.octarine.layer.LayerManager;
import info.dejv.octarine.selection.SelectionManager;
import info.dejv.octarine.tool.Tool;

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


    CommandStack getCommandStack();

    SelectionManager getSelectionManager();

    LayerManager getLayerManager();


    ObservableList<Node> getFeedback();

    ObservableList<Node> getActiveFeedback();

    ObservableList<Node> getHandle();
}
