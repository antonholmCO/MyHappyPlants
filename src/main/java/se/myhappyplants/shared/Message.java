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
    private boolean notifications;
    private String messageText;
    private String[] stringArray;
    private User user;
    private boolean success;
    private LocalDate date;
    private ArrayList<Plant> plantList;
    private ArrayList<Plant> plantLibrary;
    private Plant plant;
    private String newNickname;
    private ArrayList<Plant> userLibrary;

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

    public Message(String messageType, User user, ArrayList<Plant> plantLibrary, boolean success) {
        this(messageType, user, success);
        this.plantLibrary = plantLibrary;
    }

    public Message(String messageType, ArrayList<Plant> plantList, boolean success) {
        this(messageType, success);
        this.plantList = plantList;
    }

    public Message(String messageType, User user, Plant plant) {
        this(messageType, user);
        this.plant = plant;
    }

    public Message(String messageType, Plant plant) {
        this.messageType = messageType;
        this.plant = plant;
    }

    public Message(String messageType, boolean notifications, User user) {
        this.messageType = messageType;
        this.notifications = notifications;
        this.user = user;
    }

    public String[] getStringArray() {
        return stringArray;
    }

    public Message(String messageType, User user, Plant plant, LocalDate date) {
        this.messageType = messageType;
        this.user = user;
        this.plant = plant;
        this.date = date;
    }

    public Message(String messageType, User user, Plant plant, String newNickname) {
        this.messageType = messageType;
        this.user = user;
        this.plant = plant;
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

    public Plant getDbPlant() {
        return plant;
    }

    public ArrayList<Plant> getPlantList() {
        return plantList;
    }

    public ArrayList<Plant> getPlantLibrary() {
        return plantLibrary;
    }

    public Plant getPlant() {
        return plant;
    }

    public LocalDate getDate() {
        return date;
    }

    public boolean getNotifications() {
        return notifications;
    }
}
