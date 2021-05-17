package se.myhappyplants.server.model.ResponseHandlers;

import se.myhappyplants.server.model.ResponseHandler;
import se.myhappyplants.server.services.UserPlantRepository;
import se.myhappyplants.shared.Message;
import se.myhappyplants.shared.Plant;
import se.myhappyplants.shared.User;

import java.util.ArrayList;

public class GetLibrary implements ResponseHandler {
    private UserPlantRepository userPlantRepository;

    public GetLibrary(UserPlantRepository userPlantRepository) {
        this.userPlantRepository = userPlantRepository;
    }

    @Override
    public Message getResponse(Message request) {
        Message response;
        User user = request.getUser();
        try {
            ArrayList<Plant> userLibrary = userPlantRepository.getUserLibrary(user);
            response = new Message(userLibrary, true);
        } catch (Exception e) {
            response = new Message(false);
            e.printStackTrace();
        }
        System.out.println("getting library for " + user.getUsername());
        return response;
    }
}
