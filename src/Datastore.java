import java.sql.*;
import java.util.*;

public class Datastore {

    private static Datastore instance;
    private Connection connection;

    private Datastore() {
        try {
            Class.forName("org.sqlite.JDBC"); // Fixed driver name
            connection = DriverManager.getConnection("jdbc:sqlite:events.db");
            Statement toggleForeign = connection.createStatement();
            toggleForeign.execute("PRAGMA foreign_keys = ON");
            System.out.println("BDD Connectée !");
            createTables();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Erreur init BDD : " + e.getMessage());
        }
    }

    public static Datastore getInstance() {
        if (instance == null) {
            instance = new Datastore();
        }
        return instance;
    }

    private void createTables() throws SQLException {
        Statement stmt = connection.createStatement();
        
       
        stmt.execute("""
            CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nom VARCHAR(50),
                prenom VARCHAR(50),
                email VARCHAR(100) UNIQUE,
                role VARCHAR(20),
                password VARCHAR(50)
            )
        """);

        
        stmt.execute("INSERT OR IGNORE INTO users (id, nom, prenom, email, role, password) VALUES (1, 'Admin', 'Jean', 'admin@event.com', 'organisateur', '1234')");
        stmt.execute("INSERT OR IGNORE INTO users (id, nom, prenom, email, role, password) VALUES (2, 'Doe', 'John', 'jeune@event.com', 'jeune', '1234')");

        
        stmt.execute("""
            CREATE TABLE IF NOT EXISTS events (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nom VARCHAR(100),
                description TEXT,
                lieu VARCHAR(100),
                date_heure VARCHAR(50),
                prix DOUBLE,
                capacite INT,
                placeRestantes INT,
                organisateurId INT,
                categorie VARCHAR(50),
                FOREIGN KEY(organisateurId) REFERENCES users(id) ON DELETE CASCADE
            )
        """);
        System.out.println("Tables prêtes !");
    }

 
    public User connexion(String email, String password) {
        try {
            PreparedStatement req = connection.prepareStatement(
                "SELECT * FROM users WHERE email = ? AND password = ?"
            );
            req.setString(1, email);
            req.setString(2, password);
            ResultSet rs = req.executeQuery();

            if (rs.next()) {
                // Returns Islem's User Object!
                return new User(
                    rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"),
                    rs.getString("email"), rs.getString("role"), rs.getString("password")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erreur de connexion : " + e.getMessage());
        }
        return null;
    }

    public boolean ajouterEvent(String titre, String description, String lieu, String date, double prix, int capacite, int organisateurId, String categorie) {
        try {
            PreparedStatement req = connection.prepareStatement(
                "INSERT INTO events (nom, description, lieu, date_heure, prix, capacite, placeRestantes, organisateurId, categorie) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
            );
            req.setString(1, titre);
            req.setString(2, description);
            req.setString(3, lieu);
            req.setString(4, date); // Stored as String to avoid parsing crashes with Eya's datetime format
            req.setDouble(5, prix);
            req.setInt(6, capacite);
            req.setInt(7, capacite); // Initially, places restantes = capacite
            req.setInt(8, organisateurId);
            req.setString(9, categorie);

            return req.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erreur d'ajout : " + e.getMessage());
            return false;
        }
    }

    // --- Fixed for JeuneFrame.java & OrganisateurFrame.java ---
    public List<Map<String, Object>> getEvents() {
        List<Map<String, Object>> evnts = new ArrayList<>();
        try {
            Statement req = connection.createStatement();
            ResultSet rs = req.executeQuery("SELECT * FROM events");

            while(rs.next()){
                Map<String, Object> ev = new HashMap<>();
                // Keys must exactly match what Eya typed in her JavaFX listeners
                ev.put("id", rs.getInt("id"));
                ev.put("nom", rs.getString("nom"));
                ev.put("description", rs.getString("description"));
                ev.put("lieu", rs.getString("lieu"));
                ev.put("date_heure", rs.getString("date_heure"));
                ev.put("prix", rs.getDouble("prix"));
                ev.put("placeRestantes", rs.getInt("placeRestantes")); // Eya used placeRestantes, Gabriel used TicketRestants
                ev.put("organisateurId", rs.getInt("organisateurId"));
                ev.put("categorie", rs.getString("categorie"));
                
                evnts.add(ev);
            }
        } catch(SQLException e){
            System.out.println("Erreur de lecture Table : " + e.getMessage());
        }
        return evnts;
    }

    public void closeConnection() {
        try {
            if (connection != null) connection.close();
        } catch (SQLException e) {
            System.out.println("Erreur fermeture : " + e.getMessage());
        }
    }
}