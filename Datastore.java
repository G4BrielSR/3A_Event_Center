import java.sql.*;
import java.util.*;


public class Datastore {

    private static Datastore instance;
    private Connection connection;

    private Datastore () {
        try {
                connection = DriverManager.getConnection("jdbc:sqlite:events.db");
                System.out.println("BDD Connectée !");
                createTable();

        } catch (SQLException e) {
                System.out.println("Erreur connection" +e.getMessage());
        }
    }

    public static Datastore getInstance() {
        if (instance == null) {
            instance = new Datastore();
        }
        return instance;
    }

    private void createTable() throws SQLException {
        String sql = """
                CREATE TABLE IF NOT EXIST events (
                id              INT PRIMARY KEY AUTO_INCREMENT
                nom             VARCHAR(30)
                date_heure      DATETIME NOT NULL
                description     TEXT
                lieu            VARCHAR(50) NOT NULL
                prix            DOUBLE NOT NULL DEFAULT 0
                capacite        INT NULL
                placeRestantes  INT NOT NULL
                organisateurId 
                categorieId 
                )
                """;

        Statement msg_connection = connection.createStatement();
        msg_connection.execute(sql);
        System.out.println("Table prête !");
    }

    public List<Map<String, Object>> getEvents() {
        List<Map<String, Object>> evnts = new ArrayList<>();
        
        try {
            PreparedStatement requete = connection.prepareStatement(
                "SELECT * FROM events"
            );

            ResultSet retour = requete.executeQuery();

            while(retour.next()){
                Map<String, Object> singevnt = new HashMap <>();
                singevnt.put("id", retour.getInt("id"));
                singevnt.put("nom", retour.getString("nom"));
                //singevnt.put("duree", retour.getTime("duree"));
                singevnt.put("date_heure", retour.getDate("date_heure"));
                singevnt.put("description", retour.getString("description"));
                singevnt.put("lieu", retour.getString("lieu"));
                singevnt.put("prix", retour.getDouble("prix"));
                singevnt.put("capacite", retour.getInt("capacite"));
                singevnt.put("placeRestantes", retour.getInt("placeRestantes"));


                evnts.add(singevnt);
            }
        }

        catch(SQLException e){
            System.out.println("Erreur de lecture Table" + e.getMessage());
        }

        return evnts;
    }

    Public int addEvent(String title,) {

    }
}
