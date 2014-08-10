package app.dejv.impl.octarine.command;

import java.util.ArrayList;
import java.util.List;

import app.dejv.octarine.command.Command;

public class CompoundCommand
        implements Command {


    private final List<Command> commands = new ArrayList<>();


    public void addCommand(Command command) {
        commands.add(command);
    }


    @Override
    public void execute() {
        commands.stream().forEach(Command::execute);
    }


    @Override
    public void undo() {
        for (int i = commands.size() - 1; i >= 0; i--) {
            commands.get(i).undo();
        }
    }

}
