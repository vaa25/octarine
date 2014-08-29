package app.dejv.impl.octarine.tool.selection.editmode.resize;

import static java.util.Objects.requireNonNull;

import app.dejv.impl.octarine.feedback.handles.HandlePos;
import app.dejv.impl.octarine.tool.selection.editmode.HandleTransformationManager;
import app.dejv.octarine.input.MouseDragHelperFactory;

/**
 * Resize manager governs the "resize operation in-progress" phase.
 * It reacts on the input from handles, and updates the ResizeProgressFeedback accordingly.
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class ResizeManager extends HandleTransformationManager {

    private final ResizeProgressFeedback resizeProgressFeedback;

    public ResizeManager(ResizeHandleFeedback resizeHandleFeedback, ResizeProgressFeedback resizeProgressFeedback, MouseDragHelperFactory mouseDragHelperFactory) {
        super(resizeHandleFeedback, mouseDragHelperFactory);

        requireNonNull(resizeProgressFeedback, "resizeProgressFeedback is null");

        this.resizeProgressFeedback = resizeProgressFeedback;
    }


    @Override
    protected void showTransformationProgressFeedback(HandlePos handlePos) {

    }


    @Override
    protected void updateTransformationProgressFeedback(double deltaX, double deltaY) {

    }


    @Override
    protected void commitTransformation(double deltaX, double deltaY) {

    }


    @Override
    protected void hideTransformationFeedback() {

    }
}
