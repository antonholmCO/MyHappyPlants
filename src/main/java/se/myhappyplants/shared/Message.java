package se.myhappyplants.shared;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Class that can be used for communication between Client/Server
 * Client/Server via TCP
 * Created by: Christopher O'Driscoll
 * Updated by: Linn Borgström 2021-05-13
 */
public class Message implements Serializable {

    private MessageType messageType;
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
    private PlantDetails plantDetails;

    public Message(MessageType messageType, String messageText) {
        this.messageType = messageType;
        this.messageText = messageText;
    }

    public Message(MessageType messageType, User user) {

        this.messageType = messageType;
        this.user = user;
    }

    public Message(MessageType messageType, String[] message) {
        this.messageType = messageType;
        stringArray = message;
    }

    public Message(MessageType messageType, boolean success) {
        this.messageType = messageType;
        this.success = success;
    }

    public Message(MessageType messageType, User user, boolean success) {
        this(messageType, success);
        this.user = user;
    }

    public Message(MessageType messageType, User user, ArrayList<Plant> plantLibrary, boolean success) {
        this(messageType, user, success);
        this.plantLibrary = plantLibrary;
    }

    public Message(MessageType messageType, ArrayList<Plant> plantList, boolean success) {
        this(messageType, success);
        this.plantList = plantList;
    }

    public Message(MessageType messageType, User user, Plant plant) {
        this(messageType, user);
        this.plant = plant;
    }

    public Message(MessageType messageType, Plant plant) {
        this.messageType = messageType;
        this.plant = plant;
    }

    public Message(MessageType messageType, boolean notifications, User user) {
        this.messageType = messageType;
        this.notifications = notifications;
        this.user = user;
    }


    public Message(MessageType messageType, User user, Plant plant, LocalDate date) {
        this.messageType = messageType;
        this.user = user;
        this.plant = plant;
        this.date = date;
    }

    public Message(MessageType messageType, User user, Plant plant, String newNickname) {
        this.messageType = messageType;
        this.user = user;
        this.plant = plant;
        this.newNickname = newNickname;
    }

    public Message(MessageType messageType, PlantDetails plantDetails) {
        this.messageType = messageType;
        this.plantDetails = plantDetails;
    }

    public String getNewNickname() {
        return newNickname;
    }

    public MessageType getMessageType() {
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

    public String[] getStringArray() {
        return stringArray;
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

    public PlantDetails getPlantDetails() {
        return plantDetails;
    }
}
