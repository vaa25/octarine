package app.dejv.impl.octarine.tool.selection.editmode.transform;

import javafx.scene.transform.Transform;

import app.dejv.impl.octarine.request.CommandRequest;

/**
 *
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public abstract class TransformRequest
        extends CommandRequest {

    private final Transform transform;


    public TransformRequest(Transform transform) {
        this.transform = transform;
    }


    public Transform getTransform() {
        return transform;
    }
}
