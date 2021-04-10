package se.myhappyplants.server.model;

/**
 * ToDo
 * Could contain login responses, notifications, an array of the plants in a users library etc
 * @author Christopher O'Driscoll
 */
public class LibraryResponse extends Response{
    public LibraryResponse(boolean success) {
        super(success);
    }
}
