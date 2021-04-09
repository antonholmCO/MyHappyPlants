package se.myhappyplants.server.controller;

import se.myhappyplants.client.model.*;
import se.myhappyplants.server.model.*;
import se.myhappyplants.server.model.repository.UserRepository;
import se.myhappyplants.server.model.service.PlantService;

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



    private ServerSocket serverSocket;
    private final Thread serverThread = new Thread(this);
    private boolean serverRunning;
    private UserRepository userRepository;
    private PlantService plantService;

    /**
     * Constructor opens a port and starts a thread to listen for incoming connections/requests
     * @param port port to be used
     * @param userRepository to handle db requests
     * @param plantService to handle api requests
     */
    public Server(int port, UserRepository userRepository, PlantService plantService) {
        this(port);
        this.userRepository = userRepository;
        this.plantService = plantService;
    }

    /**
     * Simplified constructor
     * @param port port to be used
     */
    public Server(int port) {
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
                //todo remove sout method
                System.out.println("Connection made, starting server thread to handle client");
                ClientHandler clientHandler = new ClientHandler(socket);
                clientHandler.start();
            } catch (IOException e) {
                e.printStackTrace();
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
            //ToDo code to handle requests made to database
            if (request instanceof LoginRequest) {
                //for testing purposes, always returns true and a new User created from request parameters
                if (request instanceof RegisterRequest) {
                    //for testing purposes, always returns true and a new User created from request parameters
                    String username = ((RegisterRequest) request).getUserName();
                    response = new LoginResponse(true, new User(username));
                }
                else {
                    String email = ((LoginRequest) request).getEmail();
                    //creates a username based on the email given, in future shall get username from database
                    response = new LoginResponse(true, new User(email.substring(0, email.indexOf("@"))));
                }
            }
            else if (request instanceof LibraryRequest){
                response = new LibraryResponse(true);
            }
        } else if (request instanceof APIRequest) {
            //ToDo code to handle requests made to api
            response = new APIResponse(true);
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
                //todo remove test sout
                System.out.println("Request received, sending response");
                Response response = getResponse(request);
                oos.writeObject(response);
                //todo remove test sout
                System.out.println("Response sent");
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
