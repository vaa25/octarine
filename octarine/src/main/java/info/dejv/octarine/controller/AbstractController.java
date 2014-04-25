package info.dejv.octarine.controller;

import info.dejv.octarine.Octarine;
import info.dejv.octarine.actionhandler.ActionHandler;
import info.dejv.octarine.model.ModelElement;
import info.dejv.octarine.request.Request;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.scene.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractController
        implements Controller {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractController.class);
    protected final List<ActionHandler> actionHandlers = new ArrayList<>();
    protected final List<RequestHandler> requestHandlers = new ArrayList<>();

    private final ContainerController parent;

    private final ModelElement model;
    private Node view;

    private String id;


    public AbstractController(ModelElement model, ContainerController parent) {
        this.parent = parent;
        this.model = model;
    }


    @Override
    public String toString() {
        return getId();
    }


    @Override
    public String getId() {
        if (id == null) {
            id = getClass().getSimpleName() + getOctarine().getNextChildSequence();
        }

        return id;
    }


    @Override
    public Octarine getOctarine() {
        return getRoot().getOctarine();
    }


    @Override
    public ContainerController getRoot() {
        Controller c = this;

        while (c.getParent() != null) {
            c = c.getParent();
        }

        assert (c instanceof ContainerController) : "Top-level container has to implement ContainerController";

        return (ContainerController) c;
    }


    @Override
    public ContainerController getParent() {
        return parent;
    }


    @Override
    public ModelElement getModel() {
        return model;
    }


    @Override
    public Node getView() {
        if (view == null) {
            view = createAndUpdateView();
        }
        return view;
    }


    @Override
    public final void onAdded() {
        actionHandlers.stream().forEach((ah) -> {
            ah.register();
        });

        addViewToScene();

        onControllerAdded();
    }


    @Override
    public void onRemoved() {
        onControllerRemoved();

        removeViewFromScene();

        actionHandlers.stream().forEach((ah) -> {
            ah.unregister();
        });
    }


    @Override
    public void addRequestHandler(RequestHandler requestHandler) {
        if (!requestHandlers.contains(requestHandler)) {
            requestHandlers.add(requestHandler);
        }
    }


    @Override
    public void removeRequestHandler(RequestHandler requestHandler) {
        if (requestHandlers.contains(requestHandler)) {
            requestHandlers.remove(requestHandler);
        }
    }


    @Override
    public boolean supports(Class<? extends Request> request) {
        return requestHandlers.stream().anyMatch((rh) -> (rh.supports(request)));
    }


    @Override
    public void request(Request request) {
        Optional<RequestHandler> requestHandlerLookup = requestHandlers.stream().filter((rh) -> rh.supports(request.getClass())).findFirst();

        if (requestHandlerLookup.isPresent()) {
            requestHandlerLookup.get().request(request);
            return;
        }

        LOG.warn("{}: Unsatisfied request: {}", this, request);
    }

    protected void addViewToScene() {
        getRoot().getOctarine().getLayerManager().getCurrentLayer().add(getView());
    }

    protected void removeViewFromScene() {
        getRoot().getOctarine().getLayerManager().getCurrentLayer().remove(getView());
    }

    protected void onControllerAdded() {
    }

    protected void onControllerRemoved() {
    }


    protected Node createAndUpdateView() {
        Node newView = createView(model);
        updateView(model, newView);
        return newView;
    }


    protected abstract Node createView(ModelElement model);


    protected void updateView(ModelElement model, Node view) {

    }
}
