
import java.time.LocalDate;

public class AppDummy {
    public static void main(String[] args) {
        System.out.println("OK");
        Datastore BDD = Datastore.getInstance();
        LocalDate FromDate = LocalDate.of(2026, 3, 3);
        BDD.addEvent("Fête du fromage", FromDate, "Venez passer d'excellents moments en célébrant le fromage", "Rochefort", 57.3, 300, 300);
    }
}
