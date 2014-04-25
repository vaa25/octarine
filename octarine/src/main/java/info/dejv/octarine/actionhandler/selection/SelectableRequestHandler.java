package info.dejv.octarine.actionhandler.selection;

import info.dejv.octarine.controller.RequestHandler;
import info.dejv.octarine.request.Request;

/**
 *
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class SelectableRequestHandler
        implements RequestHandler {

    private static SelectableRequestHandler instance;

    public static SelectableRequestHandler getInstance() {
        if (instance == null) {
            instance = new SelectableRequestHandler();
        }
        return instance;
    }

    private SelectableRequestHandler() {
    }

    @Override
    public boolean supports(Class<? extends Request> request) {
        return (Selectable.class.isAssignableFrom(request));
    }

    @Override
    public void request(Request request) {
        //Nothing, here...
    }

}
