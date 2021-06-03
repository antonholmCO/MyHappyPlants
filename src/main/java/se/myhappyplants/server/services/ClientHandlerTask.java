package se.myhappyplants.server.services;

import se.myhappyplants.server.controller.ResponseController;
import se.myhappyplants.shared.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Runnable task class containing a task that needs to executed from client
 * Created by: Frida Jacobsson 2021-05-21
 */
public class ClientHandlerTask implements Runnable {

    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private ResponseController responseController;

    public ClientHandlerTask(Socket socket, ResponseController responseController) throws IOException {
        this.socket = socket;
        this.responseController = responseController;
        this.ois = new ObjectInputStream(socket.getInputStream());
        this.oos = new ObjectOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        try {
            Message request = (Message) ois.readObject();
            Message response = responseController.getResponse(request);
            oos.writeObject(response);
            oos.flush();
        }
        catch (IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
