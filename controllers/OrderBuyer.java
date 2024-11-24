package controllers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import models.Customer;
import models.Item;
import models.Order;
import models.User;
import views.ViewMain;

public class OrderBuyer implements MouseListener {

    private ViewMain gui;
    private Customer user;
    private ArrayList<models.Order> orderList;

    public OrderBuyer(ViewMain gui, User user) {
        this.gui = gui;
        this.user = (Customer)user;

        loadFromDb();
        gui.getOrderBuyer().loadOrder(orderList);
        gui.getOrderBuyer().setListener(this);
    }

    public void loadFromDb() {
        //TO DO: get customer's orders from database and load items
        orderList = new ArrayList<models.Order>();

        //for testing only
        orderList.add(new Order(user, new Item("labubu", 500, "skibidi", 3), 2, "11/23/2024", "Credit Card", "Unpaid", "Pending"));
        orderList.add(new Order(user, new Item("labubu", 400, "SockCon", 3), 7, "11/21/2024", "Debit Card", "Paid", "Delivered"));
        orderList.add(new Order(user, new Item("Rizzler", 4000.22, "hehe", 3), 10, "11/22/2024", "Online Payment", "Unpaid", "Shipped"));
        orderList.add(new Order(user, new Item("", 12, "fucking loser", 3), 5, "11/24/2024", "Credit Card", "Unpaid", "Pending"));
        orderList.add(new Order(user, new Item("labobo", 500, "stupid shit", 3), 2, "11/23/2024", "Credit Card", "Unpaid", "Pending"));
        orderList.add(new Order(user, new Item("laputa", 400, "stupid piece of shit", 3), 7, "11/21/2024", "Credit Card", "Unpaid", "Pending"));
        orderList.add(new Order(user, new Item("tangina", 4000.22, "tang", 3), 10, "11/22/2024", "Credit Card", "Unpaid", "Pending"));
        orderList.add(new Order(user, new Item("gago", 12, "fucking loser", 3), 5, "11/24/2024", "Credit Card", "Unpaid", "Pending"));
        orderList.add(new Order(user, new Item("labobo", 500, "stupid shit", 3), 2, "11/23/2024", "Credit Card", "Unpaid", "Pending"));
        orderList.add(new Order(user, new Item("laputa", 400, "stupid piece of shit", 3), 7, "11/21/2024", "Credit Card", "Unpaid", "Pending"));
        orderList.add(new Order(user, new Item("tangina", 4000.22, "tang", 3), 10, "11/22/2024", "Credit Card", "Unpaid", "Pending"));
        orderList.add(new Order(user, new Item("gago", 12, "fucking loser", 3), 5, "11/24/2024", "Credit Card", "Unpaid", "Pending"));
        orderList.add(new Order(user, new Item("labobo", 500, "stupid shit", 3), 2, "11/23/2024", "Credit Card", "Unpaid", "Pending"));
        orderList.add(new Order(user, new Item("laputa", 400, "stupid piece of shit", 3), 7, "11/21/2024", "Credit Card", "Unpaid", "Pending"));
        orderList.add(new Order(user, new Item("tangina", 4000.22, "tang", 3), 10, "11/22/2024", "Credit Card", "Unpaid", "Pending"));
        orderList.add(new Order(user, new Item("gago", 12, "fucking loser", 3), 5, "11/24/2024", "Credit Card", "Unpaid", "Pending"));
        orderList.add(new Order(user, new Item("labobo", 500, "stupid shit", 3), 2, "11/23/2024", "Credit Card", "Unpaid", "Pending"));
        orderList.add(new Order(user, new Item("laputa", 400, "stupid piece of shit", 3), 7, "11/21/2024", "Credit Card", "Unpaid", "Pending"));
        orderList.add(new Order(user, new Item("tangina", 4000.22, "tang", 3), 10, "11/22/2024", "Credit Card", "Unpaid", "Pending"));
        orderList.add(new Order(user, new Item("gago", 12, "fucking loser", 3), 5, "11/24/2024", "Credit Card", "Unpaid", "Pending"));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == gui.getOrderBuyer().getBtnBack()) {
            gui.getContentPane().removeAll();
            gui.getContentPane().add(gui.getMainMenu().getMainPanel());
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
