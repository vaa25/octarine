package info.dejv.octarine.demo.view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import info.dejv.octarine.model.BasicProperties;
import info.dejv.octarine.model.ModelElement;
import info.dejv.octarine.model.chunk.DoubleTuple;
import info.dejv.octarine.view.AbstractShapeFactory;

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
@Component
public class CanvasViewFactory extends AbstractShapeFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(CanvasViewFactory.class);

    @Override
    public Shape createShape(ModelElement modelElement) {
        LOGGER.info("----- CREATING CANVAS ----- ");
        DoubleTuple size = modelElement.getChunk(BasicProperties.SIZE, DoubleTuple.class);
        Rectangle r = new Rectangle();

        r.setFill(Color.WHITE);
        r.setStroke(Color.BLACK);
        r.setStrokeType(StrokeType.INSIDE);

        r.setLayoutX(0.0d);
        r.setLayoutY(0.0d);
        r.widthProperty().bind(size.getX());
        r.heightProperty().bind(size.getY());
        return r;
    }
}
