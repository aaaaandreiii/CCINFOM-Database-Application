package controllers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import models.Customer;
import models.Item;
import models.Seller;
import models.User;
import views.ViewMain;

public class MainMenu implements MouseListener {

    private ViewMain gui;
    private User user;
    private ArrayList<Item> itemsList = new ArrayList<Item>();

    /**
     * Creates a controller for the log in page
     * Sets the listener for the log in page GUI to this
     * @param MainMenu the main GUI that contains all other views
     */

     public MainMenu(ViewMain gui, User user) {
        this.gui = gui;
        this.user = user;

        //initialize main menu with the user
        gui.getMainMenu(user);

        loadItems();
    }

    public void loadItems() {
        //remove listeners from previous items
        gui.getMainMenu().removeListeners(this);

        itemsList = new ArrayList<Item>();

        if (user instanceof Customer) {
            //TO DO: get all items from the database that are still available

        } else if (user instanceof Seller) {
            //TO DO: get all of seller's items regardless of availability
        }

        //for testing only
        itemsList.add(new Item("labobo", 69420.123123, "poopmart", 3));
        itemsList.add(new Item("wopski", 1000.50, "henwo's mart", 1));
        itemsList.add(new Item("woppy angel", 600, "henwo's mart", 10));
        itemsList.add(new Item("wopski", 123, "ligma", 13));
        itemsList.add(new Item("labobo", 377, "fook inn", 0));
        itemsList.add(new Item("woppy angel", 4000, "bolz", 42));
    
        gui.getMainMenu().loadItems(itemsList);
        gui.getMainMenu().setListener(this);
    }

    public void loadItems(String search) {
        gui.getMainMenu().removeListeners(this);

        itemsList = new ArrayList<Item>();
        //TO DO: get items from the database that match search string

        //for testing only
        itemsList.add(new Item("labobo", 69420.123123, "poopmart", 3));
    
        gui.getMainMenu().loadItems(itemsList);
        gui.getMainMenu().setListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == gui.getMainMenu().getBtnSearch()) {
            String search = gui.getMainMenu().getSearch();

            loadItems(search);
        } else if (e.getSource() == gui.getMainMenu().getBtnShoppingCart()) {
            //create a controlller for shopping cart page
            ShoppingCart shoppingCart = new ShoppingCart(gui, user);

            gui.getContentPane().removeAll();
            gui.getContentPane().add(gui.getShoppingCart().getMainPanel());
            gui.revalidate();
            gui.repaint();
        } else if (e.getSource() == gui.getMainMenu().getBtnWishList()) {
            WishList wishList = new WishList(gui, user);

            gui.getContentPane().removeAll();
            gui.getContentPane().add(gui.getWishList().getMainPanel());
            gui.revalidate();
            gui.repaint();
        } else if (e.getSource() == gui.getMainMenu().getBtnOrder()) {
            if (user instanceof models.Customer) {
                OrderBuyer orderBuyer = new OrderBuyer(gui, user);

                gui.getContentPane().removeAll();
                gui.getContentPane().add(gui.getOrderBuyer().getMainPanel());
                gui.revalidate();
                gui.repaint();
            } else if (user instanceof models.Seller) {
                OrderSeller orderSeller = new OrderSeller(gui, user);

                gui.getContentPane().removeAll();
                gui.getContentPane().add(gui.getOrderSeller().getMainPanel());
                gui.revalidate();
                gui.repaint();
            }
        } else if (e.getSource() == gui.getMainMenu().getBtnResupply()) {
            Resupply resupply = new Resupply(gui, user);

            gui.getContentPane().removeAll();
            gui.getContentPane().add(gui.getResupply().getMainPanel());
            gui.revalidate();
            gui.repaint();
        } else {
            Object obj = e.getSource();
            int index = gui.getMainMenu().getItemShoppingCarts().indexOf(obj);
            if (index != -1) {
                //TO DO: add item to shopping cart in db
                System.out.println("added to cart:" + itemsList.get(index).getName());
            } else {
                index = gui.getMainMenu().getItemWishLists().indexOf(obj);

                if (index != -1) {
                    //TO DO: add item to wish list in db
                    System.out.println("added to wish list:" + itemsList.get(index).getName());
                }
            }
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
