package se.myhappyplants.client.model;
/**
 * Enum of different sorting options
 * Created by: Christopher O'Driscoll
 * Updated by:
 */
public enum SortingOption {

    nickname("  Nickname"),
    commonName("  Common name"),
    scientificName("  Scientific name"),
    waterNeed("  Water need");

    private final String name;

    SortingOption(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
