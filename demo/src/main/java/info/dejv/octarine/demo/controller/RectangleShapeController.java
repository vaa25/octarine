package info.dejv.octarine.demo.controller;

import info.dejv.octarine.actionhandler.selection.SimpleSelectionHandler;
import info.dejv.octarine.controller.AbstractController;
import info.dejv.octarine.controller.ContainerController;
import info.dejv.octarine.demo.model.RectangleShape;
import info.dejv.octarine.model.BasicProperties;
import info.dejv.octarine.model.ModelElement;
import info.dejv.octarine.model.chunk.DoubleTuple;
import info.dejv.octarine.request.handler.DefaultShapeRequestHandler;
import info.dejv.octarine.request.handler.ScaleRequestHandler;
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
        requestHandlers.add(new TranslateRequestHandler(model.getChunk(BasicProperties.LOCATION, DoubleTuple.class)));
        requestHandlers.add(new ScaleRequestHandler());
        actionHandlers.add(new SimpleSelectionHandler<>(SelectionTool.class, this));
    }


    @Override
    protected Rectangle createView(ModelElement model) {
        DoubleTuple location = model.getChunk(BasicProperties.LOCATION, DoubleTuple.class);
        DoubleTuple size = model.getChunk(BasicProperties.SIZE, DoubleTuple.class);

        Rectangle r = new Rectangle();
        r.setFill(Color.ALICEBLUE);
        r.setStroke(Color.BLACK);
        r.setStrokeType(StrokeType.INSIDE);

        r.widthProperty().bind(size.getX());
        r.heightProperty().bind(size.getY());

        r.translateXProperty().bind(location.getX());
        r.translateYProperty().bind(location.getY());
        return r;
    }


    private Shape createAndUpdateShape() {
        return (Shape) createAndUpdateView();
    }
}
