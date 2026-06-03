public class AppDummy {
    public static void main(String[] args) {
        System.out.println("OK");
        Datastore BDD = Datastore.getInstance();
        LocalDate FromDate = LocalDate.of(2026, 3, 3);
        LocalDate TicketDate = LocalDate.of(2026, 2, 5);
        BDD.addEvent("Fête du fromage", 300, FromDate, "Venez passer d'excellents moments en célébrant le fromage", "Rochefort", 300, 57.3);
        BDD.addUser("Jean", "Un", "ju@duck.cz", "mdp", "organisateur");
        BDD.addTicket(1, 1, TicketDate, "confirme", 35.40);
    }
}