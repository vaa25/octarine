package app.dejv.octarine.demo.config;

import javafx.scene.Group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import app.dejv.impl.octarine.DefaultOctarineImpl;
import app.dejv.impl.octarine.command.DefaultCommandStack;
import app.dejv.impl.octarine.layer.DefaultLayerManager;
import app.dejv.impl.octarine.selection.DefaultSelectionManager;
import app.dejv.impl.octarine.utils.CompositeObservableBounds;
import app.dejv.octarine.Octarine;
import app.dejv.octarine.command.CommandStack;
import app.dejv.octarine.layer.LayerManager;
import app.dejv.octarine.selection.SelectionManager;
import info.dejv.common.ui.ZoomableScrollPane;


/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
@Configuration
@Lazy
public class ConfigOctarine {

    @Autowired
    private ApplicationContext appContext;


    @Bean
    public CompositeObservableBounds compositeObservableBounds() {
        return new CompositeObservableBounds();
    }


    @Bean
    public CommandStack commandStack() {
        return new DefaultCommandStack();
    }


    @Bean
    public SelectionManager selectionManager(CompositeObservableBounds compositeObservableBounds) {
        return new DefaultSelectionManager(compositeObservableBounds);
    }


    @Bean
    @Autowired
    public LayerManager layerManager(@Qualifier("groupLayers") Group groupLayers) {
        return new DefaultLayerManager(groupLayers.getChildren());
    }


    @Bean
    public Group groupLayers() {
        return new Group();
    }


    @Bean
    public Group groupFeedbackStatic() {
        return new Group();
    }


    @Bean
    public Group groupFeedbackDynamic() {
        return new Group();
    }


    @Bean
    public Group groupHandles() {
        return new Group();
    }


    @Bean
    @Autowired
    Octarine octarine(ZoomableScrollPane zoomableScrollPane,
                      CommandStack commandStack,
                      SelectionManager selectionManager,
                      LayerManager layerManager,
                      @Qualifier("groupLayers") Group groupLayers,
                      @Qualifier("groupFeedbackStatic") Group groupFeedbackStatic,
                      @Qualifier("groupFeedbackDynamic") Group groupFeedbackDynamic,
                      @Qualifier("groupHandles") Group groupHandles) {
        return new DefaultOctarineImpl(zoomableScrollPane, commandStack, selectionManager, layerManager, groupLayers, groupFeedbackStatic, groupFeedbackDynamic, groupHandles);
    }
}
