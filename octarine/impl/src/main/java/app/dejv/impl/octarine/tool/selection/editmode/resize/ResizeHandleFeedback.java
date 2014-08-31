package app.dejv.impl.octarine.tool.selection.editmode.resize;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;

import app.dejv.impl.octarine.feedback.handles.CorneredHandleFeedback;
import app.dejv.impl.octarine.feedback.handles.Direction;
import app.dejv.impl.octarine.utils.CompositeObservableBounds;
import app.dejv.impl.octarine.utils.FormattingUtils;
import app.dejv.impl.octarine.utils.FormattingUtils.FeedbackOpacity;
import app.dejv.impl.octarine.utils.FormattingUtils.FeedbackType;
import app.dejv.octarine.Octarine;

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class ResizeHandleFeedback
        extends CorneredHandleFeedback {

    public ResizeHandleFeedback(Octarine octarine, CompositeObservableBounds selectionBounds) {
        super(octarine, selectionBounds);
    }


    @Override
    protected Shape createHandle(Direction direction) {
        Rectangle rectangle = new Rectangle();

        rectangle.setFill(Color.WHITE);
        rectangle.setStroke(FormattingUtils.getFeedbackColor(FeedbackType.STATIC, FeedbackOpacity.OPAQUE));
        rectangle.setStrokeType(StrokeType.OUTSIDE);

        return rectangle;
    }


    @Override
    protected void bindHandle(Shape handle, Direction direction) {
        assert handle instanceof Rectangle : "Expected handles of type Rectangle";

        bindRectangle((Rectangle) handle, direction);
    }


    @Override
    protected void unbindHandle(Shape handle) {
        assert handle instanceof Rectangle : "Expected handles of type Rectangle";

        unbindRectangle((Rectangle) handle);
    }




    private void bindRectangle(Rectangle rectangle, Direction direction) {
        rectangle.widthProperty().bind(size);
        rectangle.heightProperty().bind(size);
        rectangle.strokeWidthProperty().bind(FormattingUtils.getDefaultFeedbackStrokeWidth(FormattingUtils.FeedbackType.STATIC));
        rectangle.cursorProperty().setValue(direction.getCursor());
        rectangle.setId("Resize handles " + direction);
        switch (direction) {
            case N:
                rectangle.xProperty().bind(selectionBounds.centerXProperty().subtract(sizeHalf));
                rectangle.yProperty().bind(selectionBounds.minYProperty().subtract(sizeHalf));
                break;
            case NE:
                rectangle.xProperty().bind(selectionBounds.maxXProperty().subtract(sizeHalf));
                rectangle.yProperty().bind(selectionBounds.minYProperty().subtract(sizeHalf));
                break;
            case E:
                rectangle.xProperty().bind(selectionBounds.maxXProperty().subtract(sizeHalf));
                rectangle.yProperty().bind(selectionBounds.centerYProperty().subtract(sizeHalf));
                break;
            case SE:
                rectangle.xProperty().bind(selectionBounds.maxXProperty().subtract(sizeHalf));
                rectangle.yProperty().bind(selectionBounds.maxYProperty().subtract(sizeHalf));
                break;
            case S:
                rectangle.xProperty().bind(selectionBounds.centerXProperty().subtract(sizeHalf));
                rectangle.yProperty().bind(selectionBounds.maxYProperty().subtract(sizeHalf));
                break;
            case SW:
                rectangle.xProperty().bind(selectionBounds.minXProperty().subtract(sizeHalf));
                rectangle.yProperty().bind(selectionBounds.maxYProperty().subtract(sizeHalf));
                break;
            case W:
                rectangle.xProperty().bind(selectionBounds.minXProperty().subtract(sizeHalf));
                rectangle.yProperty().bind(selectionBounds.centerYProperty().subtract(sizeHalf));
                break;
            case NW:
                rectangle.xProperty().bind(selectionBounds.minXProperty().subtract(sizeHalf));
                rectangle.yProperty().bind(selectionBounds.minYProperty().subtract(sizeHalf));
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
