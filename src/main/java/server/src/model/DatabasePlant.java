package server.src.model;

import java.util.Date;

public class DatabasePlant {

    private String nickname;
    private String apiURL;
    private Date lastWatered;

    public DatabasePlant(String nickname, String apiURL,  Date lastWatered) {
        this.nickname = nickname;
        this.apiURL = apiURL;
        this.lastWatered = lastWatered;
    }

    public String getImageURL() {
        return null;
    }

    public String getName() {
        return null;
    }

    public double getProgress() {
        return 0;
    }
}
