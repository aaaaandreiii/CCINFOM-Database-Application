package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;

public class OrderSeller {
    private JPanel mainPanel;
    private JPanel headerPanel;
    private JPanel contentPanel;

    private JLabel lblOrder;

    private ArrayList<JLabel> itemCustomer = new ArrayList<JLabel>();
    private ArrayList<JLabel> itemCustomerAddress = new ArrayList<JLabel>();
    private ArrayList<JLabel> itemNames = new ArrayList<JLabel>();
    private ArrayList<JLabel> itemQuantities = new ArrayList<JLabel>();
    private ArrayList<JLabel> itemPrices = new ArrayList<JLabel>();
    private ArrayList<JLabel> orderDates = new ArrayList<JLabel>();
    private ArrayList<JLabel> mopsAndPaymentStatuses = new ArrayList<JLabel>();
    private ArrayList<JLabel> statuses = new ArrayList<JLabel>();

    private ArrayList<Object_RoundedButton> btnAccept = new ArrayList<Object_RoundedButton>();

    private JLabel btnBack;

    private Color cBackground = Color.decode("#305d7a");
    private Color cButton = Color.decode("#e16a33");

    private SpringLayout layout = new SpringLayout();

    public OrderSeller() {
        mainPanel = new JPanel(null);
        mainPanel.setBackground(cButton);

        headerPanel = new JPanel(layout);
        headerPanel.setBackground(cBackground);
        headerPanel.setBounds(0,0, 1423, 80);
        mainPanel.add(headerPanel);

        lblOrder = new JLabel("Order");
        lblOrder.setPreferredSize(new Dimension(600, 50));
        headerPanel.add(lblOrder);
        layout.putConstraint(SpringLayout.WEST, lblOrder, 30, SpringLayout.WEST, headerPanel);
        layout.putConstraint(SpringLayout.NORTH, lblOrder, 20, SpringLayout.NORTH, headerPanel);
    
        btnBack = new JLabel("X");
        headerPanel.add(btnBack);
        layout.putConstraint(SpringLayout.EAST, btnBack, -30, SpringLayout.EAST, headerPanel);
        layout.putConstraint(SpringLayout.NORTH, btnBack, 20, SpringLayout.NORTH, headerPanel);
    
        Font font = new Font("Serif", Font.PLAIN, 24);
        try {
			File fontStyle = new File("./fonts/horizon.otf");
			font = Font.createFont(Font.TRUETYPE_FONT, fontStyle).deriveFont(24f);
		} catch (Exception e) {
			e.printStackTrace();
			font = new Font("Serif", Font.PLAIN, 24);
        }

        lblOrder.setFont(font);
        lblOrder.setForeground(Color.WHITE);
        btnBack.setFont(font);
        btnBack.setForeground(Color.WHITE);

        contentPanel = new JPanel(layout);
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setPreferredSize(new Dimension(1426, 874));

        JScrollPane scrollPane = new JScrollPane(contentPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(15, 0));
        scrollPane.setBounds(0, 80, 1426, 877);
        mainPanel.add(scrollPane);
    }

