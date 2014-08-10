package info.dejv.octarine.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import info.dejv.octarine.Octarine;
import info.dejv.octarine.tool.selection.extension.ContainerSelectionToolExtension;
import info.dejv.octarine.tool.selection.extension.SingleSelectionToolExtension;
import info.dejv.octarine.controller.ContainerController;
import info.dejv.octarine.controller.Controller;
import info.dejv.octarine.controller.ControllerFactory;
import info.dejv.octarine.controller.DefaultContainerController;
import info.dejv.octarine.controller.DefaultController;
import info.dejv.octarine.demo.model.RectangleShape;
import info.dejv.octarine.demo.model.ShapeContainer;
import info.dejv.octarine.demo.view.CanvasViewFactory;
import info.dejv.octarine.demo.view.RectangleViewFactory;
import info.dejv.octarine.model.BasicProperties;
import info.dejv.octarine.model.ModelElement;
import info.dejv.octarine.model.chunk.DoubleTuple;
import info.dejv.octarine.request.handler.DefaultShapeRequestHandler;
import info.dejv.octarine.request.handler.DeleteRequestHandler;
import info.dejv.octarine.request.handler.ResizeRequestHandler;
import info.dejv.octarine.request.handler.RotateRequestHandler;
import info.dejv.octarine.request.handler.TranslateRequestHandler;

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
