package info.dejv.octarine.request.handler;

import static java.util.Objects.requireNonNull;

import javafx.scene.shape.Shape;

import info.dejv.octarine.model.ModelElement;
import info.dejv.octarine.request.Request;
import info.dejv.octarine.request.shape.ShapeRequest;
import info.dejv.octarine.stereotype.RequestHandler;
import info.dejv.octarine.view.ShapeFactory;

/**
 *
 * <br/>
 * Author: dejv (www.dejv.info)
 */
@RequestHandler
public class DefaultShapeRequestHandler
        extends AbstractRequestHandler {

    private ShapeFactory shapeFactory;
    private ModelElement modelElement;


    public DefaultShapeRequestHandler setShapeFactory(ShapeFactory shapeFactory) {
        this.shapeFactory = shapeFactory;
        return this;
    }


    public DefaultShapeRequestHandler setModelElement(ModelElement modelElement) {
        this.modelElement = modelElement;
        return this;
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
