package se.myhappyplants.server.model.ResponseHandlers;

import se.myhappyplants.server.model.ResponseHandler;
import se.myhappyplants.server.services.UserRepository;
import se.myhappyplants.shared.Message;
import se.myhappyplants.shared.MessageType;
import se.myhappyplants.shared.User;

public class ChangeNotifications implements ResponseHandler {
    private UserRepository userRepository;

    public ChangeNotifications(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Message getResponse(Message request) {
        Message response;
        User user = request.getUser();
        Boolean notifications = request.getNotifications();
        if (userRepository.changeNotifications(user, notifications)) {
            response = new Message(true);
        } else {
            response = new Message(false);
        }
        return response;
    }
}
