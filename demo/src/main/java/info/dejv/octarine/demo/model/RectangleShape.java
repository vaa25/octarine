package info.dejv.octarine.demo.model;

import info.dejv.octarine.model.AbstractModelElement;
import info.dejv.octarine.model.chunk.Coords2D;
import info.dejv.octarine.model.chunk.Dimension2D;
import javafx.geometry.Rectangle2D;

public class RectangleShape
        extends AbstractModelElement {

    public RectangleShape() {
    }

    public RectangleShape(Rectangle2D initialCoords) {
        chunks.put("Size", new Dimension2D(initialCoords.getWidth(), initialCoords.getHeight()));
        chunks.put("Location", new Coords2D(initialCoords.getMinX(), initialCoords.getMinY()));
    }
}
