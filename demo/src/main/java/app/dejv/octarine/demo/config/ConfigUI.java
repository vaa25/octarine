package app.dejv.octarine.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import info.dejv.common.ui.ZoomableScrollPane;
import info.dejv.common.ui.logic.impl.ZoomableScrollPaneControllerImpl;
import info.dejv.common.ui.logic.impl.ZoomableScrollPaneSpringFactory;

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
@Configuration
@Lazy
public class ConfigUI {

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
