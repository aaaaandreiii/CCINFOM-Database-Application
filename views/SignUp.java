package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.*;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

public class SignUp {
    private JPanel mainPanel;

    private JLabel lblSignup;
    private JLabel lblUsername;
    private Object_RoundedTextField usernameInput;
    private JLabel lblPassword;
    private Object_RoundedTextField passwordInput;
    private JLabel lblFirstName;
    private Object_RoundedTextField firstNameInput;
    private JLabel lblLastName;
    private Object_RoundedTextField lastNameInput;
    private JLabel lblPhone;
    private Object_RoundedTextField phoneInput;
    private JLabel lblDeliveryAddress;
    private Object_RoundedTextField deliveryAddressInput;

    private JLabel lblError;

    private JLabel btnBack;
    private Object_RoundedButton btnCreate;

    private JLabel logoPhoto;

    private Color cBackground = Color.decode("#305d7a");
    private Color cPanel = Color.decode("#39515e");
    private Color cField = Color.decode("#d9d9d9");
    private Color cButton = Color.decode("#e16a33");

    private SpringLayout layout = new SpringLayout(); 

    public SignUp() {
        mainPanel = new JPanel();
        mainPanel.setLayout(layout);
        mainPanel.setBackground(cBackground);

        lblSignup = new JLabel("SignUp");
        mainPanel.add(lblSignup);
        layout.putConstraint(SpringLayout.WEST, lblSignup, 260, SpringLayout.WEST, mainPanel);
        layout.putConstraint(SpringLayout.NORTH, lblSignup, 150, SpringLayout.NORTH, mainPanel);

        lblUsername = new JLabel("Username: ");
        mainPanel.add(lblUsername);
        layout.putConstraint(SpringLayout.WEST, lblUsername, 96, SpringLayout.WEST, mainPanel);
        layout.putConstraint(SpringLayout.NORTH, lblUsername, 50, SpringLayout.NORTH, lblSignup);

        usernameInput = new Object_RoundedTextField("", 25);
        usernameInput.setBackground(cField);
        mainPanel.add(usernameInput);
        layout.putConstraint(SpringLayout.WEST, usernameInput, 96, SpringLayout.WEST, mainPanel);
        layout.putConstraint(SpringLayout.NORTH, usernameInput, 30, SpringLayout.NORTH, lblUsername);

        lblPassword = new JLabel("Password: ");
        mainPanel.add(lblPassword);
        layout.putConstraint(SpringLayout.WEST, lblPassword, 96, SpringLayout.WEST, mainPanel);
        layout.putConstraint(SpringLayout.NORTH, lblPassword, 50, SpringLayout.NORTH, usernameInput);

        passwordInput = new Object_RoundedTextField("",25);
        passwordInput.setBackground(cField);
        mainPanel.add(passwordInput);
        layout.putConstraint(SpringLayout.WEST, passwordInput, 96, SpringLayout.WEST, mainPanel);
        layout.putConstraint(SpringLayout.NORTH, passwordInput, 30, SpringLayout.NORTH, lblPassword);

        lblFirstName = new JLabel("First Name: ");
        mainPanel.add(lblFirstName);
        layout.putConstraint(SpringLayout.WEST, lblFirstName, 96, SpringLayout.WEST, mainPanel);
        layout.putConstraint(SpringLayout.NORTH, lblFirstName, 50, SpringLayout.NORTH, passwordInput);

        firstNameInput = new Object_RoundedTextField("",25);
        firstNameInput.setBackground(cField);
        mainPanel.add(firstNameInput);
        layout.putConstraint(SpringLayout.WEST, firstNameInput, 96, SpringLayout.WEST, mainPanel);
        layout.putConstraint(SpringLayout.NORTH, firstNameInput, 30, SpringLayout.NORTH, lblFirstName);

        lblLastName = new JLabel("Last Name: ");
        mainPanel.add(lblLastName);
        layout.putConstraint(SpringLayout.WEST, lblLastName, 96, SpringLayout.WEST, mainPanel);
        layout.putConstraint(SpringLayout.NORTH, lblLastName, 50, SpringLayout.NORTH, firstNameInput);

        lastNameInput = new Object_RoundedTextField("",25);
        lastNameInput.setBackground(cField);
        mainPanel.add(lastNameInput);
        layout.putConstraint(SpringLayout.WEST, lastNameInput, 96, SpringLayout.WEST, mainPanel);
        layout.putConstraint(SpringLayout.NORTH, lastNameInput, 30, SpringLayout.NORTH, lblLastName);

        lblPhone = new JLabel("Phone: ");
        mainPanel.add(lblPhone);
        layout.putConstraint(SpringLayout.WEST, lblPhone, 96, SpringLayout.WEST, mainPanel);
        layout.putConstraint(SpringLayout.NORTH, lblPhone, 50, SpringLayout.NORTH, lastNameInput);

        phoneInput = new Object_RoundedTextField("",25);
        phoneInput.setBackground(cField);
        mainPanel.add(phoneInput);
        layout.putConstraint(SpringLayout.WEST, phoneInput, 96, SpringLayout.WEST, mainPanel);
        layout.putConstraint(SpringLayout.NORTH, phoneInput, 30, SpringLayout.NORTH, lblPhone);

        lblDeliveryAddress = new JLabel("Delivery Address: ");
        mainPanel.add(lblDeliveryAddress);
        layout.putConstraint(SpringLayout.WEST, lblDeliveryAddress, 96, SpringLayout.WEST, mainPanel);
        layout.putConstraint(SpringLayout.NORTH, lblDeliveryAddress, 50, SpringLayout.NORTH, phoneInput);

        deliveryAddressInput = new Object_RoundedTextField("",25);
        deliveryAddressInput.setBackground(cField);
        mainPanel.add(deliveryAddressInput);
        layout.putConstraint(SpringLayout.WEST, deliveryAddressInput, 96, SpringLayout.WEST, mainPanel);
        layout.putConstraint(SpringLayout.NORTH, deliveryAddressInput, 30, SpringLayout.NORTH, lblDeliveryAddress);

        lblError = new JLabel("Username is already taken.");
        lblError.setVisible(false);
        mainPanel.add(lblError);
        layout.putConstraint(SpringLayout.WEST, lblError, 96, SpringLayout.WEST, mainPanel);
        layout.putConstraint(SpringLayout.NORTH, lblError, 35, SpringLayout.NORTH, deliveryAddressInput);

        btnBack = new JLabel(new ImageIcon("photos/Back.png"));
        mainPanel.add(btnBack);
        layout.putConstraint(SpringLayout.WEST, btnBack, 96, SpringLayout.WEST, mainPanel);
        layout.putConstraint(SpringLayout.NORTH, btnBack, 140, SpringLayout.NORTH, mainPanel);

        btnCreate = new Object_RoundedButton("Create");
        btnCreate.setFocusable(false);
        btnCreate.setBackground(cButton);
        btnCreate.setPreferredSize(new Dimension(180, 30));
        mainPanel.add(btnCreate);
        layout.putConstraint(SpringLayout.WEST, btnCreate, 210, SpringLayout.WEST, mainPanel);
        layout.putConstraint(SpringLayout.NORTH, btnCreate, 70, SpringLayout.NORTH, deliveryAddressInput);

        logoPhoto = new JLabel(new ImageIcon("photos/ClickCollect_Logo.png"));
        mainPanel.add(logoPhoto);
        layout.putConstraint(SpringLayout.WEST, logoPhoto, 500, SpringLayout.WEST, mainPanel);
        layout.putConstraint(SpringLayout.NORTH, logoPhoto, 0, SpringLayout.NORTH, mainPanel);

        Font font = new Font("Serif", Font.PLAIN, 17);
        try {
			File fontStyle = new File("./fonts/horizon.otf");
			font = Font.createFont(Font.TRUETYPE_FONT, fontStyle).deriveFont(17f);
		} catch (Exception e) {
			e.printStackTrace();
			font = new Font("Serif", Font.PLAIN, 17);
        }

        lblSignup.setFont(font);
        lblSignup.setForeground(Color.white);
        lblUsername.setFont(font);
        lblUsername.setForeground(Color.white);
        lblPassword.setFont(font);
        lblPassword.setForeground(Color.white);
        lblFirstName.setFont(font);
        lblFirstName.setForeground(Color.white);
        lblLastName.setFont(font);
        lblLastName.setForeground(Color.white);
        lblPhone.setFont(font);
        lblPhone.setForeground(Color.white);
        lblDeliveryAddress.setFont(font);
        lblDeliveryAddress.setForeground(Color.white);
        btnCreate.setFont(font);
        btnCreate.setForeground(Color.white);
        btnCreate.setHorizontalAlignment(SwingConstants.CENTER);

        font = new Font("Serif", Font.PLAIN, 17);
        try {
			File fontStyle = new File("./fonts/WorkSans-Light.ttf");
			font = Font.createFont(Font.TRUETYPE_FONT, fontStyle).deriveFont(17f);
		} catch (Exception e) {
			e.printStackTrace();
			font = new Font("Serif", Font.PLAIN, 17);
        }
        
        usernameInput.setFont(font);
        usernameInput.setForeground(cPanel);
        passwordInput.setFont(font);
        passwordInput.setForeground(cPanel);
        firstNameInput.setFont(font);
        firstNameInput.setForeground(cPanel);
        lastNameInput.setFont(font);
        lastNameInput.setForeground(cPanel);
        phoneInput.setFont(font);
        phoneInput.setForeground(cPanel);
        deliveryAddressInput.setFont(font);
        deliveryAddressInput.setForeground(cPanel);
        lblError.setFont(font);
        lblError.setForeground(cButton);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public Object_RoundedTextField getUsernameInput() {
        return usernameInput;
    }

    public String getUsername() {
        return usernameInput.getText();
    }

    public Object_RoundedTextField getPasswordInput() {
        return passwordInput;
    }

    public String getPassword() {
        return passwordInput.getText();
    }

    public Object_RoundedTextField getFirstNameInput() {
        return firstNameInput;
    }

    public String getFirstName() {
        return firstNameInput.getText();
    }

    public Object_RoundedTextField getLastNameInput() {
        return lastNameInput;
    }

    public String getLastName() {
        return lastNameInput.getText();
    }

    public Object_RoundedTextField getPhoneInput() {
        return phoneInput;
    }

    public String getPhone() {
        return phoneInput.getText();
    }

    public Object_RoundedTextField getDeliveryAddressInput() {
        return deliveryAddressInput;
    }

    public String getDeliveryAddress() {
        return deliveryAddressInput.getText();
    }

    public JLabel getBtnBack() {
        return btnBack;
    }

    public Object_RoundedButton getBtnCreate() {
        return btnCreate;
    }

    public void clearInput() {
        usernameInput.setText("");
        passwordInput.setText("");
        firstNameInput.setText("");
        lastNameInput.setText("");
        phoneInput.setText("");
        deliveryAddressInput.setText("");
    }

    public void setErrorText(String s) {
        lblError.setText(s);
    }

    public void setErrorVisible(boolean b) {
        lblError.setVisible(b);
    }

    public void setListener (MouseListener listener)
	{
		btnCreate.addMouseListener (listener);
        btnBack.addMouseListener(listener);
	}
}
