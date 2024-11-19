
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

    public void createUser(String emailInput, String passwordInput, String first_name, String last_name, String phone_number, String delivery_address) {
        try {
            //create logincredentials first THEN create user
            //TODO create user sign up GUI
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = c.prepareStatement("INSERT INTO LoginCredentials (email, password) VALUES (?, ?);");
            stmt.setString(1, emailInput);
            stmt.setString(2, passwordInput);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Log In Credentials inserted successfully!");
            } else {
                System.out.println("Error inserting Log In Credentials.");
            }

            stmt.close();
            c.close();

        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println("Error inserting Log In Credentials.");
                // TODO: Handle the specific exception
                System.out.println("Foreign key constraint violation: " + e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        
        try {
            //create logincredentials first THEN create user
            //TODO create user sign up GUI
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = null;

            stmt = c.prepareStatement("INSERT INTO Customer (first_name, last_name, email, phone_number, delivery_address) VALUES (?, ?, ?, ?, ?);");
            stmt.setString(1, first_name);
            stmt.setString(2, last_name);
            stmt.setString(3, emailInput);
            stmt.setString(4, phone_number);
            stmt.setString(5, delivery_address);

            int rowsInserted = 0;
            rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Customer info inserted successfully!");
            } else {
                System.out.println("Error inserting Customer info.");
            }

            stmt.close();
            c.close();
        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println("Error inserting Customer info.");
                // TODO: Handle the specific exception
                System.out.println("Foreign key constraint violation: " + e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        
        }
    }

    //TODO read user CRUD
    public void readUser(String emailInput, String first_name, String last_name, String phone_number, String delivery_address) {        
        try {
            //create logincredentials first THEN create user
            //TODO create user sign up GUI
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = null;
            
            stmt = c.prepareStatement("INSERT INTO Customer (first_name, last_name, email, phone_number, delivery_address, customer_rating) VALUES (?, ?, ?, ?, ?, ?);");
            stmt.setString(1, first_name);
            stmt.setString(2, last_name);
            stmt.setString(3, emailInput);
            stmt.setString(4, phone_number);
            stmt.setString(5, delivery_address);
            stmt.setInt(6, 1);

            int rowsInserted = 0;
            rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Customer info inserted successfully!");
            } else {
                System.out.println("Error inserting Customer info.");
            }

            stmt.close();
            c.close();
        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println("Error inserting Customer info.");
                // TODO: Handle the specific exception
                System.out.println("Foreign key constraint violation: " + e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        
        }
    }

    public void readUser(String emailInput, String passwordInput, String first_name, String last_name, String phone_number, String delivery_address) {        
        try {
            //create logincredentials first THEN create user
            //TODO create user sign up GUI
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = null;
            
            stmt = c.prepareStatement("INSERT INTO Customer (first_name, last_name, email, phone_number, delivery_address, customer_rating) VALUES (?, ?, ?, ?, ?, ?);");
            stmt.setString(1, first_name);
            stmt.setString(2, last_name);
            stmt.setString(3, emailInput);
            stmt.setString(4, phone_number);
            stmt.setString(5, delivery_address);
            stmt.setInt(6, 1);

            int rowsInserted = 0;
            rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Customer info inserted successfully!");
            } else {
                System.out.println("Error inserting Customer info.");
            }

            stmt.close();
            c.close();
        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println("Error inserting Customer info.");
                // TODO: Handle the specific exception
                System.out.println("Foreign key constraint violation: " + e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        
        }
    }

    public void deleteUser(int idToDelete) {
        try {
            String email = null;
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();

            String sqlQueryStatement = "SELECT email FROM customer WHERE customer_id = " + idToDelete + ";";
            ResultSet rs = queryStatement.executeQuery(sqlQueryStatement);
            
            while (rs.next()) {
                email = rs.getString("email");
            }            

            PreparedStatement stmt = c.prepareStatement("DELETE FROM Customer WHERE customer_id = ?;");
            stmt.setInt(1, idToDelete);
            System.out.println("4");
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Customer data deleted successfully!");
            } else {
                System.out.println("Error deleting Customer data.");
            }
            
            stmt = null;
            stmt = c.prepareStatement("DELETE FROM loginCredentials WHERE email = ?;");
            stmt.setString(1, email);

            rowsDeleted = 0;
            rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Log In Credentials deleted successfully!");
            } else {
                System.out.println("Error deleting Log In Credentials.");
            }

            stmt.close();
            c.close();

        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println("Error deleting Log In Credentials.");
                // TODO: Handle the specific exception
                System.out.println("Foreign key constraint violation: " + e.getMessage());
            } else {
                System.out.println("Error deleting Log In Credentials.");
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }



    public void validateLogIn(String usernameInput, String passwordInput) {
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();
            
            String sqlQueryStatement = "SELECT * FROM LogInCredentials;";

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
