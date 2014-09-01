package app.dejv.impl.octarine.tool.selection.editmode.resize;

import static java.util.Objects.requireNonNull;

import java.util.Set;

import javafx.geometry.Point2D;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Transform;

import app.dejv.impl.octarine.feedback.handles.Direction;
import app.dejv.impl.octarine.tool.selection.editmode.HandleTransformationManager;
import app.dejv.octarine.input.MouseDragHelperFactory;

/**
 * Resize manager governs the "resize operation in-progress" phase.
 * It reacts on the input from handles, and updates the ResizeProgressFeedback accordingly.
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class ResizeProgressManager
        extends HandleTransformationManager {

    private final ResizeProgressFeedback resizeProgressFeedback;

    private double originalWidth;
    private double originalHeight;
    private Direction pivotDirection;
    private Point2D locPivot;
    private Scale scale;



    public ResizeProgressManager(ResizeHandleFeedback resizeHandleFeedback, ResizeProgressFeedback resizeProgressFeedback, MouseDragHelperFactory mouseDragHelperFactory) {
        super(resizeHandleFeedback, mouseDragHelperFactory);

        requireNonNull(resizeProgressFeedback, "resizeProgressFeedback is null");

        this.resizeProgressFeedback = resizeProgressFeedback;
    }


    @Override
    protected void showTransformationProgressFeedback(Direction direction, Set<Shape> shapes) {
        pivotDirection = direction.getOpposite();
        locPivot = getHandleFeedback().getHandleLocation(pivotDirection);

        final Point2D locHandle = getHandleFeedback().getHandleLocation(direction);

        originalWidth = locHandle.getX()-locPivot.getX();
        originalHeight = locHandle.getY()-locPivot.getY();

        scale = new Scale();
        scale.setPivotX(locPivot.getX());
        scale.setPivotY(locPivot.getY());

        resizeProgressFeedback.activate(shapes, scale);
    }


    @Override
    protected void updateTransformationProgressFeedback(double deltaX, double deltaY) {
        final double sx = getRatio(originalWidth, deltaX, pivotDirection, Direction.N);
        final double sy = getRatio(originalHeight, deltaY, pivotDirection, Direction.E);

        scale.setX(sx);
        scale.setY(sy);
    }


    @Override
    protected void hideTransformationFeedback() {
        resizeProgressFeedback.deactivate();
    }


    @Override
    protected Transform getTransform() {
        return scale;
    }


    private static double getRatio(double orig, double delta, Direction direction, Direction skip) {
        double r = 1.0;
        if ((direction != skip) && (direction != skip.getOpposite())) {
            r = (orig + delta) / orig;
        }
        return r;
    }

}
