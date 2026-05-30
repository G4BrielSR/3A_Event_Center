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

public class OrganisateurFrame {

    private static final String BLEU     = "#2E86AB";
    private static final String FOND     = "#F0F4F8";
    private static final String FOND2    = "#FFFFFF";
    private static final String GRIS_TXT = "#555555";
    private static final String ROUGE    = "#E74C3C";
    private static final String VERT     = "#27AE60";
    private static final String ORANGE   = "#E67E22";

    public void show(Stage stage, User user) {

   

        Label lblBonjour = new Label("Espace Organisateur — " + user.getPrenom() + " " + user.getNom());
        lblBonjour.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        lblBonjour.setTextFill(Color.web(BLEU));

        Label lblSous = new Label("Gérez et publiez vos événements");
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

        VBox titres = new VBox(2, lblBonjour, lblSous);
        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(16, 20, 12, 20));
        header.setStyle("-fx-background-color: " + FOND2 + "; -fx-border-color: #DDDDDD; -fx-border-width: 0 0 1 0;");
        header.getChildren().addAll(titres, spacer, btnDeconnexion);

       

        Label lblFormTitre = new Label("Publier un nouvel événement");
        lblFormTitre.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        lblFormTitre.setTextFill(Color.web(BLEU));

  
        Label lblTitre = new Label("Titre *");
        lblTitre.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        TextField tfTitre = new TextField();
        tfTitre.setPromptText("Nom de l'événement");
        tfTitre.setStyle(styleChamp());

  
        Label lblDesc = new Label("Description *");
        lblDesc.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        TextArea taDesc = new TextArea();
        taDesc.setPromptText("Décrivez votre événement...");
        taDesc.setPrefRowCount(3);
        taDesc.setWrapText(true);
        taDesc.setStyle(styleChamp());


        Label lblLieu = new Label("Lieu *");
        lblLieu.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        TextField tfLieu = new TextField();
        tfLieu.setPromptText("Ville ou adresse");
        tfLieu.setStyle(styleChamp());

     
        Label lblDate = new Label("Date et heure *");
        lblDate.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        TextField tfDate = new TextField();
        tfDate.setPromptText("Ex: 2026-06-15 14:00");
        tfDate.setStyle(styleChamp());

      
        Label lblPrix = new Label("Prix (DT) *");
        lblPrix.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        TextField tfPrix = new TextField();
        tfPrix.setPromptText("0 = Gratuit");
        tfPrix.setPrefWidth(120);
        tfPrix.setStyle(styleChamp());


        Label lblCapa = new Label("Capacité *");
        lblCapa.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        TextField tfCapa = new TextField();
        tfCapa.setPromptText("Nb de places");
        tfCapa.setPrefWidth(120);
        tfCapa.setStyle(styleChamp());

        HBox hPrixCapa = new HBox(12);
        VBox vPrix = new VBox(4, lblPrix, tfPrix);
        VBox vCapa = new VBox(4, lblCapa, tfCapa);
        hPrixCapa.getChildren().addAll(vPrix, vCapa);

  
        Label lblCat = new Label("Catégorie *");
        lblCat.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        ComboBox<String> cbCat = new ComboBox<>();
        cbCat.getItems().addAll(
            "danse", "peinture", "cuisine",
            "médical", "jardinage", "enfants",
            "personnes âgées", "adolescents"
        );
        cbCat.setPromptText("Choisissez une catégorie");
        cbCat.setPrefWidth(280);
        cbCat.setStyle("-fx-font-size: 12px;");


        Label lblMsg = new Label("");
        lblMsg.setFont(Font.font("Arial", 12));
        lblMsg.setWrapText(true);
        lblMsg.setMaxWidth(280);

       
        Button btnPublier = new Button("Publier l'événement");
        btnPublier.setPrefWidth(280);
        btnPublier.setPrefHeight(38);
        btnPublier.setStyle(
            "-fx-background-color: " + ORANGE + ";" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 6;" +
            "-fx-cursor: hand;"
        );


