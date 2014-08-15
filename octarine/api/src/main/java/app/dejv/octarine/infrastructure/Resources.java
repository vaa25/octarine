package app.dejv.octarine.infrastructure;

import java.util.Optional;

import javafx.scene.Group;

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public interface Resources {

    Optional<Group> getShape(String id);
}
