package se.myhappyplants.server.model.ResponseHandlers;

import se.myhappyplants.server.model.IResponseHandler;
import se.myhappyplants.server.services.UserPlantRepository;
import se.myhappyplants.shared.Message;
import se.myhappyplants.shared.Plant;
import se.myhappyplants.shared.User;
/**
 * Class that handles the chang of a nickname of a plant
 */
public class ChangeNickname implements IResponseHandler {
    private UserPlantRepository userPlantRepository;

    public ChangeNickname(UserPlantRepository userPlantRepository) {
        this.userPlantRepository = userPlantRepository;
    }

    @Override
    public Message getResponse(Message request) {
        Message response;
        User user = request.getUser();
        Plant plant = request.getPlant();
        String nickname = plant.getNickname();
        String newNickname = request.getNewNickname();
        if (userPlantRepository.changeNickname(user, nickname, newNickname)) {
            response = new Message(true);
        } else {
            response = new Message(false);
        }
        return response;
    }
}
