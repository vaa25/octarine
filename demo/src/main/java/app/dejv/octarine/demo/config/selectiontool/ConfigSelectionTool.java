package app.dejv.octarine.demo.config.selectiontool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import app.dejv.impl.octarine.tool.selection.SelectionTool;
import app.dejv.impl.octarine.tool.selection.editmode.EditModeDelete;
import app.dejv.impl.octarine.tool.selection.editmode.EditModeResize;
import app.dejv.impl.octarine.tool.selection.editmode.EditModeRotate;
import app.dejv.impl.octarine.tool.selection.editmode.EditModeTranslate;
import app.dejv.impl.octarine.tool.selection.editmode.feedback.ResizeStaticFeedback;
import app.dejv.impl.octarine.tool.selection.editmode.feedback.RotateStaticFeedback;
import app.dejv.impl.octarine.utils.CompositeObservableBounds;
import app.dejv.octarine.Octarine;
import app.dejv.octarine.tool.editmode.EditMode;
import app.dejv.octarine.tool.editmode.ExclusiveEditMode;

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
@Configuration
@Lazy
public class ConfigSelectionTool {

    @Bean
    @Autowired
    public ResizeStaticFeedback resizeStaticFeedback(Octarine octarine, CompositeObservableBounds compositeObservableBounds) {
        return new ResizeStaticFeedback(octarine, compositeObservableBounds);
    }


    @Bean
    @Autowired
    public RotateStaticFeedback rotateStaticFeedback(Octarine octarine, CompositeObservableBounds compositeObservableBounds) throws IOException {
        return new RotateStaticFeedback(octarine, compositeObservableBounds);
    }


    @Bean
    public EditModeDelete editModeDelete() {
        return new EditModeDelete();
    }

    @Bean
    public EditModeTranslate editModeTranslate() {
        return new EditModeTranslate();
    }

    @Bean
    @Autowired
    public EditModeResize editModeResize(ResizeStaticFeedback resizeStaticFeedback) {
        return new EditModeResize(resizeStaticFeedback);
    }


    @Bean
    @Autowired
    public EditModeRotate editModeRotate(RotateStaticFeedback rotateStaticFeedback) throws IOException {
        return new EditModeRotate(rotateStaticFeedback);
    }


    @Bean
    @Autowired
    public List<EditMode> coexistingEditorModes(EditModeDelete editModeDelete, EditModeTranslate editModeTranslate) {
        final List<EditMode> result = new ArrayList<>();
        result.add(editModeDelete);
        result.add(editModeTranslate);
        return result;
    }


    @Bean
    @Autowired
    public List<ExclusiveEditMode> exclusiveEditorModes(EditModeResize editModeResize, EditModeRotate editModeRotate) {
        final List<ExclusiveEditMode> result = new ArrayList<>();
        result.add(editModeResize);
        result.add(editModeRotate);
        return result;
    }

    @Bean
    @Autowired
    public SelectionTool selectionTool(Octarine octarine, List<EditMode> coexistingEditorModes, List<ExclusiveEditMode> exclusiveEditorModes) {
        return new SelectionTool(octarine, coexistingEditorModes, exclusiveEditorModes);
    }

}