    public void loadOrder(ArrayList<models.Order> orderList) {
        int height = orderList.size() * 90; // number of items/2 x (size of bg + in between padding) + bottom padding
        if (height < 874){
            height = 874;
        }
        contentPanel.setPreferredSize(new Dimension(1426, height));

        contentPanel.removeAll();

        itemCustomer = new ArrayList<JLabel>();
        itemCustomerAddress = new ArrayList<JLabel>();
        itemNames = new ArrayList<JLabel>();
        itemQuantities = new ArrayList<JLabel>();
        itemPrices = new ArrayList<JLabel>();
        orderDates = new ArrayList<JLabel>();
        mopsAndPaymentStatuses = new ArrayList<JLabel>();
        statuses = new ArrayList<JLabel>();

        btnAccept = new ArrayList<Object_RoundedButton>();

        for (int i = 0; i < orderList.size(); i++) {
            JLabel customer = new JLabel(orderList.get(i).getCustomer().getFirstName() + " " + orderList.get(i).getCustomer().getLastName());
            JLabel address = new JLabel(orderList.get(i).getCustomer().getDeliveryAddress());
            JLabel name = new JLabel(orderList.get(i).getItem().getName());
            JLabel qty = new JLabel("QTY: " + orderList.get(i).getQuantity());
            JLabel price = new JLabel(orderList.get(i).getTotalInString());
            JLabel date = new JLabel(orderList.get(i).getDate());
            JLabel mopAndPaymentStatus = new JLabel(orderList.get(i).getMop() + "(" + orderList.get(i).getPaymentStatus() + ")");
            JLabel status = new JLabel(orderList.get(i).getStatus());

            Object_RoundedButton btn = null;
            if (orderList.get(i).getStatus().equalsIgnoreCase("pending")) {
                btn = new Object_RoundedButton("Accept");
                btn.index = i;
                btn.setBackground(cButton);
                btn.setForeground(Color.WHITE);
                btn.setFocusable(false);
            }
            

            Font font = new Font("Serif", Font.PLAIN, 17);
            try {
                File fontStyle = new File("./fonts/horizon.otf");
                font = Font.createFont(Font.TRUETYPE_FONT, fontStyle).deriveFont(17f);
            } catch (Exception e) {
                e.printStackTrace();
                font = new Font("Serif", Font.PLAIN, 17);
            }

            name.setFont(font);
            name.setForeground(cBackground);
            qty.setFont(font);
            qty.setForeground(cBackground);
            price.setFont(font);
            price.setForeground(cBackground);
            date.setFont(font);
            date.setForeground(cBackground);

            font = new Font("Serif", Font.PLAIN, 13);
            try {
                File fontStyle = new File("./fonts/horizon.otf");
                font = Font.createFont(Font.TRUETYPE_FONT, fontStyle).deriveFont(13f);
            } catch (Exception e) {
                e.printStackTrace();
                font = new Font("Serif", Font.PLAIN, 13);
            }

            customer.setFont(font);
            customer.setForeground(cBackground);
            address.setFont(font);
            address.setForeground(cBackground);
            mopAndPaymentStatus.setFont(font);
            mopAndPaymentStatus.setForeground(cBackground);
            status.setFont(font);
            status.setForeground(cBackground);

            itemCustomer.add(customer);
            itemCustomerAddress.add(address);
            itemNames.add(name);
            itemQuantities.add(qty);
            itemPrices.add(price);
            orderDates.add(date);
            mopsAndPaymentStatuses.add(mopAndPaymentStatus);
            statuses.add(status);

            contentPanel.add(customer);
            contentPanel.add(address);
            contentPanel.add(name);
            contentPanel.add(qty);
            contentPanel.add(price);
            contentPanel.add(date);
            contentPanel.add(mopAndPaymentStatus);
            contentPanel.add(status);

            if (btn != null) {
                btn.setFont(font);
                btnAccept.add(btn);
                contentPanel.add(btn);
            }

            if (i == 0) {
                layout.putConstraint(SpringLayout.WEST, name, 40, SpringLayout.WEST, contentPanel);
                layout.putConstraint(SpringLayout.NORTH, name, 40, SpringLayout.NORTH, contentPanel);
            } else {
                layout.putConstraint(SpringLayout.WEST, name, 40, SpringLayout.WEST, contentPanel);
                layout.putConstraint(SpringLayout.NORTH, name, 80, SpringLayout.NORTH, itemNames.get(i-1));
            }
            
            layout.putConstraint(SpringLayout.WEST, customer, 0, SpringLayout.WEST, name);
            layout.putConstraint(SpringLayout.NORTH, customer, 5, SpringLayout.SOUTH, name);

            layout.putConstraint(SpringLayout.WEST, address, 0, SpringLayout.WEST, name);
            layout.putConstraint(SpringLayout.NORTH, address, 5, SpringLayout.SOUTH, customer);

            if (btn != null) {
                layout.putConstraint(SpringLayout.WEST, btn, 400, SpringLayout.WEST, contentPanel);
                layout.putConstraint(SpringLayout.NORTH, btn, 0, SpringLayout.NORTH, name);
            }
        
            layout.putConstraint(SpringLayout.WEST, qty, 700, SpringLayout.WEST, contentPanel);
            layout.putConstraint(SpringLayout.NORTH, qty, 0, SpringLayout.NORTH, name);

            layout.putConstraint(SpringLayout.EAST, price, -300, SpringLayout.EAST, contentPanel);
            layout.putConstraint(SpringLayout.NORTH, price, 0, SpringLayout.NORTH, name);

            layout.putConstraint(SpringLayout.EAST, date, -100, SpringLayout.EAST, contentPanel);
            layout.putConstraint(SpringLayout.NORTH, date, 0, SpringLayout.NORTH, name);

            layout.putConstraint(SpringLayout.EAST, mopAndPaymentStatus, 0, SpringLayout.EAST, price);
            layout.putConstraint(SpringLayout.NORTH, mopAndPaymentStatus, 5, SpringLayout.SOUTH, price);

            layout.putConstraint(SpringLayout.WEST, status, 0, SpringLayout.WEST, date);
            layout.putConstraint(SpringLayout.NORTH, status, 5, SpringLayout.SOUTH, date);
        }

        contentPanel.validate();
        contentPanel.repaint();
    }

    public JPanel getMainPanel () {
        return mainPanel;
    }

    public JLabel getBtnBack() {
        return btnBack;
    }

    public ArrayList<Object_RoundedButton> getBtnAccept() {
        return btnAccept;
    }

    public void setListener(MouseListener mouseListener) {
        btnBack.addMouseListener(mouseListener);

        for (int i = 0; i < btnAccept.size(); i++) {
            btnAccept.get(i).addMouseListener(mouseListener);
        }
    }
}
