package app.dejv.impl.octarine.tool.selection.editmode.translate;

import javafx.geometry.Point2D;

import app.dejv.impl.octarine.model.chunk.DoubleTuple;
import app.dejv.octarine.command.Command;

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class TranslateCommand
        implements Command {

    private final DoubleTuple locationProperty;
    private final Point2D originalLocation;
    private final Point2D newLocation;


    public TranslateCommand(DoubleTuple locationProperty, Point2D newLocation) {
        this.locationProperty = locationProperty;

        this.originalLocation = new Point2D(locationProperty.getX(), locationProperty.getY());
        this.newLocation = newLocation;
    }


    @Override
    public void execute() {
        locationProperty.set(newLocation.getX(), newLocation.getY());
    }


    @Override
    public void undo() {
        locationProperty.set(originalLocation.getX(), originalLocation.getY());
    }

}
