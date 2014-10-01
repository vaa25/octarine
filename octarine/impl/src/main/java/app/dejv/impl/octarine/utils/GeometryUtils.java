package app.dejv.impl.octarine.utils;

import javafx.geometry.Point2D;

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class GeometryUtils {

    public static double angle(Point2D p1, Point2D p2) {
        final double xDiff = p2.getX() - p1.getX();
        final double yDiff = p2.getY() - p1.getY();
        return Math.toDegrees(Math.atan2(yDiff, xDiff));
    }
}
