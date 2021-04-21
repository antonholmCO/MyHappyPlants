package se.myhappyplants.shared;

import java.sql.Date;

/**
 * Class for defining a plant from the database
 * Created by: Frida Jacobsson
 * Updated by:
 */
public class DBPlant {

  private String nickname;
  private String apiURL;
  private Date lastWatered;
  private long waterFrequency;

  /**
   * Constructor used to create a new plant based on information from the database
   *
   * @param nickname
   * @param apiURL
   * @param lastWatered
   */
  public DBPlant(String nickname, String apiURL, Date lastWatered) {
    this.nickname = nickname;
    this.apiURL = apiURL;
    this.lastWatered = lastWatered;
  }

  public DBPlant(String nickname, String apiURL, Date lastWatered, long waterFrequency) {
    this.nickname = nickname;
    this.apiURL = apiURL;
    this.lastWatered = lastWatered;
    this.waterFrequency = waterFrequency;
  }

  @Override
  public String toString() {
    return nickname;
  }

  public String getNickname() {
    return nickname;
  }

  public String getURL() {
    return apiURL;
  }

  public double getProgress () {
    long difference = System.currentTimeMillis()-lastWatered.getTime();
    double progress = 1.0 - (difference/waterFrequency);
    if(progress<=0) {
      return 0.01;
    }
    return progress;
  }
  public String getLastWatered() {
    return lastWatered.toString();
  }
}
