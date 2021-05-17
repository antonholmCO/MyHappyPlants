package se.myhappyplants.client.model;

public enum MessageText {
    sucessfullyAddPlant(" You added \n a plant"),
    sucessfullyChangedPlant(" You changed \n a plants information"),
    sucessfullyChangedDate(" You changed \n the water date"),
    remove("You removed a plant"),
    holdOnInfo("Hold on \n while we are \n getting your information");


    private final String name;

    MessageText(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
