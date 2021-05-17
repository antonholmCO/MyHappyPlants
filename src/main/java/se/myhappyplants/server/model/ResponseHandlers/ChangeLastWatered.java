package se.myhappyplants.server.model.ResponseHandlers;

import se.myhappyplants.server.model.ResponseHandler;
import se.myhappyplants.server.services.UserPlantRepository;
import se.myhappyplants.shared.Message;
import se.myhappyplants.shared.MessageType;
import se.myhappyplants.shared.Plant;
import java.time.LocalDate;

public class ChangeLastWatered implements ResponseHandler {
    private UserPlantRepository userPlantRepository;

    public ChangeLastWatered(UserPlantRepository userPlantRepository) {
        this.userPlantRepository = userPlantRepository;
    }

    @Override
    public Message getResponse(Message request) {
        Message response;
        Plant plant = request.getPlant();
        String nickname = plant.getNickname();
        LocalDate lastWatered = request.getDate();
        if(userPlantRepository.changeLastWatered(request.getUser(), nickname, lastWatered)) {
            response = new Message(true);
        }
        else {
            response = new Message(false);
        }
        return response;
    }
}
