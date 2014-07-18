package info.dejv.octarine.tool.selection.editmode;

import javafx.scene.input.KeyCode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import info.dejv.octarine.tool.selection.editmode.feedback.ResizeStaticFeedback;
import info.dejv.octarine.tool.selection.request.ResizeRequest;

/**
 * "Scale" edit mode<br/>
 * On handle drag it scales the selection in appropriate direction
 * <br/>
 * Author: dejv (www.dejv.info)
 */
@Component
public class EditModeResize
        extends AbstractExclusiveEditMode {

    @Autowired
    private ResizeStaticFeedback staticFeedback;


    public EditModeResize() {
        super(ResizeRequest.class);
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
        staticFeedback.show(selection);
    }


    @Override
    protected void doDeactivate() {
        staticFeedback.hide();
    }
}
