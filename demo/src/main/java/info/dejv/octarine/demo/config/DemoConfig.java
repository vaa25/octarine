package info.dejv.octarine.demo.config;

import javafx.geometry.Rectangle2D;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import info.dejv.common.ui.ZoomableScrollPane;
import info.dejv.common.ui.logic.impl.ZoomableScrollPaneControllerImpl;
import info.dejv.common.ui.logic.impl.ZoomableScrollPaneSpringFactory;
import info.dejv.octarine.Octarine;
import info.dejv.octarine.OctarineImpl;
import info.dejv.octarine.demo.OctarineDemoController;
import info.dejv.octarine.demo.controller.ShapeContainerController;
import info.dejv.octarine.demo.model.RectangleShape;
import info.dejv.octarine.demo.model.ShapeContainer;
import info.dejv.octarine.tool.selection.SelectionTool;

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
@Configuration
public class DemoConfig {

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


    @Bean
    public Octarine octarine(ZoomableScrollPane zoomableScrollPane) {
        final Octarine octarine = new OctarineImpl(zoomableScrollPane);

        return octarine;
    }


    @Bean
    public SelectionTool selectionTool(Octarine octarine) {
        final SelectionTool selectionTool = new SelectionTool();
        selectionTool.setOctarine(octarine);

        return selectionTool;
    }


//    @Bean
//    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public RectangleShape rectangleShape(double x, double y, double width, double height) {
        return new RectangleShape(new Rectangle2D(x, y, width, height));
    }


    @Bean
    public ShapeContainer shapeContainer() {
        final ShapeContainer shapeContainer = new ShapeContainer();

        shapeContainer.getChildren().add(rectangleShape(20.0, 20.0, 100.0, 100.0));
        shapeContainer.getChildren().add(rectangleShape(150.0, 10.0, 50.0, 150.0));

        return shapeContainer;
    }


    @Bean
    public ShapeContainerController shapeContainerController(Octarine octarine, ShapeContainer shapeContainer) {
        final ShapeContainerController shapeContainerController = new ShapeContainerController(octarine, shapeContainer);

        return shapeContainerController;
    }



}
