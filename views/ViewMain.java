package views;

import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

import models.User;

public class ViewMain extends JFrame {
    private LogInPage logInPage = new LogInPage();
    private SignUp signUp = new SignUp();
    private MainMenu mainMenu;
    private ShoppingCart shoppingCart = new ShoppingCart();
    private WishList wishList = new WishList();
    private OrderBuyer orderBuyer = new OrderBuyer();
    private OrderSeller orderSeller = new OrderSeller();
    private Resupply resupply = new Resupply();

    public ViewMain () {
        super("ClickCollect");
        setDefaultCloseOperation (EXIT_ON_CLOSE);
        try {
            BufferedImage icon = ImageIO.read(new File("photos/ClickCollect_Logo.png"));
            setIconImage(icon);
        } catch (IOException e) {
            System.out.println("ERROR: Could not read icon image");
        }
        add (logInPage.getMainPanel());
        setSize (1438, 913);
        setResizable(false);
        //setUndecorated(true);
        setVisible(true);
    }
    public LogInPage getLogInPage() {
        return logInPage;
    }

    public SignUp getSignUp() {
        return signUp;
    }

    // initialize main menu with user details
    public MainMenu getMainMenu(User user) {
        if (mainMenu == null) {
            mainMenu = new MainMenu(user);
        }

        return mainMenu;
    }

    // subsequent getMainMenu calls
    public MainMenu getMainMenu() {
        return mainMenu;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public WishList getWishList() {
        return wishList;
    }

    public OrderBuyer getOrderBuyer() {
        return orderBuyer;
    }

    public OrderSeller getOrderSeller() {
        return orderSeller;
    }

    public Resupply getResupply() {
        return resupply;
    }
}
