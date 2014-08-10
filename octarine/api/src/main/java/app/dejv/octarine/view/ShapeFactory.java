package app.dejv.octarine.view;

import javafx.scene.shape.Shape;

import app.dejv.octarine.model.ModelElement;


/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public interface ShapeFactory {

    Shape createShape(ModelElement modelElement);
}
