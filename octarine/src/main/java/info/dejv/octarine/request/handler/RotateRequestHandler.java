package info.dejv.octarine.request.handler;

import info.dejv.octarine.request.Request;
import info.dejv.octarine.stereotype.RequestHandler;
import info.dejv.octarine.tool.selection.request.RotateRequest;

/**
 *
 * <br/>
 * Author: dejv (www.dejv.info)
 */
@RequestHandler
public class RotateRequestHandler
    extends AbstractRequestHandler {

    @Override
    public boolean supports(Class<? extends Request> request) {
        return RotateRequest.class.equals(request);
    }

    @Override
    protected void requestChecked(Request request) {
        final RotateRequest rotateRequest = (RotateRequest) request;
    }

}
