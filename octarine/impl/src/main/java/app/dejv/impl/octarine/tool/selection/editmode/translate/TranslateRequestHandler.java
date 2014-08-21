package app.dejv.impl.octarine.tool.selection.editmode.translate;

import static java.util.Objects.requireNonNull;

import app.dejv.impl.octarine.model.chunk.DoubleTuple;
import app.dejv.impl.octarine.request.AbstractRequestHandler;
import app.dejv.octarine.request.Request;

/**
 *
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class TranslateRequestHandler
        extends AbstractRequestHandler {

    private final DoubleTuple coords;

    public TranslateRequestHandler(DoubleTuple coords) {
        requireNonNull(coords, "coords is null");
        this.coords = coords;
    }

    @Override
    public boolean supports(Class<? extends Request> request) {
        return TranslateRequest.class.equals(request);
    }


    @Override
    public void requestChecked(Request request) {
        TranslateRequest translateRequest = (TranslateRequest) request;

        translateRequest.setCommand(new TranslateCommand(coords, translateRequest.getDx(), translateRequest.getDy()));
    }

}
