package app.dejv.impl.octarine.tool.selection.editmode.rotate;

import javafx.scene.transform.Rotate;

import app.dejv.impl.octarine.model.chunk.RotationChunk;
import app.dejv.octarine.command.Command;

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class RotateCommand
        implements Command {

    private final RotationChunk rotationChunk;

    private final double originalAngle;
    private final double originalPivotX;
    private final double originalPivotY;

    private final double newAngle;
    private final double newPivotX;
    private final double newPivotY;


    public RotateCommand(RotationChunk rotationChunk, Rotate rotateTransform) {
        this.rotationChunk = rotationChunk;

        this.originalAngle = rotationChunk.getAngle();
        this.originalPivotX = rotationChunk.getPivotX();
        this.originalPivotY = rotationChunk.getPivotY();

        this.newAngle = originalAngle + rotateTransform.getAngle();
        this.newPivotX = rotateTransform.getPivotX();
        this.newPivotY = rotateTransform.getPivotY();
    }


    @Override
    public void execute() {
        set(newAngle, newPivotX, newPivotY);
    }


    @Override
    public void undo() {
        set(originalAngle, originalPivotX, originalPivotY);
    }


    private void set(double angle, double pivotX, double pivotY) {
        rotationChunk.setAngle(angle);
        rotationChunk.setPivotX(pivotX);
        rotationChunk.setPivotY(pivotY);
    }

}
