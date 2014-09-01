package app.dejv.impl.octarine.tool.selection.editmode.resize;

import static java.util.Objects.requireNonNull;

import app.dejv.impl.octarine.model.chunk.DoubleTuple;
import app.dejv.impl.octarine.request.AbstractRequestHandler;
import app.dejv.octarine.request.Request;

/**
 *
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class ResizeRequestHandler
        extends AbstractRequestHandler {

    private final DoubleTuple size;

    public ResizeRequestHandler(DoubleTuple size) {
        requireNonNull(size, "size is null");
        this.size = size;
    }


    @Override
    public boolean supports(Class<? extends Request> request) {
        return ResizeRequest.class.equals(request);
    }

    @Override
    protected void requestChecked(Request request) {
        final ResizeRequest resizeRequest = (ResizeRequest) request;

        resizeRequest.setCommand(new ResizeCommand(size, resizeRequest.getSizeDelta()));

    }

}
