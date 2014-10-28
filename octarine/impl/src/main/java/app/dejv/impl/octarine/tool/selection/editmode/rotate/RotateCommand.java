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

    private final double newAngle;


    public RotateCommand(RotationChunk rotationChunk, Rotate rotateTransform) {
        this.rotationChunk = rotationChunk;

        this.originalAngle = rotationChunk.getAngle();

        this.newAngle = originalAngle + rotateTransform.getAngle();
    }


    @Override
    public void execute() {
        rotationChunk.setAngle(newAngle);
    }


    @Override
    public void undo() {
        rotationChunk.setAngle(originalAngle);
    }
}
