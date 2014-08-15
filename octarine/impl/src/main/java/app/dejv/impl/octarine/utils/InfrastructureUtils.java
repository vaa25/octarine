package app.dejv.impl.octarine.utils;

import java.util.Optional;

import javafx.scene.Group;

import app.dejv.octarine.infrastructure.Resources;

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class InfrastructureUtils {

    public static Group getRequiredShape(Resources resources, String id) {
        final Optional<Group> shapeWrapper = resources.getShape(id);
        if (shapeWrapper.isPresent()) {
            return shapeWrapper.get();
        }
        throw new IllegalStateException("Required shape [" + id +"] is not available");
    }

}
