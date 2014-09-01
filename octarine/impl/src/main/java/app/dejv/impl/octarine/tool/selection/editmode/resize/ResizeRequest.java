package app.dejv.impl.octarine.tool.selection.editmode.resize;

import javafx.geometry.Dimension2D;

import app.dejv.impl.octarine.request.CommandRequest;

/**
 *
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class ResizeRequest
        extends CommandRequest {

    private final Dimension2D sizeDelta;


    public ResizeRequest(Dimension2D sizeDelta) {
        this.sizeDelta = sizeDelta;
    }


    public Dimension2D getSizeDelta() {
        return sizeDelta;
    }
}
