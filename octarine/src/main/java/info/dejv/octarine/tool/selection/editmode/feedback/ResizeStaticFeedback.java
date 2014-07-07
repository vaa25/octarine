package info.dejv.octarine.tool.selection.editmode.feedback;

import static info.dejv.octarine.utils.FormattingUtils.getDefaultFeedbackStrokeWidth;
import static info.dejv.octarine.utils.FormattingUtils.getFeedbackColor;

import javafx.scene.Cursor;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;

import org.springframework.stereotype.Component;

import info.dejv.octarine.utils.CompositeObservableBounds;
import info.dejv.octarine.utils.FormattingUtils;
import info.dejv.octarine.utils.FormattingUtils.FeedbackOpacity;
import info.dejv.octarine.utils.FormattingUtils.FeedbackType;

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
@Component
public class ResizeStaticFeedback
        extends CorneredStaticFeedback {

    private CompositeObservableBounds selectionBounds;


    public void show(CompositeObservableBounds selectionBounds) {
        this.selectionBounds = selectionBounds;
        initHandles();
    }


    @Override
    protected Shape createHandle(HandlePos handlePos) {
        Rectangle rectangle = new Rectangle();

        rectangle.widthProperty().bind(size);
        rectangle.heightProperty().bind(size);
        rectangle.strokeWidthProperty().bind(getDefaultFeedbackStrokeWidth(FormattingUtils.FeedbackType.STATIC));

        rectangle.setFill(Color.WHITE);
        rectangle.setStroke(getFeedbackColor(FeedbackType.STATIC, FeedbackOpacity.OPAQUE));
        rectangle.setStrokeType(StrokeType.OUTSIDE);

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
        return rectangle;
    }

    @Override
    protected void unbindHandle(Shape handle) {
        assert handle instanceof Rectangle : "Unexpected handle type";

        final Rectangle r = (Rectangle)handle;
        r.xProperty().unbind();
        r.yProperty().unbind();
        r.widthProperty().unbind();
        r.heightProperty().unbind();
        r.strokeWidthProperty().unbind();
    }
}
