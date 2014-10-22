package app.dejv.impl.octarine.tool.selection.editmode.translate;

import static java.util.Objects.requireNonNull;

import javafx.scene.transform.Translate;

import app.dejv.impl.octarine.model.chunk.TranslationChunk;
import app.dejv.octarine.command.Command;

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class TranslateCommand
        implements Command {


    private final TranslationChunk translationChunk;

    private final double originalX;
    private final double originalY;

    private final double newX;
    private final double newY;

    public TranslateCommand(TranslationChunk translationChunk, Translate translate) {
        requireNonNull(translationChunk, "translationChunk is null");
        requireNonNull(translate, "translate is null");

        this.translationChunk = translationChunk;

        originalX = translationChunk.getX();
        originalY = translationChunk.getY();

        newX = originalX + translate.getX();
        newY = originalY + translate.getY();
    }


    @Override
    public void execute() {
        set(newX, newY);
    }


    @Override
    public void undo() {
        set(originalX, originalY);
    }


    private void set(double x, double y) {
        translationChunk.setX(x);
        translationChunk.setY(y);
    }
}
