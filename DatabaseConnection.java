import java.sql.*;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/ccinfom_mp";
    private static final String USER = "root";
    private static final String PASSWORD = "009900";

    public void createConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database Connection: Success!");
        } catch (ClassNotFoundException e) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        } catch (SQLException e) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
        }
        
    }

    public void executeQuery() {
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();
            // ResultSet rs = queryStatement.executeQuery("INSERT INTO customer (first_name, last_name, email, phone_number, delivery_address, customer_rating) VALUES ('Jane', 'Smith', 'janesmith@example.com', '987-654-3210', '456 Elm St, Anytown, CA 12345', 3.8), ('Michael', 'Johnson', 'michaeljohnson@example.com', '555-555-5555', '789 Oak St, Anytown, CA 12345', 4.2);");
            
            ResultSet rs = queryStatement.executeQuery("SELECT * FROM customer");
            while (rs.next()) {
                String name = rs.getString("first_name");
                System.out.println(name);
            }
        } catch (SQLException e) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
