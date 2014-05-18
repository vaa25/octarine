package info.dejv.octarine.view;

import javafx.scene.Node;

import info.dejv.octarine.model.ModelElement;

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public interface ViewFactory {

    Node createView(ModelElement modelElement);
}
