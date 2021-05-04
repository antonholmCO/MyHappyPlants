package se.myhappyplants.shared;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

/**
 * Class for defining a plant from the database
 * Created by: Frida Jacobsson
 * Updated by:
 */
public class DBPlant implements Serializable {

    private String nickname;
    private String plantId;
    private Date lastWatered;
    private long waterFrequency;

    /**
     * Constructor used to create a new plant based on information from the database
     *
     * @param nickname
     * @param plantId
     * @param lastWatered
     */
    public DBPlant(String nickname, String plantId, Date lastWatered) {
        this.nickname = nickname;
        this.plantId = plantId;
        this.lastWatered = lastWatered;
    }

    public DBPlant(String nickname, String plantId, Date lastWatered, long waterFrequency) {
        this.nickname = nickname;
        this.plantId = plantId;
        this.lastWatered = lastWatered;
        this.waterFrequency = waterFrequency;
    }

    public String getPlantId() {
        return plantId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String newNickname) {
        this.nickname = newNickname;
    }

    public String getURL() {
        return plantId;
    }

    public String getLastWatered() {
        return lastWatered.toString();
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

    @Override
    public String toString() {
        return nickname;
    }
}
