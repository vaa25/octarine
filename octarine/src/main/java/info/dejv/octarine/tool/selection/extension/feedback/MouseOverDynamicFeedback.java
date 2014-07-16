package info.dejv.octarine.tool.selection.extension.feedback;

import static info.dejv.octarine.utils.FormattingUtils.formatFeedbackOutline;
import static info.dejv.octarine.utils.FormattingUtils.getDefaultFeedbackStrokeWidth;

import javax.annotation.PostConstruct;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.scene.shape.Shape;

import org.springframework.stereotype.Component;

import info.dejv.octarine.controller.Controller;
import info.dejv.octarine.feedback.DynamicFeedback;
import info.dejv.octarine.utils.ConstantZoomDoubleBinding;
import info.dejv.octarine.utils.ControllerUtils;
import info.dejv.octarine.utils.FormattingUtils;

/**
 * "Mouse over" dynamic feedback
 * <br/>
 * Author: dejv (www.dejv.info)
 */
@Component
public class MouseOverDynamicFeedback
        extends DynamicFeedback {

    private static final double SPACING = 5.0d;

    private DoubleBinding spacing;

    private Controller controller;
    private Shape outline;


    @PostConstruct
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
        addToScene();
    }

    public void remove() {
        removeFromScene();
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

        outline.strokeWidthProperty().bind(getDefaultFeedbackStrokeWidth(FormattingUtils.FeedbackType.DYNAMIC));
        formatFeedbackOutline(outline, FormattingUtils.FeedbackType.DYNAMIC, FormattingUtils.FeedbackOpacity.STRONG, "Mouseover");

        getChildren().add(outline);
    }
}
