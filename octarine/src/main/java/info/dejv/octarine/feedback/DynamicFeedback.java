package info.dejv.octarine.feedback;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import javafx.scene.Node;


/**
 * Abstract ancestor for dynamic feedback classes.
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public abstract class DynamicFeedback
        extends AbstractFeedback {

    @Override
    protected ObservableList<Node> selectFeedbackNodes() {
        requireNonNull(octarine, "octarine is null");

        return octarine.getActiveFeedback();
    }
}
