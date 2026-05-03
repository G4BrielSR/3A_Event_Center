// ═══════════════════════════════════════════════════════════════
//  LoginFrame.java  —  Écran de connexion
//  Auteur  : [ton prénom] — Collègue 2
//  Projet  : 3A_Event_Center
//  Dépend  : User.java (Islem) + Datastore.java (Gabriel)
// ═══════════════════════════════════════════════════════════════

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class LoginFrame {

    // ─── Palette de couleurs (à remplacer par AppTheme plus tard) ───
    private static final String BLEU      = "#2E86AB";
    private static final String FOND      = "#F0F4F8";
    private static final String ROUGE     = "#E74C3C";
    private static final String GRIS_TXT  = "#555555";

    // ════════════════════════════════════════════════════════════
    //  MÉTHODE PRINCIPALE — appelée pour afficher cet écran
    // ════════════════════════════════════════════════════════════
    public void show(Stage stage) {

        // ── 1. TITRE ET SOUS-TITRE ──────────────────────────────

        Label lblTitre = new Label("Event Center");
        lblTitre.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        lblTitre.setTextFill(Color.web(BLEU));

        Label lblSous = new Label("Connectez-vous à votre compte");
        lblSous.setFont(Font.font("Arial", 13));
        lblSous.setTextFill(Color.web(GRIS_TXT));

        // ── 2. CHAMP EMAIL ──────────────────────────────────────

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

        // ── 3. CHAMP MOT DE PASSE ───────────────────────────────

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

        // ── 4. LABEL MESSAGE D'ERREUR (vide au départ) ──────────

        Label lblErreur = new Label("");
        lblErreur.setTextFill(Color.web(ROUGE));
        lblErreur.setFont(Font.font("Arial", 12));
        lblErreur.setWrapText(true);   // retour à la ligne si long
        lblErreur.setMaxWidth(300);

        // ── 5. BOUTON "SE CONNECTER" ────────────────────────────

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

        // ── 6. LIEN "S'INSCRIRE" ────────────────────────────────

        Button btnInscrire = new Button("Pas encore de compte ? S'inscrire");
        btnInscrire.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: " + BLEU + ";" +
            "-fx-border-color: transparent;" +
            "-fx-font-size: 12px;" +
            "-fx-cursor: hand;"
        );

        // ════════════════════════════════════════════════════════
        //  ACTIONS DES BOUTONS
        // ════════════════════════════════════════════════════════

        // ── Action : clic sur "Se connecter" ────────────────────
        btnLogin.setOnAction(e -> {

            // Étape A — Lire les champs
            String email = tfEmail.getText().trim();
            String mdp   = pfMdp.getText();

            // Étape B — Vérifier que les champs ne sont pas vides
            if (email.isEmpty() || mdp.isEmpty()) {
                lblErreur.setText("Veuillez remplir tous les champs !");
                return; // stop — ne pas continuer
            }

            // Étape C — Vérifier le format email basique
            if (!email.contains("@") || !email.contains(".")) {
                lblErreur.setText("Format d'email invalide.");
                return;
            }

            // Étape D — Vérifier dans la base de données (Gabriel)
            // ⚠️  Gabriel doit ajouter la méthode connexion() dans Datastore.java
            // En attendant, on utilise le bloc try/catch pour gérer les deux cas

            try {
                Datastore db = Datastore.getInstance();
                User user    = db.connexion(email, mdp);
                //             ↑ Gabriel doit créer cette méthode

                if (user != null) {
                    // ✓ Connexion réussie — aller au bon écran selon le rôle
                    lblErreur.setText(""); // effacer les erreurs
                    if (user.getRole().equals("organisateur")) {
                        new OrganisateurFrame().show(stage, user);
                    } else {
                        new JeuneFrame().show(stage, user);
                    }
                } else {
                    // ✗ Identifiants incorrects
                    lblErreur.setText("Email ou mot de passe incorrect.");
                    pfMdp.clear(); // vider le champ mot de passe
                }

            } catch (Exception ex) {
                // Si Datastore ou connexion() n'existe pas encore
                lblErreur.setText("[DEV] Connexion BD non disponible : " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        // ── Action : clic sur "S'inscrire" ──────────────────────
        btnInscrire.setOnAction(e -> {
            new RegisterFrame().show(stage);
        });

        // ════════════════════════════════════════════════════════
        //  LAYOUT — organisation visuelle
        // ════════════════════════════════════════════════════════

        // Séparateur visuel entre le titre et le formulaire
        Separator sep = new Separator();
        sep.setMaxWidth(300);

        // VBox principal — tout en colonne, centré
        VBox layout = new VBox(12);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(50, 40, 40, 40));
        layout.setStyle("-fx-background-color: " + FOND + ";");

        // Aligner les labels à gauche (dans leur propre HBox)
        HBox hEmail   = new HBox(lblEmail);   hEmail.setMaxWidth(300);
        HBox hMdp     = new HBox(lblMdp);     hMdp.setMaxWidth(300);

        layout.getChildren().addAll(
            lblTitre,    // Grand titre bleu
            lblSous,     // Sous-titre gris
            sep,         // Ligne de séparation
            hEmail,      // Label "Email" (aligné gauche)
            tfEmail,     // Champ email
            hMdp,        // Label "Mot de passe" (aligné gauche)
            pfMdp,       // Champ mot de passe masqué
            lblErreur,   // Message d'erreur (rouge, vide au départ)
            btnLogin,    // Bouton connexion bleu
            btnInscrire  // Lien inscription transparent
        );

        // ════════════════════════════════════════════════════════
        //  AFFICHAGE DE LA FENÊTRE
        // ════════════════════════════════════════════════════════

        Scene scene = new Scene(layout, 440, 520);
        stage.setTitle("Event Center — Connexion");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
