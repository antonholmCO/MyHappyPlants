package se.myhappyplants.server.controller;

import se.myhappyplants.server.repository.PlantRepository;
import se.myhappyplants.server.repository.UserPlantRepository;
import se.myhappyplants.server.repository.UserRepository;
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
public class Server implements Runnable {

    private ServerSocket serverSocket;
    private final Thread serverThread = new Thread(this);
    private boolean serverRunning;

    private UserRepository userRepository;
    private UserPlantRepository userPlantRepository;
    private PlantRepository plantRepository;

    /**
     * Constructor opens a port and starts a thread to listen for incoming connections/requests
     *
     * @param port           port to be used
     * @param userRepository to handle db requests
     */
    public Server(int port, UserRepository userRepository, UserPlantRepository userPlantRepository, PlantRepository plantRepository) {
        this(port);
        this.userRepository = userRepository;
        this.userPlantRepository = userPlantRepository;
        this.plantRepository = plantRepository;
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
     * Gets a response depending on the type of requests received
     *
     * @param request request object received from client
     * @return response to be sent back to client
     */
    private Message getResponse(Message request) throws IOException, InterruptedException {
        Message response;
        String messageType = request.getMessageType();

        switch (messageType) {
            case "login":
                String email = request.getUser().getEmail();
                String password = request.getUser().getPassword();

                boolean loginSuccess = userRepository.checkLogin(email, password);
                if (loginSuccess) {
                    User user = userRepository.getUserDetails(email);
                    response = new Message("login", user, true);
                }
                else {
                    response = new Message("login", false);
                }
                break;
            case "register":
                User user = request.getUser();
                if (userRepository.saveUser(user)) {
                    User savedUser = userRepository.getUserDetails(user.getEmail());
                    response = new Message("registration", savedUser, true);
                }
                else {
                    response = new Message("registration", false);
                }
                break;
            case "delete account":
                User userToDelete = request.getUser();
                if (userRepository.deleteAccount(userToDelete.getEmail(), userToDelete.getPassword())) {
                    response = new Message("delete account", true);
                }
                else {
                    response = new Message("delete account", false);
                }
                break;
            case "search":
                try {
                    ArrayList<Plant> plantList = plantRepository.getResult(request.getMessageText());
                    response = new Message("search", plantList, true);
                }
                catch (Exception e) {
                    response = new Message("search", false);
                    e.printStackTrace();
                }
                break;
            case "getLibrary":
                ArrayList<Plant> userLibrary = userPlantRepository.getUserLibrary(request.getUser());
                User user1 = request.getUser();
                response = new Message("library", user1, userLibrary, true);
                break;
            case "change notifications":
                boolean changeNotificationsSuccess = userRepository.changeNotifications(request.getUser(), request.getNotifications());
                response = new Message("change notifications", changeNotificationsSuccess);
                break;
            case "savePlant":
                boolean saveSuccess = userPlantRepository.savePlant(request.getUser(), request.getDbPlant());
                response = new Message("success", saveSuccess);
                break;
            case "deletePlantFromLibrary":
                boolean deleteSuccess = userPlantRepository.deletePlant(request.getUser(), request.getDbPlant().getNickname());
                response = new Message("success", deleteSuccess);
                break;
            case "getMorePlantInfoOnSearch":
                String[] message = plantRepository.getMoreInformation(request.getPlant());
                response = new Message("waterLightInfo", message);
                break;
            case "changeLastWatered":
                boolean changeDateSuccess = userPlantRepository.changeLastWatered(request.getUser(), request.getDbPlant().getNickname(), request.getDate());
                response = new Message("success", changeDateSuccess);
                break;
            case "changeNickname":
                boolean changeNicknameSuccess = userPlantRepository.changeNickname(request.getUser(), request.getDbPlant().getNickname(), request.getNewNickname());
                response = new Message("success", changeNicknameSuccess);
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
                Message response = getResponse(request);
                oos.writeObject(response);
                oos.flush();
            }
            catch (IOException | ClassNotFoundException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
