package app.dejv.impl.octarine.tool.selection.editmode;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javafx.scene.shape.Shape;
import javafx.scene.transform.Transform;

import app.dejv.impl.octarine.feedback.handles.CorneredHandleFeedback;
import app.dejv.impl.octarine.feedback.handles.Direction;
import app.dejv.octarine.input.MouseDragHelper;
import app.dejv.octarine.input.MouseDragHelperFactory;
import app.dejv.octarine.input.MouseDragListener;

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public abstract class HandleTransformationManager {

    private final CorneredHandleFeedback corneredHandleFeedback;
    private final Map<Direction, MouseDragHelper> mouseDragHelpers = new HashMap<>();
    private final MouseDragHelperFactory mouseDragHelperFactory;


    public HandleTransformationManager(CorneredHandleFeedback corneredHandleFeedback, MouseDragHelperFactory mouseDragHelperFactory) {
        requireNonNull(corneredHandleFeedback, "corneredHandleFeedback is null");
        requireNonNull(mouseDragHelperFactory, "mouseDragHelperFactory is null");

        this.corneredHandleFeedback = corneredHandleFeedback;
        this.mouseDragHelperFactory = mouseDragHelperFactory;

        corneredHandleFeedback.getHandles().keySet().forEach((direction) -> mouseDragHelpers.put(direction, mouseDragHelperFactory.create()));
    }


    public void activate(Set<Shape> selectedShapes, TransformationListener listener) {
        requireNonNull(selectedShapes, "selectedShapes is null");
        requireNonNull(listener, "listener is null");

        mouseDragHelpers.entrySet().forEach((entry) ->
                entry.getValue().activate(corneredHandleFeedback.getHandles().get(entry.getKey()), new MouseDragListener() {

            private double initialX, initialY;


            @Override
            public void onDragStarted(double initialX, double initialY) {
                this.initialX = initialX;
                this.initialY = initialY;
                showTransformationProgressFeedback(entry.getKey(), selectedShapes);
            }


            @Override
            public void onDragged(double currentX, double currentY) {
                updateTransformationProgressFeedback(currentX - initialX, currentY - initialY);
            }


            @Override
            public void onDragCancelled(double finalX, double finalY) {
                hideTransformationFeedback();
            }


            @Override
            public void onDragCommited(double finalX, double finalY) {
                updateTransformationProgressFeedback(finalX - initialX, finalY - initialY);
                final Transform transform = getTransform();

                requireNonNull(transform, "transform is null");

                listener.transformationCommited(transform);

                hideTransformationFeedback();
            }


            @Override
            public void onMouseReleased(double finalX, double finalY) {
                //Not needed
            }
        }));
    }


    public void deactivate() {
        for (MouseDragHelper mdh : mouseDragHelpers.values()) {
            mdh.deactivate();
        }
    }


    public CorneredHandleFeedback getHandleFeedback() {
        return corneredHandleFeedback;
    }


    protected abstract void showTransformationProgressFeedback(Direction direction, Set<Shape> collect);

    protected abstract void updateTransformationProgressFeedback(double deltaX, double deltaY);

    protected abstract void hideTransformationFeedback();

    protected abstract Transform getTransform();
}
