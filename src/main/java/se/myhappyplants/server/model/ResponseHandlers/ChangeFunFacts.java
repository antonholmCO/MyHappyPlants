package se.myhappyplants.server.model.ResponseHandlers;

import se.myhappyplants.server.model.ResponseHandler;
import se.myhappyplants.server.services.UserRepository;
import se.myhappyplants.shared.Message;
import se.myhappyplants.shared.User;

public class ChangeFunFacts implements ResponseHandler {
    private UserRepository userRepository;

    public ChangeFunFacts (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * todo: implement functionality in userRepository & database - always returns true at the moment
     * @param request
     * @return
     */
    @Override
    public Message getResponse(Message request) {
        Message response;
//        User user = request.getUser();
//        Boolean funFactsActivated = request.getNotifications();
//        if (userRepository.changeFunFacts(user, funFactsActivated)) {
            response = new Message(true);
//        } else {
//            response = new Message(false);
//        }
        return response;
    }
}