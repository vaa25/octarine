package info.dejv.octarine.request.handler;

import info.dejv.octarine.controller.RequestHandler;
import info.dejv.octarine.request.Request;
import info.dejv.octarine.request.shape.ShapeCreator;
import info.dejv.octarine.request.shape.ShapeRequest;
import static java.util.Objects.requireNonNull;
import javafx.scene.shape.Shape;

/**
 *
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class DefaultShapeRequestHandler
        implements RequestHandler {

    private final ShapeCreator shapeCreator;

    public DefaultShapeRequestHandler(ShapeCreator shapeCreator) {
        requireNonNull(shapeCreator, "shapeCreator is null");

        this.shapeCreator = shapeCreator;
    }

    @Override
    public boolean supports(Class<? extends Request> request) {
        return ShapeRequest.class.equals(request);
    }


    @Override
    public void request(Request request) {
        requireNonNull(request, "request is null");

        if (!ShapeRequest.class.equals(request.getClass())) {
            throw new IllegalArgumentException("Unsupported request: " + request);
        }

        ShapeRequest shapeRequest = (ShapeRequest) request;

        Shape shape = shapeCreator.createAndUpdateShape();
        requireNonNull(shape, "shapeCreator.createAndUpdateShape() returned null");

        shapeRequest.setShape((Shape) shape);
    }


}