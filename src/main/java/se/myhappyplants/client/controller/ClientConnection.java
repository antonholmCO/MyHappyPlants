/**
 * @author Christopher O'Driscoll
 */

package se.myhappyplants.client.controller;

import se.myhappyplants.client.model.Request;
import se.myhappyplants.server.model.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Class that opens a connection to the server, to send request and receive response objects.
 */
public class ClientConnection{

    private final static ClientConnection INSTANCE = new ClientConnection("localhost", 2555);

    private String ipAddress;
    private int port;
    private Socket socket;

    /**
     * Creates class and initialises variables,
     * can only be called within the class
     * @param ipAddress the ipAddress of the Server
     * @param port the port the Server is listening on
     */
    private ClientConnection(String ipAddress, int port) {
        this.ipAddress = ipAddress;
        this.port = port;
    }

    /**
     * Ensures same instance is used by other classes
     * May be unnecessary?
     * @return
     */
    public static ClientConnection getInstance() {
        return INSTANCE;
    }

    public Response makeRequest(Request request) {

        Response response = null;
        try {
            socket = new Socket(ipAddress, port);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            //send request
            oos.writeObject(request);
            oos.flush();
            //wait for response
            response = (Response) ois.readObject();
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            if(socket!=null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response;
    }
}