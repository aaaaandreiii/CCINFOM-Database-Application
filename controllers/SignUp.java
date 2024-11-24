package controllers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import models.Customer;
import models.User;
import views.ViewMain;

public class SignUp implements MouseListener{
    private ViewMain gui;

    public SignUp(ViewMain gui) {
        this.gui = gui;
        gui.getSignUp().setListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == gui.getSignUp().getBtnCreate()) {
            String username = gui.getSignUp().getUsername().trim();
            String password = gui.getSignUp().getPassword().trim(); 
            String firstName = gui.getSignUp().getFirstName().trim();
            String lastName = gui.getSignUp().getLastName().trim();
            String phone = gui.getSignUp().getPhone().trim();
            String deliveryAddress = gui.getSignUp().getDeliveryAddress().trim();
            
            //TO DO: check if username already exists in the database
            User user = null;

            if (username.equals("") ||
                password.equals("") ||
                firstName.equals("") ||
                lastName.equals("") ||
                phone.equals("") ||
                deliveryAddress.equals("")) {

                gui.getSignUp().setErrorText("Kindly fill up the entire form.");
                gui.getSignUp().setErrorVisible(true); 
            } else if (user != null) {
                gui.getSignUp().setErrorText("Username is already taken.");
                gui.getSignUp().setErrorVisible(true);
            } else {
                // TO DO: add the user to the database
                user = new Customer(0, firstName, lastName, username, phone, deliveryAddress); //for testing only
                
                //initialize the controller for main menu
                MainMenu mainMenu = new MainMenu(gui, user);

                gui.getSignUp().setErrorVisible(false);
                gui.getSignUp().clearInput();

                gui.getContentPane().removeAll();
                gui.getContentPane().add(gui.getMainMenu().getMainPanel());
                gui.revalidate();
                gui.repaint();
            }
        } else if (e.getSource() == gui.getSignUp().getBtnBack()) {
            gui.getSignUp().setErrorVisible(false);
            gui.getSignUp().clearInput();

            gui.getContentPane().removeAll();
            gui.getContentPane().add(gui.getLogInPage().getMainPanel());
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
