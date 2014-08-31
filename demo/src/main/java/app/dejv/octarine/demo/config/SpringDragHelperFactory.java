package app.dejv.octarine.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import app.dejv.octarine.input.MouseDragHelper;
import app.dejv.octarine.input.MouseDragHelperFactory;

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
@Component
public class SpringDragHelperFactory
        implements MouseDragHelperFactory {

    @Autowired
    private ApplicationContext appContext;

    @Override
    public MouseDragHelper create() {
        return appContext.getBean(MouseDragHelper.class);
    }
}
