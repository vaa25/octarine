package app.dejv.impl.octarine.tool.selection.extension.feedback;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.scene.shape.Shape;

import app.dejv.impl.octarine.feedback.DynamicFeedback;
import app.dejv.impl.octarine.utils.ConstantZoomDoubleBinding;
import app.dejv.impl.octarine.utils.ControllerUtils;
import app.dejv.impl.octarine.utils.FormattingUtils;
import app.dejv.octarine.controller.Controller;

/**
 * "Mouse over" dynamic feedback
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class MouseOverDynamicFeedback
        extends DynamicFeedback {

    private static final double SPACING = 5.0d;

    private DoubleBinding spacing;

    private Controller controller;
    private Shape outline;


    public void initMouseOverDynamicFeedback() {
        final DoubleProperty zoomFactor = octarine.getViewer().zoomFactorProperty();
        spacing = new ConstantZoomDoubleBinding(zoomFactor, SPACING);

        zoomFactor.addListener((observable) -> {
            removeOutline();
            addOutline();
        });

    }



    public void add(Controller controller) {
        if (controller != null) {
            remove();
        }
        this.controller = controller;
        addOutline();
        activate();
    }

    public void remove() {
        deactivate();
        removeOutline();
        controller = null;
    }


    private void removeOutline() {
        if ((outline != null) && (getChildren().contains(outline))) {
            getChildren().remove(outline);
        }
    }


    private void addOutline() {
        if (controller == null) {
            return;
        }

        outline = FormattingUtils.grow(ControllerUtils.getShape(controller), spacing.get());

        outline.strokeWidthProperty().bind(FormattingUtils.getDefaultFeedbackStrokeWidth(FormattingUtils.FeedbackType.DYNAMIC));
        FormattingUtils.formatFeedbackOutline(outline, FormattingUtils.FeedbackType.DYNAMIC, FormattingUtils.FeedbackOpacity.STRONG, "Mouseover");

        getChildren().add(outline);
    }
}
