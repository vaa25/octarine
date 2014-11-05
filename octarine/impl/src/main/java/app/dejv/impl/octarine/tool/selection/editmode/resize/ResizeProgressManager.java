package app.dejv.impl.octarine.tool.selection.editmode.resize;

import static java.util.Objects.requireNonNull;

import java.util.Set;

import javafx.geometry.Point2D;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Transform;

import app.dejv.impl.octarine.feedback.handles.Direction;
import app.dejv.impl.octarine.tool.selection.editmode.HandleTransformationManager;
import app.dejv.impl.octarine.tool.selection.editmode.transform.TransformProgressFeedback;
import app.dejv.octarine.input.MouseDragHelperFactory;

/**
 * Resize manager governs the "resize operation in-progress" phase.
 * It reacts on the input from handles, and updates the ResizeProgressFeedback accordingly.
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class ResizeProgressManager
        extends HandleTransformationManager {

    private final TransformProgressFeedback transformProgressFeedback;

    private double originalWidth;
    private double originalHeight;
    private Point2D pivotLocation;
    private Point2D handleLocation;
    private Direction pivotDirection;
    private Scale scale = new Scale();


    public ResizeProgressManager(ResizeHandleFeedback resizeHandleFeedback, TransformProgressFeedback transformProgressFeedback, MouseDragHelperFactory mouseDragHelperFactory) {
        super(resizeHandleFeedback, mouseDragHelperFactory);

        requireNonNull(transformProgressFeedback, "transformProgressFeedback is null");

        this.transformProgressFeedback = transformProgressFeedback;
    }


    @Override
    protected void showTransformationProgressFeedback(Direction direction, Set<Shape> shapes) {
        pivotDirection = direction.getOpposite();
        pivotLocation = getHandleFeedback().getHandleLocation(pivotDirection);

        handleLocation = getHandleFeedback().getHandleLocation(direction);

        originalWidth = handleLocation.getX() - pivotLocation.getX();
        originalHeight = handleLocation.getY() - pivotLocation.getY();

        scale.setPivotX(pivotLocation.getX());
        scale.setPivotY(pivotLocation.getY());

        transformProgressFeedback.activate(shapes, scale);
    }


    @Override
    protected void updateTransformationProgressFeedback(double deltaX, double deltaY) {
        /* Non-uniform resize variant
        /* final double sx = getRatio(originalWidth, deltaX, pivotDirection, Direction.N);
        /* final double sy = getRatio(originalHeight, deltaY, pivotDirection, Direction.E);
         */

        final double r = getUniformRatio(originalWidth, originalHeight, deltaX, deltaY, pivotDirection);

        scale.setX(r);
        scale.setY(r);
    }


    @Override
    protected void hideTransformationFeedback() {
        transformProgressFeedback.deactivate();
    }


    @Override
    protected Transform getTransform() {
        return scale;
    }


    private double getUniformRatio(double originalWidth, double originalHeight, double deltaX, double deltaY, Direction pivotDirection) {
        double origSize = 1;
        double delta = 0;

        if ((pivotDirection == Direction.N) || (pivotDirection == Direction.S)) {
            origSize = originalHeight;
            delta = deltaY;
        }

        if ((pivotDirection == Direction.E) || (pivotDirection == Direction.W)) {
            origSize = originalWidth;
            delta = deltaX;
        }
        if ((pivotDirection == Direction.NW) || (pivotDirection == Direction.SE) || (pivotDirection == Direction.NE) || (pivotDirection == Direction.SW)) {
            origSize = getDiagonal(originalWidth, originalHeight, pivotDirection);
            delta = getRadiusDelta(Math.abs(origSize), deltaX, deltaY, pivotDirection);
        }

        return (origSize + delta) / origSize;
    }

    private double getDiagonal(double x, double y, Direction direction) {
        double result = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        return signByQuadrant(result, x, y, direction);
    }

    private double getRadiusDelta(double origSize, double deltaX, double deltaY, Direction direction) {
        double result = pivotLocation.distance(handleLocation.getX() + deltaX, handleLocation.getY() + deltaY) - origSize;
        return signByQuadrant(Math.abs(result), deltaX, deltaY, direction);
    }

    private double signByQuadrant(double value, double x, double y, Direction direction) {
        switch (direction) {
            case NW:
                if (!((x < 0) && (y < 0)))
                    value *= -1;
                break;
            case NE:
                if (!((x >= 0) && (y < 0)))
                    value *= -1;
                break;
            case SE:
                if (!((x >= 0) && (y >= 0)))
                    value *= -1;
                break;
            case SW:
                if (!((x < 0) && (y >= 0)))
                    value *= -1;
                break;
        }
        return value;
    }


    /* Non-uniform resize variant
    private static double getRatio(double orig, double delta, Direction direction, Direction skip) {
        double r = 1.0;
        if ((direction != skip) && (direction != skip.getOpposite())) {
            r = (orig + delta) / orig;
        }
        return r;
    }
    */
}
