package se.myhappyplants.shared;

import se.myhappyplants.server.model.plant.Links;

import java.io.Serializable;
import java.net.URL;

/**
 * Class defining a plant from the API
 * Created by: Frida Jacobsson
 * Updated by: Linn Borgström, Eric Simonson, Susanne Vikström
 */
public class APIPlant implements Serializable {


    private String plantId;
    private String commonName;
    private String scientificName;
    private String familyName;
    private String imageURL;



    public APIPlant(String plantId, String commonName, String scientificName, String familyName, String imageURL) {
        this.plantId = plantId;
        this.commonName = commonName;
        this.scientificName = scientificName;
        this.familyName = familyName;
        this.imageURL = imageURL;
    }

    public String toString() {
        String toString = String.format("Common name: %s \tFamily name: %s \tScientific name: %s ", commonName, familyName, scientificName);
        return toString;
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
}
