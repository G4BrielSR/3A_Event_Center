public class AppDummy {
    public static void main(String[] args) {
        System.out.println("OK");
        Datastore BDD = Datastore.getInstance();
        
        BDD.ajouterEvent(
            "Fête du fromage", 
            "Venez passer d'excellents moments en célébrant le fromage", 
            "Rochefort", 
            "2026-03-03", 
            57.3,         
            300,          
            1,            
            "cuisine"     
        );
    }
}