package info.dejv.octarine.request.shape;

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
