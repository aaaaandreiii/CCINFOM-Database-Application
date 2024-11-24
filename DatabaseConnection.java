
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
                    System.out.println("Foreign key constraint violation: " + e.getMessage());
                } else {
                    Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
                }
            }
            
        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println("Error inserting Log In Credentials.");
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
                System.out.println(e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        }

        return userInfo;
    }

    public ArrayList<String> findSupplierAddresses() {
        ArrayList<String> addresses = new ArrayList<>();
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();
            ResultSet rs = queryStatement.executeQuery("SELECT address FROM supplier;");
            while (rs.next()) {
                addresses.add(rs.getString("address"));
            }
            System.out.println("Supplier addresses found!");
            return addresses;
            
        } catch (SQLException e) {
            System.out.println("Error getting Supplier addresses.");
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            return null;
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

                //idk what this is for so im commenting it -andrei
                findSupplierById(supplier_id);
                //nvm this is such a cool qual of life method call

                stmt.close();
                c.close();

        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println("Error inserting Supplier info.");
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
                System.out.println("Foreign key constraint violation: " + e.getMessage());
            } else {
                System.out.println("Error deleting Log In Credentials.");
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    public ArrayList<Object> findShoppingCartItemByID (int shoppingcart_id) {
        ArrayList<Object> shoppingCartItemInfo = new ArrayList<>();
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();
            String sqlQueryStatement = "SELECT inv.inventory_entry_id AS `Inventory Entry ID`, i.item_id AS `Item ID`, i.name AS `Item Name`, i.description AS `Description`, i.srp AS `Suggested Retail Price`, i.manu_price AS `Manufacturer Price`,inv.quantity AS `Quantity`, inv.price AS `Supplier Price`, s.supplier_id AS `Supplier ID`, CONCAT(s.supplier_fname, ' ', s.supplier_lname) AS `Supplier Name`, s.email AS `Supplier Email`, s.phone AS `Supplier Phone` FROM Inventory inv JOIN Item i ON inv.item_id = i.item_id JOIN Supplier s ON inv.supplier_id = s.supplier_id WHERE shoppingcart_id = " + shoppingcart_id + ";";
            ResultSet rs = queryStatement.executeQuery(sqlQueryStatement);
            while (rs.next()) {
                shoppingCartItemInfo.add(rs.getInt("Inventory Entry ID"));
                shoppingCartItemInfo.add(rs.getInt("Item ID"));
                shoppingCartItemInfo.add(rs.getString("Item Name"));
                shoppingCartItemInfo.add(rs.getString("Description"));
                shoppingCartItemInfo.add(rs.getBigDecimal("Suggested Retail Price"));
                shoppingCartItemInfo.add(rs.getBigDecimal("Manufacturer Price"));
                shoppingCartItemInfo.add(rs.getInt("Quantity"));
                shoppingCartItemInfo.add(rs.getBigDecimal("Supplier Price"));
                shoppingCartItemInfo.add(rs.getInt("Supplier ID"));
                shoppingCartItemInfo.add(rs.getString("Supplier Name"));
                shoppingCartItemInfo.add(rs.getString("Supplier Email"));
                shoppingCartItemInfo.add(rs.getString("Supplier Phone"));
            }
            System.out.println("Item found by ID!");
            return shoppingCartItemInfo;
            
        } catch (SQLException e) {
            System.out.println("Error displaying Item info.");
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

    public List<List<Object>> findShoppingCartItemsByUserID(int customer_id) {
        List<List<Object>> shoppingCartItemInfo = new ArrayList<>();
        shoppingCartItemInfo.add(new ArrayList<>());
        shoppingCartItemInfo.add(new ArrayList<>());

        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();
            String sqlQueryStatement = "SELECT inv.inventory_entry_id AS `Inventory Entry ID`, i.item_id AS `Item ID`, i.name AS `Item Name`, i.description AS `Description`, i.srp AS `Suggested Retail Price`, i.manu_price AS `Manufacturer Price`,inv.quantity AS `Quantity`, inv.price AS `Supplier Price`, s.supplier_id AS `Supplier ID`, CONCAT(s.supplier_fname, ' ', s.supplier_lname) AS `Supplier Name`, s.email AS `Supplier Email`, s.phone AS `Supplier Phone` FROM Inventory inv JOIN Item i ON inv.item_id = i.item_id JOIN Supplier s ON inv.supplier_id = s.supplier_id;";
            ResultSet rs = queryStatement.executeQuery(sqlQueryStatement);
            int i = 0;
            while (rs.next()) {
                shoppingCartItemInfo.get(i).add(rs.getInt("Inventory Entry ID"));
                shoppingCartItemInfo.get(i).add(rs.getInt("Item ID"));
                shoppingCartItemInfo.get(i).add(rs.getString("Item Name"));
                shoppingCartItemInfo.get(i).add(rs.getString("Description"));
                shoppingCartItemInfo.get(i).add(rs.getBigDecimal("Suggested Retail Price"));
                shoppingCartItemInfo.get(i).add(rs.getBigDecimal("Manufacturer Price"));
                shoppingCartItemInfo.get(i).add(rs.getInt("Quantity"));
                shoppingCartItemInfo.get(i).add(rs.getBigDecimal("Supplier Price"));
                shoppingCartItemInfo.get(i).add(rs.getInt("Supplier ID"));
                shoppingCartItemInfo.get(i).add(rs.getString("Supplier Name"));
                shoppingCartItemInfo.get(i).add(rs.getString("Supplier Email"));
                shoppingCartItemInfo.get(i).add(rs.getString("Supplier Phone"));
                i++;
            }
            System.out.println("Shopping Cart item found by ID!");
            return shoppingCartItemInfo;
            
        } catch (SQLException e) {
            System.out.println("Error displaying Manufacturer info.");
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }
    
    public List<List<Object>> findCartItemsByUserIDAndName (int supplier_id, String name) {
        List<List<Object>> shoppingCartItemInfo = new ArrayList<>();
        shoppingCartItemInfo.add(new ArrayList<>());
        shoppingCartItemInfo.add(new ArrayList<>());

        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();
            String sqlQueryStatement = "SELECT inv.inventory_entry_id AS `Inventory Entry ID`, i.item_id AS `Item ID`, i.name AS `Item Name`, i.description AS `Description`, i.srp AS `Suggested Retail Price`, i.manu_price AS `Manufacturer Price`,inv.quantity AS `Quantity`, inv.price AS `Supplier Price`, s.supplier_id AS `Supplier ID`, CONCAT(s.supplier_fname, ' ', s.supplier_lname) AS `Supplier Name`, s.email AS `Supplier Email`, s.phone AS `Supplier Phone` FROM Inventory inv JOIN Item i ON inv.item_id = i.item_id JOIN Supplier s ON inv.supplier_id = s.supplier_id WHERE s.supplier_id = " + supplier_id + " AND i.name = '" + name + "';";
            ResultSet rs = queryStatement.executeQuery(sqlQueryStatement);
            int i = 0;
            while (rs.next()) {
                shoppingCartItemInfo.get(i).add(rs.getInt("Inventory Entry ID"));
                shoppingCartItemInfo.get(i).add(rs.getInt("Item ID"));
                shoppingCartItemInfo.get(i).add(rs.getString("Item Name"));
                shoppingCartItemInfo.get(i).add(rs.getString("Description"));
                shoppingCartItemInfo.get(i).add(rs.getBigDecimal("Suggested Retail Price"));
                shoppingCartItemInfo.get(i).add(rs.getBigDecimal("Manufacturer Price"));
                shoppingCartItemInfo.get(i).add(rs.getInt("Quantity"));
                shoppingCartItemInfo.get(i).add(rs.getBigDecimal("Supplier Price"));
                shoppingCartItemInfo.get(i).add(rs.getInt("Supplier ID"));
                shoppingCartItemInfo.get(i).add(rs.getString("Supplier Name"));
                shoppingCartItemInfo.get(i).add(rs.getString("Supplier Email"));
                shoppingCartItemInfo.get(i).add(rs.getString("Supplier Phone"));
                i++;
            }

            System.out.println("Inventory item/s found by supplier_id and item name!");
            return shoppingCartItemInfo;

        } catch (SQLException e) {
            System.out.println("Error finding inventory item/s by supplier_id and item name.");
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println(e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
            return null;
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
                System.out.println("Foreign key constraint violation: " + e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }


    public List<List<Object>> readWishlist() {    
        List<List<Object>> wishlistInfo = new ArrayList<>();
        wishlistInfo.add(new ArrayList<>());
        wishlistInfo.add(new ArrayList<>());
    
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();
            String sqlQueryStatement = "SELECT wl.wishlist_id AS `Wishlist ID`, c.customer_id AS `Customer ID`, CONCAT(c.first_name, ' ', c.last_name) AS `Customer Name`, i.item_id AS `Item ID`, i.name AS `Item Name`, i.description AS `Description`, i.srp AS `Suggested Retail Price`, i.manu_price AS `Manufacturer Price`, inv.quantity AS `Supplier Quantity`, s.supplier_id AS `Supplier ID`, CONCAT(s.supplier_fname, ' ', s.supplier_lname) AS `Supplier Name` FROM Wishlist wl JOIN Inventory inv ON wl.inventory_entry_id = inv.inventory_entry_id JOIN Item i ON inv.item_id = i.item_id JOIN Customer c ON wl.customer_id = c.customer_id JOIN Supplier s ON inv.supplier_id = s.supplier_id;";
            ResultSet rs = queryStatement.executeQuery(sqlQueryStatement);
            int i = 0;
            while (rs.next()) {
                wishlistInfo.get(i).add(rs.getInt("inventory_entry_id"));
                wishlistInfo.get(i).add(rs.getInt("inventory_entry_id"));
                wishlistInfo.get(i).add(rs.getInt("inventory_entry_id"));
                wishlistInfo.get(i).add(rs.getInt("inventory_entry_id"));
                wishlistInfo.get(i).add(rs.getInt("inventory_entry_id"));
                wishlistInfo.get(i).add(rs.getInt("inventory_entry_id"));
                wishlistInfo.get(i).add(rs.getInt("inventory_entry_id"));
                wishlistInfo.get(i).add(rs.getInt("inventory_entry_id"));
                wishlistInfo.get(i).add(rs.getInt("inventory_entry_id"));
                wishlistInfo.get(i).add(rs.getInt("inventory_entry_id"));
                wishlistInfo.get(i).add(rs.getInt("inventory_entry_id"));
                i++;
            }
            return wishlistInfo;
        } catch (SQLException e) {
            System.out.println("Error displaying Wishlist info.");
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }


    public void updateWishlist(String specifiedAttribute, int valueToUpdate, int wishlist_id) {        
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = null;
            String sqlQueryStatement = "UPDATE Wishlist SET " + specifiedAttribute + " = ?  WHERE wishlist_id = ?";

            stmt = c.prepareStatement(sqlQueryStatement);
            
            stmt.setInt(2, valueToUpdate);
            stmt.setInt(3, wishlist_id);

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
                System.out.println(e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
       
        }
    }


    public void deleteWishlist(int wishlist_id) {
        try {
            String email = null;
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = c.prepareStatement("DELETE FROM Wishlist WHERE wishlist_id = ?;");
            stmt.setInt(1, wishlist_id);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Wishlist deleted successfully!");
            } else {
                System.out.println("Error deleting Wishlist.");
            }

            stmt.close();
            c.close();

        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println("Error deleting Wishlist.");
                System.out.println("Foreign key constraint violation: " + e.getMessage());
            } else {
                System.out.println("Error deleting Log In Credentials.");
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    public void createBuyerOrderInfo(int shoppingcart_id, Date order_date, BigDecimal total_amount, String status) {
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();
            PreparedStatement stmt = c.prepareStatement("INSERT INTO BuyerOrderInfo (shoppingcart_id, order_date, total_amount, status) VALUES (?, ?, ?, ?);");
                stmt.setInt(1,shoppingcart_id);
                stmt.setDate(2, order_date);
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

        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println("Error inserting Log In Credentials.");
                System.out.println("Foreign key constraint violation: " + e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    public List<List<Object>> readBuyerOrderInfo() {
        List<List<Object>> orderInfo = new ArrayList<>();
        orderInfo.add(new ArrayList<>());
        orderInfo.add(new ArrayList<>());
   
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();
            ResultSet rs = queryStatement.executeQuery("SELECT * FROM BuyerOrderInfo");
            int i = 0;
            while (rs.next()) {
                orderInfo.get(i).add(rs.getInt("buyer_order_information_id"));
                orderInfo.get(i).add(rs.getInt("shoppingcart_id"));
                orderInfo.get(i).add(rs.getDate("order_date"));
                orderInfo.get(i).add(rs.getBigDecimal("total_amount"));
                orderInfo.get(i).add(rs.getString("statusy"));                
                i++;
            }
            System.out.println("success: readBuyerOrderInfo");
            return orderInfo;
        } catch (SQLException e) {
                System.out.println("Error displaying Buyer Order Information.");
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
                return null;
        }
    }

    public void updateBuyerOrderInfo(String specifiedAttribute, int valueToUpdate, int buyer_order_information_id) {        
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            String sqlQueryStatement = "UPDATE BuyerOrderInfo SET " + specifiedAttribute + " = ?  WHERE buyer_order_information_id = ?";
            PreparedStatement stmt = c.prepareStatement(sqlQueryStatement);
            stmt.setInt(1, valueToUpdate);
            stmt.setInt(3, buyer_order_information_id);

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
                System.out.println(e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
       
        }
    }

    public void updateBuyerOrderInfo(String specifiedAttribute, String valueToUpdate, int buyer_order_information_id) {        
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            String sqlQueryStatement = "UPDATE BuyerOrderInfo SET " + specifiedAttribute + " = ?  WHERE buyer_order_information_id = ?";
            PreparedStatement stmt = c.prepareStatement(sqlQueryStatement);
            stmt.setString(1, valueToUpdate);
            stmt.setInt(3, buyer_order_information_id);

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
                System.out.println(e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
       
        }
    }

    public void updateBuyerOrderInfo(String specifiedAttribute, Date valueToUpdate, int buyer_order_information_id) {        
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            String sqlQueryStatement = "UPDATE BuyerOrderInfo SET " + specifiedAttribute + " = ?  WHERE buyer_order_information_id = ?";
            PreparedStatement stmt = c.prepareStatement(sqlQueryStatement);
            stmt.setDate(1, valueToUpdate);
            stmt.setInt(3, buyer_order_information_id);

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
                System.out.println(e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
       
        }
    }

    public void updateBuyerOrderInfo(String specifiedAttribute, BigDecimal valueToUpdate, int buyer_order_information_id) {        
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            String sqlQueryStatement = "UPDATE BuyerOrderInfo SET " + specifiedAttribute + " = ?  WHERE buyer_order_information_id = ?";
            PreparedStatement stmt = c.prepareStatement(sqlQueryStatement);
            stmt.setBigDecimal(1, valueToUpdate);
            stmt.setInt(3, buyer_order_information_id);

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
                System.out.println(e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
       
        }
    }

    public void deleteBuyerOrderInformation(int buyer_order_information_id) {
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);

            PreparedStatement stmt = c.prepareStatement("DELETE FROM BuyerOrderInfo WHERE buyer_order_information_id = ?;");
            stmt.setInt(1, buyer_order_information_id);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Buyer Order Information deleted successfully!");
            } else {
                System.out.println("Error deleting Buyer Order Information.");
            }
            
            stmt.close();
            c.close();

        } catch (SQLException e) {
            System.out.println("Error deleting Buyer Order Information.");
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println("Foreign key constraint violation: " + e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    public void createBuyerOrderItem(int buyer_order_information_id, BigDecimal price_at_order) {
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);           
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

        } catch (SQLException e) {
            System.out.println("Error inserting Buyer Order Item.");
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println("Foreign key constraint violation: " + e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    public List<List<Object>> readBuyerOrderItem() {
        List<List<Object>> info = new ArrayList<>();
        info.add(new ArrayList<>());
        info.add(new ArrayList<>());
        
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();
            ResultSet rs = queryStatement.executeQuery("SELECT * FROM BuyerOrderItem");
            int i = 0;
            while (rs.next()) {
                info.get(i).add(rs.getInt("buyer_order_item_id"));
                info.get(i).add(rs.getInt("buyer_order_information_id"));
                info.get(i).add(rs.getDate("price_at_order"));
                i++;
            }
            System.out.println("Success: readBuyerOrderItem");
            return info;
        } catch (SQLException e) {
                System.out.println("Error: readBuyerOrderItem.");
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
                return null;
        }
    }

    public void updateBuyerOrderItem(String specifiedAttribute, int valueToUpdate, int buyer_order_item_id) {        
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            String sqlQueryStatement = "UPDATE BuyerOrderItem SET " + specifiedAttribute + " = ?  WHERE buyer_order_item_id = ?";
            PreparedStatement stmt = c.prepareStatement(sqlQueryStatement);
            
            stmt.setInt(1, valueToUpdate);
            stmt.setInt(2, buyer_order_item_id);

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
            System.out.println("Error updating Buyer Order Item.");
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println(e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    public void updateBuyerOrderItem(String specifiedAttribute, BigDecimal valueToUpdate, int buyer_order_item_id) {        
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            String sqlQueryStatement = "UPDATE BuyerOrderItem SET " + specifiedAttribute + " = ?  WHERE buyer_order_item_id = ?";
            PreparedStatement stmt = c.prepareStatement(sqlQueryStatement);
            
            stmt.setBigDecimal(1, valueToUpdate);
            stmt.setInt(2, buyer_order_item_id);

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
            System.out.println("Error updating Buyer Order Item.");
            if (e instanceof SQLIntegrityConstraintViolationException) {
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
            PreparedStatement stmt = c.prepareStatement("DELETE FROM BuyerOrderItem WHERE buyer_order_item_id = ?;");
            stmt.setInt(1, buyer_order_item_id);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Buyer Order Item deleted successfully!");
            } else {
                System.out.println("Error deleting Buyer Order Item.");
            }
            
            stmt.close();
            c.close();

        } catch (SQLException e) {
            System.out.println("Error deleting Buyer Order Item.");
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println("Foreign key constraint violation: " + e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    public void createSupplierOrderInfo(int supplier_id, Date order_date, int manufacturer_id, BigDecimal total_amount, String status) {
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = c.prepareStatement("INSERT INTO SupplierOrderInfo (supplier_id, order_date, manufacturer_id, total_amount, status) VALUES (?, ?, ?, ?, ?);");
                stmt.setInt(1, supplier_id);
                stmt.setDate(2, order_date);
                stmt.setInt(3, manufacturer_id);
                stmt.setBigDecimal(4, total_amount);
                stmt.setString(5, status);

                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Supplier Order Information inserted successfully!");
                } else {
                    System.out.println("Error inserting Supplier Order Information.");
                }

                stmt.close();
                c.close();

        } catch (SQLException e) {
            System.out.println("Error inserting Supplier Order Information.");
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println("Foreign key constraint violation: " + e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    public List<List<Object>> readSupplierOrderInfo() {
        List<List<Object>> info = new ArrayList<>();
        info.add(new ArrayList<>());
        info.add(new ArrayList<>());
        
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();
            ResultSet rs = queryStatement.executeQuery("SELECT * FROM SupplierOrderInfo");
            int i = 0;
            while (rs.next()) {
                info.get(i).add(rs.getInt("supplier_order_id"));
                info.get(i).add(rs.getInt("supplier_id"));
                info.get(i).add(rs.getDate("order_date"));
                info.get(i).add(rs.getInt("manufacturer_id"));
                info.get(i).add(rs.getBigDecimal("total_amount"));
                info.get(i).add(rs.getString("status"));
                i++;
            }
            System.out.println("Success: readSupplierOrderInfo");
            return info;
        } catch (SQLException e) {
                System.out.println("Error displaying Supplier Order Info.");
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
                return null;
        }
    }

    public void updateSupplierOrderInfo(String specifiedAttribute, int valueToUpdate, int supplier_order_information_id) {        
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            String sqlQueryStatement = "UPDATE SupplierOrderInfo SET " + specifiedAttribute + " = ?  WHERE supplier_order_information_id = ?";
            PreparedStatement stmt = c.prepareStatement(sqlQueryStatement);
            
            stmt.setInt(1, valueToUpdate);
            stmt.setInt(2, supplier_order_information_id);

            int rowsInserted = 0;
            rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Supplier Order Info updated successfully!");
            } else {
                System.out.println("Error updating Supplier Order Info.");
            }

            stmt.close();
            c.close();
        } catch (SQLException e) {
            System.out.println("Error updating Supplier Order Info.");
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println(e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    public void updateSupplierOrderInfo(String specifiedAttribute, Date valueToUpdate, int supplier_order_information_id) {        
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            String sqlQueryStatement = "UPDATE SupplierOrderInfo SET " + specifiedAttribute + " = ?  WHERE supplier_order_information_id = ?";
            PreparedStatement stmt = c.prepareStatement(sqlQueryStatement);
            
            stmt.setDate(1, valueToUpdate);
            stmt.setInt(2, supplier_order_information_id);

            int rowsInserted = 0;
            rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Supplier Order Info updated successfully!");
            } else {
                System.out.println("Error updating Supplier Order Info.");
            }

            stmt.close();
            c.close();
        } catch (SQLException e) {
            System.out.println("Error updating Supplier Order Info.");
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println(e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    public void updateSupplierOrderInfo(String specifiedAttribute, BigDecimal valueToUpdate, int supplier_order_information_id) {        
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            String sqlQueryStatement = "UPDATE SupplierOrderInfo SET " + specifiedAttribute + " = ?  WHERE supplier_order_information_id = ?";
            PreparedStatement stmt = c.prepareStatement(sqlQueryStatement);
            
            stmt.setBigDecimal(1, valueToUpdate);
            stmt.setInt(2, supplier_order_information_id);

            int rowsInserted = 0;
            rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Supplier Order Info updated successfully!");
            } else {
                System.out.println("Error updating Supplier Order Info.");
            }

            stmt.close();
            c.close();
        } catch (SQLException e) {
            System.out.println("Error updating Supplier Order Info.");
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println(e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    public void updateSupplierOrderInfo(String specifiedAttribute, String valueToUpdate, int supplier_order_information_id) {        
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            String sqlQueryStatement = "UPDATE SupplierOrderInfo SET " + specifiedAttribute + " = ?  WHERE supplier_order_information_id = ?";
            PreparedStatement stmt = c.prepareStatement(sqlQueryStatement);
            
            stmt.setString(1, valueToUpdate);
            stmt.setInt(2, supplier_order_information_id);

            int rowsInserted = 0;
            rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Supplier Order Info updated successfully!");
            } else {
                System.out.println("Error updating Supplier Order Info.");
            }

            stmt.close();
            c.close();
        } catch (SQLException e) {
            System.out.println("Error updating Supplier Order Info.");
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println(e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    public void deleteSupplierOrderInfo(int supplier_order_id) {
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = c.prepareStatement("DELETE FROM SupplierOrderInfo WHERE supplier_order_id = ?;");
            stmt.setInt(1, supplier_order_id);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Supplier Order Info deleted successfully!");
            } else {
                System.out.println("Error deleting Supplier Order Info.");
            }
            
            stmt.close();
            c.close();

        } catch (SQLException e) {
            System.out.println("Error deleting Supplier Order Info.");
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println("Foreign key constraint violation: " + e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    public void createSupplierOrderItem(int supplier_order_id, int item_id, int quantity, BigDecimal price_at_order) {
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();
            PreparedStatement stmt = c.prepareStatement("INSERT INTO SupplierOrderItem (supplier_order_id, item_id, quantity, price_at_order) VALUES (?, ?, ?, ?);");
                stmt.setInt(1,supplier_order_id);
                stmt.setInt(2, item_id);
                stmt.setInt(3, quantity);
                stmt.setBigDecimal(4, price_at_order);

                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Supplier Order Item inserted successfully!");
                } else {
                    System.out.println("Error inserting Supplier Order Item.");
                }

                stmt.close();
                c.close();

        } catch (SQLException e) {
            System.out.println("Error inserting Supplier Order Item.");
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println("Foreign key constraint violation: " + e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    public List<List<Object>> readSupplierOrderItem() {
        List<List<Object>> info = new ArrayList<>();
        info.add(new ArrayList<>());
        info.add(new ArrayList<>());
        
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();
            ResultSet rs = queryStatement.executeQuery("SELECT * FROM SupplierOrderItem");
            int i = 0;
            while (rs.next()) {
                info.get(i).add(rs.getInt("supplier_order_item_id"));
                info.get(i).add(rs.getInt("supplier_order_information_id"));
                info.get(i).add(rs.getInt("item_id"));
                info.get(i).add(rs.getInt("quantity"));
                info.get(i).add(rs.getBigDecimal("price_at_order"));
                i++;
            }
            System.out.println("Success: readSupplierOrderItem");
            return info;
        } catch (SQLException e) {
                System.out.println("Error displaying Supplier Order Item.");
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
                return null;
        }
    }

    public void updateSupplierOrderItem(String specifiedAttribute, int valueToUpdate, int supplier_order_detail_id) {        
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            String sqlQueryStatement = "UPDATE SupplierOrderItem SET " + specifiedAttribute + " = ?  WHERE supplier_order_detail_id = ?";
            PreparedStatement stmt = c.prepareStatement(sqlQueryStatement);
            
            stmt.setInt(1, valueToUpdate);
            stmt.setInt(2, supplier_order_detail_id);

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
            System.out.println("Error updating Supplier Order Item.");
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println(e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    public void updateSupplierOrderItem(String specifiedAttribute, BigDecimal valueToUpdate, int supplier_order_detail_id) {        
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            String sqlQueryStatement = "UPDATE SupplierOrderItem SET " + specifiedAttribute + " = ?  WHERE supplier_order_detail_id = ?";
            PreparedStatement stmt = c.prepareStatement(sqlQueryStatement);
            
            stmt.setBigDecimal(1, valueToUpdate);
            stmt.setInt(2, supplier_order_detail_id);

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
            System.out.println("Error updating Buyer Order Payment.");
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println(e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    public void deleteSupplierOrderItem(int supplier_order_detail_id) {
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = c.prepareStatement("DELETE FROM SupplierOrderItem WHERE supplier_order_detail_id = ?;");
            stmt.setInt(1, supplier_order_detail_id);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Supplier Order Item deleted successfully!");
            } else {
                System.out.println("Error deleting Supplier Order Item.");
            }
            
            stmt.close();
            c.close();

        } catch (SQLException e) {
            System.out.println("Error deleting Supplier Order Item.");
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println("Foreign key constraint violation: " + e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    public void createBuyerOrderPayment(int buyer_order_information_id, Date payment_date, String payment_mode, String payment_status) {
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();
            PreparedStatement stmt = c.prepareStatement("INSERT INTO SupplierOrderItem (buyer_order_information_id, payment_date, payment_mode, payment_status) VALUES (?, ?, ?, ?);");
                stmt.setInt(1,buyer_order_information_id);
                stmt.setDate(2, payment_date);
                stmt.setString(3, payment_mode);
                stmt.setString(4, payment_status);

                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Buyer Order Payment inserted successfully!");
                } else {
                    System.out.println("Error inserting Buyer Order Payment.");
                }

                stmt.close();
                c.close();

        } catch (SQLException e) {
            System.out.println("Error inserting Buyer Order Payment.");
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println("Foreign key constraint violation: " + e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    public List<List<Object>> readBuyerOrderPayment() {
        List<List<Object>> info = new ArrayList<>();
        info.add(new ArrayList<>());
        info.add(new ArrayList<>());
        
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();
            ResultSet rs = queryStatement.executeQuery("SELECT * FROM BuyerOrderPayment");
            int i = 0;
            while (rs.next()) {
                info.get(i).add(rs.getInt("payment_id"));
                info.get(i).add(rs.getInt("buyer_order_information_id"));
                info.get(i).add(rs.getDate("payment_date"));
                info.get(i).add(rs.getString("payment_mode"));
                info.get(i).add(rs.getString("payment_status"));
                i++;
            }
            System.out.println("Success: readBuyerOrderPayment");
            return info;
        } catch (SQLException e) {
                System.out.println("Error displaying Buyer Order Payment.");
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
                return null;
        }
    }

    public void updateBuyerOrderPayment(String specifiedAttribute, int valueToUpdate, int payment_id) {        
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            String sqlQueryStatement = "UPDATE BuyerOrderPayment SET " + specifiedAttribute + " = ?  WHERE payment_id = ?";
            PreparedStatement stmt = c.prepareStatement(sqlQueryStatement);
            
            stmt.setInt(1, valueToUpdate);
            stmt.setInt(2, payment_id);

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
            System.out.println("Error updating Buyer Order Payment.");
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println(e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    public void updateBuyerOrderPayment(String specifiedAttribute, Date valueToUpdate, int payment_id) {        
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            String sqlQueryStatement = "UPDATE BuyerOrderPayment SET " + specifiedAttribute + " = ?  WHERE payment_id = ?";
            PreparedStatement stmt = c.prepareStatement(sqlQueryStatement);
            
            stmt.setDate(1, valueToUpdate);
            stmt.setInt(2, payment_id);

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
            System.out.println("Error updating Buyer Order Payment.");
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println(e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    public void updateBuyerOrderPayment(String specifiedAttribute, String valueToUpdate, int payment_id) {        
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            String sqlQueryStatement = "UPDATE BuyerOrderPayment SET " + specifiedAttribute + " = ?  WHERE payment_id = ?";
            PreparedStatement stmt = c.prepareStatement(sqlQueryStatement);
            
            stmt.setString(1, valueToUpdate);
            stmt.setInt(2, payment_id);

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
            System.out.println("Error updating Buyer Order Payment.");
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println(e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    public void deleteBuyerOrderPayment(int payment_id) {
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = c.prepareStatement("DELETE FROM BuyerOrderPayment WHERE payment_id = ?;");
            stmt.setInt(1, payment_id);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Buyer Order Payment deleted successfully!");
            } else {
                System.out.println("Error deleting Buyer Order Payment.");
            }

        } catch (SQLException e) {
            System.out.println("Error deleting Buyer Order Payment.");
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println("Foreign key constraint violation: " + e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    public void createSupplierOrderPayment(int supplier_order_id, Date payment_date, String payment_mode, String payment_status) {
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();
            PreparedStatement stmt = c.prepareStatement("INSERT INTO SupplierOrderPayment (supplier_order_id, payment_date, payment_mode, payment_status) VALUES (?, ?, ?, ?);");
                stmt.setInt(1, supplier_order_id);
                stmt.setDate(2, payment_date);
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

        } catch (SQLException e) {
            System.out.println("Error inserting Supplier Order Payment.");
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println("Foreign key constraint violation: " + e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    public List<List<Object>> readSupplierOrderPayment() {
        List<List<Object>> info = new ArrayList<>();
        info.add(new ArrayList<>());
        info.add(new ArrayList<>());
        
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();
            ResultSet rs = queryStatement.executeQuery("SELECT * FROM SupplierOrderPayment");
            int i = 0;
            while (rs.next()) {
                info.get(i).add(rs.getInt("payment_id"));
                info.get(i).add(rs.getInt("supplier_order_information_id"));
                info.get(i).add(rs.getDate("payment_date"));
                info.get(i).add(rs.getString("payment_mode"));
                info.get(i).add(rs.getString("payment_status"));
                i++;
            }
            System.out.println("Success: readSupplierOrderPayment");
            return info;
        } catch (SQLException e) {
                System.out.println("Error displaying Supplier Order Payment.");
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
                return null;
        }
    }

    public void updateSupplierOrderPayment(String specifiedAttribute, int valueToUpdate, int payment_id) {        
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            String sqlQueryStatement = "UPDATE SupplierOrderPayment SET " + specifiedAttribute + " = ?  WHERE payment_id = ?";
            PreparedStatement stmt = c.prepareStatement(sqlQueryStatement);
           
            stmt.setInt(1, valueToUpdate);
            stmt.setInt(2, payment_id);

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
            System.out.println("Error updating Supplier Order Payment.");
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println(e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    public void updateSupplierOrderPayment(String specifiedAttribute, Date valueToUpdate, int payment_id) {        
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            String sqlQueryStatement = "UPDATE SupplierOrderPayment SET " + specifiedAttribute + " = ?  WHERE payment_id = ?";
            PreparedStatement stmt = c.prepareStatement(sqlQueryStatement);
           
            stmt.setDate(1, valueToUpdate);
            stmt.setInt(2, payment_id);

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
            System.out.println("Error updating Supplier Order Payment.");
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println(e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    public void updateSupplierOrderPayment(String specifiedAttribute, String valueToUpdate, int payment_id) {        
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            String sqlQueryStatement = "UPDATE SupplierOrderPayment SET " + specifiedAttribute + " = ?  WHERE payment_id = ?";
            PreparedStatement stmt = c.prepareStatement(sqlQueryStatement);
           
            stmt.setString(1, valueToUpdate);
            stmt.setInt(2, payment_id);

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
            System.out.println("Error updating Supplier Order Payment.");
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println(e.getMessage());
            } else {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    public void deleteSupplierOrderPayment(int payment_id) {
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = c.prepareStatement("DELETE FROM BuyerOrderPayment WHERE payment_id = ?;");
            stmt.setInt(1, payment_id);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Buyer Order Payment deleted successfully!");
            } else {
                System.out.println("Error deleting Buyer Order Payment.");
            }

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

    public List<List<Object>> productRecordManagement() {
        List<List<Object>> productRecord = new ArrayList<>();
        productRecord.add(new ArrayList<>());
        productRecord.add(new ArrayList<>());

        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();

            String sqlQueryStatement = "SELECT CONCAT(s.supplier_fname, ' ', s.supplier_lname) AS supplier_name, i.name AS item_name, i.description AS item_description, m.manufacturer_name AS manufacturer_name, m.address AS manufacturer_address, inv.quantity AS inventory_quantity, i.manu_price AS manufacturer_price, inv.price AS supplier_price, IFNULL(w.wishlist_count, 0) AS wishlist_count, IFNULL(sc.cart_count, 0) AS cart_count, IFNULL(o.order_count, 0) AS order_count FROM Supplier s LEFT JOIN Inventory inv ON s.supplier_id = inv.supplier_id LEFT JOIN Item i ON inv.item_id = i.item_id LEFT JOIN Manufacturer m ON i.manufacturer_id = m.manufacturer_id LEFT JOIN (SELECT inventory_entry_id, COUNT(DISTINCT customer_id) AS wishlist_count FROM Wishlist GROUP BY inventory_entry_id) w ON inv.inventory_entry_id = w.inventory_entry_id LEFT JOIN (SELECT inventory_entry_id, COUNT(DISTINCT customer_id) AS cart_count FROM ShoppingCart GROUP BY inventory_entry_id) sc ON inv.inventory_entry_id = sc.inventory_entry_id LEFT JOIN (SELECT inv.inventory_entry_id, COUNT(DISTINCT boi.buyer_order_information_id) AS order_count FROM BuyerOrderInfo boi INNER JOIN ShoppingCart sc ON boi.shoppingcart_id = sc.shoppingcart_id INNER JOIN Inventory inv ON sc.inventory_entry_id = inv.inventory_entry_id GROUP BY inv.inventory_entry_id) o ON inv.inventory_entry_id = o.inventory_entry_id ORDER BY s.supplier_id, i.item_id;";

            ResultSet rs = queryStatement.executeQuery(sqlQueryStatement);
            int i = 0;
            while (rs.next()) {
                productRecord.get(i).add(rs.getString("supplier_name"));
                productRecord.get(i).add(rs.getInt("item_name"));
                productRecord.get(i).add(rs.getString("item_description"));
                productRecord.get(i).add(rs.getString("manufacturer_name"));
                productRecord.get(i).add(rs.getString("manufacturer_address"));
                productRecord.get(i).add(rs.getInt("inventory_quantity"));
                productRecord.get(i).add(rs.getBigDecimal("manufacturer_price"));
                productRecord.get(i).add(rs.getBigDecimal("supplier_price"));
                productRecord.get(i).add(rs.getInt("wishlist_count"));
                productRecord.get(i).add(rs.getInt("cart_count"));
                productRecord.get(i).add(rs.getInt("order_count"));
                i++;
            }
            return productRecord;
        } catch (SQLException e) {
            System.out.println("Error getting Supplier Record Management.\n" + e.getMessage());
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }      
    }

    public List<List<Object>> customerRecordManagement() {
        List<List<Object>> customerRecord = new ArrayList<>();
        customerRecord.add(new ArrayList<>());
        customerRecord.add(new ArrayList<>());

        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();
            String sqlQueryStatement = "SELECT c.customer_id, concat(c.first_name,' ',c.last_name) AS name, c.email, c.phone_number, c.delivery_address, i.name AS item_name, sc.quantity FROM customer c LEFT JOIN shoppingcart sc ON c.customer_id = sc.customer_id LEFT JOIN inventory inv ON inv.inventory_entry_id = sc.inventory_entry_id LEFT JOIN item i ON i.item_id = inv.item_id;";
            ResultSet rs = queryStatement.executeQuery(sqlQueryStatement);
            int i = 0;
            while (rs.next()) {
                customerRecord.get(i).add(rs.getInt("customer_id"));
                customerRecord.get(i).add(rs.getString("name"));
                customerRecord.get(i).add(rs.getString("email"));
                customerRecord.get(i).add(rs.getString("phone_number"));
                customerRecord.get(i).add(rs.getString("delivery_address"));
                customerRecord.get(i).add(rs.getString("item_name"));
                customerRecord.get(i).add(rs.getInt("quantity"));
                i++;
            }
            return customerRecord;
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

    public List<List<Object>> orderRecordManagement () {
        List<List<Object>> orderRecord = new ArrayList<>();
        orderRecord.add(new ArrayList<>());
        orderRecord.add(new ArrayList<>());

        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();
            String sqlQueryStatement = "SELECT boi.buyer_order_information_id AS order_id, boi_item.buyer_order_item_id AS order_detail_id, boi.status AS order_status, boi.order_date, i.name AS item_name FROM BuyerOrderInfo boi JOIN BuyerOrderItem boi_item ON boi.buyer_order_information_id = boi_item.buyer_order_information_id JOIN Inventory inv ON boi_item.buyer_order_item_id = inv.inventory_entry_id JOIN Item i ON inv.item_id = i.item_id;";
            ResultSet rs = queryStatement.executeQuery(sqlQueryStatement);
            int i = 0;
            while (rs.next()) {
                orderRecord.get(i).add(rs.getInt("order_id"));
                orderRecord.get(i).add(rs.getInt("order_detail_id"));
                orderRecord.get(i).add(rs.getString("order_status"));
                orderRecord.get(i).add(rs.getString("order_date"));
                orderRecord.get(i).add(rs.getString("item_name"));
                i++;
            }
            return orderRecord;
        } catch (SQLException e) {
            System.out.println("Error getting Supplier Record Management.\n" + e.getMessage());
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }      
    }


//     #a
// SELECT 
//     c.customer_id,
//     c.first_name,
//     c.last_name,
//     c.email,
//     c.phone_number,
//     c.delivery_address,
//     sc.shoppingcart_id,
//     sc.quantity,
//     i.name AS product_name
// FROM 
//     Customer c
// JOIN 
//     ShoppingCart sc ON c.customer_id = sc.customer_id
// JOIN 
//     Inventory inv ON sc.inventory_entry_id = inv.inventory_entry_id
// JOIN 
//     Item i ON inv.item_id = i.item_id
// WHERE 
//     sc.quantity > 0;
// #b
// SELECT 
//     s.supplier_id,
//     s.supplier_fname,
//     s.supplier_lname,
//     s.email,
//     s.phone,
//     s.address,
//     s.supplier_rating,
//     i.name AS product_name
// FROM 
//     Supplier s
// JOIN 
//     Inventory inv ON s.supplier_id = inv.supplier_id
// JOIN 
//     Item i ON inv.item_id = i.item_id
// WHERE 
//     s.supplier_rating IS NOT NULL 
//     AND inv.quantity > 0;
// #c
// INSERT INTO BuyerOrderInfo (shoppingcart_id, order_date, status)
// VALUES 
//     (?, ?, ?);

// #d
// UPDATE ShoppingCart
// SET quantity = 0
// WHERE customer_id = ? AND inventory_entry_id = ?;

// #e
// UPDATE Item i
// SET i.purchase_count = i.purchase_count + 1
// WHERE i.item_id = ?;

// #f
// UPDATE Inventory inv
// SET inv.quantity = inv.quantity - ?
// WHERE inv.supplier_id = ? AND inv.item_id = ?;

// #g
//     UPDATE SupplierOrderInfo soi
// SET soi.total_amount = soi.total_amount + ?, soi.order_date = NOW(), soi.status = 'Pending'
// WHERE soi.supplier_id = ?;

//     public void transactionsVillanuevaA() {

//     }

//     -- a
// SELECT * FROM BuyerOrderPayment;

// -- b
// UPDATE BuyerOrderPayment bop
// SET bop.payment_status = 'Refunded'
// WHERE bop.buyer_order_information_id = ?;
//     public void transactionsYoungA() {

//     }

    public List<List<Object>> reportsVillanueva() {
        //Product Sales for the Week, Month, and Year per Supplier
        List<List<Object>> quarterTrends = new ArrayList<>();
        quarterTrends.add(new ArrayList<>());
        quarterTrends.add(new ArrayList<>());

        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();
            String sqlQueryStatement = """
                    SELECT 
                        s.supplier_id,
                        s.supplier_fname,
                        s.supplier_lname,
                        i.name AS product_name,
                        
                        -- Sales for the Week
                        SUM(CASE WHEN boi.order_date >= CURDATE() - INTERVAL WEEKDAY(CURDATE()) DAY 
                                AND boi.order_date < CURDATE() - INTERVAL WEEKDAY(CURDATE()) + 7 DAY THEN boi.total_amount ELSE 0 END) AS weekly_sales,

                        -- Sales for the Month
                        SUM(CASE WHEN YEAR(boi.order_date) = YEAR(CURDATE()) 
                                AND MONTH(boi.order_date) = MONTH(CURDATE()) THEN boi.total_amount ELSE 0 END) AS monthly_sales,

                        -- Sales for the Year
                        SUM(CASE WHEN YEAR(boi.order_date) = YEAR(CURDATE()) THEN boi.total_amount ELSE 0 END) AS yearly_sales
                    FROM 
                        Supplier s
                    JOIN 
                        Inventory inv ON s.supplier_id = inv.supplier_id
                    JOIN 
                        Item i ON inv.item_id = i.item_id
                    JOIN 
                        BuyerOrderItem boi_item ON boi_item.buyer_order_item_id = inv.inventory_entry_id
                    JOIN 
                        BuyerOrderInfo boi ON boi.buyer_order_information_id = boi_item.buyer_order_information_id

                    GROUP BY 
                        s.supplier_id, i.item_id
                    ORDER BY 
                        s.supplier_id, i.item_id;
                    """;
            ResultSet rs = queryStatement.executeQuery(sqlQueryStatement);
            int i = 0;
            while (rs.next()) {
                quarterTrends.get(i).add(rs.getInt("inventory_entry_id"));
                quarterTrends.get(i).add(rs.getString("name"));
                quarterTrends.get(i).add(rs.getInt("wishlist_count"));
                quarterTrends.get(i).add(rs.getInt("cart_count"));
                i++;
            }
            System.out.println("Success: Wishlist-to-Shopping Cart Trends of the Quarter");
            return quarterTrends;
        } catch (SQLException e) {
            System.out.println("Error getting Supplier Record Management.\n" + e.getMessage());
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

    public List<List<Object>> reportsAbjelina() {
        //Product Category Trends for the Quarter
        List<List<Object>> quarterTrends = new ArrayList<>();
        quarterTrends.add(new ArrayList<>());
        quarterTrends.add(new ArrayList<>());
        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT @@sql_mode");
            if (rs.next()) {
                String sqlMode = rs.getString(1);
                System.out.println("Current sql_mode: " + sqlMode);
            }

            stmt.execute("SET sql_mode = (SELECT REPLACE(@@sql_mode, 'ONLY_FULL_GROUP_BY', ''))");

            String query = """
                    SELECT
                        m.manufacturer_name AS manufacturer,
                        CONCAT('Q', QUARTER(boi.order_date)) AS quarter,
                        YEAR(boi.order_date) AS year,
                        SUM(sc.quantity) AS total_quantity_sold,
                        SUM(boi.total_amount) AS total_sales
                    FROM
                        BuyerOrderInfo boi
                    INNER JOIN
                        ShoppingCart sc ON boi.shoppingcart_id = sc.shoppingcart_id
                    INNER JOIN
                        Inventory inv ON sc.inventory_entry_id = inv.inventory_entry_id
                    INNER JOIN
                        Item i ON inv.item_id = i.item_id
                    INNER JOIN
                        Manufacturer m ON i.manufacturer_id = m.manufacturer_id
                    GROUP BY
                        m.manufacturer_name, YEAR(boi.order_date), QUARTER(boi.order_date)
                    ORDER BY
                       total_quantity_sold DESC;
                    """;

            ResultSet resultSet = stmt.executeQuery(query);
            int i = 0;
            while (resultSet.next()) {
                quarterTrends.get(i).add(resultSet.getString("manufacturer"));
                quarterTrends.get(i).add(resultSet.getString("quarter"));
                quarterTrends.get(i).add(resultSet.getInt("year"));
                quarterTrends.get(i).add(resultSet.getInt("total_quantity_sold"));
                quarterTrends.get(i).add(resultSet.getDouble("total_sales"));
                i++;
            }
            System.out.println("Success: Product Category Trends for the Quarter");
            return quarterTrends;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<List<Object>> reportsBalingitWeek() {
        //Top Selling Unique Items for the Week
        List<List<Object>> quarterTrends = new ArrayList<>();
        quarterTrends.add(new ArrayList<>());
        quarterTrends.add(new ArrayList<>());

        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();
            String sqlQueryStatement = """
                    SELECT 
                        i.name AS item_name,
                        i.description,
                        COUNT(boi.buyer_order_item_id) AS total_sold,
                        SUM(boi.price_at_order) AS total_revenue
                    FROM 
                        BuyerOrderItem boi
                    JOIN 
                        BuyerOrderInfo boi_info ON boi.buyer_order_information_id = boi_info.buyer_order_information_id
                    JOIN 
                        ShoppingCart sc ON boi_info.shoppingcart_id = sc.shoppingcart_id
                    JOIN 
                        Inventory inv ON sc.inventory_entry_id = inv.inventory_entry_id
                    JOIN 
                        Item i ON inv.item_id = i.item_id
                    WHERE 
                        YEARWEEK(boi_info.order_date, 1) = YEARWEEK(CURDATE(), 1)
                        AND boi_info.status = 'Completed'
                    GROUP BY 
                        i.item_id
                    ORDER BY 
                        total_sold DESC;
                    """;
            ResultSet rs = queryStatement.executeQuery(sqlQueryStatement);
            int i = 0;
            while (rs.next()) {
                quarterTrends.get(i).add(rs.getString("item_name"));
                quarterTrends.get(i).add(rs.getString("description"));
                quarterTrends.get(i).add(rs.getInt("total_sold"));
                quarterTrends.get(i).add(rs.getBigDecimal("total_revenue"));
                i++;
            }
            System.out.println("Success: Top Selling Unique Items for the Week");
            return quarterTrends;
        } catch (SQLException e) {
            System.out.println("Error getting Top Selling Unique Items for the Week.\n" + e.getMessage());
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

    public List<List<Object>> reportsBalingitMonth() {
        //Top Selling Unique Items for the Month
        List<List<Object>> quarterTrends = new ArrayList<>();
        quarterTrends.add(new ArrayList<>());
        quarterTrends.add(new ArrayList<>());

        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();
            String sqlQueryStatement = """
                    SELECT 
                        i.name AS item_name,
                        i.description,
                        COUNT(boi.buyer_order_item_id) AS total_sold,
                        SUM(boi.price_at_order) AS total_revenue
                    FROM 
                        BuyerOrderItem boi
                    JOIN 
                        BuyerOrderInfo boi_info ON boi.buyer_order_information_id = boi_info.buyer_order_information_id
                    JOIN 
                        ShoppingCart sc ON boi_info.shoppingcart_id = sc.shoppingcart_id
                    JOIN 
                        Inventory inv ON sc.inventory_entry_id = inv.inventory_entry_id
                    JOIN 
                        Item i ON inv.item_id = i.item_id
                    WHERE 
                        MONTH(boi_info.order_date) = MONTH(CURDATE()) 
                        AND YEAR(boi_info.order_date) = YEAR(CURDATE())
                        AND boi_info.status = 'Completed'
                    GROUP BY 
                        i.item_id
                    ORDER BY 
                        total_sold DESC;
                    """;
            ResultSet rs = queryStatement.executeQuery(sqlQueryStatement);
            int i = 0;
            while (rs.next()) {
                quarterTrends.get(i).add(rs.getString("item_name"));
                quarterTrends.get(i).add(rs.getString("description"));
                quarterTrends.get(i).add(rs.getInt("total_sold"));
                quarterTrends.get(i).add(rs.getBigDecimal("total_revenue"));
                i++;
            }
            System.out.println("Success: Top Selling Unique Items for the Month");
            return quarterTrends;
        } catch (SQLException e) {
            System.out.println("Error getting Top Selling Unique Items for the Month.\n" + e.getMessage());
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

    public List<List<Object>> reportsBalingitYear() {
        //Top Selling Unique Items for the Year
        List<List<Object>> quarterTrends = new ArrayList<>();
        quarterTrends.add(new ArrayList<>());
        quarterTrends.add(new ArrayList<>());

        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();
            String sqlQueryStatement = """
                    SELECT 
                        i.name AS item_name,
                        i.description,
                        COUNT(boi.buyer_order_item_id) AS total_sold,
                        SUM(boi.price_at_order) AS total_revenue
                    FROM 
                        BuyerOrderItem boi
                    JOIN 
                        BuyerOrderInfo boi_info ON boi.buyer_order_information_id = boi_info.buyer_order_information_id
                    JOIN 
                        ShoppingCart sc ON boi_info.shoppingcart_id = sc.shoppingcart_id
                    JOIN 
                        Inventory inv ON sc.inventory_entry_id = inv.inventory_entry_id
                    JOIN 
                        Item i ON inv.item_id = i.item_id
                    WHERE 
                        YEAR(boi_info.order_date) = YEAR(CURDATE()) -- Current year
                        AND boi_info.status = 'Completed'
                    GROUP BY 
                        i.item_id
                    ORDER BY 
                        total_sold DESC;
                    """;
            ResultSet rs = queryStatement.executeQuery(sqlQueryStatement);
            int i = 0;
            while (rs.next()) {
                quarterTrends.get(i).add(rs.getString("item_name"));
                quarterTrends.get(i).add(rs.getString("description"));
                quarterTrends.get(i).add(rs.getInt("total_sold"));
                quarterTrends.get(i).add(rs.getBigDecimal("total_revenue"));
                i++;
            }
            System.out.println("Success: Top Selling Unique Items for the Year");
            return quarterTrends;
        } catch (SQLException e) {
            System.out.println("Error getting Top Selling Unique Items for the Year.\n" + e.getMessage());
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

    public List<List<Object>> reportsYoung() {
        //Wishlist-to-Shopping Cart Trends of the Quarter
        List<List<Object>> quarterTrends = new ArrayList<>();
        quarterTrends.add(new ArrayList<>());
        quarterTrends.add(new ArrayList<>());

        try {
            Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
            java.sql.Statement queryStatement = c.createStatement();
            String sqlQueryStatement = "SELECT w.inventory_entry_id, i.name, COUNT(DISTINCT w.customer_id) AS wishlist_count, COUNT(DISTINCT s.customer_id) AS cart_count FROM Wishlist w INNER JOIN ShoppingCart s ON w.inventory_entry_id = s.inventory_entry_id LEFT JOIN inventory inv ON inv.inventory_entry_id = w.inventory_entry_id LEFT JOIN item i ON i.item_id = inv.item_id GROUP BY w.inventory_entry_id ORDER BY w.inventory_entry_id;";
            ResultSet rs = queryStatement.executeQuery(sqlQueryStatement);
            int i = 0;
            while (rs.next()) {
                quarterTrends.get(i).add(rs.getInt("inventory_entry_id"));
                quarterTrends.get(i).add(rs.getString("name"));
                quarterTrends.get(i).add(rs.getInt("wishlist_count"));
                quarterTrends.get(i).add(rs.getInt("cart_count"));
                i++;
            }
            System.out.println("Success: Wishlist-to-Shopping Cart Trends of the Quarter");
            return quarterTrends;
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
