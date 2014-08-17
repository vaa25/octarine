package app.dejv.impl.octarine.tool.selection.extension.feedback;

import javafx.scene.shape.Rectangle;

import app.dejv.impl.octarine.feedback.DynamicFeedback;
import app.dejv.impl.octarine.utils.FormattingUtils;
import app.dejv.impl.octarine.utils.FormattingUtils.FeedbackOpacity;
import app.dejv.impl.octarine.utils.FormattingUtils.FeedbackType;
import app.dejv.octarine.Octarine;

/**
 * "Marquee selection" dynamic feedback.
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class MarqueeSelectionDynamicFeedback
        extends DynamicFeedback {

    private final Rectangle rectangle;

    private double initialX, initialY;

    public MarqueeSelectionDynamicFeedback(Octarine octarine) {
        super(octarine);

        rectangle = new Rectangle();
        rectangle.setStroke(FormattingUtils.getFeedbackColor(FeedbackType.DYNAMIC, FeedbackOpacity.OPAQUE));
        rectangle.setFill(FormattingUtils.getFeedbackColor(FeedbackType.DYNAMIC, FeedbackOpacity.WEAK));
    }


    public void setInitialCoords(double initialX, double initialY) {
        this.initialX = initialX;
        this.initialY = initialY;
    }


    public void setCurrentCoords(double currentX, double currentY) {
        setRectCoords(initialX, initialY, currentX, currentY);
    }


    @Override
    protected void beforeActivate() {
        super.beforeActivate();
        getChildren().add(rectangle);
    }


    @Override
    protected void afterDeactivate() {
        getChildren().remove(rectangle);
        super.afterDeactivate();
    }


    @Override
    protected void bind() {
        super.bind();
        rectangle.strokeWidthProperty().bind(FormattingUtils.getDefaultFeedbackStrokeWidth(FormattingUtils.FeedbackType.STATIC));
    }


    @Override
    protected void unbind() {
        rectangle.strokeWidthProperty().unbind();
        super.unbind();
    }

    private void setRectCoords(double ix, double iy, double cx, double cy) {
        rectangle.setX((ix <= cx) ? ix : cx);
        rectangle.setY((iy <= cy) ? iy : cy);
        rectangle.setWidth((ix <= cx) ? cx - ix : ix - cx);
        rectangle.setHeight((iy <= cy) ? cy - iy : iy - cy);
    }
}
