/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.dejv.octarine;

import info.dejv.common.ui.ZoomableScrollPane;
import info.dejv.octarine.actionhandler.ActionHandler;
import info.dejv.octarine.command.CommandStack;
import info.dejv.octarine.controller.Controller;
import info.dejv.octarine.layer.LayerManager;
import info.dejv.octarine.selection.SelectionManager;
import info.dejv.octarine.tool.Tool;
import javafx.collections.ObservableList;
import javafx.scene.Node;

/**
 *
 * @author dejv
 */
public interface Octarine {

    void setActiveTool(Tool tool);

    Tool getActiveTool();


    void setRootController(Controller root);

    Controller getRootController();

    ZoomableScrollPane getViewer();

    long getNextChildSequence();


    void addActionHandler(Class<? extends Tool> toolClass, ActionHandler handler);

    void removeActionHandler(Class<? extends Tool> toolClass, ActionHandler handler);


    CommandStack getCommandStack();

    SelectionManager getSelectionManager();

    LayerManager getLayerManager();


    ObservableList<Node> getFeedback();

    ObservableList<Node> getActiveFeedback();

    ObservableList<Node> getHandle();
}
