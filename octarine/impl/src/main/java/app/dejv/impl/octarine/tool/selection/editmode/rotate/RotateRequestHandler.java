package app.dejv.impl.octarine.tool.selection.editmode.rotate;

import app.dejv.impl.octarine.request.AbstractRequestHandler;
import app.dejv.octarine.request.Request;

/**
 *
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class RotateRequestHandler
    extends AbstractRequestHandler {

    @Override
    public boolean supports(Class<? extends Request> request) {
        return RotateRequest.class.equals(request);
    }

    @Override
    protected void requestChecked(Request request) {
        @SuppressWarnings("UnusedAssignment")
        final RotateRequest rotateRequest = (RotateRequest) request;
    }

}
