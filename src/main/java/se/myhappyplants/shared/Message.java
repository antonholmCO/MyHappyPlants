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
    private ArrayList<Plant> plantArray;
    private Plant plant;
    private String newNickname;
    private PlantDetails plantDetails;


    /**
     * create a message that can be used to send a boolean value
     *
     * @param success
     */
    public Message(boolean success) {
        this.success = success;
    }

    /**
     * Creates a message which can be used to send a user
     *
     * @param messageType
     * @param user
     */
    public Message(MessageType messageType, User user) {

        this.messageType = messageType;
        this.user = user;
    }

    /**
     * Creates a message that can be used to send a user and a plant
     *
     * @param messageType
     * @param user
     * @param plant
     */
    public Message(MessageType messageType, User user, Plant plant) {
        this(messageType, user);
        this.plant = plant;
    }

    /**
     * create a message that can be used to send a plant
     *
     * @param messageType
     * @param plant
     */
    public Message(MessageType messageType, Plant plant) {
        this.messageType = messageType;
        this.plant = plant;
    }

    /**
     * Creates a message that can be used to send a notification setting and a user
     *
     * @param messageType
     * @param notifications
     * @param user
     */
    public Message(MessageType messageType, boolean notifications, User user) {
        this.messageType = messageType;
        this.notifications = notifications;
        this.user = user;
    }

    /**
     * Creates a message that can be used to send a user, a plant and a date
     *
     * @param messageType
     * @param user
     * @param plant
     * @param date
     */
    public Message(MessageType messageType, User user, Plant plant, LocalDate date) {
        this.messageType = messageType;
        this.user = user;
        this.plant = plant;
        this.date = date;
    }

    /**
     * Creates a message that can be used to send a user, a plant and a new nickname
     *
     * @param messageType
     * @param user
     * @param plant
     * @param newNickname
     */
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


    /**
     * Creates a message that can be used to send a plant array
     *
     * @param plantArray
     * @param success
     */
    public Message(ArrayList<Plant> plantArray, boolean success) {
        this.plantArray = plantArray;
        this.success = success;
    }

    /**
     * Creates a message which can be used to send a string
     *
     * @param messageType
     * @param messageText
     */
    public Message(MessageType messageType, String messageText) {
        this.messageType = messageType;
        this.messageText = messageText;
    }

    /**
     * Creates a message which can be used to send a string array
     *
     * @param stringArray
     * @param success
     */
    public Message(String[] stringArray, boolean success) {
        this.stringArray = stringArray;
        this.success = success;
    }

    /**
     * Creates a message which can be used to send a user and a boolean value
     *
     * @param user
     * @param success
     */
    public Message(User user, boolean success) {
        this.user = user;
        this.success = success;
    }

    public Message(PlantDetails plantDetails, boolean success) {
        this.plantDetails = plantDetails;
        this.success = success;
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

    public ArrayList<Plant> getPlantArray() {
        return plantArray;
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
