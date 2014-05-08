package info.dejv.octarine.actionhandler.feedback;

import info.dejv.octarine.Octarine;
import info.dejv.octarine.controller.Controller;
import info.dejv.octarine.utils.ConstantZoomDoubleBinding;
import info.dejv.octarine.utils.ControllerUtils;
import info.dejv.octarine.utils.FormattingUtils;
import static info.dejv.octarine.utils.FormattingUtils.formatFeedbackOutline;
import static info.dejv.octarine.utils.FormattingUtils.getDefaultFeedbackStrokeWidth;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.scene.shape.Shape;

/**
 *
 * @author dejv
 */
public class MouseOverDynamicFeedback
        extends DynamicFeedback {

    private static final double SPACING = 5.0d;

    private static MouseOverDynamicFeedback instance;

    public static void add(Octarine octarine, Controller controller) {
        if (instance != null) {
            remove();
        }
        instance = new MouseOverDynamicFeedback(octarine, controller);
        instance.addToScene();
    }

    public static void remove() {
        if (instance == null) {
            return;
        }
        instance.removeFromScene();
        instance = null;
    }


    private final DoubleProperty zoomFactor;
    private final DoubleBinding spacing;
    private Shape outline;

    public MouseOverDynamicFeedback(Octarine octarine, Controller controller) {
        super(octarine);
        this.zoomFactor = octarine.getViewer().zoomFactorProperty();
        this.spacing = new ConstantZoomDoubleBinding(zoomFactor, SPACING);

        addOutline(controller);

        zoomFactor.addListener((observable) -> {
            removeOutline();
            addOutline(controller);
        });
    }

    private void removeOutline() {
        if ((outline != null) && (getChildren().contains(outline))) {
            getChildren().remove(outline);
        }
    }


    private void addOutline(Controller controller) {
        outline = FormattingUtils.grow(ControllerUtils.getShape(controller), spacing.get());

        outline.strokeWidthProperty().bind(getDefaultFeedbackStrokeWidth(FormattingUtils.FeedbackType.DYNAMIC));
        formatFeedbackOutline(outline, FormattingUtils.FeedbackType.DYNAMIC, FormattingUtils.FeedbackOpacity.STRONG, "Mouseover");

        getChildren().add(outline);
    }
}
