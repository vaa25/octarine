package info.dejv.octarine.feedback;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import javafx.scene.Node;

/**
 * Abstract ancestor for static feedback classes.
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class StaticFeedback
        extends AbstractFeedback {

    @Override
    protected ObservableList<Node> selectFeedbackNodes() {
        requireNonNull(octarine, "octarine is null");

        return octarine.getFeedback();
    }

}
