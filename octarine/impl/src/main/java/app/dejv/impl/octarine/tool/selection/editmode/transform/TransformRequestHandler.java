package app.dejv.impl.octarine.tool.selection.editmode.transform;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import javafx.geometry.Point2D;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import app.dejv.impl.octarine.command.CompoundCommand;
import app.dejv.impl.octarine.constants.PredefinedChunkTypes;
import app.dejv.impl.octarine.model.chunk.RotationChunk;
import app.dejv.impl.octarine.model.chunk.SizeChunk;
import app.dejv.impl.octarine.model.chunk.TranslationChunk;
import app.dejv.impl.octarine.request.AbstractRequestHandler;
import app.dejv.impl.octarine.tool.selection.editmode.resize.ResizeCommand;
import app.dejv.impl.octarine.tool.selection.editmode.resize.ResizeRequest;
import app.dejv.impl.octarine.tool.selection.editmode.rotate.RotateCommand;
import app.dejv.impl.octarine.tool.selection.editmode.rotate.RotateRequest;
import app.dejv.impl.octarine.tool.selection.editmode.rotate.RotationPivotRequest;
import app.dejv.impl.octarine.tool.selection.editmode.rotate.RotationPivotResetRequest;
import app.dejv.impl.octarine.tool.selection.editmode.translate.TranslateCommand;
import app.dejv.impl.octarine.tool.selection.editmode.translate.TranslateRequest;
import app.dejv.octarine.command.Command;
import app.dejv.octarine.model.ModelElement;
import app.dejv.octarine.request.Request;

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class TransformRequestHandler
        extends AbstractRequestHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransformRequestHandler.class);

    private final Optional<TranslationChunk> oTranslationChunk;
    private final Optional<SizeChunk> oSizeChunk;
    private final Optional<RotationChunk> oRotationChunk;


    public TransformRequestHandler(ModelElement model) {
        requireNonNull(model, "model is null");

        this.oTranslationChunk = model.getChunk(PredefinedChunkTypes.TRANSLATION, TranslationChunk.class);
        this.oSizeChunk = model.getChunk(PredefinedChunkTypes.SIZE, SizeChunk.class);
        this.oRotationChunk = model.getChunk(PredefinedChunkTypes.ROTATION, RotationChunk.class);
    }


    @SuppressWarnings("SimplifiableIfStatement")
    @Override
    public boolean supports(Class<? extends Request> request) {
        if (TranslateRequest.class.equals(request)) {
            return supportsTranslate();
        }
        if (ResizeRequest.class.equals(request)) {
            return supportsResize();
        }
        if (RotateRequest.class.equals(request)) {
            return supportsRotate();
        }
        if (RotationPivotRequest.class.equals(request)) {
            return supportsRotate();
        }
        if (RotationPivotResetRequest.class.equals(request)) {
            return supportsRotate();
        }
        return false;
    }


    @Override
    public void requestChecked(Request request) {

        if ((request instanceof ResizeRequest) && supportsResize()) {

            final Scale s = ((ResizeRequest) request).getScale();
            ((ResizeRequest) request).setCommand(createResizeCommand(s));
            return;
        }

        if ((request instanceof RotationPivotRequest) && supportsRotate()) {

            final Point2D pivot = ((RotationPivotRequest) request).getPivot();
            oRotationChunk.get().setPivotX(pivot.getX());
            oRotationChunk.get().setPivotY(pivot.getY());
            return;
        }

        if ((request instanceof RotationPivotResetRequest) && supportsRotate()) {

            System.out.println("Reset");
            oRotationChunk.get().setPivotX(Double.MIN_VALUE);
            oRotationChunk.get().setPivotY(Double.MIN_VALUE);
            return;
        }

        if ((request instanceof RotateRequest) && supportsResize()) {

            final Rotate r = ((RotateRequest) request).getRotate();
            ((RotateRequest) request).setCommand(createRotateCommand(r));
            return;
        }

        if ((request instanceof TranslateRequest) && supportsTranslate()) {
            final TranslateRequest translateRequest = (TranslateRequest) request;
            translateRequest.setCommand(new TranslateCommand(oTranslationChunk.get(), translateRequest.getTranslate()));
        }
    }


    private boolean supportsTranslate() {
        return (oTranslationChunk.isPresent()) && oTranslationChunk.get().getSupportsTranslate();
    }


    private boolean supportsResize() {
        return supportsTranslate() && ((oSizeChunk.isPresent()) && oSizeChunk.get().getSupportsResize());
    }


    private boolean supportsRotate() {
        return (oRotationChunk.isPresent()) && oRotationChunk.get().getSupportsRotate();
    }


    private Command createResizeCommand(Scale scale) {
        return new CompoundCommand()
                .add(new TranslateCommand(oTranslationChunk.get(), pivotedTransformToTranslate(scale, oTranslationChunk.get().getX(), oTranslationChunk.get().getY())))
                .add(new ResizeCommand(oSizeChunk.get(), scale));
    }


    private Command createRotateCommand(Rotate rotate) {
        return new CompoundCommand()
                .add(new TranslateCommand(oTranslationChunk.get(), pivotedTransformToTranslate(rotate, oTranslationChunk.get().getX(), oTranslationChunk.get().getY())))
                .add(new RotateCommand(oRotationChunk.get(), rotate));
    }


    private Translate pivotedTransformToTranslate(Transform pivotedTransform, double originalX, double originalY) {
        final Transform pt = getFixedTransform(pivotedTransform, originalX, originalY);
        final Point2D p = pt.transform(0, 0);

        return new Translate(p.getX(), p.getY());
    }


    private Transform getFixedTransform(Transform originalTransform, double originalX, double originalY) {
        if (originalTransform instanceof Rotate) {
            final Rotate pt = (Rotate) originalTransform.clone();
            pt.setPivotX(pt.getPivotX() - originalX);
            pt.setPivotY(pt.getPivotY() - originalY);
            return pt;
        }
        if (originalTransform instanceof Scale) {
            final Scale pt = (Scale) originalTransform.clone();
            pt.setPivotX(pt.getPivotX() - originalX);
            pt.setPivotY(pt.getPivotY() - originalY);
            return pt;
        }
        throw new IllegalArgumentException("Unsupported transform type");
    }

}
