package se.myhappyplants.server.model;

import se.myhappyplants.server.model.ResponseHandlers.*;
import se.myhappyplants.server.services.PlantRepository;
import se.myhappyplants.server.services.UserPlantRepository;
import se.myhappyplants.server.services.UserRepository;
import se.myhappyplants.shared.MessageType;

import java.util.HashMap;

/**
 * Class that stores all the different handlers for database requests
 * Created by: Christopher O'Driscoll
 * Updated by:
 */
public class ResponseContext {

    private HashMap<MessageType, ResponseHandler> responders = new HashMap<>();
    private UserRepository userRepository;
    private UserPlantRepository userPlantRepository;
    private PlantRepository plantRepository;


    public ResponseContext(UserRepository userRepository, UserPlantRepository userPlantRepository, PlantRepository plantRepository) {

        this.userRepository = userRepository;
        this.userPlantRepository = userPlantRepository;
        this.plantRepository = plantRepository;
        createResponders();
    }

    private void createResponders() {
        responders.put(MessageType.changeLastWatered, new ChangeLastWatered(userPlantRepository));
        responders.put(MessageType.changeNickname, new ChangeNickname(userPlantRepository));
        responders.put(MessageType.changeNotifications, new ChangeNotifications(userRepository));
        responders.put(MessageType.deleteAccount, new DeleteAccount(userRepository));
        responders.put(MessageType.deletePlant, new DeletePlant(userPlantRepository));
        responders.put(MessageType.getLibrary, new GetLibrary(userPlantRepository));
        responders.put(MessageType.getMorePlantInfo, new GetMorePlantInfo(plantRepository));
        responders.put(MessageType.login, new Login(userRepository));
        responders.put(MessageType.register, new Register(userRepository));
        responders.put(MessageType.savePlant, new SavePlant(userPlantRepository));
        responders.put(MessageType.search, new Search(plantRepository));
    }

    public ResponseHandler getResponseHandler(MessageType messageType) {
        return responders.get(messageType);
    }

}
