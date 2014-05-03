package info.dejv.octarine.request.handler;

import info.dejv.octarine.command.TranslateCommand;
import info.dejv.octarine.model.chunk.DoubleTuple;
import info.dejv.octarine.request.Request;
import info.dejv.octarine.tool.selection.request.TranslateRequest;

/**
 *
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class TranslateRequestHandler
        extends AbstractRequestHandler {

    private final DoubleTuple coords;

    public TranslateRequestHandler(DoubleTuple coords) {
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
