package app.dejv.impl.octarine.tool.selection.editmode.rotate;

import javafx.beans.property.DoubleProperty;

import app.dejv.octarine.command.Command;

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class RotateCommand
        implements Command {

    private final DoubleProperty rotationAngle_rad;
    private final double originalAngle_rad;
    private final double newAngle_rad;


    public RotateCommand(DoubleProperty rotationAngle_rad, double angleDelta_rad) {
        this.rotationAngle_rad = rotationAngle_rad;
        this.originalAngle_rad = rotationAngle_rad.doubleValue();

        this.newAngle_rad = originalAngle_rad + angleDelta_rad;
    }


    @Override
    public void execute() {
        rotationAngle_rad.setValue(newAngle_rad);
    }


    @Override
    public void undo() {
        rotationAngle_rad.setValue(originalAngle_rad);
    }
}
