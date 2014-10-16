package app.dejv.octarine.demo.model;

import javafx.scene.paint.Color;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import app.dejv.impl.octarine.model.AbstractModelElement;
import app.dejv.impl.octarine.model.DefaultChunks;
import app.dejv.impl.octarine.model.chunk.FillChunk;
import app.dejv.impl.octarine.model.chunk.PaintChunk;
import app.dejv.impl.octarine.model.chunk.RotationChunk;
import app.dejv.impl.octarine.model.chunk.SizeChunk;
import app.dejv.impl.octarine.model.chunk.StrokeChunk;
import app.dejv.impl.octarine.model.chunk.TranslationChunk;


@Component
@Scope("prototype")
public class RectangleShape
        extends AbstractModelElement {

    public RectangleShape(double x, double y, double w, double h) {

        chunks.put(DefaultChunks.TRANSLATION, new TranslationChunk()
                .setX(x)
                .setY(y)
                .setSupportsTranslate(true));

        chunks.put(DefaultChunks.SIZE, new SizeChunk()
                .setWidth(w)
                .setHeight(h)
                .setSupportsResize(true));

        chunks.put(DefaultChunks.ROTATION, new RotationChunk()
                .setAngle(0)
                .setPivotX(0)
                .setPivotY(0)
                .setSupportsRotate(true));

        chunks.put(DefaultChunks.FILL, new FillChunk(new PaintChunk()
                .setPaint(Color.BLANCHEDALMOND)));

        chunks.put(DefaultChunks.STROKE, new StrokeChunk(new PaintChunk()));

    }
}
