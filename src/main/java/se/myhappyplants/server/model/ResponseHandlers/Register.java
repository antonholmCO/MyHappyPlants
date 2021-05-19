package se.myhappyplants.server.model.ResponseHandlers;

import se.myhappyplants.server.model.ResponseHandler;
import se.myhappyplants.server.services.UserRepository;
import se.myhappyplants.shared.Message;
import se.myhappyplants.shared.User;

public class Register implements ResponseHandler {
    private UserRepository userRepository;

    public Register(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Message getResponse(Message request) {
        Message response;
        User user = request.getUser();
        if (userRepository.saveUser(user)) {
            User savedUser = userRepository.getUserDetails(user.getEmail());
            response = new Message(savedUser, true);
        } else {
            response = new Message(false);
        }
        return response;
    }
}
