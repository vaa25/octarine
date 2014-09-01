package app.dejv.impl.octarine.tool.selection.editmode.resize;

import static java.util.Objects.requireNonNull;

import javafx.geometry.Dimension2D;
import javafx.scene.transform.Transform;

import app.dejv.impl.octarine.command.CompoundCommand;
import app.dejv.impl.octarine.model.chunk.DoubleTuple;
import app.dejv.impl.octarine.request.AbstractRequestHandler;
import app.dejv.impl.octarine.tool.selection.editmode.translate.TranslateCommand;
import app.dejv.octarine.request.Request;

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class ResizeRequestHandler
        extends AbstractRequestHandler {

    private final DoubleTuple location;
    private final DoubleTuple size;


    public ResizeRequestHandler(DoubleTuple location, DoubleTuple size) {
        requireNonNull(location, "location is null");
        requireNonNull(size, "size is null");

        this.location = location;
        this.size = size;
    }


    @Override
    public boolean supports(Class<? extends Request> request) {
        return ResizeRequest.class.equals(request);
    }


    @Override
    protected void requestChecked(Request request) {
        final ResizeRequest resizeRequest = (ResizeRequest) request;
        final Transform transform = resizeRequest.getTransform();

        final CompoundCommand commands = new CompoundCommand();
        commands.add(new TranslateCommand(location, transform.transform(location.getX(), location.getY())));
        commands.add(new ResizeCommand(size, new Dimension2D(transform.getMxx(), transform.getMyy())));

        resizeRequest.setCommand(commands);
    }

}
