package se.myhappyplants.server.model.ResponseHandlers;

import se.myhappyplants.server.model.IResponseHandler;
import se.myhappyplants.server.services.UserPlantRepository;
import se.myhappyplants.shared.Message;
import se.myhappyplants.shared.Plant;
import se.myhappyplants.shared.User;
/**
 * Class that handles the change when the user wants to delete a plant
 */
public class DeletePlant implements IResponseHandler {

    private UserPlantRepository userPlantRepository;

    public DeletePlant(UserPlantRepository userPlantRepository) {
        this.userPlantRepository = userPlantRepository;
    }


    @Override
    public Message getResponse(Message request) {
        Message response;
        User user = request.getUser();
        Plant plant = request.getPlant();
        String nickname = plant.getNickname();
        if (userPlantRepository.deletePlant(user, nickname)) {
            response = new Message(true);
        } else {
            response = new Message(false);
        }
        return response;
    }
}
