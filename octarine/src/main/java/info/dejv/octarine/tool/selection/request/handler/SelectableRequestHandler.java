package info.dejv.octarine.tool.selection.request.handler;

import info.dejv.octarine.request.Request;
import info.dejv.octarine.request.handler.AbstractRequestHandler;
import info.dejv.octarine.stereotype.RequestHandler;
import info.dejv.octarine.tool.selection.request.SelectRequest;

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
        return (SelectRequest.class.isAssignableFrom(request));
    }

    @Override
    protected void requestChecked(Request request) {
        //This handler does actually nothing
    }
}
