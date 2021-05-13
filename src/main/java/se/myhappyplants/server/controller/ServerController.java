package se.myhappyplants.server.controller;

import se.myhappyplants.server.services.PlantRepository;
import se.myhappyplants.server.services.UserPlantRepository;
import se.myhappyplants.server.services.UserRepository;
import se.myhappyplants.shared.Message;
import se.myhappyplants.shared.Plant;
import se.myhappyplants.shared.User;

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


}
