package app.dejv.impl.octarine.tool.selection.editmode.resize;

/**
 * Resize manager governs the "resize operation in-progress" phase.
 * It reacts on the input from handles, and updates the ResizeProgressFeedback accordingly.
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class ResizeManager {

    private final ResizeHandleFeedback resizeHandleFeedback;
    private final ResizeProgressFeedback resizeProgressFeedback;


    public ResizeManager(ResizeHandleFeedback resizeHandleFeedback, ResizeProgressFeedback resizeProgressFeedback) {
        this.resizeHandleFeedback = resizeHandleFeedback;
        this.resizeProgressFeedback = resizeProgressFeedback;
    }

    public void activate() {
        //TODO: Bind to handles drag events
    }

    public void deactivate() {
        //TODO: Unbind from handles drag events
    }
}
