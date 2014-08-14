package app.dejv.octarine.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

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

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
@Configuration
public class ConfigController
        implements ControllerFactory {

    @Autowired
    private ApplicationContext appContext;

    @Autowired
    private Octarine octarine;

    @Autowired
    private CanvasViewFactory canvasViewFactory;

    @Autowired
    private RectangleViewFactory rectangleViewFactory;


    @Override //TODO: Incorporate Optional
    public Controller createController(ModelElement modelElement, ContainerController parent) {
        if (modelElement instanceof ShapeContainer) {
            return (ContainerController) appContext.getBean("containerController", modelElement);
        }

        if (modelElement instanceof RectangleShape) {
            return (Controller) appContext.getBean("rectangleController", modelElement, parent);
        }
        return null;
    }


    @Bean
    @Scope("prototype")
    public ContainerController shapeContainerController(ModelElement modelElement) {
        final DefaultContainerController result = new DefaultContainerController((ShapeContainer) modelElement, null, octarine, this, canvasViewFactory);

        final DeleteRequestHandler deleteRequestHandler =
                (DeleteRequestHandler) appContext.getBean("deleteRequestHandler", result);

        final ContainerSelectionToolExtension containerSelectionToolExtension =
                (ContainerSelectionToolExtension) appContext.getBean("containerSelectionToolExtension", result, octarine.getLayerManager().getAllLayers());

        result.addRequestHandler(deleteRequestHandler);
        result.addToolExtension(containerSelectionToolExtension);

        return result;
    }


    @Bean
    @Scope("prototype")
    public Controller rectangleController(ModelElement modelElement, ContainerController parent) {
        final Controller result = new DefaultController(modelElement, parent, rectangleViewFactory);

        result.addRequestHandler((appContext.getBean(DefaultShapeRequestHandler.class))
                .setModelElement(modelElement)
                .setShapeFactory(rectangleViewFactory));

        result.addRequestHandler((appContext.getBean(TranslateRequestHandler.class))
                .setCoords(modelElement.getChunk(BasicProperties.LOCATION, DoubleTuple.class)));

        result.addRequestHandler(appContext.getBean(ResizeRequestHandler.class));
        result.addRequestHandler(appContext.getBean(RotateRequestHandler.class));

        result.addToolExtension((SingleSelectionToolExtension) appContext.getBean("singleSelectionToolExtension_Controller", result));
        return result;
    }
}
