package app.dejv.impl.octarine.request.shape;

import static java.util.Objects.requireNonNull;

import javafx.scene.shape.Shape;

import app.dejv.impl.octarine.request.AbstractRequestHandler;
import app.dejv.octarine.model.ModelElement;
import app.dejv.octarine.request.Request;
import app.dejv.octarine.view.ShapeFactory;

/**
 *
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class DefaultShapeRequestHandler
        extends AbstractRequestHandler {

    private final ShapeFactory shapeFactory;
    private final ModelElement modelElement;


    public DefaultShapeRequestHandler(ModelElement modelElement, ShapeFactory shapeFactory) {
        requireNonNull(shapeFactory, "shapeFactory is null");
        requireNonNull(modelElement, "modelElement is null");

        this.shapeFactory = shapeFactory;
        this.modelElement = modelElement;
    }


    @Override
    public boolean supports(Class<? extends Request> request) {
        return ShapeRequest.class.equals(request);
    }


    @Override
    public void requestChecked(Request request) {
        requireNonNull(shapeFactory, "shapeFactory is null");
        requireNonNull(modelElement, "modelElement is null");

        final ShapeRequest shapeRequest = (ShapeRequest) request;

        final Shape shape = shapeFactory.createShape(modelElement);
        requireNonNull(shape, "shapeCreator.createAndUpdateShape() returned null");

        shapeRequest.setShape(shape);
    }


}
