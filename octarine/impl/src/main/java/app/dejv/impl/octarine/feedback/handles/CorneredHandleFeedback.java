package app.dejv.impl.octarine.feedback.handles;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Point2D;
import javafx.scene.shape.Shape;

import app.dejv.impl.octarine.utils.CompositeObservableBounds;
import app.dejv.impl.octarine.utils.ConstantZoomDoubleBinding;
import app.dejv.octarine.Octarine;

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public abstract class CorneredHandleFeedback
        extends HandleFeedback {

    public static final Direction[] ALL_HANDLE_POSITIONS = Direction.values();
    public static final Direction[] CORNER_HANDLE_POSITIONS = {Direction.NE, Direction.NW, Direction.SE, Direction.SW};

    public static final double HANDLE_SIZE = 6.0d;

    protected final CompositeObservableBounds selectionBounds;
    protected final DoubleBinding size;
    protected final DoubleBinding sizeHalf;
    protected final Map<Direction, Shape> handles = new HashMap<>();
    protected Set<Direction> handleSet = new HashSet<>();


    @SuppressWarnings({"OverridableMethodCallDuringObjectConstruction", "OverriddenMethodCallDuringObjectConstruction", "AbstractMethodCallInConstructor"})
    protected CorneredHandleFeedback(Octarine octarine, CompositeObservableBounds selectionBounds) {
        super(octarine);

        final DoubleProperty zoom = octarine.getView().zoomFactorProperty();

        this.selectionBounds = selectionBounds;
        this.size = new ConstantZoomDoubleBinding(zoom, HANDLE_SIZE);
        this.sizeHalf = new ConstantZoomDoubleBinding(zoom, HANDLE_SIZE / 2.0d);

        fillHandlePositions(handleSet);
        handleSet.forEach(handleType -> handles.put(handleType, createHandle(handleType)));

    }


    public Map<Direction, Shape> getHandles() {
        return handles;
    }


    public Point2D getHandleLocation(Direction pos) {
        requireNonNull(pos, "pos is null");

        switch (pos) {
            case N: return new Point2D(selectionBounds.getCenterX(), selectionBounds.getMinY());
            case NE: return new Point2D(selectionBounds.getMaxX(), selectionBounds.getMinY());
            case E: return new Point2D(selectionBounds.getMaxX(), selectionBounds.getCenterY());
            case SE: return new Point2D(selectionBounds.getMaxX(), selectionBounds.getMaxY());
            case S: return new Point2D(selectionBounds.getCenterX(), selectionBounds.getMaxY());
            case SW: return new Point2D(selectionBounds.getMinX(), selectionBounds.getMaxY());
            case W: return new Point2D(selectionBounds.getMinX(), selectionBounds.getCenterY());
            case NW: return new Point2D(selectionBounds.getMinX(), selectionBounds.getMinY());
        }
        return null;
    }


    @Override
    protected void beforeActivate() {
        super.beforeActivate();

        handles.values().forEach(handle -> getChildren().add(handle));
    }


    @Override
    protected void afterDeactivate() {
        handles.values().forEach(handle -> getChildren().remove(handle));

        super.afterDeactivate();
    }


    @Override
    protected void bind() {
        super.bind();
        handles.forEach((handlePos, handle) -> bindHandle(handle, handlePos));
    }


    @Override
    protected void unbind() {
        handles.values().forEach(this::unbindHandle);
        super.unbind();
    }


    protected void fillHandlePositions(final Set<Direction> handleSet) {
        Collections.addAll(handleSet, ALL_HANDLE_POSITIONS);
    }


    protected abstract Shape createHandle(Direction direction);

    protected abstract void bindHandle(Shape handle, Direction direction);

    protected abstract void unbindHandle(Shape handle);

}
