
package se.myhappyplants.client.service;

import se.myhappyplants.shared.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Class that opens a connection to the server, to send request and receive response objects.
 * Created by: Christopher O'Driscoll
 * Updated by: Christopher, 2021-04-13
 */
public class ClientConnection {

    private String ipAddress = "localhost";
    private int port = 2555;
    private Socket socket;
    private  ObjectOutputStream oos;
    private ObjectInputStream ois;

    public ClientConnection() {
        try {
            socket = new Socket(ipAddress, port);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
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
            oos.writeObject(request);
            oos.flush();
            response = (Message) ois.readObject();
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            if (socket != null) {
                try {
                    socket.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response;
    }
}
