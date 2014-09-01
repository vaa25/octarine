package app.dejv.octarine.demo.config;

import javafx.beans.property.DoubleProperty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import app.dejv.impl.octarine.controller.DefaultContainerController;
import app.dejv.impl.octarine.controller.DefaultController;
import app.dejv.impl.octarine.drag.DefaultMouseDragHelper;
import app.dejv.impl.octarine.model.BasicProperties;
import app.dejv.impl.octarine.model.chunk.DoubleTuple;
import app.dejv.impl.octarine.request.shape.DefaultShapeRequestHandler;
import app.dejv.impl.octarine.tool.selection.editmode.delete.DeleteRequestHandler;
import app.dejv.impl.octarine.tool.selection.editmode.resize.ResizeRequestHandler;
import app.dejv.impl.octarine.tool.selection.editmode.rotate.RotateRequestHandler;
import app.dejv.impl.octarine.tool.selection.editmode.translate.TranslateRequestHandler;
import app.dejv.octarine.Octarine;
import app.dejv.octarine.controller.ContainerController;
import app.dejv.octarine.controller.Controller;
import app.dejv.octarine.demo.controller.DemoControllerFactory;
import app.dejv.octarine.demo.model.RectangleShape;
import app.dejv.octarine.demo.model.ShapeContainer;
import app.dejv.octarine.demo.view.CanvasViewFactory;
import app.dejv.octarine.demo.view.RectangleViewFactory;
import app.dejv.octarine.model.ModelElement;
import app.dejv.octarine.request.RequestHandler;
import app.dejv.octarine.tool.ToolExtension;

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
@Configuration
public class ConfigController {


    @Autowired
    private ApplicationContext appContext;

    @Autowired
    private Octarine octarine;

    @Autowired
    private CanvasViewFactory canvasViewFactory;

    @Autowired
    private RectangleViewFactory rectangleViewFactory;

    @Autowired
    private DemoControllerFactory controllerFactory;

    @Bean
    @Scope("prototype")
    public ContainerController containerController(ModelElement modelElement) {

        if (!(modelElement instanceof ShapeContainer)) {
            throw new IllegalArgumentException("Expected modelElement of type 'ShapeContainer'");
        }

        final DefaultContainerController result = new DefaultContainerController((ShapeContainer) modelElement, null, octarine, controllerFactory, canvasViewFactory);

        result.addRequestHandler(
                (RequestHandler) appContext.getBean("deleteRequestHandler", modelElement, result));

        result.addToolExtension(
                (ToolExtension) appContext.getBean("containerSelectionToolExtension", result, octarine.getLayerManager().getAllContentLayers()));

        return result;
    }


    @Bean
    @Scope("prototype")
    public Controller rectangleController(ModelElement modelElement, ContainerController parent) {

        if (!(modelElement instanceof RectangleShape)) {
            throw new IllegalArgumentException("Expected modelElement of type 'RectangleShape'");
        }

        final Controller result = new DefaultController((RectangleShape) modelElement, parent, rectangleViewFactory);

        result.addRequestHandler(
                (RequestHandler) appContext.getBean("defaultShapeRequestHandler", modelElement, result));

        result.addRequestHandler(
                (RequestHandler) appContext.getBean("translateRequestHandler", modelElement, result));

        result.addRequestHandler(
                (RequestHandler) appContext.getBean("resizeRequestHandler", modelElement, result));

        result.addRequestHandler(
                (RequestHandler) appContext.getBean("rotateRequestHandler", modelElement, result));


        result.addToolExtension(
                (ToolExtension) appContext.getBean("singleSelectionToolExtension", result));
        return result;
    }


    @Bean
    @Scope("prototype")
    public RequestHandler deleteRequestHandler(ModelElement modelElement, ContainerController controller) {
        return new DeleteRequestHandler(controller);
    }


    @Bean
    @Scope("prototype")
    public RequestHandler defaultShapeRequestHandler(ModelElement modelElement, Controller controller) {
        return new DefaultShapeRequestHandler(modelElement, rectangleViewFactory);
    }


    @Bean
    @Scope("prototype")
    public RequestHandler translateRequestHandler(ModelElement modelElement, Controller controller) {
        return new TranslateRequestHandler(modelElement.getChunk(BasicProperties.LOCATION, DoubleTuple.class));
    }


    @Bean
    @Scope("prototype")
    public RequestHandler resizeRequestHandler(ModelElement modelElement, Controller controller) {
        return new ResizeRequestHandler(modelElement.getChunk(BasicProperties.SIZE, DoubleTuple.class));
    }


    @Bean
    @Scope("prototype")
    public RequestHandler rotateRequestHandler(ModelElement modelElement, Controller controller) {
        return new RotateRequestHandler(modelElement.getChunk(BasicProperties.ROTATION, DoubleProperty.class));
    }


    @Bean
    @Scope("prototype")
    public DefaultMouseDragHelper mouseDragHelper() {
        return new DefaultMouseDragHelper();
    }

}
