package info.dejv.octarine.command;

import info.dejv.octarine.model.chunk.DoubleTuple;

/**
 *
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class TranslateCommand
        implements Command {

    private final DoubleTuple coords;
    private final double origX, origY;
    private final double newX, newY;

    public TranslateCommand(DoubleTuple coords, double dx, double dy) {
        this.coords = coords;
        this.origX = coords.getX().doubleValue();
        this.origY = coords.getY().doubleValue();
        this.newX = origX + dx;
        this.newY = origY + dy;
    }


    @Override
    public void execute() {
        coords.set(newX, newY);
    }

    @Override
    public void undo() {
        coords.set(origX, origY);
    }

}
