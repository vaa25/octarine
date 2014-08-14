package app.dejv.impl.octarine.feedback;

import javafx.collections.ObservableList;
import javafx.scene.Node;

import app.dejv.octarine.Octarine;

/**
 * Abstract ancestor for static feedback classes.
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public abstract class StaticFeedback
        extends AbstractFeedback {


    protected StaticFeedback(Octarine octarine) {
        super(octarine);
    }


    @Override
    protected ObservableList<Node> selectFeedbackNodes() {
        return octarine.getGroupFeedbackStatic();
    }

}
