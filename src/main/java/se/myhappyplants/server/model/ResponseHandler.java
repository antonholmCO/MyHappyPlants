package se.myhappyplants.server.model;

import se.myhappyplants.shared.Message;

public interface ResponseHandler {

    Message getResponse(Message request);
}

