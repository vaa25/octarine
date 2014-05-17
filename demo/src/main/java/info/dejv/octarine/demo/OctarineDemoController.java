package info.dejv.octarine.demo;

import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;

import org.springframework.beans.factory.annotation.Autowired;

import info.dejv.common.ui.ZoomableScrollPane;
import info.dejv.octarine.Octarine;
import info.dejv.octarine.demo.controller.ShapeContainerController;
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
    private ShapeContainerController shapeContainerController;

    @FXML
    void initialize() {
        zoomableScrollPane.zoomFactorProperty().bindBidirectional(slider.valueProperty());

        octarine.setRootController(shapeContainerController);

        bToolSelect.setOnAction((final ActionEvent e) -> {
            octarine.setActiveTool(selectionTool);
        });

        bToolAdd.setOnAction((final ActionEvent e) -> {
            octarine.setActiveTool(new AddRectangleTool());
        });

        bToolSelect.fire();
    }
}
