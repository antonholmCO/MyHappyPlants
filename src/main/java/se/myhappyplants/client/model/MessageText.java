package se.myhappyplants.client.model;

public enum MessageText {
    sucessfullyAdd(" You successfully added a plant"),
    sucessfullyChangedPlant(" You sucessfully changed "),
    sucessfullyChangedDate(" You sucessfully changed the date"),
    remove("You removed a plant"),
    holdOnInfo("Hold on while we are getting your information");


    private final String name;

    MessageText(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
