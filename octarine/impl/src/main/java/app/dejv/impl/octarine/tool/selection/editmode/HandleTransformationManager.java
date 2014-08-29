package app.dejv.impl.octarine.tool.selection.editmode;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javafx.scene.shape.Shape;

import app.dejv.impl.octarine.feedback.handles.CorneredHandleFeedback;
import app.dejv.impl.octarine.feedback.handles.HandlePos;
import app.dejv.octarine.input.MouseDragHelper;
import app.dejv.octarine.input.MouseDragHelperFactory;
import app.dejv.octarine.input.MouseDragListener;

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public abstract class HandleTransformationManager {

    private final CorneredHandleFeedback corneredHandleFeedback;
    private final Map<HandlePos, MouseDragHelper> mouseDragHelpers = new HashMap<>();
    private final MouseDragHelperFactory mouseDragHelperFactory;

    public HandleTransformationManager(CorneredHandleFeedback corneredHandleFeedback, MouseDragHelperFactory mouseDragHelperFactory) {
        requireNonNull(corneredHandleFeedback, "corneredHandleFeedback is null");
        requireNonNull(mouseDragHelperFactory, "mouseDragHelperFactory is null");

        this.corneredHandleFeedback = corneredHandleFeedback;
        this.mouseDragHelperFactory = mouseDragHelperFactory;
    }

    public void activate() {
        for (Entry<HandlePos, Shape> entry : corneredHandleFeedback.getHandles().entrySet()) {
            final MouseDragHelper mdh = mouseDragHelperFactory.create();
            mdh.activate(entry.getValue(), new MouseDragListener() {
                private double initialX, initialY;

                @Override
                public void onDragStarted(double initialX, double initialY) {
                    this.initialX = initialX;
                    this.initialY = initialY;
                    showTransformationProgressFeedback(entry.getKey());
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
                    commitTransformation(finalX - initialX, finalY - initialY);
                    hideTransformationFeedback();
                }


                @Override
                public void onMouseReleased(double finalX, double finalY) {
                    //Not needed
                }
            });

            mouseDragHelpers.put(entry.getKey(), mdh);
        }
    }


    public void deactivate() {
        for (MouseDragHelper mdh : mouseDragHelpers.values()) {
            mdh.deactivate();
        }
        mouseDragHelpers.clear();
    }


    protected abstract void showTransformationProgressFeedback(HandlePos handlePos);

    protected abstract void updateTransformationProgressFeedback(double deltaX, double deltaY);

    protected abstract void commitTransformation(double deltaX, double deltaY);

    protected abstract void hideTransformationFeedback();
}
