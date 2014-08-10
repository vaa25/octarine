package app.dejv.impl.octarine.utils;

import javafx.scene.shape.Shape;

import app.dejv.impl.octarine.request.shape.ShapeRequest;
import app.dejv.octarine.controller.Controller;

/**
 *
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class ControllerUtils {


    public static Shape getShape(Controller controller) {
        if (!controller.supports(ShapeRequest.class)) {
            throw new IllegalArgumentException("Selectable controller [" + controller + "] doesn't support ShapeRequest");
        }

        ShapeRequest sr = new ShapeRequest();
        controller.request(sr);

        Shape shape = sr.getShape();

        if (shape == null) {
            throw new IllegalStateException("Controller [" + controller + "] doesn't return valid Shape in response to ShapeRequest");
        }
        return shape;
    }


}
