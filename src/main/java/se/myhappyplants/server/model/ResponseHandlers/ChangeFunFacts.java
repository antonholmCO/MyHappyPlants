package se.myhappyplants.server.model.ResponseHandlers;

import se.myhappyplants.server.model.IResponseHandler;
import se.myhappyplants.server.services.UserRepository;
import se.myhappyplants.shared.Message;
import se.myhappyplants.shared.User;
/**
 * Class that handles to change the fun facts
 */
public class ChangeFunFacts implements IResponseHandler {
    private UserRepository userRepository;

    public ChangeFunFacts (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * @param request
     * @return
     */
    @Override
    public Message getResponse(Message request) {
        Message response;
        User user = request.getUser();
        Boolean funFactsActivated = request.getNotifications();
        if (userRepository.changeFunFacts(user, funFactsActivated)) {
            response = new Message(true);
        } else {
            response = new Message(false);
        }
        return response;
    }
}