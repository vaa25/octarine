package app.dejv.octarine.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import app.dejv.impl.octarine.controller.DefaultContainerController;
import app.dejv.impl.octarine.controller.DefaultController;
import app.dejv.impl.octarine.model.BasicProperties;
import app.dejv.impl.octarine.model.chunk.DoubleTuple;
import app.dejv.impl.octarine.request.handler.DefaultShapeRequestHandler;
import app.dejv.impl.octarine.request.handler.DeleteRequestHandler;
import app.dejv.impl.octarine.request.handler.ResizeRequestHandler;
import app.dejv.impl.octarine.request.handler.RotateRequestHandler;
import app.dejv.impl.octarine.request.handler.TranslateRequestHandler;
import app.dejv.impl.octarine.tool.selection.extension.ContainerSelectionToolExtension;
import app.dejv.impl.octarine.tool.selection.extension.SingleSelectionToolExtension;
import app.dejv.octarine.Octarine;
import app.dejv.octarine.controller.ContainerController;
import app.dejv.octarine.controller.Controller;
import app.dejv.octarine.controller.ControllerFactory;
import app.dejv.octarine.demo.model.RectangleShape;
import app.dejv.octarine.demo.model.ShapeContainer;
import app.dejv.octarine.demo.view.CanvasViewFactory;
import app.dejv.octarine.demo.view.RectangleViewFactory;
import app.dejv.octarine.model.ModelElement;


@Component
public class DemoControllerFactory
        implements ControllerFactory {

    @Autowired
    private ApplicationContext appContext;

    @Autowired
    private Octarine octarine;

    @Autowired
    private CanvasViewFactory canvasViewFactory;

    @Autowired
    private RectangleViewFactory rectangleViewFactory;

    @Override
    public Controller createController(ModelElement modelElement, ContainerController parent) {
        if (modelElement instanceof ShapeContainer) {
            final ContainerController controller = new DefaultContainerController((ShapeContainer) modelElement, parent, octarine, this, canvasViewFactory);

            controller.addRequestHandler((appContext.getBean(DeleteRequestHandler.class)).setTarget(controller));

            controller.addToolExtension( ((ContainerSelectionToolExtension) appContext.getBean("containerSelectionToolExtension_Controller", controller))
                    .setNodeList(octarine.getLayerManager().getAllLayers())); //TODO: Setter or constructor?

            return controller;
        }

        if (modelElement instanceof RectangleShape) {
            final Controller controller = new DefaultController(modelElement, parent, rectangleViewFactory);

            controller.addRequestHandler((appContext.getBean(DefaultShapeRequestHandler.class))
                    .setModelElement(modelElement)
                    .setShapeFactory(rectangleViewFactory));

            controller.addRequestHandler((appContext.getBean(TranslateRequestHandler.class))
                    .setCoords(modelElement.getChunk(BasicProperties.LOCATION, DoubleTuple.class)));

            controller.addRequestHandler(appContext.getBean(ResizeRequestHandler.class));
            controller.addRequestHandler(appContext.getBean(RotateRequestHandler.class));

            controller.addToolExtension( (SingleSelectionToolExtension) appContext.getBean("singleSelectionToolExtension_Controller", controller));


            return controller;
        }

        return null;
    }
}
