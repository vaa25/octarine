package app.dejv.impl.octarine.tool.selection.editmode.translate;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;

import app.dejv.impl.octarine.model.chunk.DoubleTuple;
import app.dejv.octarine.command.Command;

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class TranslateCommand
        implements Command {

    private final DoubleTuple coords;
    private final Point2D originalPosition;
    private final Point2D newPosition;


    public TranslateCommand(DoubleTuple coords, Dimension2D positionDelta) {
        this.coords = coords;
        originalPosition = new Point2D(coords.getX().doubleValue(), coords.getY().doubleValue());
        newPosition = new Point2D(originalPosition.getX() + positionDelta.getWidth(), originalPosition.getY() + positionDelta.getHeight());
    }


    @Override
    public void execute() {
        coords.set(newPosition.getX(), newPosition.getY());
    }


    @Override
    public void undo() {
        coords.set(originalPosition.getX(), originalPosition.getY());
    }

}
