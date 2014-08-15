package app.dejv.impl.octarine.tool.selection.extension.feedback;

import static java.util.Objects.requireNonNull;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.scene.shape.Shape;

import app.dejv.impl.octarine.feedback.DynamicFeedback;
import app.dejv.impl.octarine.utils.ConstantZoomDoubleBinding;
import app.dejv.impl.octarine.utils.ControllerUtils;
import app.dejv.impl.octarine.utils.FormattingUtils;
import app.dejv.octarine.Octarine;
import app.dejv.octarine.controller.Controller;

/**
 * "Mouse over" dynamic feedback
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class MouseOverDynamicFeedback
        extends DynamicFeedback {

    private static final double SPACING = 5.0d;

    private final DoubleBinding spacing;

    private Controller controller;
    private Shape outline;


    public MouseOverDynamicFeedback(Octarine octarine) {
        super(octarine);

        final DoubleProperty zoomFactor = octarine.getViewer().zoomFactorProperty();

        zoomFactor.addListener((observable) -> createOutline());
        spacing = new ConstantZoomDoubleBinding(zoomFactor, SPACING);
    }


    public void setController(Controller controller) {
        this.controller = controller;
    }


    @Override
    protected void beforeActivate() {
        requireNonNull(controller, "Controller must be set before activation");
        createOutline();

        super.beforeActivate();

        getChildren().add(outline);
    }


    @Override
    protected void afterDeactivate() {
        removeOutline();

        super.afterDeactivate();
    }


    @Override
    protected void bind() {
        super.bind();
        outline.strokeWidthProperty().bind(FormattingUtils.getDefaultFeedbackStrokeWidth(FormattingUtils.FeedbackType.DYNAMIC));
    }


    @Override
    protected void unbind() {
        super.unbind();
        outline.strokeWidthProperty().unbind();
    }


    private void createOutline() {
        if (controller == null) {
            return;
        }

        removeOutline();

        outline = FormattingUtils.grow(ControllerUtils.getShape(controller), spacing.get());
        FormattingUtils.formatFeedbackOutline(outline, FormattingUtils.FeedbackType.DYNAMIC, FormattingUtils.FeedbackOpacity.STRONG, "Mouseover");
    }


    private void removeOutline() {
        if ((outline != null) && (getChildren().contains(outline))) {
            getChildren().remove(outline);
        }
    }
}
