package app.dejv.impl.octarine.feedback.handles;

import javafx.collections.ObservableList;
import javafx.scene.Node;

import app.dejv.impl.octarine.feedback.AbstractFeedback;
import app.dejv.octarine.Octarine;

/**
 * Abstract ancestor for static feedback classes.
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public abstract class HandleFeedback
        extends AbstractFeedback {


    protected HandleFeedback(Octarine octarine) {
        super(octarine);
        this.setMouseTransparent(false);
    }


    @Override
    protected ObservableList<Node> selectFeedbackNodes() {
        return octarine.getLayerManager().getHandlesLayer();
    }

}
