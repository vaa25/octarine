package app.dejv.impl.octarine.tool.selection.editmode.translate;

import static java.util.Objects.requireNonNull;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;

import app.dejv.impl.octarine.model.chunk.DoubleTuple;
import app.dejv.impl.octarine.request.AbstractRequestHandler;
import app.dejv.octarine.request.Request;

/**
 *
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class TranslateRequestHandler
        extends AbstractRequestHandler {

    private final DoubleTuple location;

    public TranslateRequestHandler(DoubleTuple location) {
        requireNonNull(location, "location is null");
        this.location = location;
    }

    @Override
    public boolean supports(Class<? extends Request> request) {
        return TranslateRequest.class.equals(request);
    }


    @Override
    public void requestChecked(Request request) {
        final TranslateRequest translateRequest = (TranslateRequest) request;

        final Dimension2D locationDelta = translateRequest.getLocationDelta();
        final Point2D newLocation = new Point2D(location.getX() + locationDelta.getWidth(), location.getY() + locationDelta.getHeight());

        translateRequest.setCommand(new TranslateCommand(location, newLocation));
    }

}
