package info.dejv.octarine.actionhandler.feedback;

import javax.annotation.PostConstruct;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;

import org.springframework.beans.factory.annotation.Autowired;

import info.dejv.octarine.Octarine;

/**
 *
 * @author dejv
 */
public abstract class DynamicFeedback
        extends Group {

    @Autowired
    protected Octarine octarine;

    protected ObservableList<Node> feedbackNodes;


    @PostConstruct
    public void initDynamicFeedback() {
        setMouseTransparent(true);
        feedbackNodes = octarine.getActiveFeedback();
    }


    protected void addToScene() {
        if (!feedbackNodes.contains(this)) {
            feedbackNodes.add(this);
        }
    }

    protected void removeFromScene() {
        if (feedbackNodes.contains(this)) {
            feedbackNodes.remove(this);
        }
    }
}
