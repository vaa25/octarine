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
    private MouseOverDynamicFeedback mouseOverDynamicFeedback;

    @Autowired
    private MarqueeSelectionDynamicFeedback marqueeSelectionDynamicFeedback;

    @Autowired
    private IncrementalSelectionManager incrementalSelectionManager;

    @Autowired
    private MarqueeSelectionManager marqueeSelectionManager;

    @Bean
    @Autowired
    public MouseOverDynamicFeedback mouseOverDynamicFeedback(Octarine octarine) {
        return new MouseOverDynamicFeedback(octarine);
    }


    @Bean
    @Autowired
    public MarqueeSelectionDynamicFeedback marqueeSelectionDynamicFeedback(Octarine octarine) {
        return new MarqueeSelectionDynamicFeedback(octarine);
    }


    @Bean
    @Autowired
    public IncrementalSelectionDynamicFeedback incrementalSelectionDynamicFeedback(Octarine octarine) throws IOException {
        return new IncrementalSelectionDynamicFeedback(octarine);
    }


    @Bean
    @Autowired
    public IncrementalSelectionManager incrementalSelectionManager(Octarine octarine, IncrementalSelectionDynamicFeedback incrementalSelectionDynamicFeedback) {
        return new IncrementalSelectionManager(octarine, incrementalSelectionDynamicFeedback);
    }


    @Bean
    @Autowired
    public MarqueeSelectionManager marqueeSelectionManager(MarqueeSelectionDynamicFeedback marqueeSelectionDynamicFeedback, IncrementalSelectionManager incrementalSelectionManager) {
        return new MarqueeSelectionManager(marqueeSelectionDynamicFeedback, incrementalSelectionManager);
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
