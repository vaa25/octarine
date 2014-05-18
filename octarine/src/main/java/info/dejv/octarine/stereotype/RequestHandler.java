package info.dejv.octarine.stereotype;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Stereotype for RequestHandler components
 * <br/>
 * Author: dejv (www.dejv.info)
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Scope("prototype")
@Component
public @interface RequestHandler {
}
