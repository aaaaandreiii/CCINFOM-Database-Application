
import java.math.BigDecimal;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/ccinfom";
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
                int customer_id = rs.getInt("customer_id");
                String first_name = rs.getString("first_name");
                String last_name = rs.getString("last_name");
                String email = rs.getString("email");
                String phone_number = rs.getString("phone_number");
                String delivery_address = rs.getString("delivery_address");
                Double customer_rating = rs.getDouble("customer_rating");
                
                System.out.println(customer_id + "\t" + 
                                   first_name + "\t" + 
                                   last_name  + "\t" + 
                                   email + "\t\t" + 
                                   phone_number  + "\t" + 
                                   delivery_address  + "\t\t" + 
                                   customer_rating);
            }
        } catch (SQLException e) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void insertValues() {
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            // PreparedStatement stmt = c.prepareStatement("INSERT INTO OrderItem (order_id, item_id, quantity, price_at_order) VALUES (1,17,6,344.99);");
            PreparedStatement stmt = c.prepareStatement("INSERT INTO OrderItem (order_id, item_id, quantity, price_at_order) VALUES (?, ?, ?, ?);");

            BigDecimal decimalNum = new BigDecimal(344.99);

            stmt.setInt(1, 12);
            stmt.setInt(2, 17);
            stmt.setInt(3, 1);
            stmt.setBigDecimal(4, decimalNum);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Data inserted successfully!");
            } else {
                System.out.println("Error inserting data.");
            }

            stmt.close();
            c.close();

        } catch (SQLException e) {
            // Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            if (e instanceof SQLIntegrityConstraintViolationException) {
                // Handle the specific exception
                System.out.println("Foreign key constraint violation: " + e.getMessage());
                // You can take specific actions, such as logging the error, retrying with different data, or notifying the user.
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        
        }
    }

    public void addUser() {
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = c.prepareStatement("INSERT INTO Customer (first_name, last_name, email, phone_number, delivery_address, customer_rating) VALUES (?, ?, ?, ?, ?, ?);");

            BigDecimal customerRating = new BigDecimal(4.99);

            stmt.setString(1, "Andrei");
            stmt.setString(2, "Balingit");
            // stmt.setString(3, "andrei_balingit@dlsu.edu.ph");
            
            stmt.setString(3, "admin");
            stmt.setString(4, "0912-345-6789");
            stmt.setString(5, "Pasay City");
            stmt.setBigDecimal(6, customerRating);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Data inserted successfully!");
            } else {
                System.out.println("Error inserting data.");
            }

            stmt.close();
            c.close();

        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                // TODO: Handle the specific exception
                System.out.println("Foreign key constraint violation: " + e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        
        }
    }

    public void deleteUser(int idToDelete) {
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = c.prepareStatement("DELETE FROM Customer WHERE customer_id = ?;");

            stmt.setInt(1, idToDelete);

            int rowsDeleted = stmt.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Record deleted successfully.");
            } else {
                System.out.println("Record not found.");
            }

            stmt.close();
            c.close();
        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                // TODO: Handle the specific exception
                System.out.println("Foreign key constraint violation: " + e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        
        }
    }



    public void validateLogIn(String usernameInput, String passwordInput) {
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();
            
            String sqlQueryStatement = "SELECT * FROM LogInCredentials";

            ResultSet rs = queryStatement.executeQuery(sqlQueryStatement);
            while (rs.next()) {
                String username = rs.getString("username");
                String password_hash = rs.getString("password_hash");

                if (usernameInput.equals(username)){
                    if (passwordInput.equals(password_hash))
                        System.out.println("Password Validated");
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
