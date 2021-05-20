package se.myhappyplants.server.model;

import se.myhappyplants.shared.Message;

/**
 * Interface makes sure all ResponseHandlers have at
 * least these methods
 */
public interface IResponseHandler {

    Message getResponse(Message request);
}

