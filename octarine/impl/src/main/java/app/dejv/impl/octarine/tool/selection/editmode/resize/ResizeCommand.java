package app.dejv.impl.octarine.tool.selection.editmode.resize;

import javafx.geometry.Dimension2D;

import app.dejv.impl.octarine.model.chunk.DoubleTuple;
import app.dejv.octarine.command.Command;

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class ResizeCommand
        implements Command {

    private final DoubleTuple size;
    private final Dimension2D originalDimensions;
    private final Dimension2D newDimensions;


    public ResizeCommand(DoubleTuple size, Dimension2D sizeMultiplier) {
        this.size = size;
        this.originalDimensions = new Dimension2D(size.getX(), size.getY());

        this.newDimensions = new Dimension2D(originalDimensions.getWidth() * sizeMultiplier.getWidth(), originalDimensions.getHeight() * sizeMultiplier.getHeight());
    }


    @Override
    public void execute() {
        size.set(newDimensions.getWidth(), newDimensions.getHeight());
    }


    @Override
    public void undo() {
        size.set(originalDimensions.getWidth(), originalDimensions.getHeight());
    }
}
