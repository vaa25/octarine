package info.dejv.octarine.demo.controller;

import info.dejv.octarine.actionhandler.selection.SimpleSelectionHandler;
import info.dejv.octarine.controller.AbstractController;
import info.dejv.octarine.controller.ContainerController;
import info.dejv.octarine.demo.model.RectangleShape;
import info.dejv.octarine.model.ModelElement;
import info.dejv.octarine.model.chunk.Coords2D;
import info.dejv.octarine.model.chunk.Dimension2D;
import info.dejv.octarine.request.handler.DefaultShapeRequestHandler;
import info.dejv.octarine.request.handler.TranslateRequestHandler;
import info.dejv.octarine.tool.selection.SelectionTool;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;

public class RectangleShapeController
        extends AbstractController {

    public RectangleShapeController(RectangleShape model, ContainerController parent) {
        super(model, parent);

        requestHandlers.add(new DefaultShapeRequestHandler(this::createAndUpdateShape));
        requestHandlers.add(new TranslateRequestHandler(model.getChunk("Location", Coords2D.class)));
        actionHandlers.add(new SimpleSelectionHandler<>(SelectionTool.class, this));
    }


    @Override
    protected Rectangle createView(ModelElement model) {
        Dimension2D size = model.getChunk("Size", Dimension2D.class);
        Coords2D location = model.getChunk("Location", Coords2D.class);

        Rectangle r = new Rectangle();
        r.setFill(Color.ALICEBLUE);
        r.setStroke(Color.BLACK);
        r.setStrokeType(StrokeType.INSIDE);

        r.widthProperty().bind(size.getWidth());
        r.heightProperty().bind(size.getHeight());

        r.translateXProperty().bind(location.getX());
        r.translateYProperty().bind(location.getY());
        return r;
    }


    private Shape createAndUpdateShape() {
        return (Shape) createAndUpdateView();
    }
}
