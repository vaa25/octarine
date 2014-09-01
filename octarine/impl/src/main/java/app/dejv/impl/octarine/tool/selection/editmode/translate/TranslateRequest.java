package app.dejv.impl.octarine.tool.selection.editmode.translate;

import javafx.geometry.Dimension2D;

import app.dejv.impl.octarine.request.CommandRequest;

/**
 *
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class TranslateRequest
        extends CommandRequest {

    private final Dimension2D positionDelta;


    public TranslateRequest(Dimension2D positionDelta) {
        this.positionDelta = positionDelta;
    }


    public Dimension2D getPositionDelta() {
        return positionDelta;
    }
}
