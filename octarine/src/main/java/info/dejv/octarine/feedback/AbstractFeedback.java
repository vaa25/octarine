package info.dejv.octarine.feedback;

import javax.annotation.PostConstruct;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;

import org.springframework.beans.factory.annotation.Autowired;

import info.dejv.octarine.Octarine;

/**
 * Abstract ancestor for feedback classes.
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public abstract class AbstractFeedback extends Group {

    @Autowired
    protected Octarine octarine;

    private ObservableList<Node> feedbackNodes;

    @PostConstruct
    public void postConstruct() throws Exception {
        setMouseTransparent(true);
        feedbackNodes = selectFeedbackNodes();
    }

    public void hide() {
        removeFromScene();
    }


    protected final void addToScene() {
        beforeAddToScene();

        if (!feedbackNodes.contains(this)) {
            feedbackNodes.add(this);
        }
    }

    protected final void removeFromScene() {
        if (feedbackNodes.contains(this)) {
            feedbackNodes.remove(this);
        }
        afterRemoveFromScene();
    }

    protected abstract ObservableList<Node> selectFeedbackNodes();


    protected void beforeAddToScene() {
        //Override when necessary
    }


    protected void afterRemoveFromScene() {
        //Override when necessary
    }
}
