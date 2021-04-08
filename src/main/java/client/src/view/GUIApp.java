package client.src.view;

import client.src.view.ConfirmationBox;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;


public class GUIApp extends Application {

    private Stage window;
    private static Scene scene;


    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        window.setOnCloseRequest( action -> {
            action.consume(); //tell java to forget about event
            closeProgram(); //now handles close event manually
        });

        //Parent root = FXMLLoader.load(getClass().getResource("../view/LogInParent.fxml"));
        scene = new Scene(loadFXML("LogInParent"), 800, 600);

        window.setScene(scene);
        window.setTitle("My Happy Plants");
        window.show();


    }

//    public static void main(String[] args) {
//        launch(args);
//    }

    private void closeProgram() {
        if(ConfirmationBox.display("Confirm", "Are you sure?")) {
            System.out.println("Closed safely");
            window.close();
        }
    }


    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GUIApp.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

}
