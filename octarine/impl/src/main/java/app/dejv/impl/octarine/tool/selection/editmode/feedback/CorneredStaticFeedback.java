package app.dejv.impl.octarine.tool.selection.editmode.feedback;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.scene.shape.Shape;

import app.dejv.impl.octarine.feedback.StaticFeedback;
import app.dejv.impl.octarine.utils.CompositeObservableBounds;
import app.dejv.impl.octarine.utils.ConstantZoomDoubleBinding;
import app.dejv.octarine.Octarine;

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public abstract class CorneredStaticFeedback
        extends StaticFeedback {

    public static final HandlePos[] ALL_HANDLE_POSITIONS = HandlePos.values();
    public static final HandlePos[] CORNER_HANDLE_POSITIONS = {HandlePos.NE, HandlePos.NW, HandlePos.SE, HandlePos.SW};

    public static final double HANDLE_SIZE = 6.0d;

    protected final CompositeObservableBounds selectionBounds;
    protected final DoubleBinding size;
    protected final DoubleBinding sizeHalf;
    protected final Map<HandlePos, Shape> handles = new HashMap<>();
    protected Set<HandlePos> handleSet = new HashSet<>();


    protected CorneredStaticFeedback(Octarine octarine, CompositeObservableBounds selectionBounds) {
        super(octarine);

        final DoubleProperty zoom = octarine.getView().zoomFactorProperty();

        this.selectionBounds = selectionBounds;
        this.size = new ConstantZoomDoubleBinding(zoom, HANDLE_SIZE);
        this.sizeHalf = new ConstantZoomDoubleBinding(zoom, HANDLE_SIZE / 2.0d);
    }


    @Override
    protected void beforeActivate() {
        ensureHandleSetIsValid();
        ensureHandlesAreValid();

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


    protected void fillHandlePositions(final Set<HandlePos> handleSet) {
        Collections.addAll(handleSet, ALL_HANDLE_POSITIONS);
    }


    protected abstract Shape createHandle(HandlePos handlePos);

    protected abstract void bindHandle(Shape handle, HandlePos handlePos);

    protected abstract void unbindHandle(Shape handle);


    private void ensureHandleSetIsValid() {
        if (handleSet.isEmpty()) {
            fillHandlePositions(handleSet);
        }
    }


    private void ensureHandlesAreValid() {
        if (handles.isEmpty()) {
            handleSet.forEach(handleType -> handles.put(handleType, createHandle(handleType)));
        }
    }

}
