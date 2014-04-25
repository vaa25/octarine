package info.dejv.octarine.controller;

import info.dejv.octarine.Octarine;
import info.dejv.octarine.model.ModelElement;
import javafx.scene.Node;

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

    Octarine getOctarine();

    ContainerController getRoot();

    ContainerController getParent();

    void onAdded();

    void onRemoved();

    void addRequestHandler(RequestHandler requestHandler);

    void removeRequestHandler(RequestHandler requestHandler);
}
