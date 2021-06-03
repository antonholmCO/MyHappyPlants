package se.myhappyplants.server.services;

import se.myhappyplants.server.controller.ResponseController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Server that listens for incoming connections
 * Handles each connection with a new thread
 * <p>
 * Created by: Christopher O'Driscoll
 * Updated by: Frida Jacobsson 2021-05-21
 */
public class Server implements Runnable {

    private ServerSocket serverSocket;
    private final Thread serverThread = new Thread(this);
    private boolean serverRunning;
    private ResponseController responseController;
    private ExecutorService executor;

    /**
     * Constructor opens a port and starts a thread to listen for incoming connections/requests
     *
     * @param port               port to be used
     * @param responseController to handle db requests
     */
    public Server(int port, ResponseController responseController) {
        this(port);
        this.responseController = responseController;
        executor = Executors.newFixedThreadPool(5);
    }

    /**
     * Simplified constructor
     *
     * @param port port to be used
     */
    public Server(int port) {
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
                ClientHandlerTask clientHandlerTask = new ClientHandlerTask(socket, responseController);
                executor.submit(clientHandlerTask);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();
        System.out.println("Server stopped");
    }
}
