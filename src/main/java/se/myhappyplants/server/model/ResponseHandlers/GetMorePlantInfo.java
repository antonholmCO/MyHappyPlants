package se.myhappyplants.server.model.ResponseHandlers;

import se.myhappyplants.server.model.ResponseHandler;
import se.myhappyplants.server.services.PlantRepository;
import se.myhappyplants.shared.Message;
import se.myhappyplants.shared.MessageType;
import se.myhappyplants.shared.Plant;

public class GetMorePlantInfo implements ResponseHandler {
    private PlantRepository plantRepository;

    public GetMorePlantInfo(PlantRepository plantRepository) {
        this.plantRepository = plantRepository;
    }

    @Override
    public Message getResponse(Message request) {
        Message response;
        Plant plant = request.getPlant();
        try {
            String[] moreInfoOnPlant = plantRepository.getMoreInfoOnPlant(plant);
            response = new Message(MessageType.getMorePlantInfo, moreInfoOnPlant, true);
        }
        catch (Exception e) {
            response = new Message(MessageType.getMorePlantInfo, false);
            e.printStackTrace();
        }
        return response;
    }
}
