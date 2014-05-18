package info.dejv.octarine.view;

import javafx.scene.Node;

import info.dejv.octarine.model.ModelElement;

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public abstract class AbstractShapeFactory
        implements ViewFactory, ShapeFactory {

    @Override
    public Node createView(ModelElement modelElement) {
        return createShape(modelElement);
    }

}
