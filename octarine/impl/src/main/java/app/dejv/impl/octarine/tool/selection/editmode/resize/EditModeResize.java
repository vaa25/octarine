package app.dejv.impl.octarine.tool.selection.editmode.resize;

import static java.util.Objects.requireNonNull;

import javafx.scene.input.KeyCode;

import app.dejv.impl.octarine.tool.selection.editmode.AbstractExclusiveEditMode;
import app.dejv.octarine.Octarine;

/**
 * "Scale" edit mode<br/>
 * On handles drag it scales the selection in appropriate direction
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class EditModeResize
        extends AbstractExclusiveEditMode {

    private ResizeHandleFeedback resizeHandleFeedback;
    private ResizeProgressManager resizeProgressManager;


    public EditModeResize(Octarine octarine, ResizeHandleFeedback resizeHandleFeedback, ResizeProgressManager resizeProgressManager) {
        super(octarine, ResizeRequest.class);
        requireNonNull(resizeHandleFeedback, "resizeHandleFeedback is null");
        requireNonNull(resizeProgressManager, "resizeProgressManager is null");

        this.resizeHandleFeedback = resizeHandleFeedback;
        this.resizeProgressManager = resizeProgressManager;
    }


    @Override
    protected KeyCode getActivationKey() {
        return KeyCode.S;
    }


    @Override
    protected void doActivate() {
        resizeHandleFeedback.activate();
        resizeProgressManager.activate(selection);
    }


    @Override
    protected void doDeactivate() {
        resizeHandleFeedback.deactivate();
        resizeProgressManager.deactivate();
    }
}
