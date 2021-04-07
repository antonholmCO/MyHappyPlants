package server.src.controller;

import client.src.model.APIRequest;
import client.src.model.DBRequest;
import client.src.model.Request;
import server.src.model.ApiServer;
import server.src.model.DBComm;
import server.src.model.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server that listens for incoming connections
 * Handles each connection with a new thread
 */
public class Server implements Runnable {

    private final ApiServer apiServer;
    private final DBComm dbComm;

    private ServerSocket serverSocket;
    private final Thread serverThread = new Thread(this);
    private boolean serverRunning;

    /**
     * Constructor opens a port and starts a thread to listen for incoming connections/requests
     * @param port port to be used
     * @param apiServer to handle api requests
     * @param dbComm to handle db requests
     */
    public Server(int port, ApiServer apiServer, DBComm dbComm) {
        this.apiServer = apiServer;
        this.dbComm = dbComm;
        try {
            serverSocket = new ServerSocket(port);
            serverRunning = true;
            serverThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * starts a new thread for each incoming connection from a client
     */
    @Override
    public void run() {
        while (serverRunning) {
            try {
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket);
                clientHandler.start();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                stopServer();
            }
        }
    }

    /**
     * stops the server, closing the connection
     */
    public void stopServer() {
        try {
            serverRunning = false;
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Gets a response depending on the type of requests received
     * @param request request object received from client
     * @return response to be sent back to client
     */
    private Response getResponse(Request request) {
        Response response = null;
        if (request instanceof DBRequest) {
            response = dbComm.request(request);
        } else if (request instanceof APIRequest) {
            response = apiServer.request(request);
        }
        return response;
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
                Request request = (Request) ois.readObject();
                Response response = getResponse(request);
                oos.writeObject(response);
                oos.flush();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
