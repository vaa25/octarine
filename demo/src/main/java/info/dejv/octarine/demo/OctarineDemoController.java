package info.dejv.octarine.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import info.dejv.common.ui.ZoomableScrollPane;
import info.dejv.octarine.Octarine;
import info.dejv.octarine.controller.ContainerController;
import info.dejv.octarine.demo.controller.DemoControllerFactory;
import info.dejv.octarine.demo.model.RectangleShape;
import info.dejv.octarine.demo.model.ShapeContainer;
import info.dejv.octarine.demo.tools.AddRectangleTool;
import info.dejv.octarine.tool.selection.SelectionTool;

public class OctarineDemoController {

// --Commented out by Inspection START (18.7.14 1:54):
//    @FXML
//    private ResourceBundle resources;
// --Commented out by Inspection STOP (18.7.14 1:54)
    private static final Logger LOGGER = LoggerFactory.getLogger(OctarineDemoController.class);

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
        final ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext("app.xml");

        final Octarine octarine = appContext.getBean(Octarine.class);
        final SelectionTool selectionTool = appContext.getBean(SelectionTool.class);
        final ShapeContainer shapeContainer = appContext.getBean(ShapeContainer.class);
        final DemoControllerFactory demoControllerFactory = appContext.getBean(DemoControllerFactory.class);

        zoomableScrollPane.zoomFactorProperty().bindBidirectional(slider.valueProperty());

        bToolSelect.setOnAction((final ActionEvent e) -> octarine.setActiveTool(selectionTool));
        bToolAdd.setOnAction((final ActionEvent e) -> octarine.setActiveTool(new AddRectangleTool()));

        bToolSelect.fire();

        octarine.setRootController((ContainerController) demoControllerFactory.createController(shapeContainer, null));

        shapeContainer.getChildren().add(new RectangleShape(new Rectangle2D(20, 50, 100, 200)));
        shapeContainer.getChildren().add(new RectangleShape(new Rectangle2D(130, 80, 100, 120)));
        shapeContainer.getChildren().add(new RectangleShape(new Rectangle2D(200, 300, 150, 70)));

    }

}
