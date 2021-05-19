package se.myhappyplants.shared;

import java.io.Serializable;

public class PlantDetails implements Serializable {

    private String genus;
    private String scientificName;
    private int light;
    private int waterFrequency;
    private String family;

    public PlantDetails(String genus, String scientificName, int light, int waterFrequency, String family) {
        this.scientificName = scientificName;
        this.genus = genus;
        this.light = light;
        this.waterFrequency = waterFrequency;
        this.family = family;
    }

    public PlantDetails getPlantDetails() {
        return getPlantDetails();
    }

    public String getScientificName() {
        return scientificName;
    }

    public String getGenus() {
        return genus;
    }

    public int getLight() {
        return light;
    }

    public int getWaterFrequency() {
        return waterFrequency;
    }

    public String getFamily() {
        return family;
    }
}
