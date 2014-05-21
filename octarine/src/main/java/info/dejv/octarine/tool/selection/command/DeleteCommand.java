package info.dejv.octarine.tool.selection.command;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javafx.collections.ObservableList;

import info.dejv.octarine.command.Command;
import info.dejv.octarine.controller.ContainerController;
import info.dejv.octarine.controller.Controller;
import info.dejv.octarine.model.ModelElement;
import info.dejv.octarine.selection.SelectionManager;

/**
 *
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class DeleteCommand
        implements Command {

    private final ContainerController target;
    private final SelectionManager selectionManager;

    private final Set<ModelElement> modelElementsToDelete;
    private final ObservableList<ModelElement> targetChildrenList;

    public DeleteCommand(Set<ModelElement> modelElementsToDelete, ContainerController target, SelectionManager selectionManager) {
        this.target = target;
        this.selectionManager = selectionManager;

        this.modelElementsToDelete = modelElementsToDelete;
        this.targetChildrenList = target.getModel().getChildren().get();
    }


    @Override
    public void execute() {
        selectionManager.deselectAll();
        modelElementsToDelete.stream()
                .forEach(targetChildrenList::remove);
    }

    @Override
    public void undo() {
        List<Controller> controllersForModelElements = new ArrayList<>();
        modelElementsToDelete.stream()
                .forEach((model) -> {
                    targetChildrenList.add(model);
                    Controller c = target.lookup((controller) -> controller.getModel().equals(model));
                    requireNonNull(c, "Controller for model wasn't found");

                    controllersForModelElements.add(c);
                });

        selectionManager.replace(controllersForModelElements);
    }

}
