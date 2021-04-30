package se.myhappyplants.shared;

import se.myhappyplants.client.model.LoggedInUser;

import java.io.Serializable;
import java.time.LocalDate;
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
    private LocalDate date;
    private ArrayList<APIPlant> plantList;
    private ArrayList<DBPlant> plantLibrary;
    private DBPlant dbPlant;
    private String newNickname;

    public Message(String messageType, String searchWord) {
        this.messageType = messageType;
        this.searchWord = searchWord;
    }

    public Message(String messageType, User user) {

        this.messageType = messageType;
        this.user = user;
    }

    public Message(String messageType, boolean success) {
        this.messageType = messageType;
        this.success = success;
    }

    public Message(String messageType, User user, boolean success) {
        this(messageType, success);
        this.user = user;
    }
    public Message(String messageType, User user, ArrayList<DBPlant> plantLibrary, boolean success) {
        this(messageType, user, success);
        this.plantLibrary = plantLibrary;
    }

    public Message(String messageType, ArrayList<APIPlant> plantList, boolean success) {
        this(messageType, success);
        this.plantList = plantList;
    }

    public Message(String messageType, User user, DBPlant plant) {
        this(messageType, user);
        this.dbPlant = plant;
    }

    public Message(String messageType, User user, String password) {
    this.messageType = messageType;
    this.user = user;
    }

    public Message (String messageType, User user, DBPlant dbPlant, LocalDate date) {
        this.messageType = messageType;
        this.user = user;
        this.dbPlant = dbPlant;
        this.date = date;
    }

    public Message(String messageType, User user, DBPlant plant, String newNickname) {
        this.messageType = messageType;
        this.user = user;
        this.dbPlant = plant;
        this.newNickname = newNickname;
    }

    public String getNewNickname() {
        return newNickname;
    }

    public String getMessageType() {
        return messageType;
    }

    public String getSearchWord() {
        return searchWord;
    }

    public User getUser() {
        return user;
    }

    public boolean isSuccess() {
        return success;
    }

    public DBPlant getDbPlant() {
        return dbPlant;
    }

    public ArrayList<APIPlant> getPlantList() {
        return plantList;
    }

    public ArrayList<DBPlant> getPlantLibrary() {
        return plantLibrary;
    }

    public LocalDate getDate() {
        return date;
    }
}
