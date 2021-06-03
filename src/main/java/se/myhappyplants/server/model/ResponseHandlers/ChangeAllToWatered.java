package se.myhappyplants.server.model.ResponseHandlers;

import se.myhappyplants.server.model.ResponseHandler;
import se.myhappyplants.server.services.UserPlantRepository;
import se.myhappyplants.shared.Message;
import se.myhappyplants.shared.Plant;
import se.myhappyplants.shared.User;

import java.time.LocalDate;

/**
 * Class that handles to change all plants to watered
 */
public class ChangeAllToWatered implements ResponseHandler{
    private UserPlantRepository userPlantRepository;

    public ChangeAllToWatered(UserPlantRepository userPlantRepository) {
        this.userPlantRepository = userPlantRepository;
    }

    @Override
    public Message getResponse(Message request) {
        Message response;
        User user = request.getUser();
        if (userPlantRepository.changeAllToWatered(user)) {
            response = new Message(true);
        } else {
            response = new Message(false);
        }
        return response;
    }
}
