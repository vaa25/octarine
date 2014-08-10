package app.dejv.impl.octarine.view;

import javafx.scene.Node;

import app.dejv.octarine.model.ModelElement;
import app.dejv.octarine.view.ShapeFactory;
import app.dejv.octarine.view.ViewFactory;


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
