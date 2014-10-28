package app.dejv.impl.octarine.tool.selection.editmode;

import javafx.scene.transform.Transform;

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public interface TransformationListener {

    void auxiliaryOperationCommited(Object operationDescriptor);

    void transformationCommited(Transform transform);
}
