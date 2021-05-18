package se.myhappyplants.server.model.ResponseHandlers;

import se.myhappyplants.server.model.ResponseHandler;
import se.myhappyplants.server.services.PlantRepository;
import se.myhappyplants.shared.Message;
import se.myhappyplants.shared.Plant;
import se.myhappyplants.shared.PlantDetails;

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
            PlantDetails plantDetails = plantRepository.getPlantDetails(plant);
            response = new Message(plantDetails, true);
        } catch (Exception e) {
            response = new Message(false);
            e.printStackTrace();
        }
        return response;
    }
}
