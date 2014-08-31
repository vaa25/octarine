package app.dejv.impl.octarine.tool.selection.editmode.rotate;

import static app.dejv.impl.octarine.utils.FormattingUtils.getDefaultFeedbackStrokeWidth;
import static app.dejv.impl.octarine.utils.FormattingUtils.getFeedbackColor;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import javafx.beans.property.DoubleProperty;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;

import app.dejv.impl.octarine.feedback.handles.CorneredHandleFeedback;
import app.dejv.impl.octarine.feedback.handles.Direction;
import app.dejv.impl.octarine.utils.CompositeObservableBounds;
import app.dejv.impl.octarine.utils.ConstantZoomDoubleBinding;
import app.dejv.impl.octarine.utils.FormattingUtils.FeedbackOpacity;
import app.dejv.impl.octarine.utils.FormattingUtils.FeedbackType;
import app.dejv.impl.octarine.utils.InfrastructureUtils;
import app.dejv.octarine.Octarine;

/**
 * Static feedback for "Rotate" edit mode
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class RotateHandleFeedback
        extends CorneredHandleFeedback {

    private final Group pivotCross;


    public RotateHandleFeedback(Octarine octarine, CompositeObservableBounds selectionBounds) throws IOException {
        super(octarine, selectionBounds);

        this.pivotCross = InfrastructureUtils.getRequiredShape(octarine.getResources(), "rotpivot");
    }


    @Override
    protected void beforeActivate() {
        super.beforeActivate();

        bindPivotCross();
    }


    @Override
    protected void afterDeactivate() {
        unbindPivotCross();

        super.afterDeactivate();
    }


    @Override
    protected void fillHandlePositions(Set<Direction> handleSet) {
        Collections.addAll(handleSet, CORNER_HANDLE_POSITIONS);
    }


    @Override
    protected Shape createHandle(Direction direction) {
        final Circle circle = new Circle();

        circle.setFill(Color.WHITE);
        circle.setStroke(getFeedbackColor(FeedbackType.STATIC, FeedbackOpacity.OPAQUE));
        circle.setStrokeType(StrokeType.OUTSIDE);
        circle.setCursor(Cursor.HAND);

        bindCircle(circle, direction);
        return circle;
    }


    @Override
    protected void bindHandle(Shape handle, Direction direction) {
        assert handle instanceof Circle : "Expected handles of type Circle";

        bindCircle((Circle) handle, direction);
    }


    @Override
    protected void unbindHandle(Shape handle) {
        assert handle instanceof Circle : "Expected handles of type Circle";

        unbindCircle((Circle) handle);
    }


    private void bindCircle(Circle circle, Direction direction) {
        circle.radiusProperty().bind(sizeHalf);
        circle.strokeWidthProperty().bind(getDefaultFeedbackStrokeWidth(FeedbackType.STATIC));

        switch (direction) {
            case NE:
                circle.centerXProperty().bind(selectionBounds.maxXProperty());
                circle.centerYProperty().bind(selectionBounds.minYProperty());
                break;
            case SE:
                circle.centerXProperty().bind(selectionBounds.maxXProperty());
                circle.centerYProperty().bind(selectionBounds.maxYProperty());
                break;
            case SW:
                circle.centerXProperty().bind(selectionBounds.minXProperty());
                circle.centerYProperty().bind(selectionBounds.maxYProperty());
                break;
            case NW:
                circle.centerXProperty().bind(selectionBounds.minXProperty());
                circle.centerYProperty().bind(selectionBounds.minYProperty());
                break;
        }
    }


    private void unbindCircle(Circle circle) {
        circle.centerXProperty().unbind();
        circle.centerYProperty().unbind();
        circle.radiusProperty().unbind();
        circle.strokeWidthProperty().unbind();
    }


    private void bindPivotCross() {
        DoubleProperty zoomFactor = octarine.getView().zoomFactorProperty();
        ConstantZoomDoubleBinding pivotCrossScale = new ConstantZoomDoubleBinding(zoomFactor, 1.0);
        pivotCross.scaleXProperty().bind(pivotCrossScale);
        pivotCross.scaleYProperty().bind(pivotCrossScale);
    }


    private void unbindPivotCross() {
        pivotCross.scaleXProperty().unbind();
        pivotCross.scaleYProperty().unbind();
    }
}
