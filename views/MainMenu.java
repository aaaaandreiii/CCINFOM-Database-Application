package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.*;

import models.*;

public class MainMenu {
    private JPanel mainPanel; //holds the contents of objects to buy
    private JPanel sidePanel; //holds the contents of shpopping carts, wishlist, orders, and resuly 
    private JPanel contentPanel; //
    private SpringLayout layout = new SpringLayout();

    private User user;
    private JLabel imgUser;
    private JLabel lblUser;

    private Object_RoundedTextField search;
    private JLabel btnSearch;

    private JLabel imgShoppingCart;
    private JLabel btnShoppingCart;

    private JLabel imgWishList;
    private JLabel btnWishList;

    private JLabel imgOrder;
    private JLabel btnOrder;

    private JLabel imgResupply;
    private JLabel btnResupply;

    private Color cBackground = Color.decode("#305d7a");
    private Color cPanel = Color.decode("#39515e");
    private Color cField = Color.decode("#d9d9d9");
    private Color cButton = Color.decode("#e16a33");
    
    private ArrayList<JLabel> itemBgs = new ArrayList<JLabel>();
    private ArrayList<JLabel> itemPics = new ArrayList<JLabel>();
    private ArrayList<JLabel> itemNames = new ArrayList<JLabel>();
    private ArrayList<JLabel> itemPrices = new ArrayList<JLabel>();
    private ArrayList<JLabel> itemSuppliers = new ArrayList<JLabel>();
    private ArrayList<JLabel> itemAvailable = new ArrayList<JLabel>();

    private ArrayList<JLabel> itemShoppingCarts = new ArrayList<JLabel>();
    private ArrayList<JLabel> itemWishLists = new ArrayList<JLabel>();

    public MainMenu(models.User user) {
        this.user = user;

        mainPanel = new JPanel(null); // container for side panel and content panel

        sidePanel = new JPanel(layout); // side bar
        sidePanel.setBackground(Color.WHITE);
        sidePanel.setBounds(0, 0, 338, 874);
        mainPanel.add(sidePanel);

        contentPanel = new JPanel(layout); // items
        contentPanel.setBackground(cBackground);
        contentPanel.setPreferredSize(new Dimension(1100, 874));

        imgUser = new JLabel(new ImageIcon("./photos/Icon.png"));
        sidePanel.add(imgUser);
        layout.putConstraint(SpringLayout.WEST, imgUser, 30, SpringLayout.WEST, sidePanel);
        layout.putConstraint(SpringLayout.NORTH, imgUser, 50, SpringLayout.NORTH, sidePanel);

        lblUser = new JLabel(user.getLastName() + ", " + user.getFirstName());
        sidePanel.add(lblUser);
        layout.putConstraint(SpringLayout.WEST, lblUser, 10, SpringLayout.EAST, imgUser);
        layout.putConstraint(SpringLayout.NORTH, lblUser, 0, SpringLayout.NORTH, imgUser);

        search = new Object_RoundedTextField(14);
        search.setBackground(cField);
        sidePanel.add(search);
        layout.putConstraint(SpringLayout.WEST, search, 30, SpringLayout.WEST, sidePanel);
        layout.putConstraint(SpringLayout.NORTH, search, 40, SpringLayout.SOUTH, imgUser);

        btnSearch = new JLabel(new ImageIcon("./photos/Search.png"));
        sidePanel.add(btnSearch);
        layout.putConstraint(SpringLayout.WEST, btnSearch, 10, SpringLayout.EAST, search);
        layout.putConstraint(SpringLayout.NORTH, btnSearch, 7, SpringLayout.NORTH, search);

        if (user instanceof models.Customer) {
            initCustomer((models.Customer)user);
        } else if (user instanceof models.Seller) {
            initSeller((models.Seller)user);
        }

        JScrollPane scrollPane = new JScrollPane(contentPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(15, 0));
        scrollPane.setBounds(338, 0, 1087, 877);
        mainPanel.add(scrollPane);

        Font font = new Font("Serif", Font.PLAIN, 17);
        try {
			File fontStyle = new File("./fonts/horizon.otf");
			font = Font.createFont(Font.TRUETYPE_FONT, fontStyle).deriveFont(17f);
		} catch (Exception e) {
			e.printStackTrace();
			font = new Font("Serif", Font.PLAIN, 17);
        }

        lblUser.setFont(font);
        lblUser.setForeground(cButton);

        font = new Font("Serif", Font.PLAIN, 17);
        try {
			File fontStyle = new File("./fonts/WorkSans-Light.ttf");
			font = Font.createFont(Font.TRUETYPE_FONT, fontStyle).deriveFont(17f);
		} catch (Exception e) {
			e.printStackTrace();
			font = new Font("Serif", Font.PLAIN, 17);
        }

        search.setFont(font);
        search.setForeground(cPanel);
    }

