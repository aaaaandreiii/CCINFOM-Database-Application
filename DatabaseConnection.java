
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

    public void readUser() {        
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();
            ResultSet rs = queryStatement.executeQuery("SELECT * FROM customer");
            while (rs.next()) {
                int customer_id = rs.getInt("customer_id");
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






    // public void createOrder(String emailInput, String passwordInput, String first_name, String last_name, String phone_number, String delivery_address) {
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

    // public void readOrder() {        
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

    // public void updateOrder(String specifiedAttribute, String valueToUpdate, int customer_id) {        
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

    // public void deleteOrder(int idToDelete) {
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
    //         System.out.println("4");
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



    
    // public void createManufacturer(String emailInput, String passwordInput, String first_name, String last_name, String phone_number, String delivery_address) {
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

    // public void readManufacturer() {        
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

    // public void updateManufacturer(String specifiedAttribute, String valueToUpdate, int customer_id) {        
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

    // public void deleteManufacturer(int idToDelete) {
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
    //         System.out.println("4");
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
    //         System.out.println("4");
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
