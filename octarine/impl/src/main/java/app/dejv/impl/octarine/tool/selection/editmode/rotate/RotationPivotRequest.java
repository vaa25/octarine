package app.dejv.impl.octarine.tool.selection.editmode.rotate;

import javafx.geometry.Point2D;

import app.dejv.octarine.request.Request;

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class RotationPivotRequest
        implements Request {

    private final Point2D pivot;


    public RotationPivotRequest(Point2D pivot) {
        this.pivot = pivot;
    }


    public Point2D getPivot() {
        return pivot;
    }
}
