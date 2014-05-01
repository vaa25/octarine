package info.dejv.octarine.request.handler;

import info.dejv.octarine.command.TranslateCommand;
import info.dejv.octarine.controller.RequestHandler;
import info.dejv.octarine.model.chunk.Coords2D;
import info.dejv.octarine.request.Request;
import info.dejv.octarine.tool.selection.request.TranslateRequest;
import static java.util.Objects.requireNonNull;

/**
 *
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class TranslateRequestHandler
        implements RequestHandler {

    private final Coords2D coords;

    public TranslateRequestHandler(Coords2D coords) {
        this.coords = coords;
    }

    @Override
    public boolean supports(Class<? extends Request> request) {
        return TranslateRequest.class.equals(request);
    }

    @Override
    public void request(Request request) {
        requireNonNull(request, "request is null");

        if (!TranslateRequest.class.equals(request.getClass())) {
            throw new IllegalArgumentException("Unsupported request: " + request);
        }

        TranslateRequest translateRequest = (TranslateRequest) request;

        translateRequest.setCommand(new TranslateCommand(coords, translateRequest.getDx(), translateRequest.getDy()));
    }

}
