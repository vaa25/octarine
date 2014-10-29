/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.dejv.impl.octarine.tool.selection.editmode.rotate;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.beans.property.DoubleProperty;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;

import app.dejv.impl.octarine.constants.PredefinedChunkTypes;
import app.dejv.impl.octarine.model.chunk.RotationChunk;
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

    private DoubleProperty pivotX;
    private DoubleProperty pivotY;


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

            if (rotateHandleFeedback.isUsingDefaultPivot()) {
                executeGeneralRequest(new RotationPivotResetRequest());
            }
        }

    }


    @Override
    public void auxiliaryOperationCommited(Object operationDescriptor) {
        if ((operationDescriptor != null) && (operationDescriptor instanceof Point2D)) {
            final RotationPivotRequest rotationPivotRequest = new RotationPivotRequest((Point2D) operationDescriptor);

            executeGeneralRequest(rotationPivotRequest);
            updateSelectionPivot();
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

        updateSelectionPivot();

        rotateHandleFeedback.activate();
        rotateProgressManager.activate(shapes, this);
    }


    @Override
    protected void doDeactivate() {
        rotateHandleFeedback.deactivate();
        rotateProgressManager.deactivate();
    }


    private void updateSelectionPivot() {
        Set<Optional<RotationChunk>> oRCs = selection.stream()
                .map((controller) -> controller.getModel().getChunk(PredefinedChunkTypes.ROTATION, RotationChunk.class)).collect(Collectors.toSet());

        boolean defaultPivot = true;
        for (Optional<RotationChunk> oRC : oRCs) {
            if (oRC.isPresent()) {

                if (defaultPivot) {
                    //Check, that pivot in RotChunk is already set; if not, use default
                    if ((oRC.get().pivotXProperty().get() == Double.MIN_VALUE) || (oRC.get().pivotYProperty().get() == Double.MIN_VALUE)) {
                        defaultPivot = true;
                        break;
                    }

                    defaultPivot = false;
                    pivotX = oRC.get().pivotXProperty();
                    pivotY = oRC.get().pivotYProperty();

                } else {
                    // If selection contains different pivots, use default
                    if ((pivotX.get() != oRC.get().pivotXProperty().get()) || (pivotX.get() != oRC.get().pivotXProperty().get())) {
                        defaultPivot = true;
                        break;
                    }
                }

            // If RotChunk is not present for at least one element, use default
            } else {
                defaultPivot = true;
                break;
            }
        }

        if (!defaultPivot) {
            rotateHandleFeedback.setPivot(pivotX, pivotY);
        } else {
            rotateHandleFeedback.resetPivot();
        }
    }

}
