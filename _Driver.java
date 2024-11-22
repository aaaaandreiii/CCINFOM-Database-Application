import java.lang.ModuleLayer.Controller;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.View;

public class _Driver {
    private static int userID = -1;
    private static int supplierID = -1;

    private static void printObjectArrayList (ArrayList<Object> array) {
        for (Object information : array) {
            System.out.printf(information + "\t");
        }
    }

    private static void printObject2DArray (List<List<Object>> array) {
        for (int i = 0; i < array.size(); i++) {
            for (int j = 0; j < array.get(i).size(); j++) {
                System.out.print(array.get(i).get(j) + "\t");
            }
            System.out.println();
        }
    }

    public static void main (String[] args){
        ArrayList<Object> userInfo = null;
        List<List<Object>> otherInfo = null;
        _View view;
        _Model model;
        _Controller controller;

        // establish connection with database here
        DatabaseConnection jdbc = new DatabaseConnection();
        jdbc.createConnection();

        //TODO store userID during runtime, getUserID probably after logIn or signUp

        // // CRUD on users
        // jdbc.deleteLogInCredentials("andrei@balingit.com");
        // jdbc.createUser("andrei@balingit.com", "password", "Andrei", "Balingit", "0912-345-789", "Pasay City");

        // userID = jdbc.findUserIdByEmail("andrei@balingit.com");        
        // userInfo = jdbc.findUserById(userID);
        // printObjectArrayList(userInfo);

        // userInfo = null;
        // userInfo = jdbc.findUserByName("Andrei", "Balingit");
        // printObjectArrayList(userInfo);

        otherInfo = null;
        otherInfo = jdbc.readAllUsers();
        printObject2DArray(otherInfo);
        // jdbc.updateUser("phone_number", "0912-345-6789", 1);
        // jdbc.deleteUser(1);
        // jdbc.deleteLogInCredentials("ching_man_wong@ching.man");

        // // CRUD on Supplier
        // // need to create regular user account first before becoming a supplier
        // jdbc.createUser("ching_man_wong@ching.man", "ching", "Ching Man", "Wong", "0912-345-789", "Pasay City");
        // BigDecimal supplier_rating = new BigDecimal(5);
        // jdbc.createSupplier(3, supplier_rating);
        // jdbc.readAllSuppliers();
        // jdbc.updateSupplier("address", "Pasay City   ", 2);
        // jdbc.deleteSupplier(3);
        // // CRUD on Manufacturer

        // // CRUD on item entities
        // BigDecimal srp = new BigDecimal(69.99);
        // jdbc.createItemEntity("INFOM toy", 4, srp, "CCINFOM", "I fucking hate INFOM. Please just let the term end. I fucking hate databases.");
        // jdbc.readItemEntity();
        // jdbc.updateItemEntity("name", "CCINFOM MP", 68);
        // jdbc.deleteItemEntity(69);

        // // CRUD on orders
        // jdbc.createOrder();

        // // turn cart items into order and remove from cart

        // // CRUD on shopping cart             ?? could be synonymous with order adding and item adding idk not so sure yet
        // jdbc.addToShoppingCart(108, 224, 3);
        // jdbc.readShoppingCart();

        // // CRUD on wishlist                  ?? same here
        




        // System.out.println("Opening GUI");

        // view = new _View();
        // model = new Model();
		// view = new View();
		// controller = new Controller(model, view);

        //to put in controller/model
        // String username = view.get_1_LogInPage().getUsernameInput().getText();
        // String password = view.get_1_LogInPage().getPasswordInput().getText();
        // jdbc.validateLogIn(username, password);        
    }
}