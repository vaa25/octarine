package app.dejv.octarine.demo.view;

import java.util.Optional;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import app.dejv.impl.octarine.constants.PredefinedChunkTypes;
import app.dejv.impl.octarine.model.chunk.FillChunk;
import app.dejv.impl.octarine.model.chunk.RotationChunk;
import app.dejv.impl.octarine.model.chunk.SizeChunk;
import app.dejv.impl.octarine.model.chunk.StrokeChunk;
import app.dejv.impl.octarine.model.chunk.TranslationChunk;
import app.dejv.impl.octarine.view.AbstractShapeFactory;
import app.dejv.octarine.model.ModelElement;

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
@Configuration
public class RectangleViewFactory
        extends AbstractShapeFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(RectangleViewFactory.class);

    @Override
    public Shape createShape(ModelElement modelElement) {
        final Optional<TranslationChunk> oTranslationChunk = modelElement.getChunk(PredefinedChunkTypes.TRANSLATION, TranslationChunk.class);
        final Optional<SizeChunk> oSizeChunk = modelElement.getChunk(PredefinedChunkTypes.SIZE, SizeChunk.class);
        final Optional<RotationChunk> oRotationChunk = modelElement.getChunk(PredefinedChunkTypes.ROTATION, RotationChunk.class);
        final Optional<StrokeChunk> oStrokeChunk = modelElement.getChunk(PredefinedChunkTypes.STROKE, StrokeChunk.class);
        final Optional<FillChunk> oFillChunk = modelElement.getChunk(PredefinedChunkTypes.FILL, FillChunk.class);

        final Rectangle r = new Rectangle(0, 0, 1, 1);

        if (oTranslationChunk.isPresent()) {
            final TranslationChunk translationChunk = oTranslationChunk.get();

            final Translate translate = new Translate();
            translate.xProperty().bind(translationChunk.xProperty());
            translate.yProperty().bind(translationChunk.yProperty());
            //translate.yProperty().addListener((observable, oldValue, newValue) -> LOGGER.info("X:{} Y:{} ", translate.getX(), translate.getY()));

            r.getTransforms().add(translate);

            if (oRotationChunk.isPresent()) {
                final RotationChunk rotationChunk = oRotationChunk.get();

                final Rotate rotate = new Rotate();
                rotate.angleProperty().bind(rotationChunk.angleProperty());
                //rotate.angleProperty().addListener((observable, oldValue, newValue) -> LOGGER.info("Angle: {} ", rotate.getAngle()));

                rotate.setAxis(Rotate.Z_AXIS);

                r.getTransforms().add(rotate);
            }
        }

        if (oSizeChunk.isPresent()) {
            final SizeChunk sizeChunk = oSizeChunk.get();
            r.widthProperty().bind(sizeChunk.widthProperty());
            r.heightProperty().bind(sizeChunk.heightProperty());
        }

        if (oStrokeChunk.isPresent()) {
            r.setStrokeType(StrokeType.INSIDE);
            r.strokeProperty().bind(oStrokeChunk.get().getPaint().paintProperty());
        }

        if (oFillChunk.isPresent()) {
            r.fillProperty().bind(oFillChunk.get().getPaint().paintProperty());
        }

        return r;
    }
}
