package app.dejv.octarine.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import app.dejv.impl.octarine.DefaultOctarineImpl;
import app.dejv.impl.octarine.cfg.DefaultResources;
import app.dejv.impl.octarine.command.DefaultCommandStack;
import app.dejv.impl.octarine.layer.DefaultLayerManager;
import app.dejv.impl.octarine.selection.DefaultSelectionManager;
import app.dejv.impl.octarine.utils.CompositeObservableBounds;
import app.dejv.octarine.Octarine;
import app.dejv.octarine.command.CommandStack;
import app.dejv.octarine.infrastructure.Resources;
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
    public LayerManager layerManager() {
        return new DefaultLayerManager();
    }

    @Bean
    @Autowired
    public Resources resources() {
        return new DefaultResources();
    }

    @Bean
    @Autowired
    Octarine octarine(ZoomableScrollPane zoomableScrollPane,
                      CommandStack commandStack,
                      SelectionManager selectionManager,
                      LayerManager layerManager,
                      Resources resources) {

        return new DefaultOctarineImpl(zoomableScrollPane, commandStack, selectionManager, layerManager, resources);
    }
}
