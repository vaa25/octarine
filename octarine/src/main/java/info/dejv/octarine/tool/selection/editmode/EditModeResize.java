package info.dejv.octarine.tool.selection.editmode;

import static info.dejv.octarine.utils.FormattingUtils.getDefaultFeedbackStrokeWidth;
import static info.dejv.octarine.utils.FormattingUtils.getFeedbackColor;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

import org.springframework.stereotype.Component;

import info.dejv.octarine.tool.selection.ExclusivityCoordinator;
import info.dejv.octarine.tool.selection.request.ResizeRequest;
import info.dejv.octarine.utils.CompositeObservableBounds;
import info.dejv.octarine.utils.ConstantZoomDoubleBinding;
import info.dejv.octarine.utils.FormattingUtils;
import info.dejv.octarine.utils.FormattingUtils.FeedbackOpacity;
import info.dejv.octarine.utils.FormattingUtils.FeedbackType;

/**
 * "Scale" edit mode<br/>
 * On handle drag it scales the selection in appropriate direction
 * <br/>
 * Author: dejv (www.dejv.info)
 */
@Component
public class EditModeResize
        extends AbstractExclusiveEditMode {

    private static final double HANDLE_SIZE_HALF = 3.0d;

    private enum HandlePos {

        N,
        NE,
        E,
        SE,
        S,
        SW,
        W,
        NW
    }

    private final CompositeObservableBounds selectionBounds = new CompositeObservableBounds();
    private final Map<HandlePos, Rectangle> handles = new HashMap<>();

    private DoubleBinding size;
    private DoubleBinding sizeHalf;


    public EditModeResize() {
        super(ResizeRequest.class);

    }


    @PostConstruct
    public void initEditModeResize() {
        DoubleProperty zoom = getOctarine().getViewer().zoomFactorProperty();
        sizeHalf = new ConstantZoomDoubleBinding(zoom, HANDLE_SIZE_HALF);
        size = new ConstantZoomDoubleBinding(zoom, HANDLE_SIZE_HALF * 2);
    }


    @Override
    public EditModeResize setExclusivityCoordinator(ExclusivityCoordinator exclusivityCoordinator) {
        return (EditModeResize) super.setExclusivityCoordinator(exclusivityCoordinator);
    }

    @Override
    protected KeyCode getActivationKey() {
        return KeyCode.S;
    }


    @Override
    protected void doActivate() {
        handles.clear();
        selectionBounds.clear();

        selection.forEach((controller) -> selectionBounds.add(controller.getView().boundsInParentProperty()));

        ObservableList<Node> feedback = getOctarine().getFeedback();

        for (HandlePos h : HandlePos.values()) {
            Rectangle r = createHandle(h);
            handles.put(h, r);
            feedback.add(r);
        }

    }


    @Override
    protected void doDeactivate() {
        ObservableList<Node> feedback = getOctarine().getFeedback();

        for (HandlePos h : HandlePos.values()) {
            Rectangle r = handles.get(h);
            feedback.remove(r);
        }
        handles.clear();
        selectionBounds.clear();
    }


    private Rectangle createHandle(HandlePos h) {
        Rectangle r = new Rectangle();

        r.widthProperty().bind(size);
        r.heightProperty().bind(size);
        r.strokeWidthProperty().bind(getDefaultFeedbackStrokeWidth(FormattingUtils.FeedbackType.STATIC));

        r.setFill(Color.WHITE);
        r.setStroke(getFeedbackColor(FeedbackType.STATIC, FeedbackOpacity.OPAQUE));
        r.setStrokeType(StrokeType.OUTSIDE);

        switch (h) {
            case N:
                r.xProperty().bind(selectionBounds.centerXProperty().subtract(sizeHalf));
                r.yProperty().bind(selectionBounds.minYProperty().subtract(sizeHalf));
                r.setCursor(Cursor.N_RESIZE);
                break;
            case NE:
                r.xProperty().bind(selectionBounds.maxXProperty().subtract(sizeHalf));
                r.yProperty().bind(selectionBounds.minYProperty().subtract(sizeHalf));
                r.setCursor(Cursor.NE_RESIZE);
                break;
            case E:
                r.xProperty().bind(selectionBounds.maxXProperty().subtract(sizeHalf));
                r.yProperty().bind(selectionBounds.centerYProperty().subtract(sizeHalf));
                r.setCursor(Cursor.E_RESIZE);
                break;
            case SE:
                r.xProperty().bind(selectionBounds.maxXProperty().subtract(sizeHalf));
                r.yProperty().bind(selectionBounds.maxYProperty().subtract(sizeHalf));
                r.setCursor(Cursor.SE_RESIZE);
                break;
            case S:
                r.xProperty().bind(selectionBounds.centerXProperty().subtract(sizeHalf));
                r.yProperty().bind(selectionBounds.maxYProperty().subtract(sizeHalf));
                r.setCursor(Cursor.S_RESIZE);
                break;
            case SW:
                r.xProperty().bind(selectionBounds.minXProperty().subtract(sizeHalf));
                r.yProperty().bind(selectionBounds.maxYProperty().subtract(sizeHalf));
                r.setCursor(Cursor.SW_RESIZE);
                break;
            case W:
                r.xProperty().bind(selectionBounds.minXProperty().subtract(sizeHalf));
                r.yProperty().bind(selectionBounds.centerYProperty().subtract(sizeHalf));
                r.setCursor(Cursor.W_RESIZE);
                break;
            case NW:
                r.xProperty().bind(selectionBounds.minXProperty().subtract(sizeHalf));
                r.yProperty().bind(selectionBounds.minYProperty().subtract(sizeHalf));
                r.setCursor(Cursor.NW_RESIZE);
                break;
        }
        return r;
    }
}
