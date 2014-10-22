package app.dejv.impl.octarine.tool.selection.editmode.rotate;

import static java.util.Objects.requireNonNull;

import java.util.Set;

import javafx.geometry.Point2D;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;

import app.dejv.impl.octarine.feedback.handles.Direction;
import app.dejv.impl.octarine.tool.selection.editmode.HandleTransformationManager;
import app.dejv.impl.octarine.tool.selection.editmode.transform.TransformProgressFeedback;
import app.dejv.impl.octarine.utils.GeometryUtils;
import app.dejv.octarine.input.MouseDragHelperFactory;

/**
 * Resize manager governs the "resize operation in-progress" phase.
 * It reacts on the input from handles, and updates the ResizeProgressFeedback accordingly.
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class RotateProgressManager
        extends HandleTransformationManager {

    private final TransformProgressFeedback rotateProgressFeedback;

    private double originalAngle;
    private Point2D locPivot;
    private Point2D locHandle;
    private Rotate rotate = new Rotate();



    public RotateProgressManager(RotateHandleFeedback rotateHandleFeedback, TransformProgressFeedback rotateProgressFeedback, MouseDragHelperFactory mouseDragHelperFactory) {
        super(rotateHandleFeedback, mouseDragHelperFactory);

        requireNonNull(rotateProgressFeedback, "rotateProgressFeedback is null");

        this.rotateProgressFeedback = rotateProgressFeedback;
        rotate.setAxis(Rotate.Z_AXIS);
    }


    @Override
    protected void showTransformationProgressFeedback(Direction direction, Set<Shape> shapes) {

        final RotateHandleFeedback handleFeedback = (RotateHandleFeedback) getHandleFeedback();

        locPivot = new Point2D(handleFeedback.getPivotX(), handleFeedback.getPivotY());
        locHandle = handleFeedback.getHandleLocation(direction);

        originalAngle = GeometryUtils.angle(locPivot, locHandle);

        rotate.pivotXProperty().bind(handleFeedback.pivotXProperty());
        rotate.pivotYProperty().bind(handleFeedback.pivotYProperty());

        rotateProgressFeedback.activate(shapes, rotate);
    }


    @Override
    protected void updateTransformationProgressFeedback(double deltaX, double deltaY) {
        final double currentAngle = GeometryUtils.angle(locPivot, new Point2D(locHandle.getX() + deltaX, locHandle.getY() + deltaY));
        rotate.setAngle(currentAngle - originalAngle);
    }


    @Override
    protected void hideTransformationFeedback() {
        rotateProgressFeedback.deactivate();
    }


    @Override
    protected Transform getTransform() {
        return rotate;
    }


    private static double getRatio(double orig, double delta, Direction direction, Direction skip) {
        double r = 1.0;
        if ((direction != skip) && (direction != skip.getOpposite())) {
            r = (orig + delta) / orig;
        }
        return r;
    }

}
