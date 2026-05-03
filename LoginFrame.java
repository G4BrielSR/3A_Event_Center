import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class LoginFrame {

    private static final String BLEU     = "#2E86AB";
    private static final String FOND     = "#F0F4F8";
    private static final String ROUGE    = "#E74C3C";
    private static final String GRIS_TXT = "#555555";

    public void show(Stage stage) {

        Label lblTitre = new Label("Event Center");
        lblTitre.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        lblTitre.setTextFill(Color.web(BLEU));

        Label lblSous = new Label("Connectez-vous à votre compte");
        lblSous.setFont(Font.font("Arial", 13));
        lblSous.setTextFill(Color.web(GRIS_TXT));

        Label lblEmail = new Label("Email");
        lblEmail.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        lblEmail.setTextFill(Color.web(GRIS_TXT));

        TextField tfEmail = new TextField();
        tfEmail.setPromptText("votre@email.com");
        tfEmail.setPrefWidth(300);
        tfEmail.setPrefHeight(36);
        tfEmail.setStyle(
            "-fx-border-color: #CCCCCC;" +
            "-fx-border-radius: 5;" +
            "-fx-background-radius: 5;" +
            "-fx-font-size: 13px;"
        );

        Label lblMdp = new Label("Mot de passe");
        lblMdp.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        lblMdp.setTextFill(Color.web(GRIS_TXT));

        PasswordField pfMdp = new PasswordField();
        pfMdp.setPromptText("Votre mot de passe");
        pfMdp.setPrefWidth(300);
        pfMdp.setPrefHeight(36);
        pfMdp.setStyle(
            "-fx-border-color: #CCCCCC;" +
            "-fx-border-radius: 5;" +
            "-fx-background-radius: 5;" +
            "-fx-font-size: 13px;"
        );

        Label lblErreur = new Label("");
        lblErreur.setTextFill(Color.web(ROUGE));
        lblErreur.setFont(Font.font("Arial", 12));
        lblErreur.setWrapText(true);
        lblErreur.setMaxWidth(300);

        Button btnLogin = new Button("Se connecter");
        btnLogin.setPrefWidth(300);
        btnLogin.setPrefHeight(40);
        btnLogin.setStyle(
            "-fx-background-color: " + BLEU + ";" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 14px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 6;" +
            "-fx-cursor: hand;"
        );

        Button btnInscrire = new Button("Pas encore de compte ? S'inscrire");
        btnInscrire.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: " + BLEU + ";" +
            "-fx-border-color: transparent;" +
            "-fx-font-size: 12px;" +
            "-fx-cursor: hand;"
        );

        btnLogin.setOnAction(e -> {
            String email = tfEmail.getText().trim();
            String mdp   = pfMdp.getText();

            if (email.isEmpty() || mdp.isEmpty()) {
                lblErreur.setText("Veuillez remplir tous les champs !");
                return;
            }

            if (!email.contains("@") || !email.contains(".")) {
                lblErreur.setText("Format d'email invalide.");
                return;
            }

            Datastore db = Datastore.getInstance();
            User user    = db.connexion(email, mdp);

            if (user != null) {
                lblErreur.setText("");
                if (user.getRole().equals("organisateur")) {
                    new OrganisateurFrame().show(stage, user);
                } else {
                    new JeuneFrame().show(stage, user);
                }
            } else {
                lblErreur.setText("Email ou mot de passe incorrect.");
                pfMdp.clear();
            }
        });

        btnInscrire.setOnAction(e -> {
            new RegisterFrame().show(stage);
        });

        Separator sep = new Separator();
        sep.setMaxWidth(300);

        HBox hEmail = new HBox(lblEmail); hEmail.setMaxWidth(300);
        HBox hMdp   = new HBox(lblMdp);   hMdp.setMaxWidth(300);

        VBox layout = new VBox(12);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(50, 40, 40, 40));
        layout.setStyle("-fx-background-color: " + FOND + ";");
        layout.getChildren().addAll(
            lblTitre,
            lblSous,
            sep,
            hEmail,
            tfEmail,
            hMdp,
            pfMdp,
            lblErreur,
            btnLogin,
            btnInscrire
        );

        stage.setTitle("Event Center — Connexion");
        stage.setScene(new Scene(layout, 440, 520));
        stage.setResizable(false);
        stage.show();
    }
}