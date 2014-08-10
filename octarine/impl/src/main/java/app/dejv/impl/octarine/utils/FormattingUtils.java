package app.dejv.impl.octarine.utils;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.shape.StrokeType;

import app.dejv.impl.octarine.cfg.OctarineProps;

/**
 *
 * @author dejv
 */
public final class FormattingUtils {

    public enum FeedbackType {

        STATIC,
        DYNAMIC
    }

    public enum FeedbackOpacity {

        OPAQUE,
        STRONG,
        WEAK
    }


    private static final OctarineProps PROPS = OctarineProps.getInstance();

    private static DoubleBinding WIDTH_FB_STROKE_STATIC;
    private static DoubleBinding WIDTH_FB_STROKE_DYNAMIC;

    public static final Color COLOR_GLOW = fromColorAndOpacity(PROPS.getGlowColor(), PROPS.getGlowOpacity());

    public static final Color COLOR_FB_STROKE_STATIC_OPAQUE = OctarineProps.getInstance().getStaticFeedbackColor();
    public static final Color COLOR_FB_STROKE_STATIC_STRONG = fromColorAndOpacity(COLOR_FB_STROKE_STATIC_OPAQUE, PROPS.getFeedbackOpacityStrong());
    public static final Color COLOR_FB_STROKE_STATIC_WEAK = fromColorAndOpacity(COLOR_FB_STROKE_STATIC_OPAQUE, PROPS.getFeedbackOpacityWeak());

    public static final Color COLOR_FB_STROKE_DYNAMIC_OPAQUE = OctarineProps.getInstance().getDynamicFeedbackColor();
    public static final Color COLOR_FB_STROKE_DYNAMIC_STRONG = fromColorAndOpacity(COLOR_FB_STROKE_DYNAMIC_OPAQUE, PROPS.getFeedbackOpacityStrong());
    public static final Color COLOR_FB_STROKE_DYNAMIC_WEAK = fromColorAndOpacity(COLOR_FB_STROKE_DYNAMIC_OPAQUE, PROPS.getFeedbackOpacityWeak());

    private FormattingUtils() {
    }

    public static void setZoomFactor(DoubleProperty zoomFactor) {
        WIDTH_FB_STROKE_STATIC = new ConstantZoomDoubleBinding(zoomFactor, PROPS.getStaticFeedbackStrokeWidth());
        WIDTH_FB_STROKE_DYNAMIC = new ConstantZoomDoubleBinding(zoomFactor, PROPS.getDynamicFeedbackStrokeWidth());
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


    public static void formatSymbol(SVGPath shape) {
        shape.setStroke(COLOR_GLOW);
        shape.setStrokeType(StrokeType.OUTSIDE);
        shape.strokeWidthProperty().bind(WIDTH_FB_STROKE_STATIC);
        shape.setFill(getFeedbackColor(FeedbackType.STATIC, FeedbackOpacity.OPAQUE));
        shape.setMouseTransparent(true);
    }


    public static void formatFeedbackOutline(Shape outline, FeedbackType fbType, FeedbackOpacity fbOpacity, String id) {
        outline.setStroke(getFeedbackColor(fbType, fbOpacity));
        outline.setMouseTransparent(true);
        outline.setFill(null);
        outline.setSmooth(false);
        outline.setStrokeType(StrokeType.OUTSIDE);
        outline.setId(id);
    }


    public static Color fromColorAndOpacity(Color color, double opacity) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), opacity);
    }


    public static Color getFeedbackColor(FeedbackType fbType, FeedbackOpacity fbOpacity) {
        switch (fbOpacity) {
            case WEAK:
                return (FeedbackType.STATIC.equals(fbType) ? COLOR_FB_STROKE_STATIC_WEAK : COLOR_FB_STROKE_DYNAMIC_WEAK);
            case STRONG:
                return (FeedbackType.STATIC.equals(fbType) ? COLOR_FB_STROKE_STATIC_STRONG : COLOR_FB_STROKE_DYNAMIC_STRONG);
            default:
                return (FeedbackType.STATIC.equals(fbType) ? COLOR_FB_STROKE_STATIC_OPAQUE : COLOR_FB_STROKE_DYNAMIC_OPAQUE);
        }
    }

    public static DoubleBinding getDefaultFeedbackStrokeWidth(FeedbackType fbType) {
        return (FeedbackType.STATIC.equals(fbType) ? WIDTH_FB_STROKE_STATIC : WIDTH_FB_STROKE_DYNAMIC);
    }
}
