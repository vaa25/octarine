package info.dejv.octarine.demo;

import info.dejv.common.ui.ZoomableScrollPane;
import info.dejv.octarine.Octarine;
import info.dejv.octarine.OctarineImpl;
import info.dejv.octarine.demo.controller.ShapeContainerController;
import info.dejv.octarine.demo.model.RectangleShape;
import info.dejv.octarine.demo.model.ShapeContainer;
import info.dejv.octarine.demo.tools.AddRectangleTool;
import info.dejv.octarine.tool.selection.SelectionTool;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;

public class GraphEditController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private Slider slider;

    @FXML
    private ZoomableScrollPane zoom;

    @FXML
    private Button bToolSelect;

    @FXML
    private Button bToolAdd;

    private SelectionTool selectionTool;

    @FXML
    void initialize() {
        zoom.zoomFactorProperty().bindBidirectional(slider.valueProperty());

        final Octarine octarine = new OctarineImpl(zoom);
        SelectionTool.initialize(octarine);

        ShapeContainer canvas = new ShapeContainer();
        octarine.setRootController(new ShapeContainerController(octarine, canvas));

        canvas.getChildren().add(new RectangleShape(new Rectangle2D(20.0, 20.0, 100.0, 100.0)));
        canvas.getChildren().add(new RectangleShape(new Rectangle2D(150.0, 10.0, 50.0, 150.0)));

        selectionTool = SelectionTool.getInstance();

        bToolSelect.setOnAction((final ActionEvent e) -> {
            octarine.setActiveTool(selectionTool);
        });

        bToolAdd.setOnAction((final ActionEvent e) -> {
            octarine.setActiveTool(new AddRectangleTool());
        });

        bToolSelect.fire();
    }
}
