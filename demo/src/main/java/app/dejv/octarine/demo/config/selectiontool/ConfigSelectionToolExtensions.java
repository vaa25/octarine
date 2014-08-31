package app.dejv.octarine.demo.config.selectiontool;

import java.io.IOException;

import javafx.collections.ObservableList;
import javafx.scene.Node;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

import app.dejv.impl.octarine.tool.selection.extension.container.ContainerSelectionToolExtension;
import app.dejv.impl.octarine.tool.selection.extension.container.MarqueeSelectionDynamicFeedback;
import app.dejv.impl.octarine.tool.selection.extension.container.MarqueeSelectionManager;
import app.dejv.impl.octarine.tool.selection.extension.incremental.IncrementalSelectionDynamicFeedback;
import app.dejv.impl.octarine.tool.selection.extension.incremental.IncrementalSelectionManager;
import app.dejv.impl.octarine.tool.selection.extension.single.MouseOverDynamicFeedback;
import app.dejv.impl.octarine.tool.selection.extension.single.SingleSelectionToolExtension;
import app.dejv.octarine.Octarine;
import app.dejv.octarine.controller.ContainerController;
import app.dejv.octarine.controller.Controller;
import app.dejv.octarine.input.MouseDragHelperFactory;

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
@Configuration
@Lazy
public class ConfigSelectionToolExtensions {

    @Autowired
    private Octarine octarine;

    @Autowired
    private MouseDragHelperFactory mouseDragHelperFactory;

    @Autowired
    private MouseOverDynamicFeedback mouseOverDynamicFeedback;

    @Autowired
    private IncrementalSelectionDynamicFeedback incrementalSelectionDynamicFeedback;

    @Autowired
    private IncrementalSelectionManager incrementalSelectionManager;

    @Autowired
    private MarqueeSelectionDynamicFeedback marqueeSelectionDynamicFeedback;

    @Autowired
    private MarqueeSelectionManager marqueeSelectionManager;


    @Bean
    public MouseOverDynamicFeedback mouseOverDynamicFeedback() {
        return new MouseOverDynamicFeedback(octarine);
    }


    @Bean
    public MarqueeSelectionDynamicFeedback marqueeSelectionDynamicFeedback() {
        return new MarqueeSelectionDynamicFeedback(octarine);
    }


    @Bean
    public IncrementalSelectionDynamicFeedback incrementalSelectionDynamicFeedback() throws IOException {
        return new IncrementalSelectionDynamicFeedback(octarine);
    }


    @Bean
    public IncrementalSelectionManager incrementalSelectionManager() {
        return new IncrementalSelectionManager(octarine, incrementalSelectionDynamicFeedback);
    }


    @Bean
    public MarqueeSelectionManager marqueeSelectionManager() {
        return new MarqueeSelectionManager(marqueeSelectionDynamicFeedback, incrementalSelectionManager, mouseDragHelperFactory.create());
    }


    @Bean
    @Scope("prototype")
    public SingleSelectionToolExtension singleSelectionToolExtension(Controller controller) {
        return new SingleSelectionToolExtension(controller, octarine, mouseOverDynamicFeedback, incrementalSelectionManager);
    }


    @Bean
    @Scope("prototype")
    public ContainerSelectionToolExtension containerSelectionToolExtension(ContainerController controller, ObservableList<Node> childrenNodes) {
        return new ContainerSelectionToolExtension(controller, octarine, childrenNodes, marqueeSelectionManager);
    }

}
