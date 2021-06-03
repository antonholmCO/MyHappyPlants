package se.myhappyplants.client.model;

/**
 * An enum class to set the messages in the PopupBox to show the user the execution status
 * Created by: Linn Borgström, 2021-05-17
 * Updated by: Linn Borgström, 2021-05-17
 */
public enum MessageText {
    sucessfullyAddPlant("You added \n a plant"),
    sucessfullyChangedPlant("You changed \n a plants information"),
    sucessfullyChangedDate("You changed \n the water date"),
    removePlant("You removed a plant"),
    holdOnGettingInfo("Hold on \n while we are \n getting your information");


    private final String name;

    /**
     * Constreuctor to set the text
     * @param name
     */
    MessageText(String name) {
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
