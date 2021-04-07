/**
 * @author Christopher O'Driscoll
 */

package client.src.controller;

import client.src.model.Request;
import server.src.model.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Class that opens a connection to the server, to send request and receive response objects.
 * Can be run in a separate Thread
 */
public class ClientConnection implements Runnable{

    private Thread connectionThread = new Thread(this);

    private String ipAddress;
    private int port;
    private Socket socket;

    private Request request;
    private Response response;

    /**
     * Creates class and initialises variables
     * @param ipAddress the ipAddress of the Server
     * @param port the port the Server is listening on
     */
    public ClientConnection(String ipAddress, int port) {
        this.ipAddress = ipAddress;
        this.port = port;
    }

    /**
     * Starts a thread to send a request to the Server
     * @param request Request to be sent
     * @return response received from Server
     */
    public Response makeRequest(Request request) {
        this.request = request;
        //clear any old responses
        response = null;
        //starts its own thread in case of lag from connection to server
        connectionThread.start();
        return response;
    }

    /**
     * Opens a connection to to the Server
     * Sends a request and waits for a response
     */
    @Override
    public void run() {
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
    }
}
