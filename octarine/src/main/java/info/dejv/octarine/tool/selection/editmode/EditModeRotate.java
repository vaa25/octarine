/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.dejv.octarine.tool.selection.editmode;

import info.dejv.octarine.Octarine;
import info.dejv.octarine.tool.selection.ExclusivityCoordinator;
import info.dejv.octarine.tool.selection.request.RotateRequest;
import javafx.scene.input.KeyCode;

/**
 * "Rotate" edit mode<br/>
 * On handle drag it rotates the selection around the specified pivot point
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class EditModeRotate
        extends AbstractExclusiveEditMode {

    public EditModeRotate(Octarine octarine, ExclusivityCoordinator listener) {
        super(RotateRequest.class, octarine, listener);
    }


    @Override
    protected KeyCode getActivationKey() {
        return KeyCode.R;
    }

    @Override
    public void activate() {
    }


    @Override
    public void deactivate() {
    }

}
