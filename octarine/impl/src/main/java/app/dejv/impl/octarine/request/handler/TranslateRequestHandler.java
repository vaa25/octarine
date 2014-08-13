package app.dejv.impl.octarine.request.handler;

import app.dejv.impl.octarine.model.chunk.DoubleTuple;
import app.dejv.impl.octarine.tool.selection.command.TranslateCommand;
import app.dejv.impl.octarine.tool.selection.request.TranslateRequest;
import app.dejv.octarine.request.Request;

/**
 *
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class TranslateRequestHandler
        extends AbstractRequestHandler {

    private DoubleTuple coords;

    public TranslateRequestHandler setCoords(DoubleTuple coords) {
        this.coords = coords;
        return this;
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