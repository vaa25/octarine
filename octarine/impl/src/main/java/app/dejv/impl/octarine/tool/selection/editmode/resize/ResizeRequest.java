package app.dejv.impl.octarine.tool.selection.editmode.resize;

import javafx.scene.transform.Transform;

import app.dejv.impl.octarine.request.CommandRequest;

/**
 *
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class ResizeRequest
        extends CommandRequest {

    private final Transform transform;


    public ResizeRequest(Transform transform) {
        this.transform = transform;
    }


    public Transform getTransform() {
        return transform;
    }
}
