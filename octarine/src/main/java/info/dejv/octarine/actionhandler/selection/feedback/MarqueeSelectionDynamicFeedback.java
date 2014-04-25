package info.dejv.octarine.actionhandler.selection.feedback;

import info.dejv.octarine.Octarine;
import info.dejv.octarine.actionhandler.feedback.DynamicFeedback;
import info.dejv.octarine.cfg.OctarineProps;
import info.dejv.octarine.utils.FeedbackFormatter;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author dejv
 */
public class MarqueeSelectionDynamicFeedback
        extends DynamicFeedback {

    private static MarqueeSelectionDynamicFeedback instance;

    public static void add(Octarine editor, Point2D initialCoords) {
        if (instance != null) {
            remove();
        }
        instance = new MarqueeSelectionDynamicFeedback(editor, initialCoords);
        instance.addToEditor();
    }

    public static void remove() {
        if (instance == null) {
            return;
        }
        instance.removeFromEditor();
        instance = null;
    }

    public static MarqueeSelectionDynamicFeedback getInstance() {
        return instance;
    }


    private final Point2D initialCoords;
    private final Rectangle rectangle;

    public MarqueeSelectionDynamicFeedback(Octarine editor, Point2D initialCoords) {
        super(editor);

        this.initialCoords = initialCoords;
        setMouseTransparent(true);
        OctarineProps props = OctarineProps.getInstance();

        rectangle = new Rectangle();
        rectangle.setStroke(props.getDynamicFeedbackColor());
        rectangle.setFill(FeedbackFormatter.fromColorAndOpacity(props.getDynamicFeedbackColor(), props.getFeedbackOpacityWeak()));

        getChildren().add(rectangle);
    }

    public void setCurrentCoords(Point2D currentCoords) {
        setRectCoords(initialCoords.getX(), currentCoords.getX(), initialCoords.getY(), currentCoords.getY());
    }

    private void setRectCoords(double ix, double cx, double iy, double cy) {
        rectangle.setX((ix <= cx) ? ix : cx);
        rectangle.setY((iy <= cy) ? iy : cy);
        rectangle.setWidth((ix <= cx) ? cx - ix : ix - cx);
        rectangle.setHeight((iy <= cy) ? cy - iy : iy - cy);
    }
}
