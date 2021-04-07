import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class GUIApp extends Application {

    private Stage window;
    private Scene scene;


    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        window.setOnCloseRequest( action -> {
            action.consume(); //tell java to forget about event
            closeProgram(); //now handles close event manually
        });

        Parent root = FXMLLoader.load(getClass().getResource("LogInParent.fxml"));
        scene = new Scene(root, 800, 600);

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
}
