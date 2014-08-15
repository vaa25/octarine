package app.dejv.impl.octarine.tool.selection.editmode;

import javafx.scene.input.KeyCode;

import app.dejv.impl.octarine.tool.selection.editmode.feedback.ResizeStaticFeedback;
import app.dejv.impl.octarine.tool.selection.request.ResizeRequest;
import app.dejv.octarine.Octarine;
import app.dejv.octarine.tool.editmode.ExclusivityCoordinator;

/**
 * "Scale" edit mode<br/>
 * On handle drag it scales the selection in appropriate direction
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class EditModeResize
        extends AbstractExclusiveEditMode {

    private ResizeStaticFeedback staticFeedback;


    public EditModeResize(Octarine octarine, ResizeStaticFeedback resizeStaticFeedback) {
        super(octarine, ResizeRequest.class);
        this.staticFeedback = resizeStaticFeedback;
    }


    @Override
    public EditModeResize setExclusivityCoordinator(ExclusivityCoordinator exclusivityCoordinator) {
        return (EditModeResize) super.setExclusivityCoordinator(exclusivityCoordinator);
    }

    @Override
    protected KeyCode getActivationKey() {
        return KeyCode.S;
    }


    @Override
    protected void doActivate() {
        staticFeedback.activate();
    }


    @Override
    protected void doDeactivate() {
        staticFeedback.deactivate();
    }
}
