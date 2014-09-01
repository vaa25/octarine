package app.dejv.impl.octarine.tool.selection.editmode.rotate;

import app.dejv.impl.octarine.request.CommandRequest;

/**
 *
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class RotateRequest
        extends CommandRequest {

    private final double angle_rad;


    public RotateRequest(double angle_rad) {
        this.angle_rad = angle_rad;
    }


    public double getAngleDelta_rad() {
        return angle_rad;
    }
}
