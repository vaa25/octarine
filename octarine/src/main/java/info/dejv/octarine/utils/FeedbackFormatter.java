package info.dejv.octarine.utils;

import info.dejv.octarine.cfg.OctarineProps;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.shape.StrokeType;

/**
 *
 * @author dejv
 */
public class FeedbackFormatter {

    private static final OctarineProps PROPS = OctarineProps.getInstance();

    public static final Color PAINT_GLOW = fromColorAndOpacity(PROPS.getGlowColor(), PROPS.getGlowOpacity());
    public static final Color PAINT_FILL = fromColorAndOpacity(PROPS.getDynamicFeedbackColor(), PROPS.getFeedbackOpacityWeak());
    public static final Color PAINT_STROKE = fromColorAndOpacity(PROPS.getDynamicFeedbackColor(), PROPS.getFeedbackOpacityStrong());

    public static void formatGlow(SVGPath shape) {
        shape.setStroke(PAINT_GLOW);
        shape.setMouseTransparent(true);
    }


    public static void formatSymbol(SVGPath shape, boolean fill) {
        shape.setStroke(PAINT_STROKE);
        shape.setFill(fill ? PAINT_STROKE : null);
        shape.setMouseTransparent(true);
    }


    public static void formatOutline(Shape outline) {
        outline.setStroke(PAINT_STROKE);
        outline.setFill(null);
        outline.setStrokeWidth(1.0);
        outline.setStrokeType(StrokeType.OUTSIDE);
        outline.setMouseTransparent(true);
    }


    public static Color fromColorAndOpacity(Color color, double opacity) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), opacity);
    }


    public static Shape grow(Shape outline, double amount) {
        Paint p = Color.BLACK;
        outline.setStroke(p);
        outline.setFill(p);
        outline.setStrokeType(StrokeType.OUTSIDE);
        outline.setStrokeLineCap(StrokeLineCap.ROUND);
        outline.setStrokeLineJoin(StrokeLineJoin.MITER);
        outline.setStrokeWidth(amount);

        return Shape.union(outline, outline);
    }
}