        Button btnEffacer = new Button("Effacer");
        btnEffacer.setPrefWidth(280);
        btnEffacer.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: " + GRIS_TXT + ";" +
            "-fx-border-color: #CCCCCC;" +
            "-fx-border-radius: 5;" +
            "-fx-font-size: 12px;" +
            "-fx-cursor: hand;"
        );

        VBox formPanel = new VBox(8);
        formPanel.setPrefWidth(310);
        formPanel.setPadding(new Insets(16));
        formPanel.setStyle(
            "-fx-background-color: " + FOND2 + ";" +
            "-fx-border-color: #DDDDDD;" +
            "-fx-border-radius: 8;" +
            "-fx-background-radius: 8;"
        );
        formPanel.getChildren().addAll(
            lblFormTitre,
            new Separator(),
            lblTitre,   tfTitre,
            lblDesc,    taDesc,
            lblLieu,    tfLieu,
            lblDate,    tfDate,
            hPrixCapa,
            lblCat,     cbCat,
            lblMsg,
            btnPublier,
            btnEffacer
        );

      

        Label lblListeTitre = new Label("Mes événements publiés");
        lblListeTitre.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        lblListeTitre.setTextFill(Color.web(BLEU));

        ListView<String> lvMesEvents = new ListView<>();
        lvMesEvents.setPrefHeight(380);
        lvMesEvents.setStyle("-fx-font-size: 12px;");

        Label lblAucun = new Label("Aucun événement publié pour l'instant.");
        lblAucun.setTextFill(Color.web(GRIS_TXT));
        lblAucun.setFont(Font.font("Arial", 12));

        Button btnRefresh = new Button("↻ Actualiser la liste");
        btnRefresh.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: " + BLEU + ";" +
            "-fx-border-color: " + BLEU + ";" +
            "-fx-border-radius: 5;" +
            "-fx-font-size: 12px;" +
            "-fx-cursor: hand;"
        );

        VBox listePanel = new VBox(10);
        listePanel.setPadding(new Insets(16));
        listePanel.setStyle(
            "-fx-background-color: " + FOND2 + ";" +
            "-fx-border-color: #DDDDDD;" +
            "-fx-border-radius: 8;" +
            "-fx-background-radius: 8;"
        );
        listePanel.getChildren().addAll(lblListeTitre, new Separator(), lvMesEvents, lblAucun, btnRefresh);

      
        chargerMesEvents(lvMesEvents, lblAucun, user.getId());

      

        HBox contenu = new HBox(16);
        contenu.setPadding(new Insets(16, 20, 16, 20));
        contenu.getChildren().addAll(formPanel, listePanel);
        HBox.setHgrow(listePanel, Priority.ALWAYS);

      

        VBox layout = new VBox(0);
        layout.setStyle("-fx-background-color: " + FOND + ";");
        layout.getChildren().addAll(header, contenu);


  
        btnPublier.setOnAction(e -> {
            String titre       = tfTitre.getText().trim();
            String description = taDesc.getText().trim();
            String lieu        = tfLieu.getText().trim();
            String date        = tfDate.getText().trim();
            String prixTxt     = tfPrix.getText().trim();
            String capaTxt     = tfCapa.getText().trim();
            String categorie   = cbCat.getValue();

            if (titre.isEmpty() || description.isEmpty() || lieu.isEmpty() ||
                date.isEmpty()  || prixTxt.isEmpty()     || capaTxt.isEmpty() ||
                categorie == null) {
                afficherErreur(lblMsg, "Veuillez remplir tous les champs !");
                return;
            }

            double prix;
            int    capacite;
            try {
                prix     = Double.parseDouble(prixTxt);
                capacite = Integer.parseInt(capaTxt);
            } catch (NumberFormatException ex) {
                afficherErreur(lblMsg, "Prix et capacité doivent être des nombres.");
                return;
            }

            if (prix < 0 || capacite <= 0) {
                afficherErreur(lblMsg, "Prix ≥ 0 et capacité > 0.");
                return;
            }

            Datastore db = Datastore.getInstance();
            boolean succes = db.ajouterEvent(
                titre, description, lieu, date,
                prix, capacite, user.getId(), categorie
            );

            if (succes) {
                afficherSucces(lblMsg, "Événement publié avec succès !");
                viderFormulaire(tfTitre, taDesc, tfLieu, tfDate, tfPrix, tfCapa, cbCat);
                chargerMesEvents(lvMesEvents, lblAucun, user.getId());
            } else {
                afficherErreur(lblMsg, "Erreur lors de la publication.");
            }
        });

       
        btnEffacer.setOnAction(e -> {
            viderFormulaire(tfTitre, taDesc, tfLieu, tfDate, tfPrix, tfCapa, cbCat);
            lblMsg.setText("");
        });

        btnRefresh.setOnAction(e -> {
            chargerMesEvents(lvMesEvents, lblAucun, user.getId());
        });

        btnDeconnexion.setOnAction(e -> {
            new LoginFrame().show(stage);
        });



        stage.setTitle("Event Center — Espace Organisateur");
        stage.setScene(new Scene(layout, 860, 580));
        stage.setResizable(true);
        stage.show();
    }

    // ── Charger les événements de cet organisateur ───────────────
    private void chargerMesEvents(ListView<String> lv, Label lblAucun, int organisateurId) {
        lv.getItems().clear();
        Datastore db = Datastore.getInstance();
        List<Map<String, Object>> tous = db.getEvents();

        int count = 0;
        for (Map<String, Object> ev : tous) {
            Object orgId = ev.get("organisateurId");
            if (orgId != null && ((int) orgId) == organisateurId) {
                double prix = (double) ev.get("prix");
                String ligne = ev.get("nom") +
                               "   |   " + ev.get("lieu") +
                               "   |   " + ev.get("date_heure") +
                               "   |   " + (prix == 0 ? "Gratuit" : prix + " DT") +
                               "   |   " + ev.get("placeRestantes") + " places";
                lv.getItems().add(ligne);
                count++;
            }
        }
        lblAucun.setVisible(count == 0);
    }

    
    private void viderFormulaire(TextField tfTitre, TextArea taDesc,
                                  TextField tfLieu, TextField tfDate,
                                  TextField tfPrix, TextField tfCapa,
                                  ComboBox<String> cbCat) {
        tfTitre.clear();
        taDesc.clear();
        tfLieu.clear();
        tfDate.clear();
        tfPrix.clear();
        tfCapa.clear();
        cbCat.setValue(null);
    }

    private String styleChamp() {
        return  "-fx-border-color: #CCCCCC;" +
                "-fx-border-radius: 5;" +
                "-fx-background-radius: 5;" +
                "-fx-font-size: 12px;";
    }

    private void afficherErreur(Label lbl, String message) {
        lbl.setTextFill(Color.web("#E74C3C"));
        lbl.setText("⚠ " + message);
    }

    private void afficherSucces(Label lbl, String message) {
        lbl.setTextFill(Color.web("#27AE60"));
        lbl.setText("✓ " + message);
    }
}