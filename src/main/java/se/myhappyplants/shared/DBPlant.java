package se.myhappyplants.shared;

/**
 * Created by: Frida Jacobsson
 * Updated by:
 */

import java.util.Date;

public class DBPlant {

    private String nickname;
    private String apiURL;
    private Date lastWatered;

    public DBPlant(String nickname, String apiURL, Date lastWatered) {
        this.nickname = nickname;
        this.apiURL = apiURL;
        this.lastWatered = lastWatered;
    }

    @Override
    public String toString() {
        return nickname;
    }
}
