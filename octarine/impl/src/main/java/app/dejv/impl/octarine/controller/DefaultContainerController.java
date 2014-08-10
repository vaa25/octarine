package app.dejv.impl.octarine.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ListChangeListener.Change;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import app.dejv.octarine.Octarine;
import app.dejv.octarine.controller.ContainerController;
import app.dejv.octarine.controller.Controller;
import app.dejv.octarine.controller.ControllerFactory;
import app.dejv.octarine.model.ContainerModelElement;
import app.dejv.octarine.model.ModelElement;
import app.dejv.octarine.view.ViewFactory;


/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class DefaultContainerController
        extends DefaultController
        implements ContainerController {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultContainerController.class);

    private final ContainerModelElement model;
    private final Octarine octarine;
    private final ControllerFactory controllerFactory;

    private final List<Controller> children = new ArrayList<>();


    public DefaultContainerController(ContainerModelElement model, ContainerController parent, Octarine octarine, ControllerFactory controllerFactory, ViewFactory viewFactory) {
        super(model, parent, viewFactory);

        this.model = model;
        this.octarine = octarine;
        this.controllerFactory = controllerFactory;

        this.model.getChildren().addListener(this::onChildrenListChanged);
    }


    public Octarine getOctarine() {
        return octarine;
    }


    public ContainerModelElement getModel() {
        return model;
    }


    public ControllerFactory getControllerFactory() {
        return controllerFactory;
    }


    @Override
    public List<Controller> getChildren() {
        return children;
    }


    @Override
    public Controller lookup(Predicate<Controller> predicate) {
        if (predicate.test(this)) {
            return this;
        }

        Optional<Controller> result = children.stream()
                .filter((child) -> (!(child instanceof ContainerController)))
                .filter(predicate)
                .findAny();

        if (result.isPresent()) {
            return result.get();
        }

        result = children.stream()
                .filter(child -> (child instanceof ContainerController))
                .map(child -> ((ContainerController) child))
                .map(container -> container.lookup(predicate))
                .findAny();

        if (result.isPresent()) {
            return result.get();
        }

        return null;
    }


    private void onChildrenListChanged(Change<? extends ModelElement> c) {
        if (c.next()) {
            if (c.wasRemoved()) {
                c.getRemoved().forEach(this::onModelElementRemoved);
            }
            if (c.wasAdded()) {
                c.getAddedSubList().forEach(this::onModelElementAdded);
            }
        }
    }


    private void onModelElementAdded(final ModelElement item) {

        final Controller controller = controllerFactory.createController(item, this);
        getChildren().add(controller);

        controller.onAdded();
        LOG.debug("Added child element: {}", item);
    }


    private void onModelElementRemoved(final ModelElement item) {

        final Controller controller = lookup(c -> item.equals(c.getModel()));
        getChildren().remove(controller);

        controller.onRemoved();
        LOG.debug("Removed child element: {}", item);
    }

}
