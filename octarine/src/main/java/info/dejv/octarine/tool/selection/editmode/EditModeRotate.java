/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.dejv.octarine.tool.selection.editmode;

import javafx.scene.input.KeyCode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import info.dejv.octarine.tool.selection.editmode.feedback.RotateStaticFeedback;
import info.dejv.octarine.tool.selection.request.RotateRequest;

/**
 * "Rotate" edit mode<br/>
 * On handle drag it rotates the selection around the specified pivot point
 * <br/>
 * Author: dejv (www.dejv.info)
 */
@Component
public class EditModeRotate
        extends AbstractExclusiveEditMode {

    @Autowired
    private RotateStaticFeedback staticFeedback;


    public EditModeRotate() {
        super(RotateRequest.class);
    }


    @Override
    public EditModeRotate setExclusivityCoordinator(ExclusivityCoordinator exclusivityCoordinator) {
        return (EditModeRotate) super.setExclusivityCoordinator(exclusivityCoordinator);
    }


    @Override
    protected KeyCode getActivationKey() {
        return KeyCode.R;
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
