
package se.myhappyplants.client.service;

import se.myhappyplants.shared.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Class that opens a connection to the server, to send request and receive response objects.
 * Created by: Christopher O'Driscoll
 * Updated by: Christopher, 2021-04-13
 */
public class ServerConnection {

    private static ServerConnection connection;
    private String ipAddress = "localhost";
    private int port = 2555;

    public static ServerConnection getClientConnection() {
        if(connection==null) {
            connection = new ServerConnection();
        }
        return connection;
    }

    private ServerConnection() {
    }

    /**
     * Opends a socket and handles Message-requests from client side and send them to server through TCP.
     *
     * @param request instance of class Message with a certain request
     * @return instance of Message class with a certain response
     */
    public Message makeRequest(Message request) {

        Message response = null;
        try {
            Socket socket = new Socket(ipAddress, port);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            oos.writeObject(request);
            oos.flush();
            response = (Message) ois.readObject();
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return response;
    }
}
