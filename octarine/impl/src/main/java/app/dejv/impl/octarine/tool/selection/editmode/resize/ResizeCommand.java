package app.dejv.impl.octarine.tool.selection.editmode.resize;

import javafx.geometry.Dimension2D;
import javafx.scene.transform.Scale;

import app.dejv.impl.octarine.model.chunk.SizeChunk;
import app.dejv.octarine.command.Command;

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class ResizeCommand implements Command {

    private final SizeChunk sizeChunk;

    private final Dimension2D originalSize;
    private final Dimension2D newSize;


    public ResizeCommand(SizeChunk sizeChunk, Scale scaleTransform) {
        this.sizeChunk = sizeChunk;

        this.originalSize = sizeChunk.get();
        this.newSize = new Dimension2D(originalSize.getWidth() * scaleTransform.getX(), originalSize.getHeight() * scaleTransform.getY());
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
