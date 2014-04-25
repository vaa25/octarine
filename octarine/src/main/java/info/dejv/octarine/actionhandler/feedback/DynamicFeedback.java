package info.dejv.octarine.actionhandler.feedback;

import info.dejv.octarine.Octarine;
import javafx.scene.Group;

/**
 *
 * @author dejv
 */
public abstract class DynamicFeedback
        extends Group {

    private final Octarine editor;

    public DynamicFeedback(Octarine editor) {
        this.editor = editor;
    }

    protected void addToEditor() {
        editor.getActiveFeedback().add(this);
    }

    protected void removeFromEditor() {
        editor.getActiveFeedback().remove(this);
    }
}
