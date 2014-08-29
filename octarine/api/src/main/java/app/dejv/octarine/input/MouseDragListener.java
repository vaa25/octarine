package app.dejv.octarine.input;

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public interface MouseDragListener {

    void onDragStarted(double initialX, double initialY);

    void onDragged(double currentX, double currentY);

    void onDragCancelled(double finalX, double finalY);

    void onDragCommited(double finalX, double finalY);

    void onMouseReleased(double finalX, double finalY);
}
