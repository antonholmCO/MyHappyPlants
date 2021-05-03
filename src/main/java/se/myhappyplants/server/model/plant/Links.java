package se.myhappyplants.server.model.plant;

import java.io.Serializable;

/**
 * Created by: Frida Jacobsson
 * Updated by:
 */

public class Links implements Serializable {
    private static final long serialVersionUID = 1L;
    public String plant;

    public Links(String url) {
        plant = url;
    }

    public String getPlant() {
        return plant;
    }
}
