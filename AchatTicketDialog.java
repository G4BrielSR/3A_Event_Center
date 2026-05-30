import javafx.stage.Stage;
import java.util.Map;

public class AchatTicketDialog {
    public void show(Stage stage, User user, Map<String, Object> event) {
        System.out.println("Achat du ticket pour : " + event.get("nom"));
      
    }
}