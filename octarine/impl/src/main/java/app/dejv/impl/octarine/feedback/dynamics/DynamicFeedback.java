package app.dejv.impl.octarine.feedback.dynamics;

import javafx.collections.ObservableList;
import javafx.scene.Node;

import app.dejv.impl.octarine.feedback.AbstractFeedback;
import app.dejv.octarine.Octarine;

/**
 * Abstract ancestor for dynamic feedback classes.
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public abstract class DynamicFeedback
        extends AbstractFeedback {

    public DynamicFeedback(Octarine octarine) {
        super(octarine);
    }


    @Override
    protected ObservableList<Node> selectFeedbackNodes() {
        return octarine.getLayerManager().getDynamicFeedbackLayer();
    }
}
