package se.myhappyplants.shared;

import java.io.Serializable;

/**
 * ToDo
 * Class that can be used for communication between Client/Server
 * Client/Server via TCP
 * Created by: Christopher O'Driscoll
 * Updated by: Christopher, 2021-04-13
 */
public class Message implements Serializable {

    private String messageType;
    private User user;
    private boolean success;

    public Message(String messageType, User user, boolean success) {
        this.messageType = messageType;
        this.user = user;
        this.success = success;
    }

    public Message(String messageType, boolean success) {
        this.messageType = messageType;
        this.success = success;
    }

    public Message(String messageType, User user) {

        this.messageType = messageType;
        this.user = user;
    }

    public String getMessageType() {
        return messageType;
    }

    public User getUser() {
        return user;
    }

    public boolean isSuccess() {
        return success;
    }
}
