package info.dejv.octarine.demo.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import info.dejv.common.ui.ZoomableScrollPane;
import info.dejv.common.ui.logic.impl.ZoomableScrollPaneControllerImpl;
import info.dejv.common.ui.logic.impl.ZoomableScrollPaneSpringFactory;
import info.dejv.octarine.demo.OctarineDemoController;
import info.dejv.octarine.tool.selection.editmode.EditMode;
import info.dejv.octarine.tool.selection.editmode.EditModeDelete;
import info.dejv.octarine.tool.selection.editmode.EditModeResize;
import info.dejv.octarine.tool.selection.editmode.EditModeRotate;
import info.dejv.octarine.tool.selection.editmode.EditModeTranslate;
import info.dejv.octarine.tool.selection.editmode.ExclusiveEditMode;

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
@Configuration
public class DemoConfig {

    @Autowired
    private ApplicationContext appContext;


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

    @Bean
    OctarineDemoController octarineDemoController() {

        return new OctarineDemoController();
    }

    @Bean
    public ZoomableScrollPaneSpringFactory zoomableScrollPaneSpringFactory() {

        return new ZoomableScrollPaneSpringFactory();
    }


    @Bean
    public ZoomableScrollPaneControllerImpl zoomableScrollPaneLogic() {

        return new ZoomableScrollPaneControllerImpl();
    }


    @Bean
    public ZoomableScrollPane zoomableScrollPane(ZoomableScrollPaneControllerImpl logic) {
        final ZoomableScrollPane zoomableScrollPane = new ZoomableScrollPane();
        zoomableScrollPane.setController(logic);

        return zoomableScrollPane;
    }
}
