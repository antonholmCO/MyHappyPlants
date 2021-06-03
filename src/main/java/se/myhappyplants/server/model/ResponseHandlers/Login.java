package se.myhappyplants.server.model.ResponseHandlers;

import se.myhappyplants.server.model.IResponseHandler;
import se.myhappyplants.server.services.UserRepository;
import se.myhappyplants.shared.Message;
import se.myhappyplants.shared.User;
/**
 * Class that handles the procedure when a user logs in
 */
public class Login implements IResponseHandler {
    private UserRepository userRepository;

    public Login(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Message getResponse(Message request) {
        Message response;
        String email = request.getUser().getEmail();
        String password = request.getUser().getPassword();
        if (userRepository.checkLogin(email, password)) {
            User user = userRepository.getUserDetails(email);
            response = new Message(user, true);
        } else {
            response = new Message(false);
        }
        return response;
    }
}
