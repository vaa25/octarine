package info.dejv.octarine.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ListChangeListener.Change;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.dejv.octarine.Octarine;
import info.dejv.octarine.OctarineImpl;
import info.dejv.octarine.model.ContainerModelElement;
import info.dejv.octarine.model.ModelElement;

/**
 *
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class DefaultContainerController
        extends DefaultController
        implements ContainerController {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultContainerController.class);
    private final List<Controller> children = new ArrayList<>();
    private final ControllerFactory controllerFactory;
    private final ContainerModelElement model;
    private Octarine octarine;

    public DefaultContainerController(ContainerModelElement model, ContainerController parent, ControllerFactory controllerFactory) {
        super(model, parent);
        this.model = model;
        this.controllerFactory = controllerFactory;

        this.model.getChildren().addListener(this::onChildrenListChanged);

    }


    @Override
    public void setOctarine(OctarineImpl octarine) {
        this.octarine = octarine;
    }

    @Override
    public Octarine getOctarine() {
        return octarine;
    }


    @Override
    public ContainerModelElement getModel() {
        return model;
    }


    @Override
    public List<Controller> getChildren() {
        return children;
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

}
