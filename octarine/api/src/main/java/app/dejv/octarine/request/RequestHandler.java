package app.dejv.octarine.request;

/**
 *
 * <br/>
 * Author: dejv (www.dejv.info)
 */

public interface RequestHandler {

    boolean supports(Class<? extends Request> request);

    void request(Request request);

}
