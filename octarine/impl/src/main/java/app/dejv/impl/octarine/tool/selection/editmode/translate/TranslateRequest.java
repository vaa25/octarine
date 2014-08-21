package app.dejv.impl.octarine.tool.selection.editmode.translate;

import app.dejv.impl.octarine.request.CommandRequest;

/**
 *
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class TranslateRequest
        extends CommandRequest {

    private final double dx;
    private final double dy;


    public TranslateRequest(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public double getDx() {
        return dx;
    }

    public double getDy() {
        return dy;
    }
}
