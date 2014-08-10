package app.dejv.impl.octarine.tool.selection.editmode.feedback;

import javafx.scene.Cursor;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;

import app.dejv.impl.octarine.utils.CompositeObservableBounds;
import app.dejv.impl.octarine.utils.FormattingUtils;
import app.dejv.impl.octarine.utils.FormattingUtils.FeedbackOpacity;
import app.dejv.impl.octarine.utils.FormattingUtils.FeedbackType;
import app.dejv.octarine.Octarine;

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class ResizeStaticFeedback
        extends CorneredStaticFeedback {

    public ResizeStaticFeedback(Octarine octarine, CompositeObservableBounds selectionBounds) {
        super(octarine, selectionBounds);
    }


    @Override
    protected Shape createHandle(HandlePos handlePos) {
        Rectangle rectangle = new Rectangle();

        rectangle.setFill(Color.WHITE);
        rectangle.setStroke(FormattingUtils.getFeedbackColor(FeedbackType.STATIC, FeedbackOpacity.OPAQUE));
        rectangle.setStrokeType(StrokeType.OUTSIDE);

        return rectangle;
    }


    @Override
    protected void bindHandle(Shape handle, HandlePos handlePos) {
        assert handle instanceof Rectangle : "Expected handle of type Rectangle";

        bindRectangle((Rectangle) handle, handlePos);
    }


    @Override
    protected void unbindHandle(Shape handle) {
        assert handle instanceof Rectangle : "Expected handle of type Rectangle";

        unbindRectangle((Rectangle) handle);
    }


    private void bindRectangle(Rectangle rectangle, HandlePos handlePos) {
        rectangle.widthProperty().bind(size);
        rectangle.heightProperty().bind(size);
        rectangle.strokeWidthProperty().bind(FormattingUtils.getDefaultFeedbackStrokeWidth(FormattingUtils.FeedbackType.STATIC));

        switch (handlePos) {
            case N:
                rectangle.xProperty().bind(selectionBounds.centerXProperty().subtract(sizeHalf));
                rectangle.yProperty().bind(selectionBounds.minYProperty().subtract(sizeHalf));
                rectangle.setCursor(Cursor.N_RESIZE);
                break;
            case NE:
                rectangle.xProperty().bind(selectionBounds.maxXProperty().subtract(sizeHalf));
                rectangle.yProperty().bind(selectionBounds.minYProperty().subtract(sizeHalf));
                rectangle.setCursor(Cursor.NE_RESIZE);
                break;
            case E:
                rectangle.xProperty().bind(selectionBounds.maxXProperty().subtract(sizeHalf));
                rectangle.yProperty().bind(selectionBounds.centerYProperty().subtract(sizeHalf));
                rectangle.setCursor(Cursor.E_RESIZE);
                break;
            case SE:
                rectangle.xProperty().bind(selectionBounds.maxXProperty().subtract(sizeHalf));
                rectangle.yProperty().bind(selectionBounds.maxYProperty().subtract(sizeHalf));
                rectangle.setCursor(Cursor.SE_RESIZE);
                break;
            case S:
                rectangle.xProperty().bind(selectionBounds.centerXProperty().subtract(sizeHalf));
                rectangle.yProperty().bind(selectionBounds.maxYProperty().subtract(sizeHalf));
                rectangle.setCursor(Cursor.S_RESIZE);
                break;
            case SW:
                rectangle.xProperty().bind(selectionBounds.minXProperty().subtract(sizeHalf));
                rectangle.yProperty().bind(selectionBounds.maxYProperty().subtract(sizeHalf));
                rectangle.setCursor(Cursor.SW_RESIZE);
                break;
            case W:
                rectangle.xProperty().bind(selectionBounds.minXProperty().subtract(sizeHalf));
                rectangle.yProperty().bind(selectionBounds.centerYProperty().subtract(sizeHalf));
                rectangle.setCursor(Cursor.W_RESIZE);
                break;
            case NW:
                rectangle.xProperty().bind(selectionBounds.minXProperty().subtract(sizeHalf));
                rectangle.yProperty().bind(selectionBounds.minYProperty().subtract(sizeHalf));
                rectangle.setCursor(Cursor.NW_RESIZE);
                break;
        }
    }


    private void unbindRectangle(Rectangle rectangle) {
        rectangle.xProperty().unbind();
        rectangle.yProperty().unbind();
        rectangle.widthProperty().unbind();
        rectangle.heightProperty().unbind();
        rectangle.strokeWidthProperty().unbind();
    }
}
