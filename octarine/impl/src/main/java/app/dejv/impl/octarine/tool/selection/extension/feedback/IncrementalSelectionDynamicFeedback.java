package app.dejv.impl.octarine.tool.selection.extension.feedback;

import java.io.IOException;

import javafx.animation.FadeTransition;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import app.dejv.impl.octarine.feedback.DynamicFeedback;
import app.dejv.impl.octarine.tool.selection.extension.helper.IncrementType;
import app.dejv.impl.octarine.utils.ConstantZoomDoubleBinding;
import app.dejv.impl.octarine.utils.InfrastructureUtils;
import app.dejv.octarine.Octarine;
import app.dejv.octarine.infrastructure.Resources;

/**
 * "Increamental selection" dynamic feedback.
 * Adds "+" or "-" symbol next to the mouse cursor to indicate "Add to selection" / "Remove from selection" scenario.
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class IncrementalSelectionDynamicFeedback
        extends DynamicFeedback {


    public static final double SYMBOL_MAGNIFICATION = 1.25;

    private static final Logger LOGGER = LoggerFactory.getLogger(IncrementalSelectionDynamicFeedback.class);
    public static final int FADE_PERIOD = 100;

    private final Group symbolPlus;
    private final Group symbolMinus;
    private final ConstantZoomDoubleBinding symbolScale;
    private final Translate symbolTranslate = new Translate();

    private Group symbol;
    private FadeTransition fadeTransition;

    private IncrementType type;


    public IncrementalSelectionDynamicFeedback(Octarine octarine) throws IOException {
        super(octarine);

        final DoubleProperty zoomFactor = octarine.getViewer().zoomFactorProperty();
        symbolScale = new ConstantZoomDoubleBinding(zoomFactor, SYMBOL_MAGNIFICATION);

        final Resources resources = octarine.getResources();
        symbolPlus = InfrastructureUtils.getRequiredShape(resources, "plus");
        symbolMinus = InfrastructureUtils.getRequiredShape(resources, "minus");

        setType(IncrementType.ADD);
    }


    public final void setType(IncrementType type) {

        if (this.type == type) {
            return;
        }

        if ((symbol != null) && (getChildren().contains(symbol))) {
            getChildren().remove(symbol);
        }

        this.symbol = (type == IncrementType.ADD) ? symbolPlus : symbolMinus;

        getChildren().add(symbol);

        this.type = type;
    }


    public void setLocation(double x, double y) {
        translateSymbol(symbolPlus, x, y);
        translateSymbol(symbolMinus, x, y);
    }


    @Override
    protected void beforeDeactivate() {
        runFadeTransition(1.0, 0.0, event -> {
            onDeactivationConfirmed();
        });
    }


    @Override
    protected void beforeActivate() {
        super.beforeActivate();
        runFadeTransition(0.0, 1.0, null);
    }


    @Override
    protected void bind() {
        super.bind();

        symbol.getTransforms().add(symbolTranslate);

        symbol.scaleXProperty().bind(symbolScale);
        symbol.scaleYProperty().bind(symbolScale);
    }


    @Override
    protected void unbind() {
        symbol.getTransforms().remove(symbolTranslate);

        symbol.scaleXProperty().unbind();
        symbol.scaleYProperty().unbind();

        super.unbind();
    }


    private void translateSymbol(Group symbol, double x, double y) {
        symbol.setTranslateX(x);
        symbol.setTranslateY(y);
    }


    private void runFadeTransition(double from, double to, EventHandler<ActionEvent> onFinished) {

        if (fadeTransition == null) {
            fadeTransition = new FadeTransition(Duration.millis(FADE_PERIOD), this);
        }

        fadeTransition.setFromValue(from);
        fadeTransition.setToValue(to);
        fadeTransition.setCycleCount(1);
        fadeTransition.setAutoReverse(false);

        fadeTransition.setOnFinished((event) -> {
            if (onFinished != null) {
                onFinished.handle(event);
            }
            fadeTransition = null;
        });

        fadeTransition.playFromStart();
    }
}
