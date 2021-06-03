package se.myhappyplants.shared;

import se.myhappyplants.client.model.PictureRandomizer;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

/**
 * Class defining a plant
 * Created by: Frida Jacobsson
 * Updated by: Linn Borgström, Eric Simonson, Susanne Vikström
 */
public class Plant implements Serializable {

    private static final long serialVersionUID = 867522155232174497L;
    private String plantId;
    private String commonName;
    private String scientificName;
    private String familyName;
    private String imageURL;
    private String nickname;
    private Date lastWatered;
    private long waterFrequency;

    /**
     * Creates a plant object from information
     * in the Species database
     *
     * @param plantId        Unique plant id in Species database
     * @param commonName     Common name
     * @param scientificName Scientific name
     * @param familyName     Family name
     * @param imageURL       Image location
     */
    public Plant(String plantId, String commonName, String scientificName, String familyName, String imageURL) {
        this.plantId = plantId;
        this.commonName = commonName;
        this.scientificName = scientificName;
        this.familyName = familyName;
        this.imageURL = imageURL;
    }

    public Plant(String nickname, String plantId, Date lastWatered, long waterFrequency) {
        this.nickname = nickname;
        this.plantId = plantId;
        this.lastWatered = lastWatered;
        this.waterFrequency = waterFrequency;
    }

    public Plant(String nickname, String plantID, Date lastWatered) {
        this.nickname = nickname;
        this.plantId = plantID;
        this.lastWatered = lastWatered;
    }
    /**
     * Creates a plant object from a users library
     * in the MyHappyPlants database
     *
     * @param nickname
     * @param plantId        Unique plant id in Species database
     * @param lastWatered    Date the plant was last watered
     * @param waterFrequency How often the plant needs water in milliseconds
     * @param imageURL       Image location
     */
    public Plant(String nickname, String plantId, Date lastWatered, long waterFrequency, String imageURL) {

        this.nickname = nickname;
        this.plantId = plantId;
        this.lastWatered = lastWatered;
        this.waterFrequency = waterFrequency;
        this.imageURL = imageURL;
    }

    /**
     * Creates a plant object that can be used to update
     * a users library in the MyHappyPlants database
     *
     * @param nickname
     * @param plantId     Unique plant id in Species database
     * @param lastWatered Date the plant was last watered
     * @param imageURL    Image location
     */
    public Plant(String nickname, String plantId, Date lastWatered, String imageURL) {

        this.nickname = nickname;
        this.plantId = plantId;
        this.lastWatered = lastWatered;
        this.imageURL = imageURL;
    }

    public String toString() {
        String toString = String.format("Common name: %s \tFamily name: %s \tScientific name: %s ", commonName, familyName, scientificName);
        return toString;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCommonName() {
        return commonName;
    }

    public String getScientificName() {
        return scientificName;
    }

    public String getPlantId() {
        return plantId;
    }
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
    /**
     * Image location for selected plant
     *
     * @return URL location of image
     */
    public String getImageURL() {
        if(imageURL == null) {
            imageURL = PictureRandomizer.getRandomPictureURL();
        }
        String httpImageURL = imageURL.replace("https", "http");
        return httpImageURL;
    }

    public Date getLastWatered() {
        return lastWatered;
    }

    public void setLastWatered(LocalDate localDate) {
        Date date = java.sql.Date.valueOf(localDate);
        this.lastWatered = date;
    }

    /**
     * Compares the length of time since the plant was watered
     * with recommended frequency of watering. Returns a decimal value
     * that can be used in a progress bar or indicator
     *
     * @return Double between 0.02 (max time elapsed) and 1.0 (min time elapsed)
     */
    public double getProgress() {
        long difference = System.currentTimeMillis() - lastWatered.getTime();
        difference -= 43000000l;
        double progress = 1.0 - ((double) difference / (double) waterFrequency);
        if (progress <= 0.02) {
            progress = 0.02;
        }
        else if (progress >= 0.95) {
            progress = 1.0;
        }
        return progress;
    }

    /**
     * Converts time since last water from milliseconds
     * into days, then returns the value as
     * an explanation text
     *
     * @return Days since last water
     */
    public String getDaysUntilWater() {
        long millisSinceLastWatered = System.currentTimeMillis() - lastWatered.getTime();
        long millisUntilNextWatering = waterFrequency - millisSinceLastWatered;
        long millisInADay = 86400000;

        double daysExactlyUntilWatering = (double) millisUntilNextWatering / (double) millisInADay;

        int daysUntilWatering = (int) daysExactlyUntilWatering;
        double decimals = daysExactlyUntilWatering - (int) daysExactlyUntilWatering;

        if (decimals > 0.5) {
            daysUntilWatering = (int) daysExactlyUntilWatering + 1;
        }

        String strToReturn = String.format("Needs water in %d days", daysUntilWatering);
        if (getProgress() == 0.02 || daysUntilWatering == 0) {
            strToReturn = "You need to water this plant now!";
        }

        return strToReturn;
    }
}