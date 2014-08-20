package app.dejv.impl.octarine.tool.selection.extension.helper;

import javafx.geometry.Bounds;

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public interface MarqueeSelectionActionListener {

    void addToSelection(Bounds marqueeBounds);

    void removeFromSelection(Bounds marqueeBounds);

    void replaceSelection(Bounds marqueeBounds);

    void deselectAll();
}
