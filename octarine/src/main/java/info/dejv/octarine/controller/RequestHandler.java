package info.dejv.octarine.controller;

import info.dejv.octarine.request.Request;

/**
 *
 * <br/>
 * Author: dejv (www.dejv.info)
 */

public interface RequestHandler {

    boolean supports(Class<? extends Request> request);

    void request(Request request);

}
