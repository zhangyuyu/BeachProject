package beach.utils.handlers;

import beach.utils.requests.CustomerRequest;

/**
 * Created by mlding on 8/14/15.
 */
public interface RequestHandler {
    void handle(CustomerRequest request);
}