    private void initCustomer(models.Customer user) {
        imgShoppingCart = new JLabel(new ImageIcon("./photos/ShoppingCart.png"));
        sidePanel.add(imgShoppingCart);
        layout.putConstraint(SpringLayout.WEST, imgShoppingCart, 30, SpringLayout.WEST, sidePanel);
        layout.putConstraint(SpringLayout.NORTH, imgShoppingCart, 50, SpringLayout.SOUTH, search);

        btnShoppingCart = new JLabel("Shopping Cart");
        sidePanel.add(btnShoppingCart);
        layout.putConstraint(SpringLayout.WEST, btnShoppingCart, 10, SpringLayout.EAST, imgShoppingCart);
        layout.putConstraint(SpringLayout.NORTH, btnShoppingCart, 13, SpringLayout.NORTH, imgShoppingCart);

        imgWishList = new JLabel(new ImageIcon("./photos/WishList.png"));
        sidePanel.add(imgWishList);
        layout.putConstraint(SpringLayout.WEST, imgWishList, 30, SpringLayout.WEST, sidePanel);
        layout.putConstraint(SpringLayout.NORTH, imgWishList, 30, SpringLayout.SOUTH, imgShoppingCart);

        btnWishList = new JLabel("Wishlist");
        sidePanel.add(btnWishList);
        layout.putConstraint(SpringLayout.WEST, btnWishList, 10, SpringLayout.EAST, imgWishList);
        layout.putConstraint(SpringLayout.NORTH, btnWishList, 13, SpringLayout.NORTH, imgWishList);
        
        imgOrder = new JLabel(new ImageIcon("./photos/Orders.png"));
        sidePanel.add(imgOrder);
        layout.putConstraint(SpringLayout.WEST, imgOrder, 30, SpringLayout.WEST, sidePanel);
        layout.putConstraint(SpringLayout.NORTH, imgOrder, 30, SpringLayout.SOUTH, imgWishList);

        btnOrder = new JLabel("Orders");
        sidePanel.add(btnOrder);
        layout.putConstraint(SpringLayout.WEST, btnOrder, 10, SpringLayout.EAST, imgOrder);
        layout.putConstraint(SpringLayout.NORTH, btnOrder, 13, SpringLayout.NORTH, imgOrder);

        Font font = new Font("Serif", Font.PLAIN, 14);
        try {
			File fontStyle = new File("./fonts/horizon.otf");
			font = Font.createFont(Font.TRUETYPE_FONT, fontStyle).deriveFont(17f);
		} catch (Exception e) {
			e.printStackTrace();
			font = new Font("Serif", Font.PLAIN, 17);
        }

        btnShoppingCart.setFont(font);
        btnShoppingCart.setForeground(cButton);
        btnWishList.setFont(font);
        btnWishList.setForeground(cButton);
        btnOrder.setFont(font);
        btnOrder.setForeground(cButton);
    }

    private void initSeller(models.Seller user) {
        imgOrder = new JLabel(new ImageIcon("./photos/Orders.png"));
        sidePanel.add(imgOrder);
        layout.putConstraint(SpringLayout.WEST, imgOrder, 30, SpringLayout.WEST, sidePanel);
        layout.putConstraint(SpringLayout.NORTH, imgOrder, 50, SpringLayout.SOUTH, search);

        btnOrder = new JLabel("Orders");
        sidePanel.add(btnOrder);
        layout.putConstraint(SpringLayout.WEST, btnOrder, 10, SpringLayout.EAST, imgOrder);
        layout.putConstraint(SpringLayout.NORTH, btnOrder, 13, SpringLayout.NORTH, imgOrder);
        
        imgResupply = new JLabel(new ImageIcon("./photos/Resupply.png"));
        sidePanel.add(imgResupply);
        layout.putConstraint(SpringLayout.WEST, imgResupply, 30, SpringLayout.WEST, sidePanel);
        layout.putConstraint(SpringLayout.NORTH, imgResupply, 30, SpringLayout.SOUTH, imgOrder);

        btnResupply = new JLabel("Resupply");
        sidePanel.add(btnResupply);
        layout.putConstraint(SpringLayout.WEST, btnResupply, 10, SpringLayout.EAST, imgResupply);
        layout.putConstraint(SpringLayout.NORTH, btnResupply, 13, SpringLayout.NORTH, imgResupply);

        Font font = new Font("Serif", Font.PLAIN, 14);
        try {
			File fontStyle = new File("./fonts/horizon.otf");
			font = Font.createFont(Font.TRUETYPE_FONT, fontStyle).deriveFont(17f);
		} catch (Exception e) {
			e.printStackTrace();
			font = new Font("Serif", Font.PLAIN, 17);
        }

        btnOrder.setFont(font);
        btnOrder.setForeground(cButton);
        btnResupply.setFont(font);
        btnResupply.setForeground(cButton);
    }

