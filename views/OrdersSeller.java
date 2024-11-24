package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;

import models.*;

public class OrdersSeller {
    private JPanel mainPanel;
    private JPanel headerPanel;
    private JPanel contentPanel;
    private JPanel footerPanel;

    private JLabel lblOrdersSeller;

    private ArrayList<JLabel> itemCheckBoxes = new ArrayList<JLabel>();
    private ArrayList<JLabel> ItemName = new ArrayList<JLabel>();
    private ArrayList<JLabel> CustomerName = new ArrayList<JLabel>();
    private ArrayList<JLabel> DeliveryAdress = new ArrayList<JLabel>();
    private ArrayList<JLabel> itemQuantities = new ArrayList<JLabel>();
    private ArrayList<JLabel>  MOP= new ArrayList<JLabel>();
    private ArrayList<JLabel> itemPrices = new ArrayList<JLabel>();
    private ArrayList<JLabel> PaymentStatus = new ArrayList<JLabel>();
    private ArrayList<JLabel> OrderStatus = new ArrayList<JLabel>();

    private JLabel lblTotalPrice;

    private JLabel btnBack;
    private Object_RoundedButton Accept;

    private Color cBackground = Color.decode("#305d7a");
    private Color cField = Color.decode("#d9d9d9");
    private Color cButton = Color.decode("#e16a33");

    private SpringLayout layout = new SpringLayout();

    public OrdersSeller() {
        mainPanel = new JPanel(null);
        mainPanel.setBackground(cButton);

        headerPanel = new JPanel(layout);
        headerPanel.setBackground(cBackground);
        headerPanel.setBounds(0,0, 1423, 80);
        mainPanel.add(headerPanel);

        lblOrdersSeller = new JLabel("Orders");
        lblOrdersSeller.setPreferredSize(new Dimension(600, 50));
        headerPanel.add(lblOrdersSeller);
        layout.putConstraint(SpringLayout.WEST, lblOrdersSeller, 30, SpringLayout.WEST, headerPanel);
        layout.putConstraint(SpringLayout.NORTH, lblOrdersSeller, 20, SpringLayout.NORTH, headerPanel);

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

        lblOrdersSeller.setFont(font);
        lblOrdersSeller.setForeground(Color.WHITE);
        btnBack.setFont(font);
        btnBack.setForeground(Color.WHITE);
        
        contentPanel = new JPanel(layout);
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setPreferredSize(new Dimension(1426, 700));

        JScrollPane scrollPane = new JScrollPane(contentPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(15, 0));
        scrollPane.setBounds(0, 80, 1426, 703);
        mainPanel.add(scrollPane);

        footerPanel = new JPanel(layout);
        footerPanel.setBackground(cBackground);
        footerPanel.setBounds(0,783, 1423, 300);
        mainPanel.add(footerPanel);

        lblOrdersSeller = new JLabel();
        footerPanel.add(lblOrdersSeller);
        layout.putConstraint(SpringLayout.WEST, lblOrdersSeller, 10, SpringLayout.WEST, footerPanel);
        layout.putConstraint(SpringLayout.NORTH, lblDeliveryAddress, 10, SpringLayout.NORTH, footerPanel);

        lblMop = new JLabel("Mode of Payment: " + "Cash on Delivery");
        footerPanel.add(lblMop);
        layout.putConstraint(SpringLayout.WEST, lblMop, 10, SpringLayout.WEST, footerPanel);
        layout.putConstraint(SpringLayout.NORTH, lblMop, 10, SpringLayout.SOUTH, lblDeliveryAddress);

        btnCod = new Object_RoundedButton("Cash on Delivery");
        footerPanel.add(btnCod);
        layout.putConstraint(SpringLayout.WEST, btnCod, 40, SpringLayout.EAST, lblMop);
        layout.putConstraint(SpringLayout.NORTH, btnCod, 0, SpringLayout.NORTH, lblMop);
        
        btnOnlinePayment = new Object_RoundedButton("Online Payment");
        footerPanel.add(btnOnlinePayment);
        layout.putConstraint(SpringLayout.WEST, btnOnlinePayment, 10, SpringLayout.EAST, btnCod);
        layout.putConstraint(SpringLayout.NORTH, btnOnlinePayment, 0, SpringLayout.NORTH, lblMop);

        btnCredit = new Object_RoundedButton("Credit Card");
        footerPanel.add(btnCredit);
        layout.putConstraint(SpringLayout.WEST, btnCredit, 10, SpringLayout.EAST, btnOnlinePayment);
        layout.putConstraint(SpringLayout.NORTH, btnCredit, 0, SpringLayout.NORTH, lblMop);

        btnDebit = new Object_RoundedButton("Debit Card"); 
        footerPanel.add(btnDebit);
        layout.putConstraint(SpringLayout.WEST, btnDebit, 10, SpringLayout.EAST, btnCredit);
        layout.putConstraint(SpringLayout.NORTH, btnDebit, 0, SpringLayout.NORTH, lblMop);

        lblTotalPrice = new JLabel();
        loadTotal("0 PHP");
        footerPanel.add(lblTotalPrice);
        layout.putConstraint(SpringLayout.EAST, lblTotalPrice, -10, SpringLayout.EAST, footerPanel);
        layout.putConstraint(SpringLayout.NORTH, lblTotalPrice, 10, SpringLayout.NORTH, footerPanel);
        
        btnOrder = new Object_RoundedButton("Order");
        footerPanel.add(btnOrder);
        layout.putConstraint(SpringLayout.EAST, btnOrder, 0, SpringLayout.EAST, lblTotalPrice);
        layout.putConstraint(SpringLayout.NORTH, btnOrder, 10, SpringLayout.SOUTH, lblTotalPrice);

        font = new Font("Serif", Font.PLAIN, 17);
        try {
			File fontStyle = new File("./fonts/WorkSans-Light.ttf");
			font = Font.createFont(Font.TRUETYPE_FONT, fontStyle).deriveFont(17f);
		} catch (Exception e) {
			e.printStackTrace();
			font = new Font("Serif", Font.PLAIN, 17);
        }

        lblDeliveryAddress.setFont(font);
        lblDeliveryAddress.setForeground(Color.WHITE);

        lblMop.setFont(font);
        lblMop.setForeground(Color.WHITE);

        btnCod.setFont(font);
        btnCod.setForeground(cBackground);
        btnCod.setFocusable(false);

        btnOnlinePayment.setFont(font);
        btnOnlinePayment.setForeground(cBackground);
        btnOnlinePayment.setFocusable(false);

        btnCredit.setFont(font);
        btnCredit.setForeground(cBackground);
        btnCredit.setFocusable(false);

        btnDebit.setFont(font);
        btnDebit.setForeground(cBackground);
        btnDebit.setFocusable(false);

        lblTotalPrice.setFont(font);
        lblTotalPrice.setForeground(Color.WHITE);

        btnOrder.setFont(font);
        btnOrder.setForeground(Color.WHITE);
        btnOrder.setBackground(cButton);
        btnOrder.setFocusable(false);
    }

