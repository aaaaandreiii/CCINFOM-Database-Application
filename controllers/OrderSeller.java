package controllers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import models.Customer;
import models.Item;
import models.Order;
import models.Seller;
import models.User;
import views.ViewMain;

public class OrderSeller implements MouseListener {

    private ViewMain gui;
    private Seller user;
    private ArrayList<models.Order> orderList;

    public OrderSeller(ViewMain gui, User user) {
        this.gui = gui;
        this.user = (Seller)user;

        loadFromDb();
        gui.getOrderSeller().loadOrder(orderList);
        gui.getOrderSeller().setListener(this);
    }

    public void loadFromDb() {
        //TO DO: get seller's orders from database and load items
        orderList = new ArrayList<models.Order>();

        //for testing only
        orderList.add(new Order(new Customer(0, "Stupid", "Bitch", null, null, "WHORE HOUSE"), new Item("labobo", 500, "stupid shit", 3), 2, "11/23/2024", "Credit Card", "Unpaid", "Pending"));
        orderList.add(new Order(new Customer(0, "Stupid", "Bitch", null, null, "WHORE HOUSE FUCKING STREET"), new Item("laputa", 400, "stupid piece of shit", 3), 7, "11/21/2024", "Debit Card", "Paid", "Delivered"));
        orderList.add(new Order(new Customer(0, "Stupid", "Bitch", null, null, "WHORE HOUSE"), new Item("tangina", 4000.22, "tang", 3), 10, "11/22/2024", "Online Payment", "Unpaid", "Shipped"));
        orderList.add(new Order(new Customer(0, "Stupid", "Bitch", null, null, "WHORE HOUSE"), new Item("gago", 12, "fucking loser", 3), 5, "11/24/2024", "Credit Card", "Unpaid", "Pending"));
        orderList.add(new Order(new Customer(0, "Stupid", "Bitch", null, null, "WHORE HOUSE"), new Item("labobo", 500, "stupid shit", 3), 2, "11/23/2024", "Credit Card", "Unpaid", "Pending"));
        orderList.add(new Order(new Customer(0, "Stupid", "Bitch", null, null, "WHORE HOUSE"), new Item("laputa", 400, "stupid piece of shit", 3), 7, "11/21/2024", "Credit Card", "Unpaid", "Pending"));
        orderList.add(new Order(new Customer(0, "Stupid", "Bitch", null, null, "WHORE HOUSE"), new Item("tangina", 4000.22, "tang", 3), 10, "11/22/2024", "Credit Card", "Unpaid", "Pending"));
        orderList.add(new Order(new Customer(0, "Stupid", "Bitch", null, null, "WHORE HOUSE"), new Item("gago", 12, "fucking loser", 3), 5, "11/24/2024", "Credit Card", "Unpaid", "Pending"));
        orderList.add(new Order(new Customer(0, "Stupid", "Bitch", null, null, "WHORE HOUSE"), new Item("labobo", 500, "stupid shit", 3), 2, "11/23/2024", "Credit Card", "Unpaid", "Pending"));
        orderList.add(new Order(new Customer(0, "Stupid", "Bitch", null, null, "WHORE HOUSE"), new Item("laputa", 400, "stupid piece of shit", 3), 7, "11/21/2024", "Credit Card", "Unpaid", "Pending"));
        orderList.add(new Order(new Customer(0, "Stupid", "Bitch", null, null, "WHORE HOUSE"), new Item("tangina", 4000.22, "tang", 3), 10, "11/22/2024", "Credit Card", "Unpaid", "Pending"));
        orderList.add(new Order(new Customer(0, "Stupid", "Bitch", null, null, "WHORE HOUSE"), new Item("gago", 12, "fucking loser", 3), 5, "11/24/2024", "Credit Card", "Unpaid", "Pending"));
        orderList.add(new Order(new Customer(0, "Stupid", "Bitch", null, null, "WHORE HOUSE"), new Item("labobo", 500, "stupid shit", 3), 2, "11/23/2024", "Credit Card", "Unpaid", "Pending"));
        orderList.add(new Order(new Customer(0, "Stupid", "Bitch", null, null, "WHORE HOUSE"), new Item("laputa", 400, "stupid piece of shit", 3), 7, "11/21/2024", "Credit Card", "Unpaid", "Pending"));
        orderList.add(new Order(new Customer(0, "Stupid", "Bitch", null, null, "WHORE HOUSE"), new Item("tangina", 4000.22, "tang", 3), 10, "11/22/2024", "Credit Card", "Unpaid", "Pending"));
        orderList.add(new Order(new Customer(0, "Stupid", "Bitch", null, null, "WHORE HOUSE"), new Item("gago", 12, "fucking loser", 3), 5, "11/24/2024", "Credit Card", "Unpaid", "Pending"));
        orderList.add(new Order(new Customer(0, "Stupid", "Bitch", null, null, "WHORE HOUSE"), new Item("labobo", 500, "stupid shit", 3), 2, "11/23/2024", "Credit Card", "Unpaid", "Pending"));
        orderList.add(new Order(new Customer(0, "Stupid", "Bitch", null, null, "WHORE HOUSE"), new Item("laputa", 400, "stupid piece of shit", 3), 7, "11/21/2024", "Credit Card", "Unpaid", "Pending"));
        orderList.add(new Order(new Customer(0, "Stupid", "Bitch", null, null, "WHORE HOUSE"), new Item("tangina", 4000.22, "tang", 3), 10, "11/22/2024", "Credit Card", "Unpaid", "Pending"));
        orderList.add(new Order(new Customer(0, "Stupid", "Bitch", null, null, "WHORE HOUSE"), new Item("gago", 12, "fucking loser", 3), 5, "11/24/2024", "Credit Card", "Unpaid", "Pending"));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == gui.getOrderSeller().getBtnBack()) {
            gui.getContentPane().removeAll();
            gui.getContentPane().add(gui.getMainMenu().getMainPanel());
            gui.revalidate();
            gui.repaint();
        } else {
            Object obj = e.getSource();
            int index = gui.getOrderSeller().getBtnAccept().indexOf(obj);
            if (index != -1) {
                int idx = gui.getOrderSeller().getBtnAccept().get(index).index;
                orderList.get(idx).setStatus("Shipped");

                //TO DO: update order status in db
            }

            gui.getOrderSeller().loadOrder(orderList);
            gui.getOrderSeller().setListener(this);
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
