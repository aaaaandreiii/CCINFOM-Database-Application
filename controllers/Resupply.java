package controllers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import models.Customer;
import models.Item;
import models.Seller;
import models.User;
import views.ViewMain;

public class Resupply implements MouseListener {
    private ViewMain gui;
    private models.Seller user;
    private ArrayList<Item> inventory;
    
    public Resupply(ViewMain gui, User user) {
        this.gui = gui;
        this.user = (Seller)user;

        loadFromDb();
        loadInventory();
    }

    public void loadFromDb() {
        inventory = new ArrayList<Item>();

        //TO DO: get all of seller's items

        // for testing only
        inventory.add(new Item("labobo", 69420.123123, "poopmart", 3));
        inventory.add(new Item("wopski", 1000.50, "henwo's mart", 1));
        inventory.add(new Item("woppy angel", 600, "henwo's mart", 10));
        inventory.add(new Item("wopski", 123, "ligma", 13));
        inventory.add(new Item("labobo", 377, "fook inn", 0));
        inventory.add(new Item("woppy angel", 4000, "bolz", 42));
        inventory.add(new Item("labobo", 69420.123123, "poopmart", 3));
        inventory.add(new Item("wopski", 1000.50, "henwo's mart", 1));
        inventory.add(new Item("woppy angel", 600, "henwo's mart", 10));
        inventory.add(new Item("wopski", 123, "ligma", 13));
        inventory.add(new Item("labobo", 377, "fook inn", 0));
        inventory.add(new Item("woppy angel", 4000, "bolz", 42));
        inventory.add(new Item("labobo", 69420.123123, "poopmart", 3));
        inventory.add(new Item("wopski", 1000.50, "henwo's mart", 1));
        inventory.add(new Item("woppy angel", 600, "henwo's mart", 10));
        inventory.add(new Item("wopski", 123, "ligma", 13));
        inventory.add(new Item("labobo", 377, "fook inn", 0));
        inventory.add(new Item("woppy angel", 4000, "bolz", 42));
        inventory.add(new Item("labobo", 69420.123123, "poopmart", 3));
        inventory.add(new Item("wopski", 1000.50, "henwo's mart", 1));
        inventory.add(new Item("woppy angel", 600, "henwo's mart", 10));
        inventory.add(new Item("wopski", 123, "ligma", 13));
        inventory.add(new Item("labobo", 377, "fook inn", 0));
        inventory.add(new Item("woppy angel", 4000, "bolz", 42));
        inventory.add(new Item("labobo", 69420.123123, "poopmart", 3));
        inventory.add(new Item("wopski", 1000.50, "henwo's mart", 1));
        inventory.add(new Item("woppy angel", 600, "henwo's mart", 10));
        inventory.add(new Item("wopski", 123, "ligma", 13));
        inventory.add(new Item("labobo", 377, "fook inn", 0));
        inventory.add(new Item("woppy angel", 4000, "bolz", 42));
    }

    public void loadInventory() {
        gui.getResupply().loadInventory(inventory);
        gui.getResupply().setListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == gui.getResupply().getBtnBack()) {
            gui.getContentPane().removeAll();
            gui.getContentPane().add(gui.getMainMenu().getMainPanel());
            gui.revalidate();
            gui.repaint();
        } else {
            Object obj = e.getSource();
            int index = gui.getResupply().getItemMinus().indexOf(obj);
            if (index != -1) {
                inventory.get(index).decreaseAvailable();

                // TO DO: update availability of item in db
            } else {
                index = gui.getResupply().getItemPlus().indexOf(obj); 
                if (index != -1) {
                    inventory.get(index).increaseAvailable();

                    // TO DO: update availability of item in db
                }
            }

            loadInventory();
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
