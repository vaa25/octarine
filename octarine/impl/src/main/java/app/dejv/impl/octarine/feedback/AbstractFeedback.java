package app.dejv.impl.octarine.feedback;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;

import app.dejv.octarine.Octarine;


/**
 * Abstract ancestor for feedback classes.
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public abstract class AbstractFeedback
        extends Group {

    protected final Octarine octarine;

    private ObservableList<Node> feedbackNodes;
    private boolean active = false;


    public AbstractFeedback(Octarine octarine) {
        requireNonNull(octarine, "octarine is null");

        this.octarine = octarine;

        setMouseTransparent(true);
    }


    public boolean isActive() {
        return active;
    }


    public void activate() {
        if (active)
            return;

        ensureValidFeedbackNodes();

        beforeActivate();

        if (!feedbackNodes.contains(this)) {
            feedbackNodes.add(this);
        }

        active = true;
        afterActivate();
    }


    public final void deactivate() {
        if (!active) {
            return;
        }

        beforeDeactivate();
        active = false;
    }


    protected void beforeActivate() {
        bind();
    }


    protected void afterActivate() {

    }


    protected void beforeDeactivate() {
        onDeactivationConfirmed();
    }


    protected void onDeactivationConfirmed() {
        if (feedbackNodes.contains(this)) {
            feedbackNodes.remove(this);
        }

        afterDeactivate();
    }


    protected void afterDeactivate() {
        unbind();
    }


    protected void bind() {
        //Intentionally left blank
    }


    protected void unbind() {
        //Intentionally left blank
    }


    protected abstract ObservableList<Node> selectFeedbackNodes();


    private void ensureValidFeedbackNodes() {
        if (feedbackNodes == null) {
            feedbackNodes = selectFeedbackNodes();
        }

        requireNonNull(feedbackNodes, "feedbackNodes list not selected");
    }
}
