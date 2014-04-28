package info.dejv.octarine.demo.controller;

import info.dejv.octarine.Octarine;
import info.dejv.octarine.actionhandler.selection.ContainerSelectionHandler;
import info.dejv.octarine.controller.AbstractContainerController;
import info.dejv.octarine.demo.model.ShapeContainer;
import info.dejv.octarine.model.ModelElement;
import info.dejv.octarine.model.chunk.Dimension2D;
import info.dejv.octarine.request.handler.DeleteRequestHandler;
import info.dejv.octarine.tool.selection.SelectionTool;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShapeContainerController
        extends AbstractContainerController {

    private static final Logger LOG = LoggerFactory.getLogger(ShapeContainerController.class);

    private final Octarine octarine;

    public ShapeContainerController(final Octarine octarine, final ShapeContainer model) {
        super(model, null, new DemoControllerFactory());
        this.octarine = octarine;

        requestHandlers.add(new DeleteRequestHandler(this));

        actionHandlers.add(new ContainerSelectionHandler<>(SelectionTool.class, this, octarine.getLayerManager().getAllLayers()));
    }


    @Override
    public Octarine getOctarine() {
        return octarine;
    }


    @Override
    protected Node createView(ModelElement model) {
        Dimension2D size = model.getChunk("Size", Dimension2D.class);
        Rectangle r = new Rectangle();

        r.setFill(Color.WHITE);
        r.setStroke(Color.BLACK);
        r.setStrokeType(StrokeType.INSIDE);

        r.setLayoutX(0.0d);
        r.setLayoutY(0.0d);
        r.widthProperty().bind(size.getWidth());
        r.heightProperty().bind(size.getHeight());
        return r;
    }
}
