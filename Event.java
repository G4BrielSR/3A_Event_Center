import java.time.LocalDate;
public class Event{
    private int id;
    private String titre ;
    private String description;
    private String lieu;
    private LocalDate date;
    private double prix;
    private int capacite;
    private int ticketsRestants;
    private int planner_id;
    private String categorie;
    public Event(int id,String titre ,String description,String lieu,LocalDate date,double prix, int capacite, int ticketsRestants, int planner_id ,String categorie){
        this.id=id;
        this.titre=titre;
        this.capacite=capacite;
        this.categorie=categorie;
        this.date=date;
        this.description=description;
        this.lieu=lieu;
        this.ticketsRestants=ticketsRestants;
        this.planner_id=planner_id;
        this.prix=prix;
    
    }
    public int getId(){
        return id;
    }
    public int getPlannerId(){
        return planner_id;
    }
    public String getTitre(){
        return titre;
    }
    public String getDescription(){
        return description;
    }
    public String getLieu(){
        return lieu;
    }
    public int getTicketRestantes(){
        return ticketsRestants;
    }
    public double getPrix(){
        return prix;
    }
    public LocalDate getDate(){
        return date;
    }
    public int getCapacite(){
        return capacite;
    }
    public String getCategorie(){
        return categorie;
    } 
    public void setTitre(String titre){
        this.titre=titre;
    }
    public void setLieu(String lieu){
        this.lieu=lieu;
    }
    public void setDescription(String description){
        this.description=description;
    }
    public void setDate(LocalDate date){
        this.date=date;
    }
    public void setCapacite(int capacite){
        this.capacite=capacite;
    }
    public void setCategorie(String categorie){
        this.categorie=categorie;
    }
    public void setPrix(double prix){
        this.prix=prix;
    }
    public void reserverticket(){
        if (ticketsRestants>0){
            ticketsRestants=ticketsRestants-1;
        }
    }
     public boolean isFull(){
        if (ticketsRestants==0){
            return true;
        }
        return false;
    }

}