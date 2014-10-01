package app.dejv.impl.octarine.tool.selection.editmode.rotate;

import javafx.geometry.Dimension2D;
import javafx.scene.transform.Rotate;

import app.dejv.impl.octarine.model.chunk.SizeChunk;
import app.dejv.octarine.command.Command;

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class RotateCommand
        implements Command {

    private final SizeChunk sizeChunk;

    private final Dimension2D originalSize;
    private final Dimension2D newSize;


    public RotateCommand(SizeChunk sizeChunk, Rotate rotateTransform) {
        this.sizeChunk = sizeChunk;

        this.originalSize = sizeChunk.get();
        this.newSize = new Dimension2D(originalSize.getWidth() * rotateTransform.getX(), originalSize.getHeight() * rotateTransform.getY());
    }


    @Override
    public void execute() {
        sizeChunk.set(newSize);
    }


    @Override
    public void undo() {
        sizeChunk.set(originalSize);
    }
}
