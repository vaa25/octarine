package info.dejv.octarine.tool.selection.extension.feedback;

import static info.dejv.octarine.utils.FormattingUtils.getDefaultFeedbackStrokeWidth;
import static info.dejv.octarine.utils.FormattingUtils.getFeedbackColor;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

import org.springframework.stereotype.Component;

import info.dejv.octarine.feedback.DynamicFeedback;
import info.dejv.octarine.utils.FormattingUtils;
import info.dejv.octarine.utils.FormattingUtils.FeedbackOpacity;
import info.dejv.octarine.utils.FormattingUtils.FeedbackType;

/**
 * "Marquee selection" dynamic feedback.
 * <br/>
 * Author: dejv (www.dejv.info)
 */
@Component
public class MarqueeSelectionDynamicFeedback
        extends DynamicFeedback {

    private Point2D initialCoords;
    private Rectangle rectangle;


    public void add(Point2D initialCoords) {
        this.initialCoords = initialCoords;

        if (rectangle == null) {
            addRectangle();
            addToScene();
        }
    }

    public void remove() {
        removeFromScene();
        removeRectangle();
    }


    @Override
    protected void beforeAddToScene() {

    }

    @Override
    protected void afterRemoveFromScene() {

    }

    public void setCurrentCoords(Point2D currentCoords) {
        setRectCoords(initialCoords.getX(), currentCoords.getX(), initialCoords.getY(), currentCoords.getY());
    }


    private void addRectangle() {
        rectangle = new Rectangle();
        rectangle.setStroke(getFeedbackColor(FeedbackType.DYNAMIC, FeedbackOpacity.OPAQUE));
        rectangle.setFill(getFeedbackColor(FeedbackType.DYNAMIC, FeedbackOpacity.WEAK));
        rectangle.strokeWidthProperty().bind(getDefaultFeedbackStrokeWidth(FormattingUtils.FeedbackType.STATIC));

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
