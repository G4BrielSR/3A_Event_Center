import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        
        Datastore.getInstance();

       
        LoginFrame loginScreen = new LoginFrame();
        loginScreen.show(primaryStage);
    }

    @Override
    public void stop() {
       
        Datastore.getInstance().closeConnection();
        System.out.println("Application fermée.");
    }

    public static void main(String[] args) {
        launch(args); 
    }
}