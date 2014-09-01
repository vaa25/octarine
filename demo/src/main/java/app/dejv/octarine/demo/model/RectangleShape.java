package app.dejv.octarine.demo.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Rectangle2D;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import app.dejv.impl.octarine.model.AbstractModelElement;
import app.dejv.impl.octarine.model.BasicProperties;
import app.dejv.impl.octarine.model.chunk.DoubleTuple;


@Component
@Scope("prototype")
public class RectangleShape
        extends AbstractModelElement {

    public RectangleShape(Rectangle2D initialCoords) {
        chunks.put(BasicProperties.LOCATION, new DoubleTuple(initialCoords.getMinX(), initialCoords.getMinY()));
        chunks.put(BasicProperties.SIZE, new DoubleTuple(initialCoords.getWidth(), initialCoords.getHeight()));
        chunks.put(BasicProperties.ROTATION, new SimpleDoubleProperty(0));
    }
}
