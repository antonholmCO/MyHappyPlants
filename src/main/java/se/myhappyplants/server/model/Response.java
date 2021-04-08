package se.myhappyplants.server.model;

import java.io.Serializable;
/**
 * ToDo
 * Class that can be inherited by different types of responses from Server
 * Can be sent via TCP
 */
public class Response implements Serializable {

    private boolean success;

    public Response(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}
