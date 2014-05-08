package info.dejv.octarine.actionhandler.selection.feedback;

import info.dejv.octarine.Octarine;
import info.dejv.octarine.actionhandler.feedback.DynamicFeedback;
import info.dejv.octarine.utils.FormattingUtils;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.shape.SVGPath;
import javafx.scene.transform.Translate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author dejv
 */
public class IncrementalSelectionFeedback
        extends DynamicFeedback {

    private static final Logger LOGGER = LoggerFactory.getLogger(IncrementalSelectionFeedback.class);

    private static IncrementalSelectionFeedback instance;

    public static void add(Type type, Octarine editor) {
        if (instance != null) {
            remove();
        }
        try {
            instance = new IncrementalSelectionFeedback(editor, type);
            instance.addToEditor();
        } catch (IOException ex) {
            LOGGER.error("Failed to instantiate IncrementalSelectionFeedback", ex);
        }
    }

    public static void remove() {
        if (instance == null) {
            return;
        }
        instance.removeFromEditor();
        instance = null;
    }

    public static IncrementalSelectionFeedback getInstance() {
        return instance;
    }

    public enum Type {

        ADD,
        REMOVE
    }

    private final Group symbol;
    private final Translate symbolTranslate = new Translate();

    private final Type type;


    public IncrementalSelectionFeedback(Octarine editor, Type type) throws IOException {
        super(editor);

        this.type = type;

        symbol = FXMLLoader.load(System.class.getResource(type == Type.ADD ? "/fxml/plus.fxml" : "/fxml/minus.fxml"));
        symbol.getTransforms().add(symbolTranslate);
        FormattingUtils.formatGlow((SVGPath) symbol.lookup("#glowCircle"));
        FormattingUtils.formatGlow((SVGPath) symbol.lookup("#glowSymbol"));
        FormattingUtils.formatSymbol((SVGPath) symbol.lookup("#circle"), false);
        FormattingUtils.formatSymbol((SVGPath) symbol.lookup("#symbol"), true);

        getChildren().add(symbol);
    }


    public Type getType() {
        return type;
    }


    public void setMouseLocation(double x, double y) {
        try {
            Point2D p = screenToLocal(x, y);
            if (p != null) {
                symbolTranslate.setX(p.getX());
                symbolTranslate.setY(p.getY() - symbol.getBoundsInLocal().getHeight());
            }
        } catch (NullPointerException npe) {
            // A workaround for some annoying Java 8 problem in "screenToLocal"
        }
    }

}
