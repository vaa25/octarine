package info.dejv.octarine.request.handler;

import info.dejv.octarine.tool.selection.command.TranslateCommand;
import info.dejv.octarine.model.chunk.DoubleTuple;
import info.dejv.octarine.request.Request;
import info.dejv.octarine.stereotype.RequestHandler;
import info.dejv.octarine.tool.selection.request.TranslateRequest;

/**
 *
 * <br/>
 * Author: dejv (www.dejv.info)
 */
@RequestHandler
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
