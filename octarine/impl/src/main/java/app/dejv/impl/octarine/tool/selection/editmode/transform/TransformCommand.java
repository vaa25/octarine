package app.dejv.impl.octarine.tool.selection.editmode.transform;

import javafx.scene.transform.Transform;

import app.dejv.impl.octarine.model.chunk.TransformChunk;
import app.dejv.octarine.command.Command;

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class TransformCommand
        implements Command {


    private final TransformChunk transformChunk;

    private final Transform originalTransform;
    private final Transform newTransform;

    public TransformCommand(TransformChunk transformChunk, Transform transform) {
        this.transformChunk = transformChunk;

        originalTransform = transformChunk.getTransform().clone();
        newTransform = originalTransform.createConcatenation(transform);
    }


    @Override
    public void execute() {
        transformChunk.setTransform(newTransform);
    }


    @Override
    public void undo() {
        transformChunk.setTransform(originalTransform);
    }

}
