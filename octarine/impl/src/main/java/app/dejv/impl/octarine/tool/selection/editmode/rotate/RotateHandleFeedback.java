package app.dejv.impl.octarine.tool.selection.editmode.rotate;

import static app.dejv.impl.octarine.utils.FormattingUtils.getDefaultFeedbackStrokeWidth;
import static app.dejv.impl.octarine.utils.FormattingUtils.getFeedbackColor;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
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

    private ReadOnlyDoubleProperty pivotX;
    private ReadOnlyDoubleProperty pivotY;

    private ReadOnlyDoubleProperty pivotXOffset;
    private ReadOnlyDoubleProperty pivotYOffset;

    private boolean bound = false;
    private boolean usingDefaultPivot = true;


    public RotateHandleFeedback(Octarine octarine, CompositeObservableBounds selectionBounds) throws IOException {
        super(octarine, selectionBounds);

        this.pivotCross = InfrastructureUtils.getRequiredShape(octarine.getResources(), "rotpivot");
        this.pivotCross.getChildren().forEach((child) -> child.setMouseTransparent(false));
        this.pivotCross.setMouseTransparent(false);
        this.pivotCross.setCursor(Cursor.HAND);

        resetPivot();

        getChildren().add(pivotCross);
    }


    public final void setPivot(ReadOnlyDoubleProperty x, ReadOnlyDoubleProperty y) {
        pivotX = x;
        pivotY = y;

        System.out.println("Using pivot");
        usingDefaultPivot = false;
        bindPivotCrossLocation();
    }


    public final void resetPivot() {
        pivotX = null;
        pivotY = null;

        System.out.println("Reset pivot");
        usingDefaultPivot = true;
        bindPivotCrossLocation();
    }


    public final void setPivotOffset(ReadOnlyDoubleProperty ox, ReadOnlyDoubleProperty oy) {
        pivotXOffset = ox;
        pivotYOffset = oy;

        System.out.println("Using offset");
        bindPivotCrossLocation();
    }


    public final void resetPivotOffset() {
        pivotXOffset = null;
        pivotYOffset = null;

        System.out.println("Reset offset");
        bindPivotCrossLocation();
    }


    public double getPivotX() {
        return pivotCross.translateXProperty().get();
    }


    public ReadOnlyDoubleProperty pivotXProperty() {
        return pivotCross.translateXProperty();
    }


    public double getPivotY() {
        return pivotCross.translateYProperty().get();
    }


    public ReadOnlyDoubleProperty pivotYProperty() {
        return pivotCross.translateYProperty();
    }


    public Group getPivotCross() {
        return pivotCross;
    }


    public boolean isUsingDefaultPivot() {
        return usingDefaultPivot;
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
        circle.setCursor(Cursor.CROSSHAIR);

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
        final DoubleProperty zoomFactor = octarine.getView().zoomFactorProperty();
        final ConstantZoomDoubleBinding pivotCrossScale = new ConstantZoomDoubleBinding(zoomFactor, 1.0);
        pivotCross.scaleXProperty().bind(pivotCrossScale);
        pivotCross.scaleYProperty().bind(pivotCrossScale);

        bound = true;

        bindPivotCrossLocation();
    }


    private void unbindPivotCross() {
        bound = false;

        pivotCross.scaleXProperty().unbind();
        pivotCross.scaleYProperty().unbind();
        pivotCross.translateXProperty().unbind();
        pivotCross.translateYProperty().unbind();
    }


    private void bindPivotCrossLocation() {
        if (!bound) {
            return;
        }

        if ((pivotX != null) && (pivotY != null)) {

            if ((pivotXOffset != null) && (pivotYOffset != null)) {

                pivotCross.translateXProperty().bind(pivotX.add(pivotXOffset));
                pivotCross.translateYProperty().bind(pivotY.add(pivotYOffset));
            } else {
                pivotCross.translateXProperty().bind(pivotX);
                pivotCross.translateYProperty().bind(pivotY);
            }
        } else {
            if ((pivotXOffset != null) && (pivotYOffset != null)) {

                pivotCross.translateXProperty().bind(selectionBounds.centerXProperty().add(pivotXOffset));
                pivotCross.translateYProperty().bind(selectionBounds.centerYProperty().add(pivotYOffset));
            }
            else {
                pivotCross.translateXProperty().bind(selectionBounds.centerXProperty());
                pivotCross.translateYProperty().bind(selectionBounds.centerYProperty());
            }
        }
    }
}
