package app.dejv.octarine.demo.model;

import javafx.scene.paint.Color;

import org.springframework.stereotype.Component;

import app.dejv.impl.octarine.model.AbstractContainerModelElement;
import app.dejv.impl.octarine.model.DefaultChunks;
import app.dejv.impl.octarine.model.chunk.FillChunk;
import app.dejv.impl.octarine.model.chunk.PaintChunk;
import app.dejv.impl.octarine.model.chunk.SizeChunk;
import app.dejv.impl.octarine.model.chunk.StrokeChunk;
import app.dejv.impl.octarine.model.chunk.TransformChunk;


@Component
public class ShapeContainer
        extends AbstractContainerModelElement {


    public ShapeContainer() {
        chunks.put(DefaultChunks.SIZE, new SizeChunk()
                .setWidth(700d)
                .setHeight(700d)
                .setSupportsResize(false));

        chunks.put(DefaultChunks.TRANSFORMATION, new TransformChunk());

        chunks.put(DefaultChunks.FILL, new FillChunk(new PaintChunk()
        .setPaint(Color.WHITE)));

        chunks.put(DefaultChunks.STROKE, new StrokeChunk(new PaintChunk()
        .setPaint(Color.BLACK)));
    }

}
