package se.myhappyplants.server.controller;

import se.myhappyplants.server.model.repository.UserRepository;
import se.myhappyplants.server.model.service.PlantService;
import se.myhappyplants.shared.Message;
import se.myhappyplants.shared.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server that listens for incoming connections
 * Handles each connection with a new thread
 *
 * Created by Christopher O'Driscoll
 * Updated 2021-04-13 by Christopher
 */
public class Server implements Runnable {


    private ServerSocket serverSocket;
    private final Thread serverThread = new Thread(this);
    private boolean serverRunning;
    private UserRepository userRepository;
    private PlantService plantService;

    /**
     * Constructor opens a port and starts a thread to listen for incoming connections/requests
     *
     * @param port           port to be used
     * @param userRepository to handle db requests
     * @param plantService   to handle api requests
     */
    public Server(int port, UserRepository userRepository, PlantService plantService) {
        this(port);
        this.userRepository = userRepository;
        this.plantService = plantService;
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
     *
     * @param request request object received from client
     * @return response to be sent back to client
     */
    private Message getResponse(Message request) {
        Message response;
        String messageType = request.getMessageType();

        switch (messageType) {
            case "login":
//                response = new Message("login", new User(request.getUser().getEmail()), true);
                String email = request.getUser().getEmail();
                String password = request.getUser().getPassword();

                boolean loginSuccess = userRepository.checkLogin(email, password);
                if (loginSuccess) {
                    User user = userRepository.getUserDetails(email);
                    response = new Message("login", user, true);
                } else {
                    response = new Message("login", false);
                }
                break;
            case "register":
//                response = new Message("register", request.getUser(), true);
                User user = request.getUser();
                if (userRepository.saveUser(user)) {
                    response = new Message("registration", user, true);
                } else {
                    response = new Message("registration",false);
                }
                break;
            default:
                response = new Message("fail", false);
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
                    Message request = (Message) ois.readObject();
                    //todo remove test sout
                    System.out.println("Request received, sending response");
                    Message response = getResponse(request);
                    oos.writeObject(response);
                    //todo remove test sout
                    System.out.println("Response sent");
                    oos.flush();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
//                finally {
//                    if (socket != null) {
//                        try {
//                            socket.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
            }
        }
    }
