package se.myhappyplants.server.model.ResponseHandlers;

import se.myhappyplants.server.model.ResponseHandler;
import se.myhappyplants.server.services.UserPlantRepository;
import se.myhappyplants.shared.Message;
import se.myhappyplants.shared.Plant;
import se.myhappyplants.shared.User;

public class DeletePlant implements ResponseHandler {

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