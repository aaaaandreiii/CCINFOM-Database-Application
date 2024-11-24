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

public class WishList {
    private JPanel mainPanel;
    private JPanel headerPanel;
    private JPanel contentPanel;
    private JPanel footerPanel;

    private JLabel lblWishList;

    private ArrayList<JLabel> itemCheckBoxes = new ArrayList<JLabel>();
    private ArrayList<JLabel> itemSuppliers = new ArrayList<JLabel>();
    private ArrayList<JLabel> itemNames = new ArrayList<JLabel>();

    private JLabel btnBack;
    private Object_RoundedButton btnAddToCart;

    private Color cBackground = Color.decode("#305d7a");
    private Color cButton = Color.decode("#e16a33");

    private SpringLayout layout = new SpringLayout();

    public WishList() {
        mainPanel = new JPanel(null);
        mainPanel.setBackground(cButton);

        headerPanel = new JPanel(layout);
        headerPanel.setBackground(cBackground);
        headerPanel.setBounds(0,0, 1423, 80);
        mainPanel.add(headerPanel);

        lblWishList = new JLabel("Wish List");
        lblWishList.setPreferredSize(new Dimension(600, 50));
        headerPanel.add(lblWishList);
        layout.putConstraint(SpringLayout.WEST, lblWishList, 30, SpringLayout.WEST, headerPanel);
        layout.putConstraint(SpringLayout.NORTH, lblWishList, 20, SpringLayout.NORTH, headerPanel);

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

        lblWishList.setFont(font);
        lblWishList.setForeground(Color.WHITE);
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

        btnAddToCart = new Object_RoundedButton("Add To Cart");
        footerPanel.add(btnAddToCart);
        layout.putConstraint(SpringLayout.EAST, btnAddToCart, -10, SpringLayout.EAST, footerPanel);
        layout.putConstraint(SpringLayout.NORTH, btnAddToCart, 10, SpringLayout.NORTH, footerPanel);

        font = new Font("Serif", Font.PLAIN, 17);
        try {
			File fontStyle = new File("./fonts/WorkSans-Light.ttf");
			font = Font.createFont(Font.TRUETYPE_FONT, fontStyle).deriveFont(17f);
		} catch (Exception e) {
			e.printStackTrace();
			font = new Font("Serif", Font.PLAIN, 17);
        }

        btnAddToCart.setFont(font);
        btnAddToCart.setForeground(Color.WHITE);
        btnAddToCart.setBackground(cButton);
        btnAddToCart.setFocusable(false);
    }

    public void loadWishList(models.WishList wishList) {
        int height = wishList.getWishList().size() * 100; // number of items/2 x (size of bg + in between padding) + bottom padding
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

        ArrayList<Item> items = wishList.getWishList();
        ArrayList<Boolean> selected = wishList.getSelected();

        for (int i = 0; i < items.size(); i++) {
            JLabel checkBox;

            if (selected.get(i)) {
                checkBox = new JLabel(new ImageIcon("./photos/checkbox_ticked.png"));
            } else {
                checkBox = new JLabel(new ImageIcon("./photos/checkbox.png"));
            }

            JLabel supplier = new JLabel(items.get(i).getSupplier());
            JLabel name = new JLabel(items.get(i).getName());

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

            contentPanel.add(checkBox);
            contentPanel.add(name);
            contentPanel.add(supplier);

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
        }
        
        contentPanel.validate();
        contentPanel.repaint();
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

    public Object_RoundedButton getBtnAddToCart() {
        return btnAddToCart;
    }

    public JLabel getBtnBack() {
        return btnBack;
    }

    public void setListener(MouseListener mouseListener) {
        for (int i = 0; i < itemCheckBoxes.size(); i++) {
            itemCheckBoxes.get(i).addMouseListener(mouseListener);
        }

        btnAddToCart.addMouseListener(mouseListener);

        btnBack.addMouseListener(mouseListener);
    }
}
