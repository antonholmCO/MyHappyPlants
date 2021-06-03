package se.myhappyplants.server.controller;

import se.myhappyplants.server.model.IResponseHandler;
import se.myhappyplants.server.model.ResponseContext;
import se.myhappyplants.server.services.PlantRepository;
import se.myhappyplants.server.services.UserPlantRepository;
import se.myhappyplants.server.services.UserRepository;
import se.myhappyplants.shared.Message;
import se.myhappyplants.shared.MessageType;

import java.io.IOException;

/**
 * Class that handles the logic from the Server
 * Created by: Linn Borgström
 * Updated by: Linn Borgström, 2021-05-13
 */

public class ResponseController {
    private ResponseContext responseContext;


    public ResponseController(UserRepository userRepository, UserPlantRepository userPlantRepository, PlantRepository plantRepository){
        responseContext = new ResponseContext(userRepository, userPlantRepository, plantRepository);
    }
    /**
     * Gets a response depending on the type of requests received
     * @param request request object received from client
     * @return response to be sent back to client
     */
    public Message getResponse(Message request) throws IOException, InterruptedException {

        Message response;
        MessageType messageType = request.getMessageType();

        IResponseHandler responseHandler = responseContext.getResponseHandler(messageType);
        response = responseHandler.getResponse(request);
        return response;
    }
}
