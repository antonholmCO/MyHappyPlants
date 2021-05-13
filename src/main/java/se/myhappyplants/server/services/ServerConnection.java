package se.myhappyplants.server.services;

import se.myhappyplants.server.controller.ServerController;
import se.myhappyplants.server.services.PlantRepository;
import se.myhappyplants.server.services.UserPlantRepository;
import se.myhappyplants.server.services.UserRepository;
import se.myhappyplants.shared.Plant;
import se.myhappyplants.shared.Message;
import se.myhappyplants.shared.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Server that listens for incoming connections
 * Handles each connection with a new thread
 * <p>
 * Created by: Christopher O'Driscoll
 * Updated by: Linn Borgström, Eric Simonson, Susanne Vikström 2021-04-28
 */
public class ServerConnection implements Runnable {

    private ServerSocket serverSocket;
    private final Thread serverThread = new Thread(this);
    private boolean serverRunning;
    private ServerController serverController;

    /**
     * Constructor opens a port and starts a thread to listen for incoming connections/requests
     *
     * @param port           port to be used
     * @param serverController to handle db requests
     */
    public ServerConnection(int port, ServerController serverController) {
        this(port);
        this.serverController=serverController;
    }

    /**
     * Simplified constructor
     *
     * @param port port to be used
     */
    public ServerConnection(int port) {
        try {
            serverSocket = new ServerSocket(port);
            serverRunning = true;
            serverThread.start();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * starts a new thread for each incoming connection from a client
     */
    @Override
    public void run() {
        System.out.println("Server running");
        while (serverRunning) {
            try {
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket);
                clientHandler.start();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Server stopped");
    }

    /**
     * stops the server, closing the connection
     */
    public void stopServer() {
        try {
            serverRunning = false;
            serverSocket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }



    /**
     * Thread that accepts requests and delivers responses to a connected client
     */
    private class ClientHandler extends Thread {

        private final Socket socket;
        private final ObjectInputStream ois;
        private final ObjectOutputStream oos;

        /**
         * Constructor opens new input/output streams on creation
         *
         * @param socket the socket to be used for communication
         * @throws IOException
         */
        private ClientHandler(Socket socket) throws IOException {
            this.socket = socket;
            this.ois = new ObjectInputStream(socket.getInputStream());
            this.oos = new ObjectOutputStream(socket.getOutputStream());
        }

        /**
         * Waits for an incoming object, gets the appropriate response, and sends it to the client
         * Closes thread after execution
         */
        @Override
        public void run() {
            try {
                Message request = (Message) ois.readObject();
                Message response = serverController.getResponse(request);
                oos.writeObject(response);
                oos.flush();
            }
            catch (IOException | ClassNotFoundException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
