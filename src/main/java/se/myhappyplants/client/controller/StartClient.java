package se.myhappyplants.client.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import se.myhappyplants.client.view.ConfirmationBox;

import java.io.IOException;

/**
 * JavaFX Application Main class
 * If javaFX not installed, execute maven goals:
 * 1.  mvn javafx:compile
 * 2.  mvn javafx:run
 * Created by: Christopher O'Driscoll, Eric Simonsson
 */
public class StartClient extends Application {

    private static Scene scene;
    private Stage window;

    /**
     * Starts the application by opening window. Method handles close on request.
     *
     * @param stage instance of Stage to start
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        window = stage;
        window.setOnCloseRequest(action -> {
            action.consume();
            closeProgram();
        });
        scene = new Scene(loadFXML("loginPane"), 800, 600);
        window.setScene(scene);
        window.show();
    }

    /**
     * Method handles close on request.
     */
    private void closeProgram() {
        if (ConfirmationBox.display("Exit", "Are you sure?")) {
            window.close();
        }
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartClient.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * Alternative run method (not needed)
     *
     * @param args
     **/
    public static void main(String[] args) {
        launch();
    }
}