package se.myhappyplants.shared;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Class that can be used for communication between Client/Server
 * Client/Server via TCP
 * Created by: Christopher O'Driscoll
 * Updated by: Linn Borgström 2021-04-29
 */
public class Message implements Serializable {

    private String messageType;
    private String messageText;
    private String[] stringArray;
    private User user;
    private boolean success;
    private LocalDate date;
    private ArrayList<APIPlant> plantList;
    private ArrayList<DBPlant> plantLibrary;
    private DBPlant dbPlant;
    private APIPlant apiPlant;
    private String newNickname;

    public Message(String messageType, String messageText) {
        this.messageType = messageType;
        this.messageText = messageText;
    }

    public Message(String messageType, User user) {

        this.messageType = messageType;
        this.user = user;
    }

    public Message(String messageType, String[] message) {
        this.messageType = messageType;
        stringArray = message;
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

    public Message(String messageType, APIPlant apiPlant) {
        this.messageType = messageType;
        this.apiPlant = apiPlant;
    }

    public String[] getStringArray() {
        return stringArray;
    }

    public Message(String messageType, User user, DBPlant dbPlant, LocalDate date) {
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

    public String getMessageText() {
        return messageText;
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

    public APIPlant getPlant() {
        return apiPlant;
    }

    public LocalDate getDate() {
        return date;
    }
}
