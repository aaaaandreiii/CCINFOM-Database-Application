package controllers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import models.Customer;
import models.Item;
import models.User;
import views.ViewMain;

public class WishList implements MouseListener {

    private ViewMain gui;
    private Customer user;
    private models.WishList wishList;

    public WishList(ViewMain gui, User user) {
        this.gui = gui;
        this.user = (Customer)user;

        loadFromDb(); //this initializes shopping cart with information from db
        loadWishList(); //this calls the loadshoppingcart method of gui
        
        gui.getWishList().setListener(this);
    }

    public void loadFromDb() {
        //TO DO: get wishlist from database and load items
        ArrayList<Item> items = new ArrayList<Item>();
        ArrayList<Boolean> selected = new ArrayList<Boolean>();

        items.add(new Item("labobo", 69420.123123, "poopmart", 3));
        selected.add(true);
        
        items.add(new Item("wopski", 1000.50, "henwo's mart", 1));
        selected.add(false);

        items.add(new Item("wopski", 123, "ligma", 13));
        selected.add(false);
        
        items.add(new Item("woppy angel", 4000, "bolz", 42));
        selected.add(true);

        items.add(new Item("labobo", 69420.123123, "poopmart", 3));
        selected.add(true);
        
        items.add(new Item("wopski", 1000.50, "henwo's mart", 1));
        selected.add(false);

        items.add(new Item("wopski", 123, "ligma", 13));
        selected.add(false);
        
        items.add(new Item("woppy angel", 4000, "bolz", 42));
        selected.add(true);

        items.add(new Item("labobo", 69420.123123, "poopmart", 3));
        selected.add(true);
        
        items.add(new Item("wopski", 1000.50, "henwo's mart", 1));
        selected.add(false);

        items.add(new Item("wopski", 123, "ligma", 13));
        selected.add(false);
        
        items.add(new Item("woppy angel", 4000, "bolz", 42));
        selected.add(true);

        this.wishList = new models.WishList(items, selected);
    }

    public void loadWishList() {
        gui.getWishList().loadWishList(wishList);
        gui.getWishList().setListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == gui.getWishList().getBtnBack()) {
            //back to main menu
            gui.getContentPane().removeAll();
            gui.getContentPane().add(gui.getMainMenu().getMainPanel());
            gui.revalidate();
            gui.repaint();
        } else if (e.getSource() == gui.getWishList().getBtnAddToCart()) {
            //logic for ordering
            wishList.removeAllSelected();
            
            //TO DO: update db, remove items from wishlist, add items to shopping cart

            
            //then go back to main menu
            gui.getContentPane().removeAll();
            gui.getContentPane().add(gui.getMainMenu().getMainPanel());
            gui.revalidate();
            gui.repaint();
        } else {
            Object obj = e.getSource();

            // if checkbox was pressed
            int index = gui.getWishList().getItemCheckBoxes().indexOf(obj);
            if (index != -1){
                wishList.getSelected().set(index, !wishList.getSelected().get(index));
            }

            loadWishList();
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
