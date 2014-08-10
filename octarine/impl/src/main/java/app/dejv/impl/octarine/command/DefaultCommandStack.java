package app.dejv.impl.octarine.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Observable;

import app.dejv.octarine.command.Command;
import app.dejv.octarine.command.CommandStack;

public class DefaultCommandStack
        extends Observable
        implements CommandStack {

    private final List<Command> commands = new ArrayList<>();
    private int pointerCurrent = 0;
    private int pointerSaved = 0;


    @Override
    public void execute(final Command command) {
        Objects.requireNonNull(command, "command is NULL");

        final int top = commands.size() - 1;

        for (int i = pointerCurrent; i <= top; i++) {
            commands.remove(pointerCurrent);
        }

        commands.add(command);
        redo();
    }


    @Override
    public void redo() {
        if (!canRedo()) {
            return;
        }

        commands.get(pointerCurrent).execute();
        pointerCurrent++;
        setChanged();
    }


    @Override
    public void undo() {
        if (!canUndo()) {
            return;
        }

        pointerCurrent--;
        commands.get(pointerCurrent).undo();
        setChanged();
    }


    @Override
    public void markSave() {
        pointerSaved = pointerCurrent;
    }


    @Override
    public boolean isDirty() {
        return (pointerCurrent != pointerSaved);
    }


    @Override
    public boolean canRedo() {
        return (pointerCurrent < commands.size());
    }


    @Override
    public boolean canUndo() {
        return (pointerCurrent > 0);
    }
}
