package se.myhappyplants.shared;

/**
 * Class for defining a plant from the database
 * Created by: Frida Jacobsson
 * Updated by:
 */
public class DBPlant {

  private String nickname;
  private String apiURL;
  private String lastWatered;

  /**
   * Constructor used to create a new plant based on information from the database
   *
   * @param nickname
   * @param apiURL
   * @param lastWatered
   */
  public DBPlant(String nickname, String apiURL, String lastWatered) {
    this.nickname = nickname;
    this.apiURL = apiURL;
    this.lastWatered = lastWatered;
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

  public String getLastWatered() {
    return lastWatered;
  }
}