    public void loadShoppingCart(models.ShoppingCart shoppingCart) {
        int height = shoppingCart.getShoppingCart().size() * 100; // number of items/2 x (size of bg + in between padding) + bottom padding
        if (height < 700){
            height = 700;
        }
        contentPanel.setPreferredSize(new Dimension(1426, height));

        //delete previous items
        contentPanel.removeAll();

        //clear previously loaded items every time you load new items
        itemCheckBoxes = new ArrayList<JLabel>();
        itemSuppliers = new ArrayList<JLabel>();
        itemNames = new ArrayList<JLabel>();
        itemQuantities = new ArrayList<JLabel>();
        itemPrices = new ArrayList<JLabel>();
        itemPlus = new ArrayList<JLabel>();
        itemMinus = new ArrayList<JLabel>();

        ArrayList<Item> items = shoppingCart.getShoppingCart();
        ArrayList<Integer> quantities = shoppingCart.getQuantities();
        ArrayList<Boolean> selected = shoppingCart.getSelected();

        for (int i = 0; i < items.size(); i++) {
            JLabel checkBox;

            if (selected.get(i)) {
                checkBox = new JLabel(new ImageIcon("./photos/checkbox_ticked.png"));
            } else {
                checkBox = new JLabel(new ImageIcon("./photos/checkbox.png"));
            }

            JLabel supplier = new JLabel(items.get(i).getSupplier());
            JLabel name = new JLabel(items.get(i).getName());
            JLabel quantity = new JLabel("" + quantities.get(i));
            JLabel price = new JLabel(items.get(i).getPriceInString());

            JLabel plus = new JLabel("+");
            JLabel minus = new JLabel("-");

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
            price.setFont(font);
            price.setForeground(cBackground);
            quantity.setFont(font);
            quantity.setForeground(cBackground);
            plus.setFont(font);
            plus.setForeground(cBackground);
            minus.setFont(font);
            minus.setForeground(cBackground);

            font = new Font("Serif", Font.PLAIN, 13);
            try {
                File fontStyle = new File("./fonts/horizon.otf");
                font = Font.createFont(Font.TRUETYPE_FONT, fontStyle).deriveFont(13f);
            } catch (Exception e) {
                e.printStackTrace();
                font = new Font("Serif", Font.PLAIN, 13);
            }

            supplier.setFont(font);
            supplier.setForeground(cBackground);

            itemCheckBoxes.add(checkBox);
            itemNames.add(name);
            itemSuppliers.add(supplier);
            itemQuantities.add(quantity);
            itemPrices.add(price);
            itemPlus.add(plus);
            itemMinus.add(minus);

            contentPanel.add(checkBox);
            contentPanel.add(name);
            contentPanel.add(supplier);
            contentPanel.add(quantity);
            contentPanel.add(price);
            contentPanel.add(plus);
            contentPanel.add(minus);

            if (i == 0) {
                layout.putConstraint(SpringLayout.WEST, checkBox, 40, SpringLayout.WEST, contentPanel);
                layout.putConstraint(SpringLayout.NORTH, checkBox, 40, SpringLayout.NORTH, contentPanel);
            } else {
                layout.putConstraint(SpringLayout.WEST, checkBox, 40, SpringLayout.WEST, contentPanel);
                layout.putConstraint(SpringLayout.NORTH, checkBox, 100, SpringLayout.NORTH, itemCheckBoxes.get(i-1));
            }

            layout.putConstraint(SpringLayout.WEST, supplier, 10, SpringLayout.EAST, checkBox);
            layout.putConstraint(SpringLayout.NORTH, supplier, 0, SpringLayout.NORTH, checkBox);

            layout.putConstraint(SpringLayout.WEST, name, 10, SpringLayout.EAST, checkBox);
            layout.putConstraint(SpringLayout.NORTH, name, 5, SpringLayout.SOUTH, supplier);

            layout.putConstraint(SpringLayout.WEST, quantity, 600, SpringLayout.EAST, checkBox);
            layout.putConstraint(SpringLayout.NORTH, quantity, 5, SpringLayout.SOUTH, supplier);

            layout.putConstraint(SpringLayout.WEST, plus, 20, SpringLayout.EAST, quantity);
            layout.putConstraint(SpringLayout.NORTH, plus, 5, SpringLayout.SOUTH, supplier);

            layout.putConstraint(SpringLayout.EAST, minus, -20, SpringLayout.WEST, quantity);
            layout.putConstraint(SpringLayout.NORTH, minus, 5, SpringLayout.SOUTH, supplier);

            layout.putConstraint(SpringLayout.EAST, price, -100, SpringLayout.EAST, contentPanel);
            layout.putConstraint(SpringLayout.NORTH, price, 5, SpringLayout.SOUTH, supplier);
        }
        
        contentPanel.validate();
        contentPanel.repaint();
    }

