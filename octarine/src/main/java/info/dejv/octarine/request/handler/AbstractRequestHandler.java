package info.dejv.octarine.request.handler;

import info.dejv.octarine.controller.RequestHandler;
import info.dejv.octarine.request.Request;
import static java.util.Objects.requireNonNull;

/**
 *
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public abstract class AbstractRequestHandler
        implements RequestHandler {


    @Override
    public void request(Request request) {
        requireNonNull(request, "request is null");

        if (!supports(request.getClass())) {
            throw new IllegalArgumentException("Unsupported request: " + request);
        }

        requestChecked(request);
    }

    @Override
    public abstract boolean supports(Class<? extends Request> request);

    protected abstract void requestChecked(Request request);
}
