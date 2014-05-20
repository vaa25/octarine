package info.dejv.octarine.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Observable;

public class CommandStack
        extends Observable {

    private final List<Command> commands = new ArrayList<>();
    private int pointerCurrent = 0;
    private int pointerSaved = 0;


    public void execute(final Command command) {
        Objects.requireNonNull(command, "command is NULL");

        final int top = commands.size() - 1;

        for (int i = pointerCurrent; i <= top; i++) {
            commands.remove(pointerCurrent);
        }

        commands.add(command);
        redo();
    }


    public void redo() {
        if (!canRedo()) {
            return;
        }

        commands.get(pointerCurrent).execute();
        pointerCurrent++;
        setChanged();
    }


    public void undo() {
        if (!canUndo()) {
            return;
        }

        pointerCurrent--;
        commands.get(pointerCurrent).undo();
        setChanged();
    }


    public void markSave() {
        pointerSaved = pointerCurrent;
    }


    public boolean isDirty() {
        return (pointerCurrent != pointerSaved);
    }


    public boolean canRedo() {
        return (pointerCurrent < commands.size());
    }


    public boolean canUndo() {
        return (pointerCurrent > 0);
    }
}
