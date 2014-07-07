package info.dejv.octarine.tool.selection.editmode.feedback;

import static info.dejv.octarine.utils.FormattingUtils.getDefaultFeedbackStrokeWidth;
import static info.dejv.octarine.utils.FormattingUtils.getFeedbackColor;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.PostConstruct;

import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;

import org.springframework.stereotype.Component;

import info.dejv.octarine.utils.CompositeObservableBounds;
import info.dejv.octarine.utils.ConstantZoomDoubleBinding;
import info.dejv.octarine.utils.FormattingUtils;
import info.dejv.octarine.utils.FormattingUtils.FeedbackOpacity;
import info.dejv.octarine.utils.FormattingUtils.FeedbackType;

/**
 * Static feedback for "Rotate" edit mode
 * <br/>
 * Author: dejv (www.dejv.info)
 */
@Component
public class RotateStaticFeedback
        extends CorneredStaticFeedback {

    private CompositeObservableBounds selectionBounds;

    private HandlePos[] handlePositions = {HandlePos.NE, HandlePos.NW, HandlePos.SE, HandlePos.SW};
    private Group pivotCross;

    @PostConstruct
    public void initRotateStaticFeedback() throws IOException {
        this.pivotCross = FXMLLoader.load(System.class.getResource("/fxml/rotpivot.fxml"));
        FormattingUtils.formatSymbol((SVGPath) pivotCross.lookup("#symbol"));
    }


    public void show(Point2D pivot, CompositeObservableBounds selectionBounds) {
        this.selectionBounds = selectionBounds;
        bindPivotCross();
    }


    @Override
    protected Set<HandlePos> defineHandleSet() {
        final Set<HandlePos> handlePosSet = new HashSet<>();
        Collections.addAll(handlePosSet, handlePositions);
        return super.defineHandleSet();
    }

    @Override
    protected Shape createHandle(HandlePos handlePos) {
        Circle circle = new Circle();

        circle.radiusProperty().bind(size);
        circle.strokeWidthProperty().bind(getDefaultFeedbackStrokeWidth(FormattingUtils.FeedbackType.STATIC));

        circle.setFill(Color.WHITE);
        circle.setStroke(getFeedbackColor(FeedbackType.STATIC, FeedbackOpacity.OPAQUE));
        circle.setStrokeType(StrokeType.OUTSIDE);
        circle.setCursor(Cursor.HAND);

        switch (handlePos) {
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
        return circle;
    }

    @Override
    protected void unbindHandle(Shape handle) {
        assert handle instanceof Circle : "Unexpected handle type";

        final Circle circle = (Circle) handle;
        circle.centerXProperty().unbind();
        circle.centerYProperty().unbind();
        circle.radiusProperty().unbind();
        circle.strokeWidthProperty().unbind();
    }


    private void bindPivotCross() {
        DoubleProperty zoomFactor = octarine.getViewer().zoomFactorProperty();
        ConstantZoomDoubleBinding pivotCrossScale = new ConstantZoomDoubleBinding(zoomFactor, 1.0);
        pivotCross.scaleXProperty().bind(pivotCrossScale);
        pivotCross.scaleYProperty().bind(pivotCrossScale);
    }
}
