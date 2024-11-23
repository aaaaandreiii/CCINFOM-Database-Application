
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
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

    public List<List<Object>> findUserByName(String first_name, String last_name) {
        List<List<Object>> userInfo = new ArrayList<>();
        userInfo.add(new ArrayList<>());
        userInfo.add(new ArrayList<>());

        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();
            ResultSet rs = queryStatement.executeQuery("SELECT * FROM customer WHERE first_name = '" + first_name + "' AND last_name = '" + last_name + "';");
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

    public void deleteUser(int userID_ToDelete) {
        try {
            //DELETE in this specific order
            //LogInCreds, Customer, ShoppingCart, Wishlist, BuyerOrderInfo, BuyerOrderItem
            String email = null;
            int shoppingcart_id = -1;
            int buyer_order_information_id = -1;

            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();

            //shoppingcart_id for BuyerOrderInfo
            String sqlQueryStatement = "SELECT shoppingcart_id FROM ShoppingCart WHERE customer_id = " + userID_ToDelete + ";";
            ResultSet rs = queryStatement.executeQuery(sqlQueryStatement);
            while (rs.next()) {
                shoppingcart_id = rs.getInt("shoppingcart_id");
            }

            //buyer_order_information_id for BuyerOrderItem
            sqlQueryStatement = null;
            sqlQueryStatement = "SELECT buyer_order_information_id FROM BuyerOrderInfo WHERE shoppingcart_id = " + shoppingcart_id + ";";
            rs = null;
            rs = queryStatement.executeQuery(sqlQueryStatement);
            while (rs.next()) {
                buyer_order_information_id = rs.getInt("buyer_order_information_id");
            }

            //email for LogInCredentials
            sqlQueryStatement = null;
            sqlQueryStatement = "SELECT email FROM customer WHERE customer_id = " + userID_ToDelete + ";";
            rs = null;
            rs = queryStatement.executeQuery(sqlQueryStatement);
            while (rs.next()) {
                email = rs.getString("email");
            }

            //DELETE Log In Credentials///////////////////////////////////////////////////////////
            PreparedStatement stmt = c.prepareStatement("DELETE FROM loginCredentials WHERE email = ?;");
            stmt.setString(1, email);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {                                                                                                                                           
                System.out.println("Log In Credentials deleted successfully!");              
            } else {
                System.out.println("Error deleting Log In Credentials.");
            }

            //DELETE Customer///////////////////////////////////////////////////////////
            stmt = null;
            stmt = c.prepareStatement("DELETE FROM Customer WHERE customer_id = ?;");
            stmt.setInt(1, userID_ToDelete);
            rowsDeleted = 0;
            rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Customer data deleted successfully!");
            } else {
                System.out.println("Error deleting Customer data.");
            }

            //DELETE ShoppingCart///////////////////////////////////////////////////////////
            stmt = null;
            stmt = c.prepareStatement("DELETE FROM ShoppingCart WHERE customer_id = ?;");
            stmt.setInt(1, userID_ToDelete);
            rowsDeleted = 0;
            rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("ShoppingCart data deleted successfully!");
            } else {
                System.out.println("Error deleting ShoppingCart data.");
            }

            //DELETE Wishlist///////////////////////////////////////////////////////////
            stmt = null;
            stmt = c.prepareStatement("DELETE FROM Wishlist WHERE customer_id = ?;");
            stmt.setInt(1, userID_ToDelete);
            rowsDeleted = 0;
            rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Wishlist data deleted successfully!");
            } else {
                System.out.println("Error deleting Wishlist data.");
            }

            //DELETE BuyerOrderInfo///////////////////////////////////////////////////////////
            stmt = null;
            stmt = c.prepareStatement("DELETE FROM BuyerOrderInfo WHERE shoppingcart_id = ?;");
            stmt.setInt(1, shoppingcart_id);
            rowsDeleted = 0;
            rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Buyer Order Information data deleted successfully!");
            } else {
                System.out.println("Error deleting Buyer Order Information data.");
            }

            //DELETE BuyerOrderItem///////////////////////////////////////////////////////////
            stmt = null;
            stmt = c.prepareStatement("DELETE FROM BuyerOrderItem WHERE buyer_order_information_id = ?;");
            stmt.setInt(1, buyer_order_information_id);
            rowsDeleted = 0;
            rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Buyer Order Item data deleted successfully!");
            } else {
                System.out.println("Error deleting Buyer Order Item data.");
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

    public ArrayList<Object> findSupplierById(int supplier_id) {
        ArrayList<Object> userInfo = new ArrayList<>();
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();
            ResultSet rs = queryStatement.executeQuery("SELECT * FROM supplier WHERE supplier_id = " + supplier_id + ";");
            while (rs.next()) {
                userInfo.add(rs.getInt("supplier_id")); // int supplier_id = rs.getInt("supplier_id");
                userInfo.add(rs.getString("supplier_fname")); //String supplier_fname = rs.getString("supplier_fname");
                userInfo.add(rs.getString("supplier_lname")); //String supplier_lname = rs.getString("supplier_lname");
                userInfo.add(rs.getString("supplier_id")); //String email = rs.getString("email");
                userInfo.add(rs.getString("email")); //String phone = rs.getString("phone");
                userInfo.add(rs.getString("address")); //String address = rs.getString("address");
                userInfo.add(rs.getBigDecimal("supplier_rating")); //BigDecimal supplier_rating = rs.getBigDecimal("supplier_rating");
            }
            System.out.println("Supplier found by ID!");
            return userInfo;
            
        } catch (SQLException e) {
            System.out.println("Error displaying Supplier info.");
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

    public List<List<Object>> findSupplierByName(String supplier_fname, String supplier_lname) {
        List<List<Object>> userInfo = new ArrayList<>();
        userInfo.add(new ArrayList<>());
        userInfo.add(new ArrayList<>());

        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();
            ResultSet rs = queryStatement.executeQuery("SELECT * FROM Supplier WHERE supplier_fname = '" + supplier_fname + "' AND supplier_lname = '" + supplier_lname + "';");
            int i = 0;
            while (rs.next()) {
                userInfo.get(i).add(rs.getInt("supplier_id")); // int supplier_id = rs.getInt("supplier_id");
                userInfo.get(i).add(rs.getString("supplier_fname")); //String supplier_fname = rs.getString("supplier_fname");
                userInfo.get(i).add(rs.getString("supplier_lname")); //String supplier_lname = rs.getString("supplier_lname");
                userInfo.get(i).add(rs.getString("supplier_id")); //String email = rs.getString("email");
                userInfo.get(i).add(rs.getString("email")); //String phone = rs.getString("phone");
                userInfo.get(i).add(rs.getString("address")); //String address = rs.getString("address");
                userInfo.get(i).add(rs.getBigDecimal("supplier_rating")); //BigDecimal supplier_rating = rs.getBigDecimal("supplier_rating");
                i++;
            }

            System.out.println("Supplier found by name!");
            return userInfo;

        } catch (SQLException e) {
            System.out.println("Error finding supplier by name.");
            if (e instanceof SQLIntegrityConstraintViolationException) {
                // TODO: Handle the specific exception
                System.out.println(e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        }

        return userInfo;
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

                //idk what this is for so im commenting it -andrei
                findSupplierById(supplier_id);
                //nvm this is such a cool qual of life method call

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

    public List<List<Object>> readAllSuppliers() {
        List<List<Object>> userInfo = new ArrayList<>();
        userInfo.add(new ArrayList<>());
        userInfo.add(new ArrayList<>());
        
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();
            ResultSet rs = queryStatement.executeQuery("SELECT * FROM supplier");
            int i = 0;
            while (rs.next()) {
                userInfo.get(i).add(rs.getInt("supplier_id"));   //int supplier_id = rs.getInt("supplier_id");
                userInfo.get(i).add(rs.getString("supplier_fname"));   //String supplier_fname = rs.getString("supplier_fname");
                userInfo.get(i).add(rs.getString("supplier_lname"));   //String supplier_lname = rs.getString("supplier_lname");
                userInfo.get(i).add(rs.getString("email"));   //String email = rs.getString("email");
                userInfo.get(i).add(rs.getString("phone"));   //String phone = rs.getString("phone");
                userInfo.get(i).add(rs.getString("address"));   //String address = rs.getString("address");
                userInfo.get(i).add(rs.getBigDecimal("supplier_id"));   //BigDecimal supplier_rating = rs.getBigDecimal("supplier_rating");
                i++;
            }
            return userInfo;

        } catch (SQLException e) {
            System.out.println("Error displaying Supplier info.");
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            return null;
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

    public void deleteSupplier(int supplierID_dToDelete) {
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = c.prepareStatement("DELETE FROM supplier WHERE supplier_id = ?;");
            stmt.setInt(1, supplierID_dToDelete);
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
    
    public ArrayList<Object> findItemById(int item_id) {
        ArrayList<Object> userInfo = new ArrayList<>();
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();
            ResultSet rs = queryStatement.executeQuery("SELECT * FROM Item WHERE item_id = " + item_id + ";");
            while (rs.next()) {
                userInfo.add(rs.getInt("item_id"));
                userInfo.add(rs.getString("name"));
                userInfo.add(rs.getInt("manufacturer_id"));
                userInfo.add(rs.getBigDecimal("srp"));
                userInfo.add(rs.getBigDecimal("manu_price"));
                userInfo.add(rs.getString("description"));
            }
            System.out.println("Item found by ID!");
            return userInfo;
            
        } catch (SQLException e) {
            System.out.println("Error displaying Item info.");
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

    public List<List<Object>> findItemByName(String name) {
        List<List<Object>> userInfo = new ArrayList<>();
        userInfo.add(new ArrayList<>());
        userInfo.add(new ArrayList<>());

        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();
            ResultSet rs = queryStatement.executeQuery("SELECT * FROM Item WHERE name = '" + name + "';");
            int i = 0;
            while (rs.next()) {
                userInfo.get(i).add(rs.getInt("item_id"));
                userInfo.get(i).add(rs.getString("name"));
                userInfo.get(i).add(rs.getInt("manufacturer_id"));
                userInfo.get(i).add(rs.getBigDecimal("srp"));
                userInfo.get(i).add(rs.getBigDecimal("manu_price"));
                userInfo.get(i).add(rs.getString("description"));
                i++;
            }

            System.out.println("Item found by name!");
            return userInfo;

        } catch (SQLException e) {
            System.out.println("Error finding item by name.");
            if (e instanceof SQLIntegrityConstraintViolationException) {
                // TODO: Handle the specific exception
                System.out.println(e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        }

        return userInfo;
    }

    public void createItemEntity(String name, int manufacturer_id, BigDecimal srp, BigDecimal manu_price, String description) {      
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = c.prepareStatement("INSERT INTO Item (name, manufacturer_id, srp, manu_price, description) VALUES (?, ?, ?, ?, ?);");
            stmt.setString(1, name);
            stmt.setInt(2, manufacturer_id);
            stmt.setBigDecimal(3, srp);
            stmt.setBigDecimal(4, manu_price);
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
            System.out.println("Error inserting Item info.");
            if (e instanceof SQLIntegrityConstraintViolationException) {
                // TODO: Handle the specific exception
                System.out.println("Foreign key constraint violation: " + e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        
        }
    }

    public List<List<Object>> readItemEntity() {  
        List<List<Object>> userInfo = new ArrayList<>();
        userInfo.add(new ArrayList<>());
        userInfo.add(new ArrayList<>());
      
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();
            ResultSet rs = queryStatement.executeQuery("SELECT * FROM item");
            int i = 0;
            while (rs.next()) {
                userInfo.get(i).add(rs.getInt("item_id"));   //int item_id = rs.getInt("item_id");
                userInfo.get(i).add(rs.getInt("name"));   //String name = rs.getString("name");
                userInfo.get(i).add(rs.getInt("manufacturer_id"));   //int manufactturer_id = rs.getInt("manufacturer_id");
                userInfo.get(i).add(rs.getInt("srp"));   //BigDecimal srp = rs.getBigDecimal("srp");
                userInfo.get(i).add(rs.getInt("manu_price"));   //String manu_price = rs.getString("manu_price");
                userInfo.get(i).add(rs.getInt("description"));   //String description = rs.getString("description");
                i++;
            }
            return userInfo;

        } catch (SQLException e) {
            System.out.println("Error displaying Customer info.");
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

    public void updateItemEntity(String specifiedAttribute, String valueToUpdate, int item_id) {    
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            String sqlQueryStatement = "UPDATE item SET " + specifiedAttribute + " = ?  WHERE item_id = " + item_id +";";
            PreparedStatement stmt = c.prepareStatement(sqlQueryStatement);
            
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

    public void updateItemEntity(String specifiedAttribute, int valueToUpdate, int item_id) {    
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            String sqlQueryStatement = "UPDATE item SET " + specifiedAttribute + " = ?  WHERE item_id = " + item_id +";";
            PreparedStatement stmt = c.prepareStatement(sqlQueryStatement);
            
            stmt.setInt(1, valueToUpdate);
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

    public void updateItemEntity(String specifiedAttribute, BigDecimal valueToUpdate, int item_id) {    
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            String sqlQueryStatement = "UPDATE item SET " + specifiedAttribute + " = ?  WHERE item_id = " + item_id +";";
            PreparedStatement stmt = c.prepareStatement(sqlQueryStatement);
            
            stmt.setBigDecimal(1, valueToUpdate);
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

    public void deleteItemEntity(int itemID_ToDelete) {
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = c.prepareStatement("DELETE FROM Item WHERE item_id = ?;");
            stmt.setInt(1, itemID_ToDelete);
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
            
            PreparedStatement stmt = c.prepareStatement("INSERT INTO ShoppingCart (customer_id, item_id, quantity) VALUES (?, ?, ?);");
            stmt.setInt(1, customer_id);
            stmt.setInt(2, item_id);
            stmt.setInt(3, quantity);

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
            System.out.println("Error adding item to cart.");
            if (e instanceof SQLIntegrityConstraintViolationException) {
                // TODO: Handle the specific exception
                System.out.println("Foreign key constraint violation: " + e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    public List<List<Object>> readShoppingCart(int shoppingcart_id) {
        List<List<Object>> userInfo = new ArrayList<>();
        userInfo.add(new ArrayList<>());
        userInfo.add(new ArrayList<>());   
        
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();
            ResultSet rs = queryStatement.executeQuery("SELECT * FROM ShoppingCart WHERE shoppingcart_id = " + shoppingcart_id + ";");
            int i = 0;
            while (rs.next()) {
                userInfo.get(i).add(rs.getInt("shoppingcart_id"));
                userInfo.get(i).add(rs.getInt("customer_id")); 
                userInfo.get(i).add(rs.getInt("inventory_entry_id"));
                userInfo.get(i).add(rs.getInt("quantity"));
                i++;
            }
            return userInfo;
        } catch (SQLException e) {
            System.out.println("Error displaying Customer info.");
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

    public List<List<Object>> readAllShoppingCarts() {
        List<List<Object>> userInfo = new ArrayList<>();
        userInfo.add(new ArrayList<>());
        userInfo.add(new ArrayList<>());   
        
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();
            ResultSet rs = queryStatement.executeQuery("SELECT * FROM ShoppingCart;");
            int i = 0;
            while (rs.next()) {
                userInfo.get(i).add(rs.getInt("shoppingcart_id"));
                userInfo.get(i).add(rs.getInt("customer_id")); 
                userInfo.get(i).add(rs.getInt("inventory_entry_id"));
                userInfo.get(i).add(rs.getInt("quantity"));
                i++;
            }
            return userInfo;
        } catch (SQLException e) {
            System.out.println("Error displaying Customer info.");
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }
    
    public void updateShoppingCart(String specifiedAttribute, String valueToUpdate, int customer_id) {        
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

    public void updateShoppingCart(String specifiedAttribute, int valueToUpdate, int customer_id) {        
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = null;
    
            String sqlQueryStatement = "UPDATE customer SET " + specifiedAttribute + " = ?  WHERE customer_id = ?";
    
            stmt = c.prepareStatement(sqlQueryStatement);
                
            stmt.setInt(1, valueToUpdate);
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

    public void updateShoppingCart(String specifiedAttribute, BigDecimal valueToUpdate, int customer_id) {        
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = null;
    
            String sqlQueryStatement = "UPDATE customer SET " + specifiedAttribute + " = ?  WHERE customer_id = ?";
    
            stmt = c.prepareStatement(sqlQueryStatement);
                
            stmt.setBigDecimal(1, valueToUpdate);
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
    
    public void deleteShoppingCartEntry(int shoppingCartID_ToDelete) {
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = c.prepareStatement("DELETE FROM ShoppingCart WHERE shoppingcart_id = ?;");
            stmt.setInt(1, shoppingCartID_ToDelete);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Item removed from Shopping Cart successfully!");
            } else {
                System.out.println("Error removing Item from Shopping Cart.");
            }

            stmt.close();
            c.close();

        } catch (SQLException e) {
            System.out.println("Error removing Item from Shopping Cart.");
            if (e instanceof SQLIntegrityConstraintViolationException) {
                // TODO: Handle the specific exception
                System.out.println("Foreign key constraint violation: " + e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    public ArrayList<Object> findManufacturerById(int manufacturer_id) {
        ArrayList<Object> userInfo = new ArrayList<>();
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();
            ResultSet rs = queryStatement.executeQuery("SELECT * FROM Manufacturer WHERE manufacturer_id = " + manufacturer_id + ";");
            while (rs.next()) {
                userInfo.add(rs.getInt("manufacturer_id"));
                userInfo.add(rs.getString("manufacturer_name"));
            }
            System.out.println("Manufacturer found by ID!");
            return userInfo;
            
        } catch (SQLException e) {
            System.out.println("Error displaying Manufacturer info.");
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

    public List<List<Object>> findManufacturerByName(String manufacturer_name) {
        List<List<Object>> userInfo = new ArrayList<>();
        userInfo.add(new ArrayList<>());
        userInfo.add(new ArrayList<>());

        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();
            ResultSet rs = queryStatement.executeQuery("SELECT * FROM Manufacturer WHERE manufacturer_name = '" + manufacturer_name + "';");
            int i = 0;
            while (rs.next()) {
                userInfo.get(i).add(rs.getInt("manufacturer_id"));
                userInfo.get(i).add(rs.getString("manufacturer_name"));
                i++;
            }
            System.out.println("Manufacturer found by name!");
            return userInfo;

        } catch (SQLException e) {
            System.out.println("Error finding Manufacturer by name.");
            if (e instanceof SQLIntegrityConstraintViolationException) {
                // TODO: Handle the specific exception
                System.out.println(e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        }

        return userInfo;
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
            System.out.println("Error inserting Manufacturer Credentials.");
            if (e instanceof SQLIntegrityConstraintViolationException) {
                // TODO: Handle the specific exception
                System.out.println("Foreign key constraint violation: " + e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    public List<List<Object>> readAllManufacturers() {  
        List<List<Object>> userInfo = new ArrayList<>();
        userInfo.add(new ArrayList<>());
        userInfo.add(new ArrayList<>());
      
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();
            ResultSet rs = queryStatement.executeQuery("SELECT * FROM Manufacturer");
            int i = 0;
            while (rs.next()) {
                userInfo.get(i).add(rs.getInt("manufacturer_id"));   //int manufacturer_id = rs.getInt("manufacturer_id");
                userInfo.get(i).add(rs.getInt("manufaturer_name"));   //String manufacturer_name = rs.getString("manufaturer_name");
                userInfo.get(i).add(rs.getInt("address"));   //String address = rs.getString("address");
                i++;
            }
            return userInfo;

        } catch (SQLException e) {
            System.out.println("Error displaying Manufacturer info.");
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            return null;
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
            System.out.println("Error updating Manufacturer info.");
            if (e instanceof SQLIntegrityConstraintViolationException) {
                // TODO: Handle the specific exception
                System.out.println(e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
       
        }
    }

    public void deleteManufacturer(int manufactturer_id_ToDelete) {
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = c.prepareStatement("DELETE FROM Manufacturer WHERE manufacturer_id = ?;");
            stmt.setInt(1, manufactturer_id_ToDelete);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Manufacturer data deleted successfully!");
            } else {
                System.out.println("Error deleting Manufacturer data.");
            }

            stmt.close();
            c.close();

        } catch (SQLException e) {
            System.out.println("Error deleting Manufacturer data.");
            if (e instanceof SQLIntegrityConstraintViolationException) {
                // TODO: Handle the specific exception
                System.out.println("Foreign key constraint violation: " + e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    public ArrayList<Object> findInventoryItemById(int inventory_entry_id) {
        ArrayList<Object> inventoryInfo = new ArrayList<>();
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();
            ResultSet rs = queryStatement.executeQuery("SELECT * FROM Inventory WHERE inventory_entry_id = " + inventory_entry_id + ";");
            while (rs.next()) {
                inventoryInfo.add(rs.getInt("inventory_entry_id"));
                inventoryInfo.add(rs.getInt("item_id"));
                inventoryInfo.add(rs.getInt("supplier_id"));
                inventoryInfo.add(rs.getInt("quantity"));
                inventoryInfo.add(rs.getBigDecimal("price"));
            }
            System.out.println("Inventory item found by ID!");
            return inventoryInfo;
            
        } catch (SQLException e) {
            System.out.println("Error displaying Manufacturer info.");
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }
    
    public List<List<Object>> findInventoryBySupplierIDAndName(int supplier_id, String name) {
        List<List<Object>> userInfo = new ArrayList<>();
        userInfo.add(new ArrayList<>());
        userInfo.add(new ArrayList<>());

        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();
            String sqlQueryStatement = "SELECT inv.inventory_entry_id AS `Inventory Entry ID`, i.item_id AS `Item ID`, i.name AS `Item Name`, i.description AS `Description`, i.srp AS `Suggested Retail Price`, i.manu_price AS `Manufacturer Price`,inv.quantity AS `Quantity`, inv.price AS `Supplier Price`, s.supplier_id AS `Supplier ID`, CONCAT(s.supplier_fname, ' ', s.supplier_lname) AS `Supplier Name`, s.email AS `Supplier Email`, s.phone AS `Supplier Phone` FROM Inventory inv JOIN Item i ON inv.item_id = i.item_id JOIN Supplier s ON inv.supplier_id = s.supplier_id WHERE s.supplier_id = " + supplier_id + " AND i.name = '" + name + "';";
            ResultSet rs = queryStatement.executeQuery(sqlQueryStatement);
            int i = 0;
            while (rs.next()) {
                userInfo.get(i).add(rs.getInt("Inventory Entry ID"));
                userInfo.get(i).add(rs.getInt("Item ID"));
                userInfo.get(i).add(rs.getString("Item Name"));
                userInfo.get(i).add(rs.getString("Description"));
                userInfo.get(i).add(rs.getBigDecimal("Suggested Retail Price"));
                userInfo.get(i).add(rs.getBigDecimal("Manufacturer Price"));
                userInfo.get(i).add(rs.getInt("Quantity"));
                userInfo.get(i).add(rs.getBigDecimal("Supplier Price"));
                userInfo.get(i).add(rs.getInt("Supplier ID"));
                userInfo.get(i).add(rs.getString("Supplier Name"));
                userInfo.get(i).add(rs.getString("Supplier Email"));
                userInfo.get(i).add(rs.getString("Supplier Phone"));
                i++;
            }

            System.out.println("Inventory item/s found by supplier_id and item name!");
            return userInfo;

        } catch (SQLException e) {
            System.out.println("Error finding inventory item/s by supplier_id and item name.");
            if (e instanceof SQLIntegrityConstraintViolationException) {
                // TODO: Handle the specific exception
                System.out.println(e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        }

        return userInfo;
    }

    public void createInventory(int item_id, int supplier_id, int quantity, BigDecimal price) {
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = c.prepareStatement("INSERT INTO Inventory (item_id, supplier_id, quantity, price) VALUES (?, ?, ?, ?);");
            stmt.setInt(1, item_id);
            stmt.setInt(2, supplier_id);
            stmt.setInt(3, quantity);
            stmt.setBigDecimal(4, price);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Item inserted into Inventory successfully!");
            } else {
                System.out.println("Error inserting into Inventory.");
            }

            stmt.close();
            c.close();

        } catch (SQLException e) {
            System.out.println("Error inserting into Inventory.");
            if (e instanceof SQLIntegrityConstraintViolationException) {
                // TODO: Handle the specific exception
                System.out.println("Foreign key constraint violation: " + e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    public List<List<Object>> readInventory() {  
        List<List<Object>> userInfo = new ArrayList<>();
        userInfo.add(new ArrayList<>());
        userInfo.add(new ArrayList<>());      
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();
            ResultSet rs = queryStatement.executeQuery("SELECT * FROM Inventory");
            int i = 0;
            while (rs.next()) {
                userInfo.get(i).add(rs.getInt("inventory_entry_id"));   //int inventory_entry_id = rs.getInt("inventory_entry_id");
                userInfo.get(i).add(rs.getInt("item_id"));              //int item_id = rs.getInt("item_id");
                userInfo.get(i).add(rs.getInt("lsupplier_id"));         //int supplier_id = rs.getInt("lsupplier_id");
                userInfo.get(i).add(rs.getInt("quantity"));             //int quantity = rs.getInt("quantity");
                userInfo.get(i).add(rs.getInt("price"));                //BigDecimal price = rs.getBigDecimal("price");
                i++;
            }
            return userInfo;
        } catch (SQLException e) {
                System.out.println("Error displaying Customer info.");
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
                return null;
        }
    }

    public void updateInventory(String specifiedAttribute, BigDecimal valueToUpdate, int inventory_entry_id) {        
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            String sqlQueryStatement = "UPDATE Inventory SET " + specifiedAttribute + " = ?  WHERE inventory_entry_id = " + inventory_entry_id + ";";
            PreparedStatement stmt = c.prepareStatement(sqlQueryStatement);

            stmt.setBigDecimal(1, valueToUpdate);

            int rowsInserted = 0;
            rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Inventory info updated successfully!");
            } else {
                System.out.println("Error updating Inventory info.");
            }

            stmt.close();
            c.close();
        } catch (SQLException e) {
            System.out.println("Error updating Inventory info.");
            if (e instanceof SQLIntegrityConstraintViolationException) {
                // TODO: Handle the specific exception
                System.out.println(e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
       
        }
    }

    public void updateInventory(String specifiedAttribute, int valueToUpdate, int inventory_entry_id) {        
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            String sqlQueryStatement = "UPDATE Inventory SET " + specifiedAttribute + " = ?  WHERE inventory_entry_id = " + inventory_entry_id + ";";
            PreparedStatement stmt = c.prepareStatement(sqlQueryStatement);

            stmt.setInt(1, valueToUpdate);

            int rowsInserted = 0;
            rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Inventory info updated successfully!");
            } else {
                System.out.println("Error updating Inventory info.");
            }

            stmt.close();
            c.close();
        } catch (SQLException e) {
            System.out.println("Error updating Inventory info.");
            if (e instanceof SQLIntegrityConstraintViolationException) {
                // TODO: Handle the specific exception
                System.out.println(e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
       
        }
    }

    public void deleteInventory(int inventory_entry_id_ToDelete) {
        try {
            int inventory_entry_id = -1;
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();

            PreparedStatement stmt = c.prepareStatement("DELETE FROM Inventory WHERE inventory_entry_id = ?;");
            stmt.setInt(1, inventory_entry_id);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Inventory data deleted successfully!");
            } else {
                System.out.println("Error deleting Inventory data.");
            }

            stmt.close();
            c.close();

        } catch (SQLException e) {
            System.out.println("Error deleting Inventory data.");
            if (e instanceof SQLIntegrityConstraintViolationException) {
                // TODO: Handle the specific exception
                System.out.println("Foreign key constraint violation: " + e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    public void createWishlist(int customer_id, int inventory_entry_id) {
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = c.prepareStatement("INSERT INTO Wishlist (customer_id, inventory_entry_id) VALUES (?, ?);");
            stmt.setInt(1, customer_id);
            stmt.setInt(2, inventory_entry_id);
            
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Wishlist inserted successfully!");
            } else {
                System.out.println("Error inserting into Wishlist.");
            }

            stmt.close();
            c.close();

        } catch (SQLException e) {
            System.out.println("Error inserting into Wishlist.");
            if (e instanceof SQLIntegrityConstraintViolationException) {
                // TODO: Handle the specific exception
                System.out.println("Foreign key constraint violation: " + e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }


    public void readWishlist() {        
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();
            ResultSet rs = queryStatement.executeQuery("SELECT * FROM Wishlist");
            while (rs.next()) {
                int wishlist_id = rs.getInt("wishlist_id");
                int customer_id = rs.getInt("customer_id");
                int inventory_entry_id = rs.getInt("inventory_entry_id");
               
                System.out.println(wishlist_id + "\t" +
                                   customer_id + "\t" +
                                   inventory_entry_id);
            }
        } catch (SQLException e) {
                System.out.println("Error displaying Inventory info.");
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
    }


    public void updateWishlist(int wishlist_id, int customer_id, int inventory_entry_id) {        
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = null;


            String sqlQueryStatement = "UPDATE Wishlist SET " + specifiedAttribute + " = ?  WHERE wishlist_id = ?";


            stmt = c.prepareStatement(sqlQueryStatement);
           
            //IDK IF NEED ISAMA CUSTOMER
            stmt.setInt(2, customer_id);
            stmt.setInt(3, inventory_entry_id);


            int rowsInserted = 0;
            rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Wishlist updated successfully!");
            } else {
                System.out.println("Error updating Wishlist.");
            }


            stmt.close();
            c.close();
        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println("Error updating Wishlist info.");
                // TODO: Handle the specific exception
                System.out.println(e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
       
        }
    }


    public void deleteWishlist(int wishlist_id) {
        try {
            //delete user first THEN logincredentials
            String email = null;
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();


            String sqlQueryStatement = "SELECT wishlist_id FROM Wishlist WHERE customer_id = " + idToDelete + ";";
            ResultSet rs = queryStatement.executeQuery(sqlQueryStatement);
           
            while (rs.next()) {
                wishlist_id = rs.getString("wishlist_id");
            }            

            
            PreparedStatement stmt = c.prepareStatement("DELETE FROM Wishlist WHERE wishlist_id = ?;");
            stmt.setInt(1, wishlist_id);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Wishlist deleted successfully!");
            } else {
                System.out.println("Error deleting Wishlist.");
            }
           
            /*
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
            */

            stmt.close();
            c.close();


        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println("Error deleting Wishlist.");
                // TODO: Handle the specific exception
                System.out.println("Foreign key constraint violation: " + e.getMessage());
            } else {
                System.out.println("Error deleting Log In Credentials.");
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }


//BuyerOrderInfo CRUD
public void createBuyerOrderInfo(int buyer_order_information_id, int shoppingcart_id, LocalDate order_date, BigDecimal total_amount, String status ) {
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();


            String sqlQueryStatement = "SELECT email FROM customer;";
            ResultSet rs = queryStatement.executeQuery(sqlQueryStatement);
                         
            PreparedStatement stmt = c.prepareStatement("INSERT INTO BuyerOrderInfo (shoppingcart_id, order_date, total_amount, status) VALUES (?, ?, ?, ?);");
                stmt.setInt(1,shoppingcart_id);
                stmt.setLocalDate(2, order_date);
                stmt.setBigDecimal(3, total_amount);
                stmt.setString(4, status);




                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Buyer Order Information inserted successfully!");
                } else {
                    System.out.println("Error inserting Buyer Order Information.");
                }


                stmt.close();
                c.close();

                //DIDNT CHANGE
                try {
                    //create logincredentials first THEN create user
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


    public void readBuyerOrderInfo() {        
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();
            ResultSet rs = queryStatement.executeQuery("SELECT * FROM BuyerOrderInfo");
            while (rs.next()) {
                int buyer_order_information_id = rs.getInt("buyer_order_information_id");
                int shoppingcart_id = rs.getInt("shoppingcart_id");
                LocalDate order_date = rs.getLocalDate("order_date");
                BigDecimal total_amount = rs.getBigDecimal("total_amount");
                String status = rs.getString("status");
                
               
                System.out.println(buyer_order_information_id + "\t" +
                                   shoppingcart_id + "\t" +
                                   order_date  + "\t" +
                                   total_amount + "\t" +
                                   status);
            }
        } catch (SQLException e) {
                System.out.println("Error displaying Buyer Order Information.");
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
    }


    public void updateBuyerOrderInfo(int buyer_order_information_id, int shoppingcart_id, LocalDate order_date, BigDecimal total_amount, String status) {        
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = null;


            String sqlQueryStatement = "UPDATE BuyerOrderInfo SET " + specifiedAttribute + " = ?  WHERE buyer_order_information_id = ?";


            stmt = c.prepareStatement(sqlQueryStatement);
           
            stmt.setInt(2, shoppingcart_id);
            stmt.setLocalDate(3, order_date);
            stmt.setBigDecmial(4, total_amount);
            stmt.setString(5, status);

            int rowsInserted = 0;
            rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Buyer Order Information updated successfully!");
            } else {
                System.out.println("Error updating Buyer Order Information.");
            }


            stmt.close();
            c.close();
        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println("Error updating Buyer Order Information.");
                // TODO: Handle the specific exception
                System.out.println(e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
       
        }
    }


    public void deleteBuyerOrderInformation(int buyer_order_information_id) {
        try {
            //delete user first THEN logincredentials
            String email = null;
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();


            String sqlQueryStatement = "SELECT buyer_order_information_id FROM BuyerOrderInfo WHERE customer_id = " + idToDelete + ";";
            ResultSet rs = queryStatement.executeQuery(sqlQueryStatement);
           
            while (rs.next()) {
                buyer_order_information_id = rs.getString("buyer_order_information_id");
            }            


            PreparedStatement stmt = c.prepareStatement("DELETE FROM BuyerOrderInfo WHERE buyer_order_information_id = ?;");
            stmt.setInt(1, buyer_order_information_id);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Buyer Order Information deleted successfully!");
            } else {
                System.out.println("Error deleting Buyer Order Information.");
            }
           
           /*
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

            */
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

//BuyerOrderItem CRUD
public void createBuyerOrderItem(int buyer_order_item_id, int buyer_order_information_id, BigDecimal price_at_order) {
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();


            String sqlQueryStatement = "SELECT buyer_order_item_id FROM BuyerOrderItem;";
            ResultSet rs = queryStatement.executeQuery(sqlQueryStatement);
                         
            PreparedStatement stmt = c.prepareStatement("INSERT INTO BuyerOrderItem (buyer_order_information_id, price_at_order) VALUES (?, ?);");
                stmt.setInt(1, buyer_order_information_id);
                stmt.setBigDecimal(2, price_at_order);





                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Buyer Order Item inserted successfully!");
                } else {
                    System.out.println("Error inserting Buyer Order Item.");
                }


                stmt.close();
                c.close();

                // DIDNT CHANGE
                try {
                    //create logincredentials first THEN create user
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


    public void readBuyerOrderItem() {        
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();
            ResultSet rs = queryStatement.executeQuery("SELECT * FROM BuyerOrderItem");
            while (rs.next()) {
                int buyer_order_item_id = rs.getInt("buyer_order_item_id");
                int buyer_order_information_id = rs.getInt("buyer_order_information_id");
                BigDecimal price_at_order = rs.getBigDecimal("price_at_order");

               
                System.out.println(buyer_order_item_id + "\t" +
                                   buyer_order_information_id + "\t" +
                                   price_at_order);
            }
        } catch (SQLException e) {
                System.out.println("Error displaying Buyer Order Item.");
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
    }


    public void updateBuyerOrderItem(int buyer_order_item_id, int buyer_order_information_id, BigDecimal price_at_order) {        
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = null;


            String sqlQueryStatement = "UPDATE BuyerOrderItem SET " + specifiedAttribute + " = ?  WHERE buyer_order_item_id = ?";


            stmt = c.prepareStatement(sqlQueryStatement);
           
            stmt.setInt(2, buyer_order_information_id);
            stmt.setBigDecimal(3, price_at_order);


            int rowsInserted = 0;
            rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Buyer Order Item updated successfully!");
            } else {
                System.out.println("Error updating Buyer Order Item.");
            }


            stmt.close();
            c.close();
        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println("Error updating Buyer Order Item.");
                // TODO: Handle the specific exception
                System.out.println(e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
       
        }
    }


    public void deleteBuyerOrderItem(int buyer_order_item_id) {
        try {
            //delete user first THEN logincredentials
            String email = null;
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();


            String sqlQueryStatement = "SELECT buyer_order_item_id FROM BuyerOrderItem = " + buyer_order_item_id + ";";
            ResultSet rs = queryStatement.executeQuery(sqlQueryStatement);
           
            while (rs.next()) {
                buyer_order_item_id = rs.getString("buyer_order_item_id");
            }            


            PreparedStatement stmt = c.prepareStatement("DELETE FROM BuyerOrderItem WHERE buyer_order_item_id = ?;");
            stmt.setInt(1, buyer_order_item_id);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Buyer Order Item deleted successfully!");
            } else {
                System.out.println("Error deleting Buyer Order Item.");
            }
            
            /*
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

            */
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

//SupplierOrderInfo
public void createSupplierOrderInfo(int supplier_order_information_id, int supplier_id, LocalDate order_date, int manufacturer_id, BigDecmimal total_amount) {
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();


            String sqlQueryStatement = "SELECT supplier_order_information_id FROM SupplierOrderInfo;";
            ResultSet rs = queryStatement.executeQuery(sqlQueryStatement);
                         
            PreparedStatement stmt = c.prepareStatement("INSERT INTO SuplierOrderInfo (supplier_id, order_date, manufacturer_id, total_amount) VALUES (?, ?, ?, ?);");
                stmt.setInt(1, emailInput);
                stmt.setLocalDate(2, passwordInput);
                stmt.setInt(3, manufacturer_id);
                stmt.setBigDecimal (4, total_amount);




                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Supplier Order Information inserted successfully!");
                } else {
                    System.out.println("Error inserting Supplier Order Information.");
                }


                stmt.close();
                c.close();


                try {
                    //create logincredentials first THEN create user
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


    public void readSupplierOrderInfo() {        
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();
            ResultSet rs = queryStatement.executeQuery("SELECT * FROM SuplierOrderInfo");
            while (rs.next()) {
                int supplier_order_information_id = rs.getInt("supplier_order_information_id");
                int supplier_id = rs.getInt("supplier_id");
                LocalDate order_date = rs.getLocalDate("order_date");
                int manufacturer_id = rs.getInt("manufacturer_id");
                BigDecimal total_amount = rs.getBigDecimal("total_amount");
               
                System.out.println(supplier_order_information_id + "\t" +
                                   supplier_id + "\t" +
                                   order_date  + "\t" +
                                   manufacturer_id + "\t" +
                                   total_amount);
            }
        } catch (SQLException e) {
                System.out.println("Error displaying Supplier Order Information.");
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
    }


    public void updateSupplierOrderInfo(int supplier_order_information_id, int supplier_id, LocalDate order_date, int manufacturer_id, BigDecmimal total_amount) {        
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = null;


            String sqlQueryStatement = "UPDATE SupplierOrderInfo SET " + specifiedAttribute + " = ?  WHERE supplier_order_information = ?";


            stmt = c.prepareStatement(sqlQueryStatement);
           
            stmt.setInt(2, supplier_id);
            stmt.setLocalDate(3, order_date);
            stmt.setInt(4, manufacturer_id);
            stmt.setBigDecimal(5, total_amount);

            int rowsInserted = 0;
            rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Supplier Order Information updated successfully!");
            } else {
                System.out.println("Error updating Supplier Order Information.");
            }


            stmt.close();
            c.close();
        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println("Error updating Supplier Order Information.");
                // TODO: Handle the specific exception
                System.out.println(e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
       
        }
    }


    public void deleteUser(int supplier_order_information_id) {
        try {
            //delete user first THEN logincredentials
            String email = null;
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();


            String sqlQueryStatement = "SELECT supplier_order_information_id FROM SupplierOrderInfo = " + SupplierOrderInfo + ";";
            ResultSet rs = queryStatement.executeQuery(sqlQueryStatement);
           
            while (rs.next()) {
                supplier_order_information_id = rs.getString("supplier_order_information_id");
            }            


            PreparedStatement stmt = c.prepareStatement("DELETE FROM SupplierOrderInfo WHERE supplier_order_information_id = ?;");
            stmt.setInt(1, supplier_order_information_id);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Supplier Order Information deleted successfully!");
            } else {
                System.out.println("Error deleting Supplier Order Information.");
            }
           
            /*
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
            */

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


//SupplierOrderItem CRUD
public void createSupplierOrderItem(int supplier_order_item_id, int supplier_order_information_id, int item_id, int quantity, BigDecimal price_at_order) {
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();


            String sqlQueryStatement = "SELECT supplier_order_item_id FROM SupplierOrderItem;";
            ResultSet rs = queryStatement.executeQuery(sqlQueryStatement);
                         
            PreparedStatement stmt = c.prepareStatement("INSERT INTO SuplierOrderItem (supplier_order_information_id, item_id, quantity, price_at_order) VALUES (?, ?, ?, ?);");
                stmt.setInt(1, supplier_order_information_id);
                stmt.setInt(2, item_id);
                stmt.setInt(3, quantity);
                stmt.setBigDecimal (4, price_at_order);




                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Supplier Order Item inserted successfully!");
                } else {
                    System.out.println("Error inserting Supplier Order Item.");
                }


                stmt.close();
                c.close();


                try {
                    //create logincredentials first THEN create user
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


    public void readSupplierOrderItem() {        
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();
            ResultSet rs = queryStatement.executeQuery("SELECT * FROM SuplierOrderItem");
            while (rs.next()) {
                int supplier_order_item_id = rs.getInt("supplier_order_item_id");
                int supplier_order_information_id = rs.getInt("supplier_order_information_id");
                int item_id = rs.getInt("item_id");
                int quantity = rs.getInt("quantity");
                BigDecimal price_at_order = rs.getBigDecimal("price_at_order");
               
                System.out.println(supplier_order_item_id + "\t" +
                                   supplier_order_information_id + "\t" +
                                   item_id + "\t" +
                                   quantity  + "\t" +
                                   price_at_order);
            }
        } catch (SQLException e) {
                System.out.println("Error displaying Supplier Order Item.");
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
    }


    public void updateSuplierOrderItem(int supplier_order_item_id, int supplier_order_information_id, int item_id, int quantity, BigDecimal price_at_order) {        
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = null;


            String sqlQueryStatement = "UPDATE SupplierOrderItem SET " + specifiedAttribute + " = ?  WHERE supplier_order_item_id = ?";


            stmt = c.prepareStatement(sqlQueryStatement);
           
            stmt.setInt(2, supplier_order_information_id);
            stmt.setInt(3, item_id);
            stmt.setInt(4, quantity);
            stmt.setBigDecimal(5, price_at_order);

            int rowsInserted = 0;
            rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Supplier Order Item updated successfully!");
            } else {
                System.out.println("Error updating Supplier Order Item.");
            }


            stmt.close();
            c.close();
        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println("Error updating Supplier Order Item.");
                // TODO: Handle the specific exception
                System.out.println(e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
       
        }
    }


    public void deleteUser(int supplier_order_item_id) {
        try {
            //delete user first THEN logincredentials
            String email = null;
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();


            String sqlQueryStatement = "SELECT supplier_order_item_id FROM SupplierOrderItem = " + supplier_order_item_id + ";";
            ResultSet rs = queryStatement.executeQuery(sqlQueryStatement);
           
            while (rs.next()) {
                supplier_order_item_id = rs.getString("supplier_order_item_id");
            }            


            PreparedStatement stmt = c.prepareStatement("DELETE FROM SupplierOrderItem WHERE supplier_order_item_id = ?;");
            stmt.setInt(1, supplier_order_item_id);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Supplier Order Item deleted successfully!");
            } else {
                System.out.println("Error deleting Supplier Order Item.");
            }
           
            /*
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
            */

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

//Buyer Order Payment
public void createBuyerOrderPayment(int payment_id, int buyer_order_information_id, LocalDate payment_date, String payment_mode, String payment_status) {
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();


            String sqlQueryStatement = "SELECT payment_id  FROM BuyerOrderPayment;";
            ResultSet rs = queryStatement.executeQuery(sqlQueryStatement);
                         
            PreparedStatement stmt = c.prepareStatement("INSERT INTO BuyerOrderPayment (buyer_order_information_id, payment_date, payment_mode, payment_status) VALUES (?, ?, ?, ?);");
                stmt.setInt(1, buyer_order_information_id);
                stmt.setLocalDate(2, payment_date);
                stmt.setString(3, payment_mode);
                stmt.setString(4, payment_status);



                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("BuyerOrderPayment inserted successfully!");
                } else {
                    System.out.println("Error inserting BuyerOrderPayment.");
                }


                stmt.close();
                c.close();


                try {
                    //create logincredentials first THEN create user
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


    public void readBuyerOrderPayment() {        
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();
            ResultSet rs = queryStatement.executeQuery("SELECT * FROM BuyerOrderPayment");
            while (rs.next()) {
                int payment_id = rs.getInt("payment_id");
                int buyer_order_information_id = rs.getInt("buyer_order_information_id");
                LocalDate payment_date = rs.getLocalDate("payment_date");
                String payment_mode = rs.getString("payment_mode");
                String payment_status = rs.getString("payment_status");
               
                System.out.println(payment_id + "\t" +
                                   buyer_order_information_id + "\t" +
                                   payment_date  + "\t" +
                                   payment_mode + "\t" +
                                   payment_status);
            }
        } catch (SQLException e) {
                System.out.println("Error displaying Buyer Order Payment.");
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
    }


    public void updateBuyerOrderPayment(int payment_id, int buyer_order_information_id, LocalDate payment_date, String payment_mode, String payment_status) {        
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = null;


            String sqlQueryStatement = "UPDATE BuyerOrderPayment SET " + specifiedAttribute + " = ?  WHERE payment_id = ?";


            stmt = c.prepareStatement(sqlQueryStatement);
           
            stmt.setInt(2, buyer_order_information_id);
            stmt.setLocalDate(3, payment_date);
            stmt.setString(4, payment_mode);
            stmt.setString(5, payment_status)


            int rowsInserted = 0;
            rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Buyer Order Payment updated successfully!");
            } else {
                System.out.println("Error updating Buyer Order Payment.");
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


    public void deleteBuyerOrderPayment(int payment_id) {
        try {
            //delete user first THEN logincredentials
            String email = null;
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();


            String sqlQueryStatement = "SELECT payment_id FROM BuyerOrderPayment = " + payment_id + ";";
            ResultSet rs = queryStatement.executeQuery(sqlQueryStatement);
           
            while (rs.next()) {
                payment_id = rs.getString("payment_id");
            }            


            PreparedStatement stmt = c.prepareStatement("DELETE FROM BuyerOrderPayment WHERE payment_id = ?;");
            stmt.setInt(1, payment_id);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Buyer Order Payment deleted successfully!");
            } else {
                System.out.println("Error deleting Buyer Order Payment.");
            }
           
            /*
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
        */

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

//SupplierOrderPayment CRUD
public void createSupplierOrderPayment(int payment_id, int supplier_order_information_id, LocalDate payment_date, String payment_mode, String payment_status) {
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();


            String sqlQueryStatement = "SELECT payment_id  FROM SupplierOrderPayment;";
            ResultSet rs = queryStatement.executeQuery(sqlQueryStatement);
                         
            PreparedStatement stmt = c.prepareStatement("INSERT INTO SupplierOrderPayment (supplier_order_information_id, payment_date, payment_mode, payment_status) VALUES (?, ?, ?, ?);");
                stmt.setInt(1, supplier_order_information_id);
                stmt.setLocalDate(2, payment_date);
                stmt.setString(3, payment_mode);
                stmt.setString(4, payment_status);



                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Supplier Order Payment inserted successfully!");
                } else {
                    System.out.println("Error inserting Supplier Order Payment.");
                }


                stmt.close();
                c.close();


                try {
                    //create logincredentials first THEN create user
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


    public void readSupplierOrderPayment() {        
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();
            ResultSet rs = queryStatement.executeQuery("SELECT * FROM SupplierOrderPayment");
            while (rs.next()) {
                int payment_id = rs.getInt("payment_id");
                int buyer_order_information_id = rs.getInt("supplier_order_information_id");
                LocalDate payment_date = rs.getLocalDate("payment_date");
                String payment_mode = rs.getString("payment_mode");
                String payment_status = rs.getString("payment_status");
               
                System.out.println(payment_id + "\t" +
                                   supplier_order_information_id + "\t" +
                                   payment_date  + "\t" +
                                   payment_mode + "\t" +
                                   payment_status);
            }
        } catch (SQLException e) {
                System.out.println("Error displaying Buyer Order Payment.");
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
    }


    public void updateSupplierOrderPayment(int payment_id, int supplier_order_information_id, LocalDate payment_date, String payment_mode, String payment_status) {        
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = null;


            String sqlQueryStatement = "UPDATE SupplierOrderPayment SET " + specifiedAttribute + " = ?  WHERE payment_id = ?";


            stmt = c.prepareStatement(sqlQueryStatement);
           
            stmt.setInt(2, supplier_order_information_id);
            stmt.setLocalDate(3, payment_date);
            stmt.setString(4, payment_mode);
            stmt.setString(5, payment_status)


            int rowsInserted = 0;
            rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Supplier Order Payment updated successfully!");
            } else {
                System.out.println("Error updating Supplier Order Payment.");
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


    public void deleteSupplierOrderPayment(int payment_id) {
        try {
            //delete user first THEN logincredentials
            String email = null;
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();


            String sqlQueryStatement = "SELECT payment_id FROM SupplierOrderPayment = " + payment_id + ";";
            ResultSet rs = queryStatement.executeQuery(sqlQueryStatement);
           
            while (rs.next()) {
                payment_id = rs.getString("payment_id");
            }            


            PreparedStatement stmt = c.prepareStatement("DELETE FROM BuyerOrderPayment WHERE payment_id = ?;");
            stmt.setInt(1, payment_id);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Buyer Order Payment deleted successfully!");
            } else {
                System.out.println("Error deleting Buyer Order Payment.");
            }
           
            /*
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
        */

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







    public List<List<Object>> productRecordManagement() {
        List<List<Object>> productRecord = new ArrayList<>();
        productRecord.add(new ArrayList<>());
        productRecord.add(new ArrayList<>());

        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();

            String sqlQueryStatement = 
            "SELECT " +
                "CONCAT(s.supplier_fname, ' ', s.supplier_lname) AS supplier_name, " +
                "s.email AS supplier_email," +
                "s.phone AS supplier_phone, " +
                s.address AS supplier_address,
                s.supplier_rating,
                i.name AS item_name,
                i.description AS item_description
            FROM 
                Supplier s
            LEFT JOIN 
                Inventory inv ON s.supplier_id = inv.supplier_id
            LEFT JOIN 
                Item i ON inv.item_id = i.item_id
            ORDER BY 
                s.supplier_id, i.item_id;
                
            -- Product Record Management
            SELECT 
                CONCAT(s.supplier_fname, ' ', s.supplier_lname) AS supplier_name,
                i.name AS item_name,
                i.description AS item_description,
                m.manufacturer_name AS manufacturer_name,
                m.address AS manufacturer_address,
                inv.quantity AS inventory_quantity,
                i.manu_price AS manufacturer_price,
                inv.price AS supplier_price,
                IFNULL(w.wishlist_count, 0) AS wishlist_count,
                IFNULL(sc.cart_count, 0) AS cart_count,
                IFNULL(o.order_count, 0) AS order_count
            FROM 
                Supplier s
            LEFT JOIN 
                Inventory inv ON s.supplier_id = inv.supplier_id
            LEFT JOIN 
                Item i ON inv.item_id = i.item_id
            LEFT JOIN 
                Manufacturer m ON i.manufacturer_id = m.manufacturer_id
            LEFT JOIN 
                (
                    SELECT 
                        inventory_entry_id,
                        COUNT(DISTINCT customer_id) AS wishlist_count
                    FROM 
                        Wishlist
                    GROUP BY 
                        inventory_entry_id
                ) w ON inv.inventory_entry_id = w.inventory_entry_id
            LEFT JOIN 
                (
                    SELECT 
                        inventory_entry_id,
                        COUNT(DISTINCT customer_id) AS cart_count
                    FROM 
                        ShoppingCart
                    GROUP BY 
                        inventory_entry_id
                ) sc ON inv.inventory_entry_id = sc.inventory_entry_id
            LEFT JOIN 
                (
                    SELECT 
                        inv.inventory_entry_id,
                        COUNT(DISTINCT boi.buyer_order_information_id) AS order_count
                    FROM 
                        BuyerOrderInfo boi
                    INNER JOIN 
                        ShoppingCart sc ON boi.shoppingcart_id = sc.shoppingcart_id
                    INNER JOIN 
                        Inventory inv ON sc.inventory_entry_id = inv.inventory_entry_id
                    GROUP BY 
                        inv.inventory_entry_id
                ) o ON inv.inventory_entry_id = o.inventory_entry_id
            ORDER BY 
                "s.supplier_id, i.item_id;";

            ResultSet rs = queryStatement.executeQuery(sqlQueryStatement);
            int i = 0;
            while (rs.next()) {
                supplierRecord.get(i).add(rs.getInt("supplier_id"));
                supplierRecord.get(i).add(rs.getString("supplier_name"));
                supplierRecord.get(i).add(rs.getString("supplier_email"));
                supplierRecord.get(i).add(rs.getString("supplier_phone"));
                supplierRecord.get(i).add(rs.getInt("item_id"));
                supplierRecord.get(i).add(rs.getString("item_name"));
                supplierRecord.get(i).add(rs.getString("item_description"));
                supplierRecord.get(i).add(rs.getInt("inventory_quantity"));
                supplierRecord.get(i).add(rs.getBigDecimal("inventory_price"));
                i++;
            }
            return productRecord;
        } catch (SQLException e) {
            System.out.println("Error getting Supplier Record Management.\n" + e.getMessage());
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }      
    }






    public List<List<Object>> supplierRecordManagement() {
        List<List<Object>> supplierRecord = new ArrayList<>();
        supplierRecord.add(new ArrayList<>());
        supplierRecord.add(new ArrayList<>());

        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();

            String sqlQueryStatement = 
                "SELECT " +
                    "s.supplier_id, " +
                    "CONCAT(s.supplier_fname, ' ', s.supplier_lname) AS supplier_name, " +
                    "s.email AS supplier_email, " + 
                    "s.phone AS supplier_phone, " +
                    "i.item_id, " +
                    "i.name AS item_name, " +
                    "i.description AS item_description, " +
                    "inv.quantity AS inventory_quantity, " +
                    "inv.price AS inventory_price" +
                "FROM " +
                    "Supplier s " +
                "LEFT JOIN " +
                    "Inventory inv ON s.supplier_id = inv.supplier_id " +
                "LEFT JOIN " + 
                    "Item i ON inv.item_id = i.item_id " + 
                "ORDER BY " +
                    "s.supplier_id, i.item_id;";

            ResultSet rs = queryStatement.executeQuery(sqlQueryStatement);
            int i = 0;
            while (rs.next()) {
                supplierRecord.get(i).add(rs.getInt("supplier_id"));
                supplierRecord.get(i).add(rs.getString("supplier_name"));
                supplierRecord.get(i).add(rs.getString("supplier_email"));
                supplierRecord.get(i).add(rs.getString("supplier_phone"));
                supplierRecord.get(i).add(rs.getInt("item_id"));
                supplierRecord.get(i).add(rs.getString("item_name"));
                supplierRecord.get(i).add(rs.getString("item_description"));
                supplierRecord.get(i).add(rs.getInt("inventory_quantity"));
                supplierRecord.get(i).add(rs.getBigDecimal("inventory_price"));
                i++;
            }
            return supplierRecord;
        } catch (SQLException e) {
            System.out.println("Error getting Supplier Record Management.\n" + e.getMessage());
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            return null;
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
