package info.dejv.octarine.actionhandler.feedback;

import info.dejv.octarine.Octarine;
import javafx.scene.Group;

/**
 *
 * @author dejv
 */
public abstract class DynamicFeedback
        extends Group {

    private final Octarine octarine;

    public DynamicFeedback(Octarine octarine) {
        this.octarine = octarine;
    }

    protected void addToScene() {
        octarine.getActiveFeedback().add(this);
    }

    protected void removeFromScene() {
        octarine.getActiveFeedback().remove(this);
    }
}
