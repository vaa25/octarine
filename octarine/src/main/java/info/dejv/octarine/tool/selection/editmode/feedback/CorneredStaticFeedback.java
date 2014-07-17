package info.dejv.octarine.tool.selection.editmode.feedback;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.geometry.Bounds;
import javafx.scene.shape.Shape;

import org.springframework.beans.factory.annotation.Autowired;

import info.dejv.octarine.controller.Controller;
import info.dejv.octarine.feedback.StaticFeedback;
import info.dejv.octarine.utils.CompositeObservableBounds;
import info.dejv.octarine.utils.ConstantZoomDoubleBinding;

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public abstract class CorneredStaticFeedback
        extends StaticFeedback {

    protected static final double HANDLE_SIZE_HALF = 3.0d;
    protected final Map<HandlePos, Shape> handles = new HashMap<>();
    @Autowired
    protected CompositeObservableBounds selectionBounds;
    protected DoubleBinding size;
    protected DoubleBinding sizeHalf;
    protected Set<HandlePos> handleSet;

    private HandlePos[] handlePositions = HandlePos.values();

    @Override
    public void init() throws Exception {
        super.init();

        DoubleProperty zoom = octarine.getViewer().zoomFactorProperty();
        sizeHalf = new ConstantZoomDoubleBinding(zoom, HANDLE_SIZE_HALF);
        size = new ConstantZoomDoubleBinding(zoom, HANDLE_SIZE_HALF * 2);

        handleSet = defineHandleSet();
    }

    public void show(Set<Controller> selection) {
        updateBounds(selection);
    }


    protected void updateBounds(Set<Controller> selection) {

        final Stream<ReadOnlyObjectProperty<Bounds>> boundsStream = selection.stream()
                .map((controller) -> controller.getView().boundsInParentProperty());

        selectionBounds.clear();

        boundsStream.forEach(selectionBounds::add);
        initHandles();
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

    private void initHandles() {
        removeFromScene();

        handleSet.forEach(handleType -> handles.put(handleType, createHandle(handleType)));

        addToScene();
    }

}
