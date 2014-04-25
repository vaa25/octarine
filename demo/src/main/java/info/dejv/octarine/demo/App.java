package info.dejv.octarine.demo;

import info.dejv.ui.spring.ZoomableScrollPaneSpringFactory;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Hello world!
 *
 */
public class App extends Application {

    public static void main(final String[] args) {
        Application.launch(App.class, (String[]) null);

    }

    @Override
    public void start(final Stage primaryStage) throws Exception {
        try {
            final AnchorPane page = (AnchorPane) FXMLLoader.load(App.class.getResource("/fxml/GraphEdit.fxml"), null, new ZoomableScrollPaneSpringFactory("spring/demo.xml", "zoomableScrollPane"));
            final Scene scene = new Scene(page);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Octarine test");
            primaryStage.setWidth(846);
            primaryStage.setHeight(914);
            primaryStage.show();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

}
