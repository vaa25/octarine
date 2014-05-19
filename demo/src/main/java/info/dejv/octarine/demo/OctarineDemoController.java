package info.dejv.octarine.demo;

import java.util.ResourceBundle;
import javax.annotation.PostConstruct;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;

import org.springframework.beans.factory.annotation.Autowired;

import info.dejv.common.ui.ZoomableScrollPane;
import info.dejv.octarine.Octarine;
import info.dejv.octarine.controller.ContainerController;
import info.dejv.octarine.demo.controller.DemoControllerFactory;
import info.dejv.octarine.demo.model.RectangleShape;
import info.dejv.octarine.demo.model.ShapeContainer;
import info.dejv.octarine.demo.tools.AddRectangleTool;
import info.dejv.octarine.tool.selection.SelectionTool;

public class OctarineDemoController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private Slider slider;

    @FXML
    private ZoomableScrollPane zoomableScrollPane;

    @FXML
    private Button bToolSelect;

    @FXML
    private Button bToolAdd;

    @Autowired
    private Octarine octarine;

    @Autowired
    private SelectionTool selectionTool;

    @Autowired
    private ShapeContainer shapeContainer;

    @Autowired
    private DemoControllerFactory demoControllerFactory;

    @FXML
    void initialize() {
        zoomableScrollPane.zoomFactorProperty().bindBidirectional(slider.valueProperty());

        bToolSelect.setOnAction((final ActionEvent e) -> {
            octarine.setActiveTool(selectionTool);
        });

        bToolAdd.setOnAction((final ActionEvent e) -> {
            octarine.setActiveTool(new AddRectangleTool());
        });

        bToolSelect.fire();
    }

    @PostConstruct
    public void init() {

        octarine.setRootController((ContainerController) demoControllerFactory.createController(shapeContainer, null));

        shapeContainer.getChildren().add(new RectangleShape(new Rectangle2D(20, 50, 100, 200)));
        shapeContainer.getChildren().add(new RectangleShape(new Rectangle2D(130, 80, 100, 120)));
        shapeContainer.getChildren().add(new RectangleShape(new Rectangle2D(200, 300, 150, 70)));
    }
}
