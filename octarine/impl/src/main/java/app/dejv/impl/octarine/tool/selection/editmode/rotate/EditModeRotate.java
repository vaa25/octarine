/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.dejv.impl.octarine.tool.selection.editmode.rotate;

import java.io.IOException;

import javafx.scene.input.KeyCode;

import app.dejv.impl.octarine.tool.selection.editmode.AbstractExclusiveEditMode;
import app.dejv.octarine.Octarine;

/**
 * "Rotate" edit mode<br/>
 * On handles drag it rotates the selection around the specified pivot point
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class EditModeRotate
        extends AbstractExclusiveEditMode {

    private final RotateHandleFeedback staticFeedback;


    public EditModeRotate(Octarine octarine, RotateHandleFeedback rotateStaticFeedback) throws IOException {
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