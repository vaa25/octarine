package info.dejv.octarine.demo;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import info.dejv.common.ui.logic.impl.ZoomableScrollPaneSpringFactory;

/**
 * Octarine Demo launcher
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class App extends Application {


    public static void main(final String[] args) {
        Application.launch(App.class, (String[]) null);
    }


    @Override
    public void start(final Stage primaryStage) throws Exception {
        ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext("app.xml");
        try {

            final FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(App.class.getResource("/fxml/OctarineDemo.fxml"));
            fxmlLoader.setBuilderFactory(appContext.getBean(ZoomableScrollPaneSpringFactory.class));
            fxmlLoader.setControllerFactory(param -> {

                if (OctarineDemoController.class.equals(param)) {
                    return appContext.getBean(OctarineDemoController.class);
                }
                return null;

            });

            final AnchorPane page = (AnchorPane) fxmlLoader.load();
            final Scene scene = new Scene(page);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Octarine demo [0.1.0]");
            primaryStage.setWidth(846);
            primaryStage.setHeight(914);
            primaryStage.show();

        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

}
