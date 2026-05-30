import java.time.LocalDateTime;

public class Ticket {
    private int id;
    private int id_E;
    private int id_U;
    private LocalDateTime date_achat;
    private String statut;
    private double prix;
    public Ticket(int id,int id_E,int id_U,double prix){
        this.id=id;
        this.id_E=id_E;
        this.id_U=id_U;
        this.date_achat=LocalDateTime.now();
        this.prix=prix;
        this.statut="confirme";
    }
    public int getId(){
        return id;
    }
    public int getId_E(){
        return id_E;
    }
    public int getId_U(){
        return id_U;
    }
    public LocalDateTime getDateAchat(){
        return date_achat;
    }
    public String getStatus(){
        return statut;
    }
    public double getPrix(){
        return prix;
    }
    public void setStatut(String statut){
        this.statut=statut;
    }


}
