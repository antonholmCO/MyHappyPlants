package se.myhappyplants.shared;

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
}
