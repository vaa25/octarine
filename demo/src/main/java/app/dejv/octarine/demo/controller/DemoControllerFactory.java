package app.dejv.octarine.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import app.dejv.octarine.controller.ContainerController;
import app.dejv.octarine.controller.Controller;
import app.dejv.octarine.controller.ControllerFactory;
import app.dejv.octarine.demo.model.RectangleShape;
import app.dejv.octarine.demo.model.ShapeContainer;
import app.dejv.octarine.model.ModelElement;

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
@Component
public class DemoControllerFactory
        implements ControllerFactory {

    @Autowired
    private ApplicationContext appContext;


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

}
