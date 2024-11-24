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

import models.Item;

public class Resupply {
    private JPanel mainPanel;
    private JPanel headerPanel;
    private JPanel contentPanel;

    private JLabel lblResupply;

    private ArrayList<JLabel> itemNames = new ArrayList<JLabel>();
    private ArrayList<JLabel> itemQuantities = new ArrayList<JLabel>();
    private ArrayList<JLabel> itemPrices = new ArrayList<JLabel>();
    private ArrayList<JLabel> itemPlus = new ArrayList<JLabel>();
    private ArrayList<JLabel> itemMinus = new ArrayList<JLabel>();

    private JLabel btnBack;

    private Color cBackground = Color.decode("#305d7a");
    private Color cField = Color.decode("#d9d9d9");
    private Color cButton = Color.decode("#e16a33");

    private SpringLayout layout = new SpringLayout();

    public Resupply() {
        mainPanel = new JPanel(null);
        mainPanel.setBackground(cButton);

        headerPanel = new JPanel(layout);
        headerPanel.setBackground(cBackground);
        headerPanel.setBounds(0,0, 1423, 80);
        mainPanel.add(headerPanel);

        lblResupply = new JLabel("Resupply");
        lblResupply.setPreferredSize(new Dimension(600, 50));
        headerPanel.add(lblResupply);
        layout.putConstraint(SpringLayout.WEST, lblResupply, 30, SpringLayout.WEST, headerPanel);
        layout.putConstraint(SpringLayout.NORTH, lblResupply, 20, SpringLayout.NORTH, headerPanel);
    
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

        lblResupply.setFont(font);
        lblResupply.setForeground(Color.WHITE);
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

    public void loadInventory(ArrayList<Item> inventory) {
        int height = inventory.size() * 50; // number of items/2 x (size of bg + in between padding) + bottom padding
        if (height < 874){
            height = 874;
        }
        contentPanel.setPreferredSize(new Dimension(1426, height));

        //delete previous items
        contentPanel.removeAll();

        //clear previously loaded items every time you load new items
        itemNames = new ArrayList<JLabel>();
        itemQuantities = new ArrayList<JLabel>();
        itemPrices = new ArrayList<JLabel>();
        itemPlus = new ArrayList<JLabel>();
        itemMinus = new ArrayList<JLabel>();

        Font font = new Font("Serif", Font.PLAIN, 17);
        try {
            File fontStyle = new File("./fonts/horizon.otf");
            font = Font.createFont(Font.TRUETYPE_FONT, fontStyle).deriveFont(17f);
        } catch (Exception e) {
            e.printStackTrace();
            font = new Font("Serif", Font.PLAIN, 17);
        }

        for (int i = 0; i < inventory.size(); i++) {
            JLabel name = new JLabel(inventory.get(i).getName());
            JLabel quantity = new JLabel("" + inventory.get(i).getAvailable());
            JLabel price = new JLabel(inventory.get(i).getPriceInString());

            JLabel plus = new JLabel("+");
            JLabel minus = new JLabel("-");

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

            itemNames.add(name);
            itemQuantities.add(quantity);
            itemPrices.add(price);
            itemPlus.add(plus);
            itemMinus.add(minus);

            contentPanel.add(name);
            contentPanel.add(quantity);
            contentPanel.add(price);
            contentPanel.add(plus);
            contentPanel.add(minus);

            if (i == 0) {
                layout.putConstraint(SpringLayout.WEST, name, 40, SpringLayout.WEST, contentPanel);
                layout.putConstraint(SpringLayout.NORTH, name, 40, SpringLayout.NORTH, contentPanel);
            } else {
                layout.putConstraint(SpringLayout.WEST, name, 40, SpringLayout.WEST, contentPanel);
                layout.putConstraint(SpringLayout.NORTH, name, 40, SpringLayout.NORTH, itemNames.get(i-1));
            }
            
            layout.putConstraint(SpringLayout.WEST, quantity, 400, SpringLayout.WEST, contentPanel);
            layout.putConstraint(SpringLayout.NORTH, quantity, 0, SpringLayout.NORTH, name);

            layout.putConstraint(SpringLayout.WEST, plus, 70, SpringLayout.EAST, minus);
            layout.putConstraint(SpringLayout.NORTH, plus, 0, SpringLayout.NORTH, name);

            layout.putConstraint(SpringLayout.EAST, minus, -20, SpringLayout.WEST, quantity);
            layout.putConstraint(SpringLayout.NORTH, minus, 0, SpringLayout.NORTH, name);

            layout.putConstraint(SpringLayout.EAST, price, -100, SpringLayout.EAST, contentPanel);
            layout.putConstraint(SpringLayout.NORTH, price, 0, SpringLayout.NORTH, name);
        }

        contentPanel.validate();
        contentPanel.repaint();
    }

    public JPanel getMainPanel () {
        return mainPanel;
    }

    public ArrayList<JLabel> getItemMinus() {
        return itemMinus;
    }

    public ArrayList<JLabel> getItemPlus() {
        return itemPlus;
    }

    public JLabel getBtnBack() {
        return btnBack;
    }

    public void setListener(MouseListener mouseListener) {
        for (int i = 0; i < itemMinus.size(); i++) {
            itemMinus.get(i).addMouseListener(mouseListener);
            itemPlus.get(i).addMouseListener(mouseListener);
        }

        btnBack.addMouseListener(mouseListener);
    }
}
