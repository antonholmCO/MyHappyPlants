package se.myhappyplants.client.model;
/**
 * Enum of different sorting options
 * Created by: Christopher O'Driscoll
 * Updated by: Christopher O'Driscoll
 */
public enum SortingOption {

    nickname("  Nickname"),
    commonName("  Common name"),
    scientificName("  Scientific name"),
    waterNeed("  Water need");

    private final String name;

    /**
     * Constreuctor to set the text
     * @param name
     */
    SortingOption(String name) {
        this.name = name;
    }
    /**
     * ToString method with the text
     * @return
     */
    public String toString() {
        return name;
    }
}
