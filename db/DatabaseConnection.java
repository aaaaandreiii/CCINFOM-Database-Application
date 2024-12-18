
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

//result set for receiving queries
//prepared statement for instruction queries to insert, delete

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

    public ArrayList<Object> findUserById(int customer_id) {
        ArrayList<Object> userInfo = new ArrayList<>();
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();
            String sqlQueryStatement = "SELECT * FROM customer WHERE customer_id = " + customer_id + ";";
            ResultSet rs = queryStatement.executeQuery(sqlQueryStatement);
            while (rs.next()) {
                userInfo.add(customer_id);                                  //int customer_id = rs.getInt("customer_id");
                userInfo.add(rs.getString("first_name"));       //String first_name = rs.getString("first_name");
                userInfo.add(rs.getString("last_name"));        //String last_name = rs.getString("last_name");
                userInfo.add(rs.getString("email"));            //String email = rs.getString("email");
                userInfo.add(rs.getString("phone_number"));     //String phone_number = rs.getString("phone_number");
                userInfo.add(rs.getString("delivery_address")); //String delivery_address = rs.getString("delivery_address");

                System.out.println("User found by ID!");
                return userInfo;
            }
        } catch (SQLException e) {
                System.out.println("Error displaying customer info.");
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
        }
        return userInfo;
    }

    public int findUserIdByEmail(String email) {
        int customer_id = -1;
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();
            String sqlQueryStatement = "SELECT customer_id FROM customer WHERE email = '" + email + "';";
            ResultSet rs = queryStatement.executeQuery(sqlQueryStatement);
            
            while (rs.next()) {
                customer_id = rs.getInt("customer_id");
            }
            rs.close();
            c.close();

            System.out.println("Account " + email + " has a user ID of: " + customer_id);
            return customer_id;

        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println("Error finding user by email.");
                // TODO: Handle the specific exception
                System.out.println(e.getMessage());
            } else {
                System.out.println("Error deleting Log In Credentials.");
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        }

        return customer_id;
    }

    public ArrayList<Object> findUserByName(String first_name, String last_name) {
        ArrayList<Object> userInfo = new ArrayList<>();
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();

            ResultSet rs = queryStatement.executeQuery("SELECT * FROM customer WHERE first_name = '" + first_name + "' AND last_name = '" + last_name + "';");
            while (rs.next()) {
                userInfo.add(rs.getInt("customer_id"));             // int customer_id = rs.getInt("customer_id");
                userInfo.add(rs.getString("first_name"));           // String first_name = rs.getString("first_name");
                userInfo.add(rs.getString("last_name"));            // String last_name = rs.getString("last_name");
                userInfo.add(rs.getString("email"));                // String email = rs.getString("email");
                userInfo.add(rs.getString("phone_number"));         // String phone_number = rs.getString("phone_number");
                userInfo.add(rs.getString("delivery_address"));     // String delivery_address = rs.getString("delivery_address");
            }

            System.out.println("User found by name!");
            return userInfo;

        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println("Error finding user by email.");
                // TODO: Handle the specific exception
                System.out.println(e.getMessage());
            } else {
                System.out.println("Error deleting Log In Credentials.");
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        }

        return userInfo;
    }

    public void createUser(String emailInput, String passwordInput, String first_name, String last_name, String phone_number, String delivery_address) {
        try {
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

            try {
                //create logincredentials first THEN create user
                //TODO create user sign up GUI
                stmt = null;
        
                stmt = c.prepareStatement("INSERT INTO Customer (first_name, last_name, email, phone_number, delivery_address) VALUES (?, ?, ?, ?, ?);");
                stmt.setString(1, first_name);
                stmt.setString(2, last_name);
                stmt.setString(3, emailInput);
                stmt.setString(4, phone_number);
                stmt.setString(5, delivery_address);
        
                rowsInserted = 0;
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
            
        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println("Error inserting Log In Credentials.");
                // TODO: Handle the specific exception
                System.out.println("Foreign key constraint violation: " + e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    public List<List<Object>> readAllUsers() {    
        List<List<Object>> userInfo = new ArrayList<>();
        userInfo.add(new ArrayList<>());
        userInfo.add(new ArrayList<>());
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();
            ResultSet rs = queryStatement.executeQuery("SELECT * FROM customer");
            int i = 0;
            while (rs.next()) {
                userInfo.get(i).add(rs.getInt("customer_id"));             // int customer_id = rs.getInt("customer_id");
                userInfo.get(i).add(rs.getString("first_name"));           // String first_name = rs.getString("first_name");
                userInfo.get(i).add(rs.getString("last_name"));            // String last_name = rs.getString("last_name");
                userInfo.get(i).add(rs.getString("email"));                // String email = rs.getString("email");
                userInfo.get(i).add(rs.getString("phone_number"));         // String phone_number = rs.getString("phone_number");
                userInfo.get(i).add(rs.getString("delivery_address"));     // String delivery_address = rs.getString("delivery_address");
                i++;
            }
            return userInfo;
        } catch (SQLException e) {
            System.out.println("Error displaying Customer info.");
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

    public void updateUser(String specifiedAttribute, String valueToUpdate, int customer_id) {        
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = null;

            String sqlQueryStatement = "UPDATE customer SET " + specifiedAttribute + " = ?  WHERE customer_id = ?";

            stmt = c.prepareStatement(sqlQueryStatement);
            
            stmt.setString(1, valueToUpdate);
            stmt.setInt(2, customer_id);

            int rowsInserted = 0;
            rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Customer info updated successfully!");
            } else {
                System.out.println("Error updating Customer info.");
            }

            stmt.close();
            c.close();
        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println("Error updating Customer info.");
                // TODO: Handle the specific exception
                System.out.println(e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        
        }
    }

    public void deleteUser(int idToDelete) {
        try {
            //delete user first THEN logincredentials 
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

    public void deleteLogInCredentials(String email) {
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = c.prepareStatement("DELETE FROM loginCredentials WHERE email = ?;");
            stmt.setString(1, email);

            int rowsDeleted = 0;
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
                System.out.println("Foreign key constraint violation: " + e.getMessage());
            } else {
                System.out.println("Error deleting Log In Credentials.");
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    //TODO Create findObjectById for all objects
    public void findSupplierById(int supplier_id) {
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();
            ResultSet rs = queryStatement.executeQuery("SELECT * FROM supplier WHERE supplier_id = " + supplier_id + ";");
            while (rs.next()) {
                // int supplier_id = rs.getInt("supplier_id");
                String supplier_fname = rs.getString("supplier_fname");
                String supplier_lname = rs.getString("supplier_lname");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                BigDecimal supplier_rating = rs.getBigDecimal("supplier_rating");
                
                System.out.println(supplier_id + "\t" + 
                                    supplier_fname + "\t" + 
                                    supplier_lname  + "\t" + 
                                    email + "\t" + 
                                    phone  + "\t" + 
                                    address + "\t" +
                                    supplier_rating);
            }
        } catch (SQLException e) {
                System.out.println("Error displaying Supplier info.");
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void createSupplier(int customer_id, BigDecimal supplier_rating) {        
        try {
            int supplier_id = -1;
            String supplier_fname = null;
            String supplier_lname = null;
            String email = null;
            String phone = null;
            String address = null;

            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);

            java.sql.Statement queryStatement = c.createStatement();
            String sqlQueryStatement = "SELECT first_name, last_name, email, phone_number, delivery_address FROM customer WHERE customer_id = " + customer_id + ";";
            ResultSet rs = queryStatement.executeQuery(sqlQueryStatement);
            
            while (rs.next()) {
                supplier_fname = rs.getString("first_name");
                supplier_lname = rs.getString("last_name");
                email = rs.getString("email");
                phone = rs.getString("phone_number");
                address = rs.getString("delivery_address");
            }

            
                PreparedStatement stmt = c.prepareStatement("INSERT INTO Supplier (supplier_fname, supplier_lname, email, phone, address, supplier_rating) VALUES (?, ?, ?, ?, ?, ?);");
                stmt.setString(1, supplier_fname);
                stmt.setString(2, supplier_lname);
                stmt.setString(3, email);
                stmt.setString(4, phone);
                stmt.setString(5, address);
                stmt.setBigDecimal(6, supplier_rating);

                int rowsInserted = 0;
                rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Supplier info inserted successfully!");
                } else {
                    System.out.println("Error inserting Supplier info.");
                }

                System.out.println("Newly created supplier: ");
                queryStatement = null;
                queryStatement = c.createStatement();

                sqlQueryStatement = null;
                sqlQueryStatement = "SELECT supplier_id FROM supplier WHERE email = " + email + ";";
                
                rs = null;
                rs = queryStatement.executeQuery(sqlQueryStatement);
                
                while (rs.next()) {
                    supplier_id = rs.getInt("supplier_id");
                }            

                findSupplierById(supplier_id);

                stmt.close();
                c.close();

        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println("Error inserting Supplier info.");
                // TODO: Handle the specific exception
                System.out.println("Foreign key constraint violation: " + e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    public void readAllSuppliers() {        
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();
            ResultSet rs = queryStatement.executeQuery("SELECT * FROM supplier");
            while (rs.next()) {
                int supplier_id = rs.getInt("supplier_id");
                String supplier_fname = rs.getString("supplier_fname");
                String supplier_lname = rs.getString("supplier_lname");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                BigDecimal supplier_rating = rs.getBigDecimal("supplier_rating");
                
                System.out.println(supplier_id + "\t" + 
                                    supplier_fname + "\t" + 
                                    supplier_lname  + "\t" + 
                                    email + "\t" + 
                                    phone  + "\t" + 
                                    address + "\t" +
                                    supplier_rating);
            }
        } catch (SQLException e) {
                System.out.println("Error displaying Supplier info.");
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void updateSupplier(String specifiedAttribute, String valueToUpdate, int supplier_id) {        
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = null;

            String sqlQueryStatement = "UPDATE supplier SET " + specifiedAttribute + " = ?  WHERE supplier_id = ?";

            stmt = c.prepareStatement(sqlQueryStatement);
            
            stmt.setString(1, valueToUpdate);
            stmt.setInt(2, supplier_id);

            int rowsInserted = 0;
            rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Supplier info updated successfully!");
            } else {
                System.out.println("Error updating Supplier info.");
            }

            System.out.println("New updated information: ");
            findSupplierById(supplier_id);

            stmt.close();
            c.close();
        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println("Error updating Supplier info.");
                // TODO: Handle the specific exception
                System.out.println(e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        
        }
    }

    public void deleteSupplier(int idToDelete) {
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = c.prepareStatement("DELETE FROM supplier WHERE supplier_id = ?;");
            stmt.setInt(1, idToDelete);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Supplier data deleted successfully!");
            } else {
                System.out.println("Error deleting Supplier data.");
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














    public void createItemEntity(String name, int manufacturer_id, BigDecimal srp, String manufacturer, String description) {      
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = null;

            stmt = c.prepareStatement("INSERT INTO Item (name, manufacturer_id, srp, manufacturer, description) VALUES (?, ?, ?, ?, ?);");
            stmt.setString(1, name);
            stmt.setInt(2, manufacturer_id);
            stmt.setBigDecimal(3, srp);
            stmt.setString(4, manufacturer);
            stmt.setString(5, description);

            int rowsInserted = 0;
            rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Item info inserted successfully!");
            } else {
                System.out.println("Error inserting Item info.");
            }

            stmt.close();
            c.close();
        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println("Error inserting Item info.");
                // TODO: Handle the specific exception
                System.out.println("Foreign key constraint violation: " + e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        
        }
    }

    public void readItemEntity() {        
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();
            ResultSet rs = queryStatement.executeQuery("SELECT * FROM item");
            while (rs.next()) {
                int item_id = rs.getInt("item_id");
                String name = rs.getString("name");
                int manufactturer_id = rs.getInt("manufacturer_id");
                BigDecimal srp = rs.getBigDecimal("srp");
                String manufacturer = rs.getString("manufacturer");
                String description = rs.getString("description");
                
                System.out.println(item_id + "\t" + 
                                    name + "\t" + 
                                    manufactturer_id  + "\t" + 
                                    srp + "\t\t" + 
                                    manufacturer  + "\t" + 
                                    description);
            }
        } catch (SQLException e) {
                System.out.println("Error displaying Customer info.");
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void updateItemEntity(String specifiedAttribute, String valueToUpdate, int item_id) {    
        //TODO if trying to edit id, DONT ALLOW
        // if (!specifiedAttribute.equals(specifiedAttribute)){

        // } else {
        //     System.out.println("Please select another attribute to update");
        // }
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = null;

            String sqlQueryStatement = "UPDATE item SET " + specifiedAttribute + " = ?  WHERE item_id = ?";

            stmt = c.prepareStatement(sqlQueryStatement);
            
            stmt.setString(1, valueToUpdate);
            stmt.setInt(2, item_id);

            int rowsInserted = 0;
            rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Item info updated successfully!");
            } else {
                System.out.println("Error updating Item info.");
            }

            stmt.close();
            c.close();
        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println("Error updating Item info.");
                // TODO: Handle the specific exception
                System.out.println(e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        
        }
    }

    public void deleteItemEntity(int idToDelete) {
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = c.prepareStatement("DELETE FROM Item WHERE item_id = ?;");
            stmt.setInt(1, idToDelete);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Item data deleted successfully!");
            } else {
                System.out.println("Error deleting Item data.");
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


    public void addToShoppingCart(int customer_id, int item_id, int quantity) {
        try {
            int supplier_id = -1;
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            // INSERT INTO Wishlist (customer_id, item_id, supplier_id) VALUES
            java.sql.Statement queryStatement = c.createStatement();

            String sqlQueryStatement = "SELECT supplier_id FROM Inventory WHERE item_id = " + item_id + ";";
            ResultSet rs = queryStatement.executeQuery(sqlQueryStatement);
            
            while (rs.next()) {
                supplier_id = rs.getInt("supplier_id");
            }

            PreparedStatement stmt = c.prepareStatement("INSERT INTO ShoppingCart (customer_id, item_id, quantity, supplier_id) VALUES (?, ?, ?, ?);");
            stmt.setInt(1, customer_id);
            stmt.setInt(2, item_id);
            stmt.setInt(3, quantity);
            stmt.setInt(4, supplier_id);

            int rowsInserted = 0;
            rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Item added to cart successfully!");
            } else {
                System.out.println("Error adding item to cart.");
            }

            stmt.close();
            c.close();
        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println("Error adding item to cart.");
                // TODO: Handle the specific exception
                System.out.println("Foreign key constraint violation: " + e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    public void readShoppingCart(int customer_id) {        
            try {
                Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
                java.sql.Statement queryStatement = c.createStatement();
                ResultSet rs = queryStatement.executeQuery("SELECT * FROM ShoppingCart WHERE customer_id = " + customer_id + ";");
                while (rs.next()) {
                    String first_name = rs.getString("first_name");
                    String last_name = rs.getString("last_name");
                    String email = rs.getString("email");
                    String phone_number = rs.getString("phone_number");
                    String delivery_address = rs.getString("delivery_address");
                    
                    System.out.println(customer_id + "\t" + 
                                       first_name + "\t" + 
                                       last_name  + "\t" + 
                                       email + "\t\t" + 
                                       phone_number  + "\t" + 
                                       delivery_address);
                }
            } catch (SQLException e) {
                    System.out.println("Error displaying Customer info.");
                    Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
                }
        }
    
    public void updateOrder(String specifiedAttribute, String valueToUpdate, int customer_id) {        
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = null;
    
            String sqlQueryStatement = "UPDATE customer SET " + specifiedAttribute + " = ?  WHERE customer_id = ?";
    
            stmt = c.prepareStatement(sqlQueryStatement);
                
            stmt.setString(1, valueToUpdate);
            stmt.setInt(2, customer_id);
    
            int rowsInserted = 0;
            rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Customer info updated successfully!");
            } else {
                System.out.println("Error updating Customer info.");
            }
    
            stmt.close();
            c.close();
        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println("Error updating Customer info.");
                // TODO: Handle the specific exception
                System.out.println(e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }
    
    public void deleteOrder(int idToDelete) {
        try {
            //delete user first THEN logincredentials 
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
    
    public void createManufacturer(int manufacturer_id, String manufacturer_name, String address) {
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();


            String sqlQueryStatement = "SELECT manufacturer_id FROM Manufacturer;";
            ResultSet rs = queryStatement.executeQuery(sqlQueryStatement);
                         
            PreparedStatement stmt = c.prepareStatement("INSERT INTO Manufacturer (manufacturer_name, address) VALUES (?, ?);");
            stmt.setString(1, manufacturer_name);
            stmt.setString(2, address);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Manufacturer Credentials inserted successfully!");
            } else {
                System.out.println("Error inserting Manufacturer Credentials.");
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
    }

    public void readAllManufacturers() {        
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();
            ResultSet rs = queryStatement.executeQuery("SELECT * FROM Manufacturer");
            while (rs.next()) {
                int manufacturer_id = rs.getInt("manufacturer_id");
                String manufacturer_name = rs.getString("manufaturer_name");
                String address = rs.getString("address");
               
                System.out.println(manufacturer_id + "\t" +
                                   manufacturer_name + "\t" +
                                   address);
            }
        } catch (SQLException e) {
                System.out.println("Error displaying Manufacturer info.");
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
        }
    }


    public void updateManufacturer(String specifiedAttribute, String valueToUpdate, int manufacturer_id) {        
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = null;

            String sqlQueryStatement = "UPDATE manufacturer SET " + specifiedAttribute + " = ?  WHERE manufacturer_id = ?";
            stmt = c.prepareStatement(sqlQueryStatement);
            
            stmt.setString(1, valueToUpdate);
            stmt.setInt(2, manufacturer_id);

            int rowsInserted = 0;
            rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Manufacturer info updated successfully!");
            } else {
                System.out.println("Error updating Manufacturer info.");
            }

            stmt.close();
            c.close();

        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println("Error updating Customer info.");
                // TODO: Handle the specific exception
                System.out.println(e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
       
        }
    }

    public void deleteManufacturer(int idToDelete) {
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = c.prepareStatement("DELETE FROM Manufacturer WHERE manufacturer_id = ?;");
            stmt.setInt(1, idToDelete);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Manufacturer data deleted successfully!");
            } else {
                System.out.println("Error deleting Manufacturer data.");
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



    // public void createOrder(String emailInput, String passwordInput, String first_name, String last_name, String phone_number, String delivery_address) {
    //     try {
    //         Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
    //         PreparedStatement stmt = c.prepareStatement("INSERT INTO LoginCredentials (email, password) VALUES (?, ?);");


    //         get items from cart
    //         double check with supplier inventory
            

    //         INSERT INTO OrderInfo (customer_id, order_date, supplier_id, total_amount, status) VALUES
    //         INSERT INTO OrderItem (order_id, item_id, quantity, price_at_order) VALUES






    //         stmt.setString(1, emailInput);
    //         stmt.setString(2, passwordInput);

    //         int rowsInserted = stmt.executeUpdate();
    //         if (rowsInserted > 0) {
    //             System.out.println("Log In Credentials inserted successfully!");
    //         } else {
    //             System.out.println("Error inserting Log In Credentials.");
    //         }

    //         stmt.close();
    //         c.close();

    //     } catch (SQLException e) {
    //         if (e instanceof SQLIntegrityConstraintViolationException) {
    //             System.out.println("Error inserting Log In Credentials.");
    //             // TODO: Handle the specific exception
    //             System.out.println("Foreign key constraint violation: " + e.getMessage());
    //         } else {
    //             Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
    //         }
    //     }
        
    //     try {
    //         //create logincredentials first THEN create user
    //         //TODO create user sign up GUI
    //         Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
    //         PreparedStatement stmt = null;

    //         stmt = c.prepareStatement("INSERT INTO Customer (first_name, last_name, email, phone_number, delivery_address) VALUES (?, ?, ?, ?, ?);");
    //         stmt.setString(1, first_name);
    //         stmt.setString(2, last_name);
    //         stmt.setString(3, emailInput);
    //         stmt.setString(4, phone_number);
    //         stmt.setString(5, delivery_address);

    //         int rowsInserted = 0;
    //         rowsInserted = stmt.executeUpdate();
    //         if (rowsInserted > 0) {
    //             System.out.println("Customer info inserted successfully!");
    //         } else {
    //             System.out.println("Error inserting Customer info.");
    //         }

    //         stmt.close();
    //         c.close();
    //     } catch (SQLException e) {
    //         if (e instanceof SQLIntegrityConstraintViolationException) {
    //             System.out.println("Error inserting Customer info.");
    //             // TODO: Handle the specific exception
    //             System.out.println("Foreign key constraint violation: " + e.getMessage());
    //         } else {
    //             Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
    //         }
        
    //     }
    // }

    
    



    // public void createSupplier(String emailInput, String passwordInput, String first_name, String last_name, String phone_number, String delivery_address) {
    //     try {
    //         //create logincredentials first THEN create user
    //         //TODO create user sign up GUI
    //         Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
    //         PreparedStatement stmt = c.prepareStatement("INSERT INTO LoginCredentials (email, password) VALUES (?, ?);");
    //         stmt.setString(1, emailInput);
    //         stmt.setString(2, passwordInput);

    //         int rowsInserted = stmt.executeUpdate();
    //         if (rowsInserted > 0) {
    //             System.out.println("Log In Credentials inserted successfully!");
    //         } else {
    //             System.out.println("Error inserting Log In Credentials.");
    //         }

    //         stmt.close();
    //         c.close();

    //     } catch (SQLException e) {
    //         if (e instanceof SQLIntegrityConstraintViolationException) {
    //             System.out.println("Error inserting Log In Credentials.");
    //             // TODO: Handle the specific exception
    //             System.out.println("Foreign key constraint violation: " + e.getMessage());
    //         } else {
    //             Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
    //         }
    //     }
        
    //     try {
    //         //create logincredentials first THEN create user
    //         //TODO create user sign up GUI
    //         Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
    //         PreparedStatement stmt = null;

    //         stmt = c.prepareStatement("INSERT INTO Customer (first_name, last_name, email, phone_number, delivery_address) VALUES (?, ?, ?, ?, ?);");
    //         stmt.setString(1, first_name);
    //         stmt.setString(2, last_name);
    //         stmt.setString(3, emailInput);
    //         stmt.setString(4, phone_number);
    //         stmt.setString(5, delivery_address);

    //         int rowsInserted = 0;
    //         rowsInserted = stmt.executeUpdate();
    //         if (rowsInserted > 0) {
    //             System.out.println("Customer info inserted successfully!");
    //         } else {
    //             System.out.println("Error inserting Customer info.");
    //         }

    //         stmt.close();
    //         c.close();
    //     } catch (SQLException e) {
    //         if (e instanceof SQLIntegrityConstraintViolationException) {
    //             System.out.println("Error inserting Customer info.");
    //             // TODO: Handle the specific exception
    //             System.out.println("Foreign key constraint violation: " + e.getMessage());
    //         } else {
    //             Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
    //         }
        
    //     }
    // }

    // public void readSupplier() {        
    //     try {
    //         Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
    //         java.sql.Statement queryStatement = c.createStatement();
    //         ResultSet rs = queryStatement.executeQuery("SELECT * FROM customer");
    //         while (rs.next()) {
    //             int customer_id = rs.getInt("customer_id");
    //             String first_name = rs.getString("first_name");
    //             String last_name = rs.getString("last_name");
    //             String email = rs.getString("email");
    //             String phone_number = rs.getString("phone_number");
    //             String delivery_address = rs.getString("delivery_address");
                
    //             System.out.println(customer_id + "\t" + 
    //                                first_name + "\t" + 
    //                                last_name  + "\t" + 
    //                                email + "\t\t" + 
    //                                phone_number  + "\t" + 
    //                                delivery_address);
    //         }
    //     } catch (SQLException e) {
    //             System.out.println("Error displaying Customer info.");
    //             Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
    //         }
    // }

    // public void updateSupplier(String specifiedAttribute, String valueToUpdate, int customer_id) {        
    //     try {
    //         Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
    //         PreparedStatement stmt = null;

    //         String sqlQueryStatement = "UPDATE customer SET " + specifiedAttribute + " = ?  WHERE customer_id = ?";

    //         stmt = c.prepareStatement(sqlQueryStatement);
            
    //         stmt.setString(1, valueToUpdate);
    //         stmt.setInt(2, customer_id);

    //         int rowsInserted = 0;
    //         rowsInserted = stmt.executeUpdate();
    //         if (rowsInserted > 0) {
    //             System.out.println("Customer info updated successfully!");
    //         } else {
    //             System.out.println("Error updating Customer info.");
    //         }

    //         stmt.close();
    //         c.close();
    //     } catch (SQLException e) {
    //         if (e instanceof SQLIntegrityConstraintViolationException) {
    //             System.out.println("Error updating Customer info.");
    //             // TODO: Handle the specific exception
    //             System.out.println(e.getMessage());
    //         } else {
    //             Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
    //         }
        
    //     }
    // }

    // public void deleteSupplier(int idToDelete) {
    //     try {
    //         //delete user first THEN logincredentials 
    //         String email = null;
    //         Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
    //         java.sql.Statement queryStatement = c.createStatement();

    //         String sqlQueryStatement = "SELECT email FROM customer WHERE customer_id = " + idToDelete + ";";
    //         ResultSet rs = queryStatement.executeQuery(sqlQueryStatement);
            
    //         while (rs.next()) {
    //             email = rs.getString("email");
    //         }            

    //         PreparedStatement stmt = c.prepareStatement("DELETE FROM Customer WHERE customer_id = ?;");
    //         stmt.setInt(1, idToDelete);
    //         int rowsDeleted = stmt.executeUpdate();
    //         if (rowsDeleted > 0) {
    //             System.out.println("Customer data deleted successfully!");
    //         } else {
    //             System.out.println("Error deleting Customer data.");
    //         }
            
    //         stmt = null;
    //         stmt = c.prepareStatement("DELETE FROM loginCredentials WHERE email = ?;");
    //         stmt.setString(1, email);

    //         rowsDeleted = 0;
    //         rowsDeleted = stmt.executeUpdate();
    //         if (rowsDeleted > 0) {
    //             System.out.println("Log In Credentials deleted successfully!");
    //         } else {
    //             System.out.println("Error deleting Log In Credentials.");
    //         }

    //         stmt.close();
    //         c.close();

    //     } catch (SQLException e) {
    //         if (e instanceof SQLIntegrityConstraintViolationException) {
    //             System.out.println("Error deleting Log In Credentials.");
    //             // TODO: Handle the specific exception
    //             System.out.println("Foreign key constraint violation: " + e.getMessage());
    //         } else {
    //             System.out.println("Error deleting Log In Credentials.");
    //             Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
    //         }
    //     }
    // }

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
