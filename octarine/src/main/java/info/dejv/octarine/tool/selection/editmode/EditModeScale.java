package info.dejv.octarine.tool.selection.editmode;

import info.dejv.octarine.Octarine;
import info.dejv.octarine.cfg.OctarineProps;
import info.dejv.octarine.tool.selection.ExclusivityCoordinator;
import info.dejv.octarine.tool.selection.request.ScaleRequest;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * "Scale" edit mode<br/>
 * On handle drag it scales the selection in appropriate direction
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class EditModeScale
        extends AbstractExclusiveEditMode {

    public EditModeScale(Octarine octarine, ExclusivityCoordinator listener) {
        super(ScaleRequest.class, octarine, listener);
    }


    @Override
    protected KeyCode getActivationKey() {
        return KeyCode.S;
    }


    @Override
    public void activate() {
    }

    @Override
    public void deactivate() {
    }


    private interface ScalingHandleListener {

        void scalingStarted();

        void scalingFinished();
    }

    private class ScalingHandle {

        private static final double SIZE_HALF = 3.0d;
        private Bounds totalBounds;
        private Rectangle node;
        private Point2D center;

        private ScalingHandle(Bounds totalBounds, HandlePos pos) {
            this.totalBounds = totalBounds;
            center = getNodeCenter(totalBounds, pos);
            node = createNode();

            //node.setOnDragDetected((event) -> {
            //});

            //node.setOnMouseDragged(this);
            //node.setOnMouseReleased(this);
            //node.setCursor(getCursor(pos));
        }

        private Point2D getNodeCenter(Bounds totalBounds, HandlePos pos) {
            switch (pos) {
                case N:
                    return new Point2D((totalBounds.getMaxX() - totalBounds.getMinX()) / 2.0d, totalBounds.getMinY());
                case NE:
                    return new Point2D(totalBounds.getMaxX(), totalBounds.getMinY());
                case E:
                    return new Point2D(totalBounds.getMaxX(), (totalBounds.getMaxY() - totalBounds.getMinY()) / 2.0d);
                case SE:
                    return new Point2D(totalBounds.getMaxX(), totalBounds.getMinY() / 2.0d);
                case S:
                    return new Point2D((totalBounds.getMaxX() - totalBounds.getMinX()) / 2.0d, totalBounds.getMaxY());
                case SW:
                    return new Point2D(totalBounds.getMinX(), totalBounds.getMinY() / 2.0d);
                case W:
                    return new Point2D(totalBounds.getMinX(), (totalBounds.getMaxY() - totalBounds.getMinY()) / 2.0d);
                case NW:
                    return new Point2D(totalBounds.getMinX(), totalBounds.getMinY());
                default:
                    throw new IllegalArgumentException("Invalid HandlePos");
            }
        }

        private Cursor getCursor(HandlePos pos) {
            switch (pos) {
                case N:
                    return Cursor.N_RESIZE;
                case NE:
                    return Cursor.NE_RESIZE;
                case E:
                    return Cursor.E_RESIZE;
                case SE:
                    return Cursor.SE_RESIZE;
                case S:
                    return Cursor.S_RESIZE;
                case SW:
                    return Cursor.SW_RESIZE;
                case W:
                    return Cursor.W_RESIZE;
                case NW:
                    return Cursor.NW_RESIZE;
                default:
                    throw new IllegalArgumentException("Invalid HandlePos");
            }
        }

        private Rectangle createNode() {
            Rectangle r = new Rectangle(center.getX() - SIZE_HALF, center.getY() - SIZE_HALF, SIZE_HALF * 2, SIZE_HALF * 2);
            r.setFill(Color.WHITE);
            r.setStroke(OctarineProps.getInstance().getStaticFeedbackColor());

            return r;
        }


    }

}
