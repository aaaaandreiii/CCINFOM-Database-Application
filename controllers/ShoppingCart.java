package controllers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JLabel;

import models.Customer;
import models.Item;
import models.User;
import views.ViewMain;

public class ShoppingCart implements MouseListener {

    private ViewMain gui;
    private models.Customer user;
    private models.ShoppingCart shoppingCart;

    public ShoppingCart(ViewMain gui, User user) {
        this.gui = gui;
        this.user = (Customer)user;

        loadFromDb(); //this initializes shopping cart with information from db
        loadShoppingCart(); //this calls the loadshoppingcart method of gui
        setAddress();
        loadTotal();
        
        gui.getShoppingCart().setListener(this);
    }

    public void loadFromDb() {
        //TO DO: get shopping cart from database and load items
        ArrayList<Item> items = new ArrayList<Item>();
        ArrayList<Integer> quantities = new ArrayList<Integer>();
        ArrayList<Boolean> selected = new ArrayList<Boolean>();

        //for testing only
        items.add(new Item("labobo", 69420.123123, "poopmart", 3));
        quantities.add(3);
        selected.add(true);
        items.add(new Item("wopski", 1000.50, "henwo's mart", 1));
        quantities.add(2);
        selected.add(false);
        items.add(new Item("wopski", 123, "ligma", 13));
        quantities.add(2);
        selected.add(false);
        items.add(new Item("woppy angel", 4000, "bolz", 42));
        quantities.add(1);
        selected.add(true);
        items.add(new Item("labobo", 69420.123123, "poopmart", 3));
        quantities.add(3);
        selected.add(true);
        items.add(new Item("wopski", 1000.50, "henwo's mart", 1));
        quantities.add(2);
        selected.add(false);
        items.add(new Item("wopski", 123, "ligma", 13));
        quantities.add(2);
        selected.add(false);
        items.add(new Item("woppy angel", 4000, "bolz", 42));
        quantities.add(1);
        selected.add(true);
        items.add(new Item("labobo", 69420.123123, "poopmart", 3));
        quantities.add(3);
        selected.add(true);
        items.add(new Item("wopski", 1000.50, "henwo's mart", 1));
        quantities.add(2);
        selected.add(false);
        items.add(new Item("wopski", 123, "ligma", 13));
        quantities.add(2);
        selected.add(false);
        items.add(new Item("woppy angel", 4000, "bolz", 42));
        quantities.add(1);
        selected.add(true);

        this.shoppingCart = new models.ShoppingCart(items, quantities, selected);
    }

    public void loadShoppingCart() {
        gui.getShoppingCart().loadShoppingCart(shoppingCart);
        gui.getShoppingCart().setListener(this);
    }

    private void loadTotal() {
        String total = shoppingCart.getTotalInString();
        gui.getShoppingCart().loadTotal(total);
    }

    private void setAddress() {
        gui.getShoppingCart().setAddress(user.getDeliveryAddress());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == gui.getShoppingCart().getBtnBack()) {
            //back to main menu
            gui.getContentPane().removeAll();
            gui.getContentPane().add(gui.getMainMenu().getMainPanel());
            gui.revalidate();
            gui.repaint();
        } else if (e.getSource() == gui.getShoppingCart().getBtnCod()) {
            gui.getShoppingCart().setMop("Cash on Delivery");
        } else if (e.getSource() == gui.getShoppingCart().getBtnOnlinePayment()) {
            gui.getShoppingCart().setMop("Online Payment");
        } else if (e.getSource() == gui.getShoppingCart().getBtnCredit()) {
            gui.getShoppingCart().setMop("Credit Card");
        } else if (e.getSource() == gui.getShoppingCart().getBtnDebit()) {
            gui.getShoppingCart().setMop("Debit Card");
        } else if (e.getSource() == gui.getShoppingCart().getBtnOrder()) {
            shoppingCart.removeAllSelected();

            //TO DO: update db, remove items from shopping cart, create new orders


            //then go back to main menu
            gui.getContentPane().removeAll();
            gui.getContentPane().add(gui.getMainMenu().getMainPanel());
            gui.revalidate();
            gui.repaint();
        } else {
            Object obj = e.getSource();

            // if checkbox was pressed
            int index = gui.getShoppingCart().getItemCheckBoxes().indexOf(obj);
            if (index != -1){
                shoppingCart.getSelected().set(index, !shoppingCart.getSelected().get(index));
            } else {
                index = gui.getShoppingCart().getItemMinus().indexOf(obj); 
                if (index != -1) {
                    shoppingCart.decreaseQuantity(index);
                } else {
                    index = gui.getShoppingCart().getItemPlus().indexOf(obj); 
                    if (index != -1) {
                        if (shoppingCart.getShoppingCart().get(index).getAvailable() >= shoppingCart.getQuantities().get(index) + 1) {
                           shoppingCart.increaseQuantity(index); 
                        }
                    }
                }
            }

            gui.getShoppingCart().loadShoppingCart(shoppingCart);
            gui.getShoppingCart().setListener(this);
            loadTotal();
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
