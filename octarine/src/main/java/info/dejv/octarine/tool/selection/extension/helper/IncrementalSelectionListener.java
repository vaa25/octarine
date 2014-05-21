package info.dejv.octarine.tool.selection.extension.helper;

/**
 *
 * @author dejv
 */
public interface IncrementalSelectionListener {

    void addToSelection();

    void removeFromSelection();

    void replaceSelection();
}