    public void loadItems(ArrayList<Item> itemsList) {
        int height = (int)itemsList.size()/2 * (333 + 40) + 40; // number of items/2 x (size of bg + in between padding) + bottom padding
        if (height < 874){
            height = 874;
        }
        contentPanel.setPreferredSize(new Dimension(1100, height));

        //delete previous items
        contentPanel.removeAll();

        //clear previously loaded items every time you load new items
        itemBgs = new ArrayList<JLabel>();
        itemPics = new ArrayList<JLabel>();
        itemNames = new ArrayList<JLabel>();
        itemPrices = new ArrayList<JLabel>();
        itemSuppliers = new ArrayList<JLabel>();
        itemAvailable = new ArrayList<JLabel>();
        itemShoppingCarts = new ArrayList<JLabel>();
        itemWishLists = new ArrayList<JLabel>();

        Font font = new Font("Serif", Font.PLAIN, 17);
        try {
			File fontStyle = new File("./fonts/horizon.otf");
			font = Font.createFont(Font.TRUETYPE_FONT, fontStyle).deriveFont(17f);
		} catch (Exception e) {
			e.printStackTrace();
			font = new Font("Serif", Font.PLAIN, 17);
        }

        for (int i = 0; i < itemsList.size(); i++) {
            JLabel bg = new JLabel(new ImageIcon("./photos/ItemBgResized.png"));
            JLabel pic = new JLabel(new ImageIcon(new ImageIcon(itemsList.get(i).getPathToImg()).getImage().getScaledInstance(160,160, java.awt.Image.SCALE_SMOOTH)));
            JLabel name = new JLabel(itemsList.get(i).getName());
            JLabel price = new JLabel(itemsList.get(i).getPriceInString());
            JLabel supplier = new JLabel(itemsList.get(i).getSupplier());
            JLabel available = new JLabel(itemsList.get(i).getAvailable() + " available");

            JLabel wishList = null;
            JLabel shoppingCart = null;

            if (user instanceof models.Customer) {
                wishList = new JLabel(new ImageIcon(new ImageIcon("./photos/WishList.png").getImage().getScaledInstance(20,20, java.awt.Image.SCALE_SMOOTH)));
                shoppingCart = new JLabel(new ImageIcon(new ImageIcon("./photos/ShoppingCart.png").getImage().getScaledInstance(20,20, java.awt.Image.SCALE_SMOOTH)));
            }

            name.setFont(font);
            name.setForeground(Color.WHITE);
            price.setFont(font);
            price.setForeground(Color.WHITE);
            supplier.setFont(font);
            supplier.setForeground(Color.WHITE);
            available.setFont(font);
            available.setForeground(Color.WHITE);

            itemBgs.add(bg);
            itemPics.add(pic);
            itemNames.add(name);
            itemPrices.add(price);
            itemSuppliers.add(supplier);
            itemAvailable.add(available);

            if (user instanceof models.Customer && wishList != null & shoppingCart != null) {
                itemWishLists.add(wishList);
                itemShoppingCarts.add(shoppingCart);
            }

            contentPanel.add(pic);
            contentPanel.add(name);
            contentPanel.add(price);
            contentPanel.add(supplier);
            contentPanel.add(available);

            if (user instanceof models.Customer && wishList != null & shoppingCart != null) {
                contentPanel.add(wishList);
                contentPanel.add(shoppingCart);
            }

            contentPanel.add(bg);

            // if even, put on left side
            if (i % 2 == 0) {
                if (i == 0) {
                    layout.putConstraint(SpringLayout.WEST, bg, 40, SpringLayout.WEST, contentPanel);
                    layout.putConstraint(SpringLayout.NORTH, bg, 40, SpringLayout.NORTH, contentPanel);
                } else {
                    layout.putConstraint(SpringLayout.WEST, bg, 40, SpringLayout.WEST, contentPanel);
                    layout.putConstraint(SpringLayout.NORTH, bg, 40, SpringLayout.SOUTH, itemBgs.get(i-2));
                }
                
            } else { //if odd, put on right side
                if (i == 1) {
                    layout.putConstraint(SpringLayout.EAST, bg, -65, SpringLayout.EAST, contentPanel);
                    layout.putConstraint(SpringLayout.NORTH, bg, 40, SpringLayout.NORTH, contentPanel);
                } else {
                    layout.putConstraint(SpringLayout.EAST, bg, -65, SpringLayout.EAST, contentPanel);
                    layout.putConstraint(SpringLayout.NORTH, bg, 40, SpringLayout.SOUTH, itemBgs.get(i-2));
                }
            }

            layout.putConstraint(SpringLayout.WEST, pic, 140, SpringLayout.WEST, bg);
            layout.putConstraint(SpringLayout.NORTH, pic, 25, SpringLayout.NORTH, bg);

            layout.putConstraint(SpringLayout.WEST, name, 60, SpringLayout.WEST, bg);
            layout.putConstraint(SpringLayout.NORTH, name, 15, SpringLayout.SOUTH, pic);

            layout.putConstraint(SpringLayout.WEST, price, 60, SpringLayout.WEST, bg);
            layout.putConstraint(SpringLayout.NORTH, price, 10, SpringLayout.SOUTH, name);

            layout.putConstraint(SpringLayout.WEST, supplier, 60, SpringLayout.WEST, bg);
            layout.putConstraint(SpringLayout.NORTH, supplier, 10, SpringLayout.SOUTH, price);

            layout.putConstraint(SpringLayout.WEST, available, 60, SpringLayout.WEST, bg);
            layout.putConstraint(SpringLayout.NORTH, available, 10, SpringLayout.SOUTH, supplier);

            if (user instanceof models.Customer && wishList != null & shoppingCart != null) {
                layout.putConstraint(SpringLayout.WEST, wishList, -70, SpringLayout.EAST, bg);
                layout.putConstraint(SpringLayout.NORTH, wishList, 0, SpringLayout.NORTH, available);

                layout.putConstraint(SpringLayout.WEST, shoppingCart, 10, SpringLayout.EAST, wishList);
                layout.putConstraint(SpringLayout.NORTH, shoppingCart, 0, SpringLayout.NORTH, available);
            }

            //refresh screen
            contentPanel.validate();
            contentPanel.repaint();
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public String getSearch() {
        return search.getText();
    }

    public JLabel getBtnSearch() {
        return btnSearch;
    }

    public JLabel getBtnShoppingCart() {
        return btnShoppingCart;
    }

    public JLabel getBtnWishList() {
        return btnWishList;
    }

    public JLabel getBtnOrder() {
        return btnOrder;
    }

    public JLabel getBtnResupply() {
        return btnResupply;
    }

    public ArrayList<JLabel> getItemShoppingCarts() {
        return itemShoppingCarts;
    }

    public ArrayList<JLabel> getItemWishLists() {
        return itemWishLists;
    }

    public void removeListeners(MouseListener listener) {
        for (int i = 0; i < itemWishLists.size(); i++) {
            itemWishLists.get(i).removeMouseListener(listener);
            itemShoppingCarts.get(i).removeMouseListener(listener);
        }
    }

    public void setListener(MouseListener listener) {
        btnSearch.addMouseListener(listener);

        if (btnShoppingCart != null)
            btnShoppingCart.addMouseListener(listener);

        if (btnWishList != null)    
            btnWishList.addMouseListener(listener);

        btnOrder.addMouseListener(listener);
        
        if (btnResupply != null)
            btnResupply.addMouseListener(listener);

        for (int i = 0; i < itemWishLists.size(); i++) {
            itemWishLists.get(i).addMouseListener(listener);
            itemShoppingCarts.get(i).addMouseListener(listener);
        }
    }
}
