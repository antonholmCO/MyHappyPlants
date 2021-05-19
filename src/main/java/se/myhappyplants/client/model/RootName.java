package se.myhappyplants.client.model;

/**
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

    RootName(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
