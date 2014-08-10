package app.dejv.octarine.command;

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public interface CommandStack {

    void execute(Command command);

    void undo();

    void redo();

    void markSave();

    boolean isDirty();

    boolean canRedo();

    boolean canUndo();
}
