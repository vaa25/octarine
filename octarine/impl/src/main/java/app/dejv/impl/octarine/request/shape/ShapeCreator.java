package app.dejv.impl.octarine.request.shape;

import javafx.scene.shape.Shape;

/**
 *
 * <br/>
 * Author: dejv (www.dejv.info)
 */
@FunctionalInterface
public interface ShapeCreator {

    Shape createAndUpdateShape();
}
