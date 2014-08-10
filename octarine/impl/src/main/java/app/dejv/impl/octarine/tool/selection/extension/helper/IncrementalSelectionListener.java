package app.dejv.impl.octarine.tool.selection.extension.helper;

/**
 *
 * @author dejv
 */
public interface IncrementalSelectionListener {

    void addToSelection();

    void removeFromSelection();

    void replaceSelection();
}
