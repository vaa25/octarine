package info.dejv.octarine.actionhandler.selection;

import info.dejv.octarine.request.Request;
import info.dejv.octarine.request.handler.AbstractRequestHandler;
import info.dejv.octarine.stereotype.RequestHandler;

/**
 *
 * <br/>
 * Author: dejv (www.dejv.info)
 */

@RequestHandler
public class SelectableRequestHandler
        extends AbstractRequestHandler {

    @Override
    public boolean supports(Class<? extends Request> request) {
        return (Selectable.class.isAssignableFrom(request));
    }

    @Override
    protected void requestChecked(Request request) {
        //This handler does actually nothing
    }
}
