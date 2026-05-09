import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
//import javax.naming.spi.DirStateFactory;
//import javax.xml.catalog.Catalog;


public class Datastore {

    private static Datastore instance;
    private Connection connection;

    private static final DateTimeFormatter FORMAT_DATE = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private Datastore () {
        try {
                Class.forName("org.sqlite.jdbc4.JDBC4Connection");
                connection = DriverManager.getConnection("jdbc:sqlite:events.db");
                Statement toggleForeign = connection.createStatement();
                toggleForeign.execute("PRAGMA foreign_keys = ON");
                System.out.println("BDD Connectée !");
                createTable();

        } 
        catch (ClassNotFoundException e) {
            System.out.println("Driver not found: " + e.getMessage());
        }
        catch (SQLException e) {
                //e.printStackTrace();
                System.out.println("Erreur connection : " + e.getMessage());
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
                CREATE TABLE IF NOT EXISTS events (
                id              INTEGER PRIMARY KEY AUTOINCREMENT,
                nom             VARCHAR(30),
                date_heure      DATETIME NOT NULL,
                description     TEXT,
                lieu            VARCHAR(50) NOT NULL,
                prix            DOUBLE NOT NULL DEFAULT 0,
                capacite        INT NULL,
                placeRestantes  INT NOT NULL
 
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
                singevnt.put("date_heure", LocalDate.parse(retour.getString("date_heure"), FORMAT_DATE));
                singevnt.put("description", retour.getString("description"));
                singevnt.put("lieu", retour.getString("lieu"));
                singevnt.put("prix", retour.getDouble("prix"));
                singevnt.put("capacite", retour.getInt("capacite"));
                

                evnts.add(singevnt);
            }
        }

        catch(SQLException e){
            System.out.println("Erreur de lecture Table" + e.getMessage());
        }

        return evnts;
    }

    public int addEvent(String nom, LocalDate dateh, String description, String lieu, double prix, int capacite, int placeRestantes) {
        try {
            PreparedStatement msg_ajout = connection.prepareStatement(
                "INSERT INTO events (nom, date_heure, description, lieu, prix, capacite, placeRestantes)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?)"
            );

            msg_ajout.setString(1, nom);
            msg_ajout.setString(2, dateh.format(FORMAT_DATE));
            msg_ajout.setString(3, description);
            msg_ajout.setString(4, lieu);
            msg_ajout.setDouble(5, prix);
            msg_ajout.setInt(6, capacite);
            msg_ajout.setInt(7, placeRestantes);

            msg_ajout.executeUpdate();

            ResultSet cle = msg_ajout.getGeneratedKeys();
            if (cle.next()) {
                int proch_id = cle.getInt(1);
                System.out.println("Évènement crée avec l'id : " + proch_id);
                return proch_id;
            }
        }
        catch (SQLException e) {
            System.out.println("Erreur d'ajout : " + e.getMessage());
        }

        return -1;
    }
    
    public boolean updateEvent(int id, String nom, LocalDate dateh, String description, String lieu, double prix, int capacite, int placeRestantes) {
        try {
            PreparedStatement maj = connection.prepareStatement(
                "UPDATE events SET nom=?, date_heure=?, description=?, lieu=?, prix=?, capacite=?, placeRestantes=?" + "WHERE id=?"
            );

            maj.setString(1, nom);
            maj.setString(2, dateh.format(FORMAT_DATE));
            maj.setString(3, description);
            maj.setString(4, lieu);
            maj.setDouble(5, prix);
            maj.setInt(6, capacite);
            maj.setInt(7, placeRestantes);
            maj.setInt(8, id);

            int col_modifiees = maj.executeUpdate();
            return col_modifiees > 0;

        }
        catch (SQLException e) {
            System.out.println("Erreur de mise à jour" + e.getMessage());
            return false;
        }
    }

    public boolean supprEvent(int id) {
        try {
            PreparedStatement suppr = connection.prepareStatement(
                "DELETE FROM events WHERE id=?"
            );
            suppr.setInt(1, id);

            int col_modifiees = suppr.executeUpdate();
            return col_modifiees > 0;
        }
        catch(SQLException e) {
            System.out.println("Erreur de suppression " + e.getMessage());
            return false;
        }
    }

    public void closeConnection() {
        try {
            if (connection != null) 
                connection.close();
            System.out.println("Connection BDD terminée avec succès");
        } 
        catch (SQLException e) {
            System.out.println("Erreur lors de la tentative de fermeture" + e.getMessage());
        }
    }
}
