package app.dejv.impl.octarine.tool.selection.extension.helper;

/**
 *
 * @author dejv
 */
public interface SelectionActionListener {

    void addToSelection();

    void removeFromSelection();

    void replaceSelection();

    void deselectAll();
}
