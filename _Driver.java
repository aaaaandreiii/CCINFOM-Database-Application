import java.lang.ModuleLayer.Controller;
import java.math.BigDecimal;

import javax.swing.text.View;

public class _Driver {
    public static void main (String[] args){

        _View view;
        _Model model;
        _Controller controller;

        // establish connection with database here
        DatabaseConnection jdbc = new DatabaseConnection();
        jdbc.createConnection();

        // // CRUD on users
        // jdbc.createUser("andrei@balingit.com", "password", "Andrei", "Balingit", "0912-345-789", "Pasay City");
        // jdbc.readUser();
        // jdbc.updateUser("phone_number", "0912-345-6789", 108);
        // jdbc.deleteUser(107);

        // // CRUD on item entities
        BigDecimal fuckYouInfom = new BigDecimal(69.99);
        // jdbc.createItemEntity("INFOM toy", 4, fuckYouInfom, "CCINFOM", "I fucking hate INFOM. Please just let the term end. I fucking hate databases.");
        // jdbc.readItemEntity();
        // jdbc.updateItemEntity("name", "CCINFOM MP", 68);
        jdbc.readItemEntity();


        // // CRUD on orders
        // // CRUD on shopping cart             ?? could be synonymous with order adding and item adding idk not so sure yet
        // // CRUD on wishlist                  ?? same here
        // // CRUD on Supplier
        // // CRUD on Manufacturer




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