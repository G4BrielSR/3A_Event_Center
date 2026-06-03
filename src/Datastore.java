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
                createCategoriesTable();
                createTable();
                createUserTable();

                createTicketTable();
        } 
        catch (ClassNotFoundException e) {
            System.out.println("Driver not found: " + e.getMessage());
        }
        catch (SQLException e) {
                System.out.println("Erreur connexion : " + e.getMessage());
        }
    }

    public static Datastore getInstance() {
        if (instance == null) {
            instance = new Datastore();
        }
        return instance;
    }


    private void createCategoriesTable() {

        String sqlCategories = """
                CREATE TABLE IF NOT EXISTS categories (
                id              INTEGER PRIMARY KEY AUTOINCREMENT,
                typeCat         VARCHAR(30) NOT NULL
                )
                """;
        try {
            Statement msg_connection = connection.createStatement();
            msg_connection.execute(sqlCategories);
        } catch (SQLException e) {
            System.err.println("Erreur init table de catégorie : " + e.getMessage());
        }
      

    }
    private void createTable() throws SQLException {
        String sql = """
                CREATE TABLE IF NOT EXISTS events (
                id              INTEGER PRIMARY KEY AUTOINCREMENT,
                nom             VARCHAR(100),
                capacite        INT NOT NULL,
                date_heure      DATETIME NOT NULL,
                description     TEXT,
                lieu            VARCHAR(50) NOT NULL,
                placeRestantes  INT NOT NULL,
                prix            REAL NOT NULL DEFAULT 0,
                organisateurId  INT,
                categorieId     INT,


                FOREIGN KEY(organisateurId) REFERENCES users(id)
                    ON DELETE SET NULL
                    ON UPDATE CASCADE   
                FOREIGN KEY(categorieId) REFERENCES categories(id)
                    ON DELETE SET NULL
                    ON UPDATE CASCADE
                )
                """;
        
        createCategoriesTable();
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
                singevnt.put("capacite", retour.getInt("capacite"));
                //singevnt.put("duree", retour.getTime("duree"));
                singevnt.put("date_heure", LocalDate.parse(retour.getString("date_heure"), FORMAT_DATE));
                singevnt.put("description", retour.getString("description"));
                singevnt.put("lieu", retour.getString("lieu"));
                singevnt.put("placeRestantes", retour.getInt("placeRestantes"));
                singevnt.put("prix", retour.getDouble("prix"));
                singevnt.put("organisateurId", retour.getInt("organisateurId"));
                singevnt.put("categorieId", retour.getInt("categorieId"));

                evnts.add(singevnt);
            }
        }

        catch(SQLException e){
            System.out.println("Erreur de lecture Table" + e.getMessage());
        }

        return evnts;
    }

    public int addEvent(String nom, int capacite, LocalDate dateh, String description, String lieu, int placeRestantes, double prix) { //int organId, int categId) {
        try {
            PreparedStatement msg_ajout = connection.prepareStatement(
                "INSERT INTO events (nom, description, lieu, date_heure, prix, capacite, placeRestantes, organisateurId, categorieId)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
            );

            msg_ajout.setString(1, nom);
            msg_ajout.setInt(2, capacite);
            msg_ajout.setString(3, dateh.format(FORMAT_DATE));
            msg_ajout.setString(4, description);
            msg_ajout.setString(5, lieu);
            msg_ajout.setInt(6, placeRestantes);
            msg_ajout.setDouble(7, prix);
            //msg_ajout.setInt(8, organId);
            //msg_ajout.setInt(9, categId);


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
    
    public boolean updateEvent(int id, String nom, int capacite, LocalDate dateh, String description, String lieu, int placeRestantes, double prix, int organId, int categId) {
        try {
            PreparedStatement maj = connection.prepareStatement(
                "UPDATE events SET nom=?, capacite=?, date_heure=?, description=?, lieu=?, placeRestantes=?, prix=?, organisateurId=?, categorieId=? " + "WHERE id=?"
            );

            maj.setString(1, nom);
            maj.setInt(2, capacite);
            maj.setString(3, dateh.format(FORMAT_DATE));
            maj.setString(4, description);
            maj.setString(5, lieu);
            maj.setInt(6, placeRestantes);
            maj.setDouble(7, prix);
            maj.setInt(8, organId);
            maj.setInt(9, categId);
            maj.setInt(10, id);

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


//Gestion de table utilisateur:

    private void createUserTable() throws SQLException {
        String sql = """
                CREATE TABLE IF NOT EXISTS users (
                id              INTEGER PRIMARY KEY AUTOINCREMENT,
                nom             VARCHAR(50) NOT NULL,
                prenom          VARCHAR(30) NOT NULL,
                email           VARCHAR(100) NOT NULL,
                password        TEXT NOT NULL,
                role            VARCHAR(20) NOT NULL
                )
                """;
        
        
        Statement msg_user = connection.createStatement();
        msg_user.execute(sql);
        System.out.println("Table utilisateur prête !");
    }


    public List<Map<String, Object>> getUsers() {
        List<Map<String, Object>> users = new ArrayList<>();
        try {
            PreparedStatement requete = connection.prepareStatement(
                "SELECT * FROM users"
            );

            ResultSet retour = requete.executeQuery();

            while(retour.next()){
                Map<String, Object> singuser = new HashMap <>();
                singuser.put("id", retour.getInt("id"));
                singuser.put("nom", retour.getString("nom"));
                singuser.put("prenom", retour.getString("prenom"));
                singuser.put("email", retour.getString("email"));
                singuser.put("password", retour.getString("password"));
                singuser.put("role", retour.getString("role"));     
                

                users.add(singuser);
            }
        }

        catch(SQLException e){
            System.out.println("Erreur de lecture Table" + e.getMessage());
        }

        return users;
    }   

    public int addUser(String nom, String prenom, String email, String password, String role) {
        try {
            PreparedStatement msg_ajout = connection.prepareStatement(
                "INSERT INTO users (nom, prenom, email, password, role)" +
                "VALUES (?, ?, ?, ?, ?)"
            );

            msg_ajout.setString(1, nom);
            msg_ajout.setString(2, prenom);
            msg_ajout.setString(3, email);
            msg_ajout.setString(4, password);
            msg_ajout.setString(5, role);

            msg_ajout.executeUpdate();

            ResultSet cle = msg_ajout.getGeneratedKeys();
            if (cle.next()) {
                int proch_id = cle.getInt(1);
                System.out.println("Utilisateur crée avec l'id : " + proch_id);
                return proch_id;
            }
        }
        catch (SQLException e) {
            System.out.println("Erreur d'ajout : " + e.getMessage());
        }

        return -1;
    }

    public boolean updateUser(int id, String nom, String prenom, String email, String password, String role) {
        try {
            PreparedStatement maj = connection.prepareStatement(
                "UPDATE users SET nom=?, prenom=?, email=?, password=?, role=?" + "WHERE id=?"
            );

            maj.setString(1, nom);
            maj.setString(2, prenom);
            maj.setString(3, email);
            maj.setString(4, password);
            maj.setString(5, role);
            maj.setInt(6, id);

            int col_modifiees = maj.executeUpdate();
            return col_modifiees > 0;

        }
        catch (SQLException e) {
            System.out.println("Erreur de mise à jour" + e.getMessage());
            return false;
        }
    }

    public boolean supprUser(int id) {
        try {
            PreparedStatement suppr = connection.prepareStatement(
                "DELETE FROM users WHERE id=?"
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

//Gestion table ticket

    private void createTicketTable() throws SQLException {
        String sql = """
                CREATE TABLE IF NOT EXISTS tickets (
                id              INTEGER PRIMARY KEY AUTOINCREMENT,
                EventId         INT,
                userId          INT, 
                date_achat      DATETIME NOT NULL,
                statut          VARCHAR(20) NOT NULL,
                prix            REAL NOT NULL DEFAULT 0, 
                
                FOREIGN KEY(UserId) REFERENCES users(id)
                    ON DELETE SET NULL
                    ON UPDATE CASCADE   
                FOREIGN KEY(EventId) REFERENCES events(id)
                    ON DELETE SET NULL
                    ON UPDATE CASCADE
                )
                """;
        
        
        Statement msg_user = connection.createStatement();
        msg_user.execute(sql);
        System.out.println("Table tickets prête !");
    }


    public List<Map<String, Object>> getTickets() {
        List<Map<String, Object>> tickets = new ArrayList<>();
        try {
            PreparedStatement requete = connection.prepareStatement(
                "SELECT * FROM tickets"
            );

            ResultSet retour = requete.executeQuery();

            while(retour.next()){
                Map<String, Object> singtick = new HashMap <>();
                singtick.put("id", retour.getInt("id"));
                singtick.put("EventId", retour.getString("EventId"));
                singtick.put("userId", retour.getString("userId"));
                singtick.put("date_achat", LocalDate.parse(retour.getString("date_achat"), FORMAT_DATE));
                singtick.put("statut", retour.getString("statut"));
                singtick.put("prix", retour.getDouble("prix"));     
                

                tickets.add(singtick);
            }
        }

        catch(SQLException e){
            System.out.println("Erreur de lecture Table" + e.getMessage());
        }

        return tickets;
    }   

    public int addTicket(int EventId, int UserId, LocalDate date_achat, String statut, double prix) {
        try {
            PreparedStatement msg_ajout = connection.prepareStatement(
                "INSERT INTO tickets (EventId, UserId, date_achat, statut, prix)" +
                "VALUES (?, ?, ?, ?, ?)"
            );

            msg_ajout.setInt(1, EventId);
            msg_ajout.setInt(2, UserId);
            msg_ajout.setString(3, date_achat.format(FORMAT_DATE));
            msg_ajout.setString(4, statut);
            msg_ajout.setDouble(5, prix);
            msg_ajout.executeUpdate();

            ResultSet cle = msg_ajout.getGeneratedKeys();
            if (cle.next()) {
                int proch_id = cle.getInt(1);
                System.out.println("Ticket crée avec l'id : " + proch_id);
                return proch_id;
            }
        }
        catch (SQLException e) {
            System.out.println("Erreur d'ajout : " + e.getMessage());
        }

        return -1;
    }

    public boolean updateTickets(int id, int EventId, int UserId, LocalDate date_achat, String statut, Double prix) {
        try {
            PreparedStatement maj = connection.prepareStatement(
                "UPDATE tickets SET EventId=?, UserId=?, date_achat=?, statut=?, prix=?" + "WHERE id=?"
            );

            maj.setInt(1, EventId);
            maj.setInt(2, UserId);
            maj.setString(3, date_achat.format(FORMAT_DATE));
            maj.setString(4, statut);
            maj.setDouble(5, prix);
            maj.setInt(6, id);

            int col_modifiees = maj.executeUpdate();
            return col_modifiees > 0;

        }
        catch (SQLException e) {
            System.out.println("Erreur de mise à jour" + e.getMessage());
            return false;
        }
    }

    public boolean supprTickets(int id) {
        try {
            PreparedStatement suppr = connection.prepareStatement(
                "DELETE FROM tickets WHERE id=?"
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
}