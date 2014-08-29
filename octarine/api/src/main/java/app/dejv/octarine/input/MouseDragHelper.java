package app.dejv.octarine.input;

import javafx.scene.Node;

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public interface MouseDragHelper {

    void activate(Node node, MouseDragListener listener);


    void deactivate();
}
