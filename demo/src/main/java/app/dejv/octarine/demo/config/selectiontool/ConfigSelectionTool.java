package app.dejv.octarine.demo.config.selectiontool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import app.dejv.impl.octarine.tool.selection.SelectionOutlinesStaticFeedback;
import app.dejv.impl.octarine.tool.selection.SelectionTool;
import app.dejv.impl.octarine.tool.selection.editmode.delete.EditModeDelete;
import app.dejv.impl.octarine.tool.selection.editmode.resize.EditModeResize;
import app.dejv.impl.octarine.tool.selection.editmode.resize.ResizeHandleFeedback;
import app.dejv.impl.octarine.tool.selection.editmode.resize.ResizeProgressFeedback;
import app.dejv.impl.octarine.tool.selection.editmode.resize.ResizeProgressManager;
import app.dejv.impl.octarine.tool.selection.editmode.rotate.EditModeRotate;
import app.dejv.impl.octarine.tool.selection.editmode.rotate.RotateHandleFeedback;
import app.dejv.impl.octarine.tool.selection.editmode.translate.EditModeTranslate;
import app.dejv.impl.octarine.utils.CompositeObservableBounds;
import app.dejv.octarine.Octarine;
import app.dejv.octarine.input.MouseDragHelperFactory;
import app.dejv.octarine.tool.editmode.EditMode;
import app.dejv.octarine.tool.editmode.ExclusiveEditMode;

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
@Configuration
@Lazy
public class ConfigSelectionTool {

    @Autowired
    private Octarine octarine;

    @Autowired
    private MouseDragHelperFactory mouseDragHelperFactory;

    @Autowired
    private ResizeHandleFeedback resizeHandleFeedback;

    @Autowired
    private ResizeProgressFeedback resizeProgressFeedback;

    @Autowired
    private ResizeProgressManager resizeProgressManager;

    @Bean
    @Autowired
    public ResizeHandleFeedback resizeStaticFeedback(CompositeObservableBounds compositeObservableBounds) {
        return new ResizeHandleFeedback(octarine, compositeObservableBounds);
    }

    @Bean
    public ResizeProgressFeedback resizeProgressFeedback() {
        return new ResizeProgressFeedback(octarine);
    }

    @Bean
    ResizeProgressManager resizeProgressManager() {
        return new ResizeProgressManager(resizeHandleFeedback, resizeProgressFeedback, mouseDragHelperFactory);
    }

    @Bean
    @Autowired
    public RotateHandleFeedback rotateStaticFeedback(CompositeObservableBounds compositeObservableBounds) throws IOException {
        return new RotateHandleFeedback(octarine, compositeObservableBounds);
    }


    @Bean
    public EditModeDelete editModeDelete() {
        return new EditModeDelete(octarine);
    }


    @Bean
    public EditModeTranslate editModeTranslate() {
        return new EditModeTranslate(octarine);
    }


    @Bean
    @Autowired
    public EditModeResize editModeResize(ResizeHandleFeedback resizeStaticFeedback) {
        return new EditModeResize(octarine, resizeStaticFeedback, resizeProgressManager);
    }


    @Bean
    @Autowired
    public EditModeRotate editModeRotate(RotateHandleFeedback rotateStaticFeedback) throws IOException {
        return new EditModeRotate(octarine, rotateStaticFeedback);
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
    public SelectionOutlinesStaticFeedback selectionOutlines() {
        return new SelectionOutlinesStaticFeedback(octarine);
    }


    @Bean
    @Autowired
    public SelectionTool selectionTool(List<EditMode> coexistingEditorModes, List<ExclusiveEditMode> exclusiveEditorModes, SelectionOutlinesStaticFeedback selectionOutlinesStaticFeedback) {
        final SelectionTool result = new SelectionTool(octarine, coexistingEditorModes, exclusiveEditorModes, selectionOutlinesStaticFeedback);
        for (ExclusiveEditMode eem : exclusiveEditorModes) {
            eem.setExclusivityCoordinator(result);
        }
        return result;
    }

}
