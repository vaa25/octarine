package info.dejv.octarine.actionhandler.feedback;

import info.dejv.octarine.Octarine;
import info.dejv.octarine.controller.Controller;
import info.dejv.octarine.utils.ControllerUtils;
import info.dejv.octarine.utils.FormattingUtils;
import static info.dejv.octarine.utils.FormattingUtils.formatFeedbackOutline;
import static info.dejv.octarine.utils.FormattingUtils.getDefaultFeedbackStrokeWidth;
import javafx.scene.shape.Shape;

/**
 *
 * @author dejv
 */
public class MouseOverDynamicFeedback
        extends DynamicFeedback {

    private static final double SPACING = 5.0d;

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

        this.outline = FormattingUtils.grow(ControllerUtils.getShape(controller), SPACING);
        outline.strokeWidthProperty().bind(getDefaultFeedbackStrokeWidth(FormattingUtils.FeedbackType.DYNAMIC));

        formatFeedbackOutline(outline, FormattingUtils.FeedbackType.DYNAMIC, FormattingUtils.FeedbackOpacity.STRONG, "Mouseover");

        getChildren().add(outline);
    }

}
