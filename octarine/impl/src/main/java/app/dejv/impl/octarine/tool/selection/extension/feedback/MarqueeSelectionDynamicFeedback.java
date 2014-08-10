package app.dejv.impl.octarine.tool.selection.extension.feedback;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

import app.dejv.impl.octarine.feedback.DynamicFeedback;
import app.dejv.impl.octarine.utils.FormattingUtils;
import app.dejv.impl.octarine.utils.FormattingUtils.FeedbackOpacity;
import app.dejv.impl.octarine.utils.FormattingUtils.FeedbackType;

/**
 * "Marquee selection" dynamic feedback.
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class MarqueeSelectionDynamicFeedback
        extends DynamicFeedback {

    private Point2D initialCoords;
    private Rectangle rectangle;


    public void add(Point2D initialCoords) {
        this.initialCoords = initialCoords;

        if (rectangle == null) {
            addRectangle();
            activate();
        }
    }

    public void remove() {
        deactivate();
        removeRectangle();
    }


    public void setCurrentCoords(Point2D currentCoords) {
        setRectCoords(initialCoords.getX(), currentCoords.getX(), initialCoords.getY(), currentCoords.getY());
    }


    private void addRectangle() {
        rectangle = new Rectangle();
        rectangle.setStroke(FormattingUtils.getFeedbackColor(FeedbackType.DYNAMIC, FeedbackOpacity.OPAQUE));
        rectangle.setFill(FormattingUtils.getFeedbackColor(FeedbackType.DYNAMIC, FeedbackOpacity.WEAK));
        rectangle.strokeWidthProperty().bind(FormattingUtils.getDefaultFeedbackStrokeWidth(FormattingUtils.FeedbackType.STATIC));

        setCurrentCoords(initialCoords);

        getChildren().add(rectangle);
    }


    private void removeRectangle() {
        if ((rectangle != null) && (getChildren().contains(rectangle))) {
            getChildren().remove(rectangle);
        }
        rectangle = null;
    }


    private void setRectCoords(double ix, double cx, double iy, double cy) {
        rectangle.setX((ix <= cx) ? ix : cx);
        rectangle.setY((iy <= cy) ? iy : cy);
        rectangle.setWidth((ix <= cx) ? cx - ix : ix - cx);
        rectangle.setHeight((iy <= cy) ? cy - iy : iy - cy);
    }
}
