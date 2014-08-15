package app.dejv.impl.octarine.cfg;

import java.util.Optional;

import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.shape.SVGPath;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import app.dejv.impl.octarine.utils.FormattingUtils;
import app.dejv.octarine.infrastructure.Resources;

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class DefaultResources
        implements Resources {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultResources.class);

    @Override
    public Optional<Group> getShape(String id) {
        final String path = "fxml/" + id + ".fxml";

        try {
            final Group symbol = FXMLLoader.load(getClass().getClassLoader().getResource(path));

            FormattingUtils.formatSymbol((SVGPath) symbol.lookup("#symbol"));

            return Optional.of(symbol);

        } catch (Exception e) {
            LOGGER.error("Unable to obtain resource [" + path + "]", e);
            return Optional.empty();
        }
    }
}
