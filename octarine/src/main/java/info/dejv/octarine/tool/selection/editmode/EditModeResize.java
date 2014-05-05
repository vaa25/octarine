package info.dejv.octarine.tool.selection.editmode;

import info.dejv.octarine.Octarine;
import info.dejv.octarine.cfg.OctarineProps;
import info.dejv.octarine.tool.selection.ExclusivityCoordinator;
import info.dejv.octarine.tool.selection.request.ResizeRequest;
import info.dejv.octarine.utils.CompositeBounds;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * "Scale" edit mode<br/>
 * On handle drag it scales the selection in appropriate direction
 * <br/>
 * Author: dejv (www.dejv.info)
 */
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

    private final CompositeBounds selectionBounds = new CompositeBounds();
    private final Map<HandlePos, Rectangle> handles = new HashMap<>();

    public EditModeResize(Octarine octarine, ExclusivityCoordinator listener) {
        super(ResizeRequest.class, octarine, listener);
    }


    @Override
    protected KeyCode getActivationKey() {
        return KeyCode.S;
    }


    @Override
    protected void doActivate() {
        handles.clear();
        selectionBounds.clear();

        selection.stream().forEach((controller) -> {
            selectionBounds.add(controller.getView().boundsInParentProperty());
        });

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
        r.setWidth(HANDLE_SIZE_HALF * 2);
        r.setHeight(HANDLE_SIZE_HALF * 2);
        r.setFill(Color.WHITE);
        r.setStroke(OctarineProps.getInstance().getStaticFeedbackColor());
        switch (h) {
            case N:
                r.xProperty().bind(selectionBounds.centerXProperty().subtract(HANDLE_SIZE_HALF));
                r.yProperty().bind(selectionBounds.minYProperty().subtract(HANDLE_SIZE_HALF));
                r.setCursor(Cursor.N_RESIZE);
                break;
            case NE:
                r.xProperty().bind(selectionBounds.maxXProperty().subtract(HANDLE_SIZE_HALF));
                r.yProperty().bind(selectionBounds.minYProperty().subtract(HANDLE_SIZE_HALF));
                r.setCursor(Cursor.NE_RESIZE);
                break;
            case E:
                r.xProperty().bind(selectionBounds.maxXProperty().subtract(HANDLE_SIZE_HALF));
                r.yProperty().bind(selectionBounds.centerYProperty().subtract(HANDLE_SIZE_HALF));
                r.setCursor(Cursor.E_RESIZE);
                break;
            case SE:
                r.xProperty().bind(selectionBounds.maxXProperty().subtract(HANDLE_SIZE_HALF));
                r.yProperty().bind(selectionBounds.maxYProperty().subtract(HANDLE_SIZE_HALF));
                r.setCursor(Cursor.SE_RESIZE);
                break;
            case S:
                r.xProperty().bind(selectionBounds.centerXProperty().subtract(HANDLE_SIZE_HALF));
                r.yProperty().bind(selectionBounds.maxYProperty().subtract(HANDLE_SIZE_HALF));
                r.setCursor(Cursor.S_RESIZE);
                break;
            case SW:
                r.xProperty().bind(selectionBounds.minXProperty().subtract(HANDLE_SIZE_HALF));
                r.yProperty().bind(selectionBounds.maxYProperty().subtract(HANDLE_SIZE_HALF));
                r.setCursor(Cursor.SW_RESIZE);
                break;
            case W:
                r.xProperty().bind(selectionBounds.minXProperty().subtract(HANDLE_SIZE_HALF));
                r.yProperty().bind(selectionBounds.centerYProperty().subtract(HANDLE_SIZE_HALF));
                r.setCursor(Cursor.W_RESIZE);
                break;
            case NW:
                r.xProperty().bind(selectionBounds.minXProperty().subtract(HANDLE_SIZE_HALF));
                r.yProperty().bind(selectionBounds.minYProperty().subtract(HANDLE_SIZE_HALF));
                r.setCursor(Cursor.NW_RESIZE);
                break;
        }
        return r;
    }
}
