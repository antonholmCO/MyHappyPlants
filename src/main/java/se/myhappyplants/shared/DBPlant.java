package se.myhappyplants.shared;

/**
 * Created by: Frida Jacobsson
 * Updated by:
 */

import java.util.Date;

public class DBPlant {

    private String nickname;
    private String apiURL;
    private String lastWatered;

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
