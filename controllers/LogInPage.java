package controllers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import models.Customer;
import models.Seller;
import models.User;
import views.ViewMain;

public class LogInPage implements MouseListener{
    private  ViewMain gui;

    /**
     * Creates a controller for the log in page
     * Sets the listener for the log in page GUI to this
     * @param MainMenu the main GUI that contains all other views
     */

     public LogInPage(ViewMain gui) {
        this.gui = gui;
        gui.getLogInPage().setListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == gui.getLogInPage().getBtnLogin()) {
            String username = gui.getLogInPage().getUsername().trim();
            String password = gui.getLogInPage().getPassword().trim(); 
            
            //TO DO: validate your user login, check database for any matches then create a user variable to be passed to the gui 
            User user = null;
            user = new Customer(0, "Juan", "dela Cruz", null, null, "Quezon City"); //for testing only
            user = new Seller(0, "Jane", "dela Cruz"); //for testing only

            //on success, redirect user to the main menu
            //if (user != null) {
            if (user != null) { //FOR TESTING ONLY
                //initialize the controller for main menu
                MainMenu mainMenu = new MainMenu(gui, user);

                gui.getLogInPage().setErrorVisible(false);
                gui.getLogInPage().clearInput();

                gui.getContentPane().removeAll();
                gui.getContentPane().add(gui.getMainMenu().getMainPanel());
                gui.revalidate();
                gui.repaint();
            }
            
            //if not, stay on login page and display an error
            if (user == null) {
                gui.getLogInPage().setErrorVisible(true);
            }
        } else if (e.getSource() == gui.getLogInPage().getBtnSignup()) {
            gui.getLogInPage().setErrorVisible(false);
            gui.getLogInPage().clearInput();

            gui.getContentPane().removeAll();
            gui.getContentPane().add(gui.getSignUp().getMainPanel());
            gui.revalidate();
            gui.repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
    
}