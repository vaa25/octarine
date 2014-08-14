package app.dejv.octarine.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import app.dejv.impl.octarine.tool.selection.SelectionTool;
import app.dejv.octarine.Octarine;
import app.dejv.octarine.controller.ContainerController;
import app.dejv.octarine.demo.config.ConfigController;
import app.dejv.octarine.demo.model.RectangleShape;
import app.dejv.octarine.demo.model.ShapeContainer;
import app.dejv.octarine.demo.tools.AddRectangleTool;
import info.dejv.common.ui.ZoomableScrollPane;

public class OctarineDemoFXMLController {

    static final long serialVersionUID = 02L;

    public static ApplicationContext applicationContext = null;

// --Commented out by Inspection START (18.7.14 1:54):
//    @FXML
//    private ResourceBundle resources;
// --Commented out by Inspection STOP (18.7.14 1:54)
    private static final Logger LOGGER = LoggerFactory.getLogger(OctarineDemoFXMLController.class);

    @FXML
    private Slider slider;

    @FXML
    private ZoomableScrollPane zoomableScrollPane;

    @FXML
    private Button bToolSelect;

    @FXML
    private Button bToolAdd;

    @FXML
    public void initialize() {
        LOGGER.info("---------- Initializing FXML ----------- ");
        final ApplicationContext appContext = App.APPLICATION_CONTEXT;

        final Octarine octarine = appContext.getBean(Octarine.class);
        final SelectionTool selectionTool = appContext.getBean(SelectionTool.class);

        zoomableScrollPane.zoomFactorProperty().bindBidirectional(slider.valueProperty());

        bToolSelect.setOnAction((final ActionEvent e) -> octarine.setActiveTool(selectionTool));
        bToolAdd.setOnAction((final ActionEvent e) -> octarine.setActiveTool(new AddRectangleTool()));

        initScene(appContext, octarine);

        bToolSelect.fire();
    }


    private void initScene(ApplicationContext appContext, Octarine octarine) {

        final ConfigController controllerFactory = appContext.getBean(ConfigController.class);
        final ShapeContainer shapeContainer = appContext.getBean(ShapeContainer.class);

        octarine.setRootController((ContainerController) controllerFactory.createController(shapeContainer, null));

        shapeContainer.getChildren().add(new RectangleShape(new Rectangle2D(20, 50, 100, 200)));
        shapeContainer.getChildren().add(new RectangleShape(new Rectangle2D(130, 80, 100, 120)));
        shapeContainer.getChildren().add(new RectangleShape(new Rectangle2D(200, 300, 150, 70)));
    }

}
