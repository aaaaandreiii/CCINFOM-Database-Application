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
        jdbc.insertValues();

        // jdbc.selectAllFromTable("customer");
        // jdbc.executeQuery();


        System.out.println("Opening GUI");

        view = new _View();
        // model = new Model();
		// view = new View();
		// controller = new Controller(model, view);

        //to put in controller/model
        String username = view.get_1_LogInPage().getUsernameInput().getText();
        String password = view.get_1_LogInPage().getPasswordInput().getText();
        jdbc.validateLogIn(username, password);        
    }
}