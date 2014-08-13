package app.dejv.octarine.demo.view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;

import org.springframework.context.annotation.Configuration;

import app.dejv.impl.octarine.model.BasicProperties;
import app.dejv.impl.octarine.model.chunk.DoubleTuple;
import app.dejv.impl.octarine.view.AbstractShapeFactory;
import app.dejv.octarine.model.ModelElement;

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
@Configuration
public class RectangleViewFactory
        extends AbstractShapeFactory {

    @Override
    public Shape createShape(ModelElement modelElement) {
        DoubleTuple location = modelElement.getChunk(BasicProperties.LOCATION, DoubleTuple.class);
        DoubleTuple size = modelElement.getChunk(BasicProperties.SIZE, DoubleTuple.class);

        Rectangle r = new Rectangle();
        r.setFill(Color.ALICEBLUE);
        r.setStroke(Color.BLACK);
        r.setStrokeType(StrokeType.INSIDE);

        r.widthProperty().bind(size.getX());
        r.heightProperty().bind(size.getY());

        r.translateXProperty().bind(location.getX());
        r.translateYProperty().bind(location.getY());
        return r;
    }
}
