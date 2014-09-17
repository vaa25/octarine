package app.dejv.octarine.demo.view;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;
import javafx.scene.transform.Transform;

import org.springframework.context.annotation.Configuration;

import app.dejv.impl.octarine.model.DefaultChunks;
import app.dejv.impl.octarine.model.chunk.FillChunk;
import app.dejv.impl.octarine.model.chunk.SizeChunk;
import app.dejv.impl.octarine.model.chunk.StrokeChunk;
import app.dejv.impl.octarine.model.chunk.TransformChunk;
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
        final TransformChunk transformChunk = modelElement.getChunk(DefaultChunks.TRANSFORMATION, TransformChunk.class);
        final SizeChunk sizeChunk = modelElement.getChunk(DefaultChunks.SIZE, SizeChunk.class);
        final StrokeChunk strokeChunk = modelElement.getChunk(DefaultChunks.STROKE, StrokeChunk.class);
        final FillChunk fillChunk = modelElement.getChunk(DefaultChunks.FILL, FillChunk.class);

        requireNonNull(transformChunk, "transform chunk not present in model");
        requireNonNull(sizeChunk, "size chunk not present in model");
        requireNonNull(strokeChunk, "stroke chunk not present in model");
        requireNonNull(fillChunk, "fill chunk not present in model");

        final Rectangle r = new Rectangle(0, 0, 1, 1);
        r.widthProperty().bind(sizeChunk.widthProperty());
        r.heightProperty().bind(sizeChunk.heightProperty());

        final ObservableList<Transform> transforms = r.getTransforms();
        transforms.add(transformChunk.getTransform());

        transformChunk.transformProperty().addListener((observable, oldValue, newValue) -> {
            transforms.remove(oldValue);
            transforms.add(newValue);
        });

        r.setStrokeType(StrokeType.INSIDE);
        r.strokeProperty().bind(strokeChunk.getPaint().paintProperty());
        r.fillProperty().bind(fillChunk.getPaint().paintProperty());

        return r;
    }
}
