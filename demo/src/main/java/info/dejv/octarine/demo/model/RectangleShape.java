package info.dejv.octarine.demo.model;

import javafx.geometry.Rectangle2D;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import info.dejv.octarine.model.AbstractModelElement;
import info.dejv.octarine.model.BasicProperties;
import info.dejv.octarine.model.chunk.DoubleTuple;

@Component
@Scope("prototype")
public class RectangleShape
        extends AbstractModelElement {

    public RectangleShape(Rectangle2D initialCoords) {
        chunks.put(BasicProperties.LOCATION, new DoubleTuple(initialCoords.getMinX(), initialCoords.getMinY()));
        chunks.put(BasicProperties.SIZE, new DoubleTuple(initialCoords.getWidth(), initialCoords.getHeight()));
    }
}
