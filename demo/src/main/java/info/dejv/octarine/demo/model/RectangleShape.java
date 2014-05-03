package info.dejv.octarine.demo.model;

import info.dejv.octarine.model.AbstractModelElement;
import info.dejv.octarine.model.BasicProperties;
import info.dejv.octarine.model.chunk.DoubleTuple;
import javafx.geometry.Rectangle2D;

public class RectangleShape
        extends AbstractModelElement {

    public RectangleShape() {
    }

    public RectangleShape(Rectangle2D initialCoords) {
        chunks.put(BasicProperties.LOCATION, new DoubleTuple(initialCoords.getMinX(), initialCoords.getMinY()));
        chunks.put(BasicProperties.SIZE, new DoubleTuple(initialCoords.getWidth(), initialCoords.getHeight()));
    }
}
