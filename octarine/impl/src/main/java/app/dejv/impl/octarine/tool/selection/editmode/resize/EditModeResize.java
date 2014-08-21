package app.dejv.impl.octarine.tool.selection.editmode.resize;

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

    private ResizeHandleFeedback staticFeedback;


    public EditModeResize(Octarine octarine, ResizeHandleFeedback resizeStaticFeedback) {
        super(octarine, ResizeRequest.class);
        this.staticFeedback = resizeStaticFeedback;
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
