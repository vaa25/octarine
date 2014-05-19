package info.dejv.octarine.actionhandler.selection.feedback;

import java.io.IOException;
import java.net.URL;
import javax.annotation.PostConstruct;

import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.shape.SVGPath;
import javafx.scene.transform.Translate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import info.dejv.octarine.actionhandler.feedback.DynamicFeedback;
import info.dejv.octarine.utils.ConstantZoomDoubleBinding;
import info.dejv.octarine.utils.FormattingUtils;

/**
 * "Increamental selection" dynamic feedback.
 * Adds "+" or "-" symbol next to the mouse cursor to indicate "Add to selection" / "Remove from selection" scenario.
 * <br/>
 * Author: dejv (www.dejv.info)
 */
@Component
public class IncrementalSelectionFeedback
        extends DynamicFeedback {

    private static final Logger LOGGER = LoggerFactory.getLogger(IncrementalSelectionFeedback.class);
    private Group symbolPlus;
    private Group symbolMinus;
    private ConstantZoomDoubleBinding symbolScale;
    private Translate symbolTranslate = new Translate();
    private Group symbol;
    private Type type;

    @PostConstruct
    public void initIncrementalSelectionFeedback() throws IOException {
        DoubleProperty zoomFactor = octarine.getViewer().zoomFactorProperty();
        symbolScale = new ConstantZoomDoubleBinding(zoomFactor, 1.5);

        createAndFormat("/fxml/plus.fxml");
        createAndFormat("/fxml/minus.fxml");
    }

    public void set(Type type) {
        if (this.symbol != null) {

            if (this.type.equals(type)) {
                return;
            }

            remove();
        }

        symbol = (type == Type.ADD) ? symbolPlus : symbolMinus;

        getChildren().add(symbol);
        bind();
        addToScene();

        this.type = type;
    }

    public void remove() {
        if (symbol != null) {

            removeFromScene();
            unbind();
            getChildren().remove(symbol);

            symbol = null;
            type = null;
        }
    }

    public Type getType() {
        return type;
    }

    public void setMouseLocation(double x, double y) {
        try {
            Point2D p = screenToLocal(x, y);
            if (p != null) {
                symbol.setTranslateX(p.getX());
                symbol.setTranslateY(p.getY() - symbol.getBoundsInLocal().getHeight());
            }
        } catch (NullPointerException npe) {
            // A workaround for some annoying Java 8 problem in "screenToLocal"
        }
    }

    private Group createAndFormat(String path) throws IOException {
        final URL url = new ClassPathResource(path).getURL();
        final Group symbol = FXMLLoader.load(url);

        FormattingUtils.formatGlow((SVGPath) symbol.lookup("#glowCircle"));
        FormattingUtils.formatGlow((SVGPath) symbol.lookup("#glowSymbol"));
        FormattingUtils.formatSymbol((SVGPath) symbol.lookup("#circle"), false);
        FormattingUtils.formatSymbol((SVGPath) symbol.lookup("#symbol"), true);

        return symbol;
    }

    private void bind() {
        symbol.getTransforms().add(symbolTranslate);

        symbol.scaleXProperty().bind(symbolScale);
        symbol.scaleYProperty().bind(symbolScale);
    }

    private void unbind() {
        symbol.getTransforms().remove(symbolTranslate);

        symbol.scaleXProperty().unbind();
        symbol.scaleYProperty().unbind();
    }


    public enum Type {
        ADD,
        REMOVE
    }
}
