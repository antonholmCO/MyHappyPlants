package se.myhappyplants.server.model;

import se.myhappyplants.shared.Message;

/**
 * Interface makes sure all ResponseHandlers have at
 * least these methods
 */
public interface ResponseHandler {

    Message getResponse(Message request);
}

