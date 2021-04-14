package se.myhappyplants.shared;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * ToDo
 * Class that can be used for communication between Client/Server
 * Client/Server via TCP
 * Created by: Christopher O'Driscoll
 * Updated by: Christopher, 2021-04-13
 */
public class Message implements Serializable {

    private String messageType;
    private String searchWord;
    private User user;
    private boolean success;
    private ArrayList<APIPlant> plantList;

    public Message(String messageType, User user, boolean success) {
        this(messageType, success);
        this.user = user;
    }

    public Message(String messageType, boolean success) {
        this.messageType = messageType;
        this.success = success;
    }

    public Message(String messageType, ArrayList<APIPlant> plantList, boolean success) {
        this(messageType, success);
        this.plantList = plantList;
    }

    public Message(String messageType, String searchWord) {
        this.messageType = messageType;
        this.searchWord = searchWord;
    }

    public Message(String messageType, User user) {

        this.messageType = messageType;
        this.user = user;
    }

    public String getMessageType() {
        return messageType;
    }

    public ArrayList<APIPlant> getPlantList() {
        return plantList;
    }

    public User getUser() {
        return user;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getSearchWord() {
        return searchWord;
    }

}
