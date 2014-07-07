package info.dejv.octarine.tool.selection.editmode.feedback;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.scene.shape.Shape;

import info.dejv.octarine.feedback.StaticFeedback;
import info.dejv.octarine.utils.ConstantZoomDoubleBinding;

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public abstract class CorneredStaticFeedback
        extends StaticFeedback {

    protected static final double HANDLE_SIZE_HALF = 3.0d;

    protected final Map<HandlePos, Shape> handles = new HashMap<>();

    protected DoubleBinding size;
    protected DoubleBinding sizeHalf;
    protected Set<HandlePos> handleSet;

    private HandlePos[] handlePositions = HandlePos.values();

    @PostConstruct
    public void initCorneredStaticFeedback() {
        DoubleProperty zoom = octarine.getViewer().zoomFactorProperty();
        sizeHalf = new ConstantZoomDoubleBinding(zoom, HANDLE_SIZE_HALF);
        size = new ConstantZoomDoubleBinding(zoom, HANDLE_SIZE_HALF * 2);

        handleSet = defineHandleSet();
    }

    public void initHandles() {
        removeFromScene();

        handleSet.forEach(handleType -> handles.put(handleType, createHandle(handleType)));

        addToScene();
    }

    @Override
    protected void beforeAddToScene() {
        handleSet.forEach(handleType -> handles.put(handleType, createHandle(handleType)));
        handles.values().forEach(handle -> getChildren().add(handle));
    }

    @Override
    protected void afterRemoveFromScene() {
        handles.values().forEach(handle -> {
            unbindHandle(handle);
            getChildren().remove(handle);
        });
        handles.clear();
    }


    protected Set<HandlePos> defineHandleSet() {
        final Set<HandlePos> handlePosSet = new HashSet<HandlePos>();
        Collections.addAll(handlePosSet, handlePositions);
        return handlePosSet;
    }


    protected abstract Shape createHandle(HandlePos handlePos);


    protected abstract void unbindHandle(Shape handle);
}
