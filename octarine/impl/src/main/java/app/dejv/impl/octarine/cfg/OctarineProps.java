package app.dejv.impl.octarine.cfg;

import javafx.scene.paint.Color;

/**
 *
 * @author dejv
 */
public class OctarineProps {

    @SuppressWarnings("unused")
	private static final String PROP_STATIC_FEEDBACK_COLOR = "Static_Feedback_Color";
    @SuppressWarnings("unused")
    private static final String PROP_DYNAMIC_FEEDBACK_COLOR = "Dynamic_Feedback_Color";
    @SuppressWarnings("unused")
    private static final String PROP_FEEDBACK_OPACITY_WEAK = "Feedback_Opacity_Weak";
    @SuppressWarnings("unused")
    private static final String PROP_FEEDBACK_OPACITY_STRONG = "Feedback_Opacity_Strong";
    @SuppressWarnings("unused")
    private static final String PROP_GLOW_COLOR = "Glow_Color";
    @SuppressWarnings("unused")
    private static final String PROP_GLOW_OPACITY = "Glow_Opacity";
    @SuppressWarnings("unused")
    private static final String PROP_GLOW_SIZE = "Glow_Size";

    private static final Color DEF_STATIC_FEEDBACK_COLOR = Color.DEEPSKYBLUE;
    private static final Color DEF_DYNAMIC_FEEDBACK_COLOR = Color.DARKTURQUOISE;
    private static final double DEF_FEEDBACK_OPACITY_WEAK = 0.1d;
    private static final double DEF_FEEDBACK_OPACITY_STRONG = 0.5d;
    private static final Color DEF_GLOW_COLOR = Color.WHITE;
    private static final double DEF_GLOW_OPACITY = 0.6d;
    private static final double DEF_GLOW_SIZE = 3.0d;
    private static final double DEF_STATIC_FEEDBACK_STROKE_WIDTH = 2.0d;
    private static final double DEF_DYNAMIC_FEEDBACK_STROKE_WIDTH = 1.0d;

    private static OctarineProps octarineProps;

    @SuppressWarnings("unused")
    private Props props;

    private OctarineProps() {
        //TODO: Incorporate "Props"
    }

    public static OctarineProps getInstance() {
        if (octarineProps == null) {
            octarineProps = new OctarineProps();
        }
        return octarineProps;
    }


    public Color getStaticFeedbackColor() {
        return DEF_STATIC_FEEDBACK_COLOR;
    }

    public double getStaticFeedbackStrokeWidth() {
        return DEF_STATIC_FEEDBACK_STROKE_WIDTH;
    }

    public double getDynamicFeedbackStrokeWidth() {
        return DEF_DYNAMIC_FEEDBACK_STROKE_WIDTH;
    }

    public Color getDynamicFeedbackColor() {
        return DEF_DYNAMIC_FEEDBACK_COLOR;
    }

    public double getFeedbackOpacityWeak() {
        return DEF_FEEDBACK_OPACITY_WEAK;
    }

    public double getFeedbackOpacityStrong() {
        return DEF_FEEDBACK_OPACITY_STRONG;
    }

    public Color getGlowColor() {
        return DEF_GLOW_COLOR;
    }

    public double getGlowOpacity() {
        return DEF_GLOW_OPACITY;
    }

    public double getGlowSize() {
        return DEF_GLOW_SIZE;
    }
}
