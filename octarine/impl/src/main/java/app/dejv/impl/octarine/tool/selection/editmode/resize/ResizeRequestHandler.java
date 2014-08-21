package app.dejv.impl.octarine.tool.selection.editmode.resize;

import app.dejv.impl.octarine.request.AbstractRequestHandler;
import app.dejv.octarine.request.Request;

/**
 *
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class ResizeRequestHandler
        extends AbstractRequestHandler {

    @Override
    public boolean supports(Class<? extends Request> request) {
        return ResizeRequest.class.equals(request);
    }

    @Override
    protected void requestChecked(Request request) {

    }

}
