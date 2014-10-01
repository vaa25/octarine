/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.dejv.impl.octarine.tool.selection.editmode.rotate;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javafx.scene.input.KeyCode;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;

import app.dejv.impl.octarine.tool.selection.editmode.AbstractExclusiveEditMode;
import app.dejv.impl.octarine.tool.selection.editmode.TransformationListener;
import app.dejv.impl.octarine.utils.ControllerUtils;
import app.dejv.octarine.Octarine;

/**
 * "Rotate" edit mode<br/>
 * On handles drag it rotates the selection around the specified pivot point
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class EditModeRotate
        extends AbstractExclusiveEditMode
        implements TransformationListener {


    private final RotateHandleFeedback rotateHandleFeedback;
    private final RotateProgressManager rotateProgressManager;


    public EditModeRotate(Octarine octarine, RotateHandleFeedback rotateHandleFeedback, RotateProgressManager rotateProgressManager) throws IOException {
        super(octarine, RotateRequest.class);

        requireNonNull(rotateHandleFeedback, "rotateHandleFeedback is null");
        requireNonNull(rotateProgressManager, "rotateProgressManager is null");

        this.rotateHandleFeedback = rotateHandleFeedback;
        this.rotateProgressManager = rotateProgressManager;
    }


    @Override
    public void transformationCommited(Transform transform) {
        if (transform instanceof Rotate) {
            executeOnSelection(new RotateRequest((Rotate) transform));
        }

    }


    @Override
    protected KeyCode getActivationKey() {
        return KeyCode.R;
    }


    @Override
    protected void doActivate() {
        final Set<Shape> shapes = new HashSet<>();

        selection.forEach((controller) -> {
            Shape shape = ControllerUtils.getShape(controller);
            shapes.add(shape);
        });

        rotateHandleFeedback.activate();
        rotateProgressManager.activate(shapes, this);
    }


    @Override
    protected void doDeactivate() {
        rotateHandleFeedback.deactivate();
        rotateProgressManager.deactivate();
    }

}
