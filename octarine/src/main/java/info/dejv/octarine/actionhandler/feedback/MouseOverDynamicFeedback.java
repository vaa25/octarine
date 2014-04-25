package info.dejv.octarine.actionhandler.feedback;

import info.dejv.octarine.Octarine;
import info.dejv.octarine.controller.Controller;
import info.dejv.octarine.utils.ControllerUtils;
import info.dejv.octarine.utils.FeedbackFormatter;
import javafx.scene.shape.Shape;

/**
 *
 * @author dejv
 */
public class MouseOverDynamicFeedback
        extends DynamicFeedback {

    private static MouseOverDynamicFeedback instance;

    public static void add(Octarine editor, Controller controller) {
        if (instance != null) {
            remove();
        }
        instance = new MouseOverDynamicFeedback(editor, controller);
        instance.addToEditor();
    }

    public static void remove() {
        if (instance == null) {
            return;
        }
        instance.removeFromEditor();
        instance = null;
    }


    private final Shape outline;

    public MouseOverDynamicFeedback(Octarine editor, Controller controller) {
        super(editor);

        this.outline = FeedbackFormatter.grow(ControllerUtils.getShape(controller), 5.0d);
        FeedbackFormatter.formatOutline(outline);

        getChildren().add(outline);
    }


}
