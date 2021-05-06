package se.myhappyplants.shared;


import java.io.Serializable;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;

/**
 * Class defining a plant from the API
 * Created by: Frida Jacobsson
 * Updated by: Linn Borgström, Eric Simonson, Susanne Vikström
 */
public class DBPlant implements Serializable {


    private String plantId;
    private String commonName;
    private String scientificName;
    private String familyName;
    private String imageURL;
    private String nickname;
    private Date lastWatered;
    private long waterFrequency;


    public DBPlant(String plantId, String commonName, String scientificName, String familyName, String imageURL) {
        this.plantId = plantId;
        this.commonName = commonName;
        this.scientificName = scientificName;
        this.familyName = familyName;
        this.imageURL = imageURL;
    }

    public DBPlant(String nickname, String plantId, Date lastWatered, long waterFrequency) {
        this.nickname = nickname;
        this.plantId = plantId;
        this.lastWatered = lastWatered;
        this.waterFrequency = waterFrequency;

    }


    public DBPlant(String nickname, String plantID, Date lastWatered) {
        this.nickname = nickname;
        this.plantId = plantID;
        this.lastWatered = lastWatered;
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

    public String getImageURL() {
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

    public double getProgress() {
        long difference = System.currentTimeMillis() - lastWatered.getTime();
        difference -= 43000000l;
        double progress = 1.0 - ((double) difference / (double) waterFrequency);
        if (progress <= 0) {
            return 0.05;
        }
        return progress;
    }

}
