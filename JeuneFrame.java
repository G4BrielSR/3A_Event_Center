import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import java.util.List;
import java.util.Map;

public class JeuneFrame {

    private static final String BLEU      = "#2E86AB";
    private static final String FOND      = "#F0F4F8";
    private static final String FOND2     = "#FFFFFF";
    private static final String GRIS_TXT  = "#555555";
    private static final String ROUGE     = "#E74C3C";
    private static final String VERT      = "#27AE60";

    private List<Map<String, Object>> tousLesEvents;

    public void show(Stage stage, User user) {


        Label lblBonjour = new Label("Bonjour, " + user.getPrenom() + " " + user.getNom());
        lblBonjour.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        lblBonjour.setTextFill(Color.web(BLEU));

        Label lblSous = new Label("Découvrez les événements près de chez vous");
        lblSous.setFont(Font.font("Arial", 12));
        lblSous.setTextFill(Color.web(GRIS_TXT));

        Button btnDeconnexion = new Button("Déconnexion");
        btnDeconnexion.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: " + ROUGE + ";" +
            "-fx-border-color: " + ROUGE + ";" +
            "-fx-border-radius: 5;" +
            "-fx-font-size: 11px;" +
            "-fx-cursor: hand;"
        );

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(16, 20, 12, 20));
        header.setStyle("-fx-background-color: " + FOND2 + "; -fx-border-color: #DDDDDD; -fx-border-width: 0 0 1 0;");
        VBox titres = new VBox(2, lblBonjour, lblSous);
        header.getChildren().addAll(titres, spacer, btnDeconnexion);


        Label lblCat = new Label("Catégorie :");
        lblCat.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        lblCat.setTextFill(Color.web(GRIS_TXT));

        ComboBox<String> cbCategorie = new ComboBox<>();
        cbCategorie.getItems().addAll(
            "Toutes", "danse", "peinture", "cuisine", "sport", "educatif" ,
            "médical", "jardinage", "enfants", "personnes âgées", "adolescents"
        );
        cbCategorie.setValue("Toutes");
        cbCategorie.setStyle("-fx-font-size: 12px;");

        Label lblLieu = new Label("Lieu :");
        lblLieu.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        lblLieu.setTextFill(Color.web(GRIS_TXT));

        TextField tfLieu = new TextField();
        tfLieu.setPromptText("Ville ou zone...");
        tfLieu.setPrefWidth(180);
        tfLieu.setStyle("-fx-font-size: 12px; -fx-border-radius: 5; -fx-background-radius: 5;");

        Button btnFiltrer = new Button("Filtrer");
        btnFiltrer.setStyle(
            "-fx-background-color: " + BLEU + ";" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 12px;" +
            "-fx-background-radius: 5;" +
            "-fx-cursor: hand;" +
            "-fx-padding: 6 14;"
        );

        Button btnReset = new Button("Réinitialiser");
        btnReset.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: " + BLEU + ";" +
            "-fx-border-color: " + BLEU + ";" +
            "-fx-border-radius: 5;" +
            "-fx-font-size: 12px;" +
            "-fx-cursor: hand;" +
            "-fx-padding: 6 14;"
        );

        HBox filtres = new HBox(10);
        filtres.setAlignment(Pos.CENTER_LEFT);
        filtres.setPadding(new Insets(12, 20, 12, 20));
        filtres.setStyle("-fx-background-color: " + FOND2 + "; -fx-border-color: #DDDDDD; -fx-border-width: 0 0 1 0;");
        filtres.getChildren().addAll(lblCat, cbCategorie, lblLieu, tfLieu, btnFiltrer, btnReset);

     

        ListView<String> lvEvents = new ListView<>();
        lvEvents.setPrefHeight(320);
        lvEvents.setStyle("-fx-font-size: 13px;");

        Label lblAucun = new Label("Aucun événement trouvé.");
        lblAucun.setTextFill(Color.web(GRIS_TXT));
        lblAucun.setFont(Font.font("Arial", 13));
        lblAucun.setVisible(false);

        Datastore db = Datastore.getInstance();
        tousLesEvents = db.getEvents();
        remplirListe(lvEvents, tousLesEvents, lblAucun);

       

        Label lblDetailTitre = new Label("Sélectionnez un événement");
        lblDetailTitre.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        lblDetailTitre.setTextFill(Color.web(BLEU));
        lblDetailTitre.setWrapText(true);

        Label lblDetailInfo = new Label("");
        lblDetailInfo.setFont(Font.font("Arial", 12));
        lblDetailInfo.setTextFill(Color.web(GRIS_TXT));
        lblDetailInfo.setWrapText(true);
        lblDetailInfo.setMaxWidth(220);

        Label lblDetailPrix = new Label("");
        lblDetailPrix.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        lblDetailPrix.setTextFill(Color.web(VERT));

        Label lblDetailPlaces = new Label("");
        lblDetailPlaces.setFont(Font.font("Arial", 12));
        lblDetailPlaces.setTextFill(Color.web(GRIS_TXT));

        Button btnAcheter = new Button("Acheter un ticket");
        btnAcheter.setPrefWidth(200);
        btnAcheter.setDisable(true);
        btnAcheter.setStyle(
            "-fx-background-color: " + VERT + ";" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 6;" +
            "-fx-cursor: hand;" +
            "-fx-padding: 10 0;"
        );

        VBox detail = new VBox(10);
        detail.setPadding(new Insets(16));
        detail.setPrefWidth(240);
        detail.setStyle(
            "-fx-background-color: " + FOND2 + ";" +
            "-fx-border-color: #DDDDDD;" +
            "-fx-border-radius: 8;" +
            "-fx-background-radius: 8;"
        );
        detail.getChildren().addAll(lblDetailTitre, lblDetailInfo, lblDetailPrix, lblDetailPlaces, btnAcheter);

     

        HBox contenu = new HBox(16);
        contenu.setPadding(new Insets(16, 20, 16, 20));
        contenu.getChildren().addAll(lvEvents, detail);
        HBox.setHgrow(lvEvents, Priority.ALWAYS);

      

        VBox layout = new VBox(0);
        layout.setStyle("-fx-background-color: " + FOND + ";");
        layout.getChildren().addAll(header, filtres, lblAucun, contenu);

      
        lvEvents.setOnMouseClicked(e -> {
            int index = lvEvents.getSelectionModel().getSelectedIndex();
            if (index < 0) return;

            List<Map<String, Object>> listeActuelle = obtenirListeFiltrée(
                cbCategorie.getValue(), tfLieu.getText().trim()
            );

            if (index >= listeActuelle.size()) return;

            Map<String, Object> ev = listeActuelle.get(index);

            lblDetailTitre.setText((String) ev.get("nom"));

            String info = "📍 " + ev.get("lieu") + "\n" +
                          "📅 " + ev.get("date_heure") + "\n" +
                          "📝 " + ev.get("description");
            lblDetailInfo.setText(info);

            double prix = (double) ev.get("prix");
            lblDetailPrix.setText(prix == 0 ? "Gratuit" : prix + " DT");

            int places = (int) ev.get("placeRestantes");
            lblDetailPlaces.setText("Places restantes : " + places);
            lblDetailPlaces.setTextFill(places > 0 ? Color.web(VERT) : Color.web(ROUGE));

            btnAcheter.setDisable(places <= 0);
        });

  
        btnFiltrer.setOnAction(e -> {
            String categorie = cbCategorie.getValue();
            String lieu = tfLieu.getText().trim();
            List<Map<String, Object>> filtrés = obtenirListeFiltrée(categorie, lieu);
            remplirListe(lvEvents, filtrés, lblAucun);
            lblDetailTitre.setText("Sélectionnez un événement");
            lblDetailInfo.setText("");
            lblDetailPrix.setText("");
            lblDetailPlaces.setText("");
            btnAcheter.setDisable(true);
        });

        
        btnReset.setOnAction(e -> {
            cbCategorie.setValue("Toutes");
            tfLieu.clear();
            remplirListe(lvEvents, tousLesEvents, lblAucun);
            lblDetailTitre.setText("Sélectionnez un événement");
            lblDetailInfo.setText("");
            lblDetailPrix.setText("");
            lblDetailPlaces.setText("");
            btnAcheter.setDisable(true);
        });

 
        btnAcheter.setOnAction(e -> {
            int index = lvEvents.getSelectionModel().getSelectedIndex();
            if (index < 0) return;
            List<Map<String, Object>> listeActuelle = obtenirListeFiltrée(
                cbCategorie.getValue(), tfLieu.getText().trim()
            );
            Map<String, Object> ev = listeActuelle.get(index);
            new AchatTicketDialog().show(stage, user, ev);
        });

        btnDeconnexion.setOnAction(e -> {
            new LoginFrame().show(stage);
        });


        stage.setTitle("Event Center — Événements");
        stage.setScene(new Scene(layout, 780, 520));
        stage.setResizable(true);
        stage.show();
    }

   
    private void remplirListe(ListView<String> lv,
                               List<Map<String, Object>> liste,
                               Label lblAucun) {
        lv.getItems().clear();
        if (liste == null || liste.isEmpty()) {
            lblAucun.setVisible(true);
            return;
        }
        lblAucun.setVisible(false);
        for (Map<String, Object> ev : liste) {
            double prix = (double) ev.get("prix");
            String ligne = (String) ev.get("nom") +
                           "   |   " + ev.get("lieu") +
                           "   |   " + (prix == 0 ? "Gratuit" : prix + " DT");
            lv.getItems().add(ligne);
        }
    }

   
    private List<Map<String, Object>> obtenirListeFiltrée(String categorie, String lieu) {
        List<Map<String, Object>> résultat = new java.util.ArrayList<>();
        for (Map<String, Object> ev : tousLesEvents) {
            boolean matchCat = categorie.equals("Toutes") ||
                ev.getOrDefault("categorie", "").toString()
                  .equalsIgnoreCase(categorie);
            boolean matchLieu = lieu.isEmpty() ||
                ev.getOrDefault("lieu", "").toString()
                  .toLowerCase().contains(lieu.toLowerCase());
            if (matchCat && matchLieu) résultat.add(ev);
        }
        return résultat;
    }
}