package app.dejv.impl.octarine.request.shape;

import javafx.scene.shape.Shape;

import app.dejv.octarine.request.Request;

/**
 * A request to return objects "Shape"
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class ShapeRequest
        implements Request {

    private Shape shape;


    /**
     * @return The shape, that was set as an answer to the request
     */
    public Shape getShape() {
        return shape;
    }

    /**
     * Set the requested shape<br/>
     *<b>Note: A consumer of ShapeRequest is supposed to feed the requested shape using this method</b>
     * @param requestedShape Shape to answer the request with
     */
    public void setShape(Shape requestedShape) {
        this.shape = requestedShape;
    }
}
