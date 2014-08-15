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


    public static final double SYMBOL_MAGNIFICATION = 1.5;

    private static final Logger LOGGER = LoggerFactory.getLogger(IncrementalSelectionDynamicFeedback.class);

    private final Group symbolPlus;
    private final Group symbolMinus;
    private final ConstantZoomDoubleBinding symbolScale;
    private final Translate symbolTranslate = new Translate();
    private Group symbol;
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
        boolean reactivate = false;

        if (this.type == type) {
            return;
        }

        if (isActive()) {
            deactivate();
            reactivate = true;
        }

        this.symbol = (type == IncrementType.ADD) ? symbolPlus : symbolMinus;
        this.type = type;

        if (reactivate) {
            activate();
        }
    }


    public void setLocation(double x, double y) {
        try {
            //Point2D p = screenToLocal(x, y);

            //if (p != null) {
//                translateSymbol(symbolPlus, p.getX(), p.getY());
//                translateSymbol(symbolMinus, p.getX(), p.getY());
                translateSymbol(symbolPlus, x, y + symbolPlus.getBoundsInParent().getHeight() * symbolScale.get() / 2);
                translateSymbol(symbolMinus, x, y + symbolMinus.getBoundsInParent().getHeight()  * symbolScale.get() / 2);
            //}
        } catch (NullPointerException npe) {
            LOGGER.trace("NPE in screenToLocal");
        }
    }


    @Override
    protected void beforeActivate() {
        super.beforeActivate();
        getChildren().add(symbol);
        runFadeTransition(0.0, 1.0, null);
    }


    @Override
    protected void afterDeactivate() {
        runFadeTransition(0.0, 1.0, event -> {
            super.afterDeactivate();
            getChildren().remove(symbol);
        });
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
        symbol.setTranslateY(y - symbol.getBoundsInLocal().getHeight());
    }

    private void runFadeTransition(double from, double to, EventHandler<ActionEvent> onFinished) {
        final FadeTransition ft = new FadeTransition(Duration.millis(250), symbol);
        ft.setFromValue(from);
        ft.setToValue(to);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);

        if (onFinished != null) {
            ft.setOnFinished(onFinished);
        }

        ft.play();
    }
}
