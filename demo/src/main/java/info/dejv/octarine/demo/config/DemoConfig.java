package info.dejv.octarine.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import info.dejv.common.ui.ZoomableScrollPane;
import info.dejv.common.ui.logic.impl.ZoomableScrollPaneControllerImpl;
import info.dejv.common.ui.logic.impl.ZoomableScrollPaneSpringFactory;
import info.dejv.octarine.demo.OctarineDemoController;

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
@Configuration
public class DemoConfig {

    @Autowired
    private ApplicationContext appContext;


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
