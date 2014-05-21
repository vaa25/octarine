package info.dejv.octarine.controller;

import javafx.scene.Node;

import info.dejv.octarine.Octarine;
import info.dejv.octarine.tool.ToolExtension;
import info.dejv.octarine.model.ModelElement;
import info.dejv.octarine.view.ViewFactory;

/**
 * Controller interface<br/>
 * Controller serves as the mediator between the user (resp. the tools) and the model
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public interface Controller
        extends RequestHandler {

    String getId();

    ModelElement getModel();

    Node getView();

    ViewFactory getViewFactory();

    void setViewFactory(ViewFactory viewFactory);

    Octarine getOctarine();

    ContainerController getRoot();

    ContainerController getParent();

    void onAdded();

    void onRemoved();

    void addRequestHandler(RequestHandler requestHandler);

    void removeRequestHandler(RequestHandler requestHandler);

    void addToolExtension(ToolExtension actionHandler);

    void removeToolExtension(ToolExtension actionHandler);


}
