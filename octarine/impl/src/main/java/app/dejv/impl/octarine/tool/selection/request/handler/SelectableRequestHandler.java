package app.dejv.impl.octarine.tool.selection.request.handler;

import app.dejv.impl.octarine.request.handler.AbstractRequestHandler;
import app.dejv.impl.octarine.tool.selection.request.SelectRequest;
import app.dejv.octarine.request.Request;

/**
 *
 * <br/>
 * Author: dejv (www.dejv.info)
 */

public class SelectableRequestHandler
        extends AbstractRequestHandler {

    @Override
    public boolean supports(Class<? extends Request> request) {
        return (SelectRequest.class.isAssignableFrom(request));
    }

    @Override
    protected void requestChecked(Request request) {
        //This handler does actually nothing
    }
}
