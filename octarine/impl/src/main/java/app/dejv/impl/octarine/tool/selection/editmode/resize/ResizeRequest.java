package app.dejv.impl.octarine.tool.selection.editmode.resize;

import javafx.scene.transform.Scale;

import app.dejv.impl.octarine.tool.selection.editmode.transform.TransformRequest;

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class ResizeRequest
        extends TransformRequest {


    public ResizeRequest(Scale scale) {
        super(scale);
    }

    public Scale getScale() {
        return (Scale) getTransform();
    }
}
