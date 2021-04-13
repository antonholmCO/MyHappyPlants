package se.myhappyplants.client.model;

/**
 * ToDo
 * Could contain search requests
 * A Builder class could be used to help with formatting
 * @author Christopher O'Driscoll
 */
public class APIRequest extends Request {
    private String searchWord;

    public APIRequest(String searchWord) {
        this.searchWord=searchWord;
    }
}
