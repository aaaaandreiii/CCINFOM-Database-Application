import java.lang.ModuleLayer.Controller;

import javax.swing.text.View;

public class _Driver {
    public static void main (String[] args){

        _View view;
        _Model model;
        _Controller controller;

        // establish connection with database here
        DatabaseConnection jdbc = new DatabaseConnection();
        jdbc.createConnection();
        // jdbc.createUser("andrei@balingit.com", "password", "Andrei", "Balingit", "0912-345-789", "Pasay City");
        jdbc.readUser();
        // jdbc.updateUser("phone_number", "0912-345-6789", 108);
        // jdbc.deleteUser(107);


        // jdbc.selectAllFromTable("customer");
        // jdbc.executeQuery();


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