    public void loadTotal(String total) {
        lblTotalPrice.setText("Total: " + total);
        footerPanel.validate();
        footerPanel.repaint();
    }

    public void setAddress(String address) {
        lblDeliveryAddress.setText(address);
        footerPanel.validate();
        footerPanel.repaint();
    }

    public void setMop(String mop) {
        lblMop.setText("Mode of Payment: " + mop);
        footerPanel.validate();
        footerPanel.repaint();
    }

    public JPanel getMainPanel () {
        return mainPanel;
    }

    public void setCheckBox(int index, boolean isSelected) {
        if (isSelected) {
            itemCheckBoxes.get(index).setIcon(new ImageIcon("./photos/checkbox_ticked.png"));
        } else {
            itemCheckBoxes.get(index).setIcon(new ImageIcon("./photos/checkbox.png"));
        }

        contentPanel.validate();
        contentPanel.repaint();
    }

    public ArrayList<JLabel> getItemCheckBoxes() {
        return itemCheckBoxes;
    }

    public ArrayList<JLabel> getItemMinus() {
        return itemMinus;
    }

    public ArrayList<JLabel> getItemPlus() {
        return itemPlus;
    }

    public Object_RoundedButton getBtnCod() {
        return btnCod;
    }

    public Object_RoundedButton getBtnOnlinePayment() {
        return btnOnlinePayment;
    }

    public Object_RoundedButton getBtnCredit() {
        return btnCredit;
    }

    public Object_RoundedButton getBtnDebit() {
        return btnDebit;
    }

    public Object_RoundedButton getBtnOrder() {
        return btnOrder;
    }

    public JLabel getBtnBack() {
        return btnBack;
    }

    public void setListener(MouseListener mouseListener) {
        for (int i = 0; i < itemCheckBoxes.size(); i++) {
            itemCheckBoxes.get(i).addMouseListener(mouseListener);
            itemMinus.get(i).addMouseListener(mouseListener);
            itemPlus.get(i).addMouseListener(mouseListener);
        }

        btnCod.addMouseListener(mouseListener);
        btnOnlinePayment.addMouseListener(mouseListener);
        btnCredit.addMouseListener(mouseListener);
        btnDebit.addMouseListener(mouseListener);

        btnOrder.addMouseListener(mouseListener);

        btnBack.addMouseListener(mouseListener);
    }
}
