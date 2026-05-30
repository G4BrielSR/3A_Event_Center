import javafx.scene.paint.Color;

public class Apptheme {

    public static final String BLEU      = "#2E86AB";
    public static final String FOND      = "#F0F4F8";
    public static final String FOND2     = "#FFFFFF";
    public static final String GRIS_TXT  = "#555555";
    public static final String ROUGE     = "#E74C3C";
    public static final String VERT      = "#27AE60";
    public static final String ORANGE    = "#E67E22";

   
    public static Color getColorBleu()     { return Color.web(BLEU); }
    public static Color getColorGrisTxt()  { return Color.web(GRIS_TXT); }
    public static Color getColorRouge()    { return Color.web(ROUGE); }
    public static Color getColorVert()     { return Color.web(VERT); }

    
    
    
    public static final String STYLE_BOUTON_PRINCIPAL = 
        "-fx-background-color: " + BLEU + ";" +
        "-fx-text-fill: white;" +
        "-fx-font-size: 14px;" +
        "-fx-font-weight: bold;" +
        "-fx-background-radius: 6;" +
        "-fx-cursor: hand;";

   
    public static final String STYLE_BOUTON_SECONDAIRE = 
        "-fx-background-color: transparent;" +
        "-fx-text-fill: " + GRIS_TXT + ";" +
        "-fx-border-color: #CCCCCC;" +
        "-fx-border-radius: 5;" +
        "-fx-font-size: 12px;" +
        "-fx-cursor: hand;";

    
    public static final String STYLE_CHAMP_TEXTE = 
        "-fx-border-color: #CCCCCC;" +
        "-fx-border-radius: 5;" +
        "-fx-background-radius: 5;" +
        "-fx-font-size: 13px;";
}