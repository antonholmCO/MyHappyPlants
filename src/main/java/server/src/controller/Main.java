package server.src.controller;
import server.src.model.repository.IUserRepository;
import server.src.model.repository.UserRepository;
import server.src.model.service.PlantService;

/**
 * @version 2.0
 */
public class Main {
    public static void main(String[] args) throws Exception {
        IUserRepository userRepository = new UserRepository();
        Controller controller = new Controller((UserRepository) userRepository);
        //Testa logga in och spara användare på DB samt skapa en ny User
        //controller.createNewUser();
        //controller.logIn();

        //för att testa API:et används koden nedan
        PlantService plantService = new PlantService();
        plantService.getResult();
    }
}