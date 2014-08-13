package app.dejv.octarine.demo.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

import app.dejv.impl.octarine.tool.selection.editmode.EditModeDelete;
import app.dejv.impl.octarine.tool.selection.editmode.EditModeResize;
import app.dejv.impl.octarine.tool.selection.editmode.EditModeRotate;
import app.dejv.impl.octarine.tool.selection.editmode.EditModeTranslate;
import app.dejv.impl.octarine.tool.selection.extension.ContainerSelectionToolExtension;
import app.dejv.impl.octarine.tool.selection.extension.SingleSelectionToolExtension;
import app.dejv.impl.octarine.tool.selection.extension.feedback.MarqueeSelectionDynamicFeedback;
import app.dejv.impl.octarine.tool.selection.extension.feedback.MouseOverDynamicFeedback;
import app.dejv.impl.octarine.tool.selection.extension.helper.IncrementalSelectionManager;
import app.dejv.octarine.Octarine;
import app.dejv.octarine.controller.ContainerController;
import app.dejv.octarine.controller.Controller;
import app.dejv.octarine.tool.editmode.EditMode;
import app.dejv.octarine.tool.editmode.ExclusiveEditMode;


/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
@Configuration
@Lazy
public class ConfigOctarine {

    @Autowired
    private ApplicationContext appContext;
//
    @Autowired
    private Octarine octarine;
//
    @Autowired
    private MouseOverDynamicFeedback mouseOverDynamicFeedback;

    @Autowired
    private MarqueeSelectionDynamicFeedback marqueeSelectionDynamicFeedback;

    @Autowired
    private IncrementalSelectionManager incrementalSelectionManager;


    @Bean
    @Autowired
    List<EditMode> coexistingEditorModes(EditModeDelete editModeDelete, EditModeTranslate editModeTranslate) {
        final List<EditMode> result = new ArrayList<>();
        result.add(editModeDelete);
        result.add(editModeTranslate);
        return result;
    }

    @Bean
    @Autowired
    List<ExclusiveEditMode> exclusiveEditorModes(EditModeResize editModeResize, EditModeRotate editModeRotate) {
        final List<ExclusiveEditMode> result = new ArrayList<>();
        result.add(editModeResize);
        result.add(editModeRotate);
        return result;
    }


    @Bean(name = "singleSelectionToolExtension_Controller")
    @Scope("prototype")
    SingleSelectionToolExtension singleSelectionToolExtension(Controller controller) {
        return new SingleSelectionToolExtension(controller, octarine, mouseOverDynamicFeedback, incrementalSelectionManager);
    }


    @Bean(name = "containerSelectionToolExtension_Controller")
    @Scope("prototype")
    ContainerSelectionToolExtension containerSelectionToolExtension(ContainerController controller) {
        return new ContainerSelectionToolExtension(controller, octarine, marqueeSelectionDynamicFeedback, incrementalSelectionManager);
    }
}
