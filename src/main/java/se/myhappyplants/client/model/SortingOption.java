package se.myhappyplants.client.model;

public enum SortingOption {

    nickname("  Nickname"),
    commonName("  Common name"),
    scientificName("  Scientific name"),
    genus("  Genus"),
    family("  Family"),
    waterNeed("  Water need");

    private final String name;

    SortingOption(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
