public class Driver {
    public static void main (String[] args){

        // establish connection with database here
        DatabaseConnection jdbc = new DatabaseConnection();
        jdbc.createConnection();

        jdbc.executeQuery();
    }
}