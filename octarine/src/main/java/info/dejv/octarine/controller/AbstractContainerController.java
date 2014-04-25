package info.dejv.octarine.controller;

import info.dejv.octarine.model.ContainerModelElement;
import info.dejv.octarine.model.ModelElement;
import info.dejv.octarine.request.handler.DeleteRequestHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import javafx.collections.ListChangeListener.Change;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public abstract class AbstractContainerController
        extends AbstractController
        implements ContainerController {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractContainerController.class);
    private final List<Controller> children = new ArrayList<>();
    private final ControllerFactory controllerFactory;
    private final ContainerModelElement model;

    public AbstractContainerController(ContainerModelElement model, ContainerController parent, ControllerFactory controllerFactory) {
        super(model, parent);
        this.model = model;
        this.controllerFactory = controllerFactory;

        requestHandlers.add(new DeleteRequestHandler(this));

        this.model.getChildren().addListener(this::onChildrenListChanged);

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
            c.getRemoved().stream().forEach((addedElement) -> onModelElementRemoved(addedElement));
            c.getAddedSubList().stream().forEach((addedElement) -> onModelElementAdded(addedElement));
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
