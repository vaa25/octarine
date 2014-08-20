package app.dejv.impl.octarine.controller;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.scene.Node;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import app.dejv.octarine.Octarine;
import app.dejv.octarine.controller.ContainerController;
import app.dejv.octarine.controller.Controller;
import app.dejv.octarine.model.ModelElement;
import app.dejv.octarine.request.Request;
import app.dejv.octarine.request.RequestHandler;
import app.dejv.octarine.tool.ToolExtension;
import app.dejv.octarine.view.ViewFactory;

public class DefaultController
        implements Controller {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultController.class);

    protected final List<ToolExtension> toolExtensions = new ArrayList<>();
    protected final List<RequestHandler> requestHandlers = new ArrayList<>();

    private final ModelElement model;
    private final ContainerController parent;

    private ViewFactory viewFactory;
    private Node view;

    private String id;


    public DefaultController(ModelElement model, ContainerController parent, ViewFactory viewFactory) {
        requireNonNull(model, "model is null");
        requireNonNull(viewFactory, "viewFactory is null");

        this.parent = parent;
        this.model = model;

        setViewFactory(viewFactory);
    }


    @Override
    public String getId() {
        if (id == null) {
            id = getClass().getSimpleName() + getOctarine().getNextChildSequence();
        }

        return id;
    }


    @Override
    public ModelElement getModel() {
        return model;
    }


    @Override
    public Node getView() {
        if (view == null) {
            view = viewFactory.createView(model);
        }
        return view;
    }


    public ViewFactory getViewFactory() {
        return viewFactory;
    }


    public final void setViewFactory(ViewFactory viewFactory) {
        requireNonNull(viewFactory, "viewFactory is null");

        this.viewFactory = viewFactory;
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
    public final void onAdded() {
        toolExtensions.forEach(ToolExtension::register);

        addViewToScene();

        afterAdded();
    }


    @Override
    public final void onRemoved() {
        beforeRemoved();

        removeViewFromScene();

        toolExtensions.forEach(ToolExtension::unregister);
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
    public void addToolExtension(ToolExtension toolExtension) {
        if (!toolExtensions.contains(toolExtension)) {
            toolExtensions.add(toolExtension);
        }
    }


    @Override
    public void removeToolExtension(ToolExtension toolExtension) {
        if (toolExtensions.contains(toolExtension)) {
            toolExtensions.remove(toolExtension);
        }
    }


    @Override
    public boolean supports(Class<? extends Request> request) {
        // Look first within request handlers...
        boolean result = requestHandlers.stream().anyMatch((rh) -> (rh.supports(request)));

        // ... and if not found there, try also tool extensions.
        if (!result) {
            result = toolExtensions.stream().anyMatch((rh) -> (rh.supports(request)));
        }
        return result;
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


    @Override
    public String toString() {
        return getId();
    }


    protected void addViewToScene() {
        getRoot().getOctarine().getLayerManager().getCurrentContentLayer().add(getView());
    }


    protected void removeViewFromScene() {
        getRoot().getOctarine().getLayerManager().getCurrentContentLayer().remove(getView());
    }


    protected void afterAdded() {

    }


    protected void beforeRemoved() {

    }

}
