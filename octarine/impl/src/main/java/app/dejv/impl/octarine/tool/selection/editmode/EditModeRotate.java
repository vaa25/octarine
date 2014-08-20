/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.dejv.impl.octarine.tool.selection.editmode;

import java.io.IOException;

import javafx.scene.input.KeyCode;

import app.dejv.impl.octarine.tool.selection.editmode.feedback.RotateStaticFeedback;
import app.dejv.impl.octarine.tool.selection.request.RotateRequest;
import app.dejv.octarine.Octarine;

/**
 * "Rotate" edit mode<br/>
 * On handle drag it rotates the selection around the specified pivot point
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class EditModeRotate
        extends AbstractExclusiveEditMode {

    private final RotateStaticFeedback staticFeedback;


    public EditModeRotate(Octarine octarine, RotateStaticFeedback rotateStaticFeedback) throws IOException {
        super(octarine, RotateRequest.class);
        this.staticFeedback = rotateStaticFeedback;
    }


    @Override
    protected KeyCode getActivationKey() {
        return KeyCode.R;
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
