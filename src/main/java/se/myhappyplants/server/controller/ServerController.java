package se.myhappyplants.server.controller;

import se.myhappyplants.server.services.PlantRepository;
import se.myhappyplants.server.services.UserPlantRepository;
import se.myhappyplants.server.services.UserRepository;
import se.myhappyplants.shared.*;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by: Linn Borgström
 * Updated by: Linn Borgström, 2021-05-13
 */

public class ServerController {
    private UserRepository userRepository;
    private UserPlantRepository userPlantRepository;
    private PlantRepository plantRepository;

    public ServerController(UserRepository userRepository, UserPlantRepository userPlantRepository, PlantRepository plantRepository){
        this.userRepository = userRepository;
        this.userPlantRepository = userPlantRepository;
        this.plantRepository = plantRepository;
    }
    /**
     * Gets a response depending on the type of requests received
     *
     * @param request request object received from client
     * @return response to be sent back to client
     */
    public Message getResponse(Message request) throws IOException, InterruptedException {

        Message response;
        MessageType messageType = request.getMessageType();

        switch (messageType) {
            case login:
                String email = request.getUser().getEmail();
                String password = request.getUser().getPassword();

                boolean loginSuccess = userRepository.checkLogin(email, password);
                if (loginSuccess) {
                    User user = userRepository.getUserDetails(email);
                    response = new Message(messageType.login, user, true);
                }
                else {
                    response = new Message(messageType.login, false);
                }
                break;
            case register:
                User user = request.getUser();
                if (userRepository.saveUser(user)) {
                    User savedUser = userRepository.getUserDetails(user.getEmail());
                    response = new Message(messageType.register, savedUser, true);
                }
                else {
                    response = new Message(messageType.register, false);
                }
                break;
            case deleteAccount:
                User userToDelete = request.getUser();
                if (userRepository.deleteAccount(userToDelete.getEmail(), userToDelete.getPassword())) {
                    response = new Message(messageType.deleteAccount, true);
                }
                else {
                    response = new Message(MessageType.deleteAccount, false);
                }
                break;
            case searchForPlant:
                try {
                    ArrayList<Plant> plantList = plantRepository.getResult(request.getMessageText());
                    response = new Message(messageType.searchForPlant, plantList, true);
                }
                catch (Exception e) {
                    response = new Message(messageType.searchForPlant, false);
                    e.printStackTrace();
                }
                break;
            case getLibrary:
                ArrayList<Plant> userLibrary = userPlantRepository.getUserLibrary(request.getUser());
                User user1 = request.getUser();
                response = new Message(messageType.getLibrary, user1, userLibrary, true);
                break;
            case changeNotifications:
                boolean changeNotificationsSuccess = userRepository.changeNotifications(request.getUser(), request.getNotifications());
                response = new Message(messageType.changeNotifications, changeNotificationsSuccess);
                break;
            case savePlant:
                boolean saveSuccess = userPlantRepository.savePlant(request.getUser(), request.getDbPlant());
                response = new Message(messageType.success, saveSuccess);
                break;
            case deletePlantFromLibrary:
                boolean deleteSuccess = userPlantRepository.deletePlant(request.getUser(), request.getDbPlant().getNickname());
                response = new Message(messageType.success, deleteSuccess);
                break;
            case getPlantDetails:
                PlantDetails plantDetails = plantRepository.getPlantDetails(request.getPlant());
                response = new Message(messageType.plantDetails, plantDetails);
                break;
            case changeLastWatered:
                boolean changeDateSuccess = userPlantRepository.changeLastWatered(request.getUser(), request.getDbPlant().getNickname(), request.getDate());
                response = new Message(messageType.success, changeDateSuccess);
                break;
            case changeNickname:
                boolean changeNicknameSuccess = userPlantRepository.changeNickname(request.getUser(), request.getDbPlant().getNickname(), request.getNewNickname());
                response = new Message(messageType.success, changeNicknameSuccess);
                break;
            default:
                response = new Message(messageType.fail, false);
        }
        return response;
    }


}
