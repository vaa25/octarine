package app.dejv.impl.octarine.tool.selection.editmode.rotate;

import javafx.beans.property.DoubleProperty;

import app.dejv.impl.octarine.request.AbstractRequestHandler;
import app.dejv.octarine.request.Request;

/**
 *
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class RotateRequestHandler
    extends AbstractRequestHandler {

    private final DoubleProperty rotationAngle_rad;


    public RotateRequestHandler(DoubleProperty rotationAngle_rad) {

        this.rotationAngle_rad = rotationAngle_rad;
    }


    @Override
    public boolean supports(Class<? extends Request> request) {
        return RotateRequest.class.equals(request);
    }

    @Override
    protected void requestChecked(Request request) {
        final RotateRequest rotateRequest = (RotateRequest) request;

        rotateRequest.setCommand(new RotateCommand(rotationAngle_rad, rotateRequest.getAngleDelta_rad()));
    }

}
