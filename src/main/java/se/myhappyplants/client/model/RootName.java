package se.myhappyplants.client.model;

/**
 * Enum class that is used to switch to different FXML-files
 * Created by: Linn Borgström, 2021-05-13
 * Updated by: Linn Borgström, 2021-05-13
 */
public enum RootName {
    mainPane("mainPane"),
    loginPane("loginPane"),
    searchTabPane("searchTabPane"),
    settingsTabPane("settingsTabPane"),
    myPlantsTabPane("myPlantsTabPane"),
    registerPane("registerPane");

    private final String name;

    /**
     * Constructor to set the text to the root name
     * @param name
     */
    RootName(String name) {
        this.name = name;
    }

    /**
     * ToString method to the root name
     * @return
     */
    public String toString() {
        return name;
    }
}
