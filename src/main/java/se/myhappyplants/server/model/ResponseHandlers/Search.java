package se.myhappyplants.server.model.ResponseHandlers;

import se.myhappyplants.server.model.ResponseHandler;
import se.myhappyplants.server.services.PlantRepository;
import se.myhappyplants.shared.Message;
import se.myhappyplants.shared.Plant;

import java.util.ArrayList;

public class Search implements ResponseHandler {
    private PlantRepository plantRepository;

    public Search(PlantRepository plantRepository) {
        this.plantRepository = plantRepository;
    }


    @Override
    public Message getResponse(Message request) {
        Message response;
        String searchText = request.getMessageText();
        try {
            ArrayList<Plant> plantList = plantRepository.getResult(searchText);
            response = new Message(plantList, true);
        } catch (Exception e) {
            response = new Message(false);
            e.printStackTrace();
        }
        return response;
    }
}