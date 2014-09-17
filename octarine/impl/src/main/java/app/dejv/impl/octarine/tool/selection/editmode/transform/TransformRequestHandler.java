package app.dejv.impl.octarine.tool.selection.editmode.transform;

import static java.util.Objects.requireNonNull;

import javafx.geometry.Point2D;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

import app.dejv.impl.octarine.command.CompoundCommand;
import app.dejv.impl.octarine.model.DefaultChunks;
import app.dejv.impl.octarine.model.chunk.SizeChunk;
import app.dejv.impl.octarine.model.chunk.TransformChunk;
import app.dejv.impl.octarine.request.AbstractRequestHandler;
import app.dejv.impl.octarine.tool.selection.editmode.resize.ResizeCommand;
import app.dejv.impl.octarine.tool.selection.editmode.resize.ResizeRequest;
import app.dejv.impl.octarine.tool.selection.editmode.rotate.RotateRequest;
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

    private final TransformChunk transformChunk;
    private final SizeChunk sizeChunk;


    public TransformRequestHandler(ModelElement model) {
        requireNonNull(model, "model is null");

        this.transformChunk = model.getChunk(DefaultChunks.TRANSFORMATION, TransformChunk.class);
        this.sizeChunk = model.getChunk(DefaultChunks.SIZE, SizeChunk.class);
    }


    @SuppressWarnings("SimplifiableIfStatement")
    @Override
    public boolean supports(Class<? extends Request> request) {
        if (TranslateRequest.class.equals(request)) {
            return supportsTranslate();
        }
        if (ResizeRequest.class.equals(request)) {
            return supportsResize() || supportsScale();
        }
        if (RotateRequest.class.equals(request)) {
            return supportsRotate();
        }
        return false;
    }


    @Override
    public void requestChecked(Request request) {
        // If element supports both Resize and Scale, Resize takes priority...
        if ((request instanceof ResizeRequest) && supportsResize()) {

            final Scale s = ((ResizeRequest) request).getScale();
            ((ResizeRequest) request).setCommand(createResizeCommand(s));
            return;
        }
        final TransformRequest transformRequest = (TransformRequest) request;
        transformRequest.setCommand(new TransformCommand(transformChunk, transformRequest.getTransform()));
    }


    private boolean supportsTranslate() {
        return (transformChunk != null) && transformChunk.getSupportsTranslate();
    }


    private boolean supportsResize() {
        return supportsTranslate() && ((sizeChunk != null) && sizeChunk.getSupportsResize());
    }


    private boolean supportsScale() {
        return supportsTranslate() && ((transformChunk != null) && transformChunk.getSupportsScale());
    }


    private boolean supportsRotate() {
        return (transformChunk != null) && transformChunk.getSupportsRotate();
    }


    private Command createResizeCommand(Scale scale) {
        return new CompoundCommand()
                .add(new TransformCommand(transformChunk, scaleToTranslate(scale, transformChunk.getTransform().getTx(), transformChunk.getTransform().getTy())))
                .add(new ResizeCommand(sizeChunk, scale));
    }


    private Translate scaleToTranslate(Scale scale, double originalX, double originalY) {
        final Point2D p = scale.transform(originalX, originalY);
        return new Translate(p.getX() - originalX, p.getY() - originalY);
    }

}
