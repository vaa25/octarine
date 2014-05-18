package info.dejv.octarine.view;

import javafx.scene.shape.Shape;

import info.dejv.octarine.model.ModelElement;

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public interface ShapeFactory {

    Shape createShape(ModelElement modelElement);
}
