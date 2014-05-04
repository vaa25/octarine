package info.dejv.octarine.request.handler;

import info.dejv.octarine.request.Request;
import info.dejv.octarine.tool.selection.request.ScaleRequest;

/**
 *
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class ScaleRequestHandler
        extends AbstractRequestHandler {

    @Override
    public boolean supports(Class<? extends Request> request) {
        return ScaleRequest.class.equals(request);
    }

    @Override
    protected void requestChecked(Request request) {

    }

}
