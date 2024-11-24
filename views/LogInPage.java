package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.*;
import java.io.File;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;
import javax.swing.Spring;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class LogInPage {
    private JPanel mainPanel;

    private JLabel lblLogin;
    private JLabel lblUsername;
    private Object_RoundedTextField usernameInput;
    private JLabel lblPassword;
    private Object_RoundedTextField passwordInput;

    private JLabel lblError;

    private Object_RoundedButton btnLogin;
    private Object_RoundedButton btnSignup;

    private JLabel logoPhoto;

    private Color cBackground = Color.decode("#305d7a");
    private Color cPanel = Color.decode("#39515e");
    private Color cField = Color.decode("#d9d9d9");
    private Color cButton = Color.decode("#e16a33");

    private SpringLayout layout = new SpringLayout(); 

    public LogInPage() {
        mainPanel = new JPanel();
        mainPanel.setLayout(layout);
        mainPanel.setBackground(cBackground);

        lblLogin = new JLabel("Login");
        mainPanel.add(lblLogin);
        layout.putConstraint(SpringLayout.WEST, lblLogin, 260, SpringLayout.WEST, mainPanel);
        layout.putConstraint(SpringLayout.NORTH, lblLogin, 150, SpringLayout.NORTH, mainPanel);

        lblUsername = new JLabel("Username: ");
        mainPanel.add(lblUsername);
        layout.putConstraint(SpringLayout.WEST, lblUsername, 96, SpringLayout.WEST, mainPanel);
        layout.putConstraint(SpringLayout.NORTH, lblUsername, 200, SpringLayout.NORTH, lblLogin);

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

        lblError = new JLabel("Username and/or password are incorrect.");
        lblError.setVisible(false);
        mainPanel.add(lblError);
        layout.putConstraint(SpringLayout.WEST, lblError, 96, SpringLayout.WEST, mainPanel);
        layout.putConstraint(SpringLayout.NORTH, lblError, 35, SpringLayout.NORTH, passwordInput);

        btnLogin = new Object_RoundedButton("Login");
        btnLogin.setFocusable(false);
        btnLogin.setBackground(cButton);
        btnLogin.setPreferredSize(new Dimension(180, 30));
        mainPanel.add(btnLogin);
        layout.putConstraint(SpringLayout.WEST, btnLogin, 210, SpringLayout.WEST, mainPanel);
        layout.putConstraint(SpringLayout.NORTH, btnLogin, 70, SpringLayout.NORTH, passwordInput);

        btnSignup = new Object_RoundedButton("Sign-Up");
        btnSignup.setFocusable(false);
        btnSignup.setBackground(cButton);
        btnSignup.setPreferredSize(new Dimension(180, 30));
        mainPanel.add(btnSignup);
        layout.putConstraint(SpringLayout.WEST, btnSignup, 210, SpringLayout.WEST, mainPanel);
        layout.putConstraint(SpringLayout.NORTH, btnSignup, 40, SpringLayout.NORTH, btnLogin);

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

        lblLogin.setFont(font);
        lblLogin.setForeground(Color.white);
        lblUsername.setFont(font);
        lblUsername.setForeground(Color.white);
        lblPassword.setFont(font);
        lblPassword.setForeground(Color.white);
        btnLogin.setFont(font);
        btnLogin.setForeground(Color.white);
        btnLogin.setHorizontalAlignment(SwingConstants.CENTER);
        btnSignup.setFont(font);
        btnSignup.setForeground(Color.white);
        btnSignup.setHorizontalAlignment(SwingConstants.CENTER);

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

    public Object_RoundedButton getBtnLogin() {
        return btnLogin;
    }

    public Object_RoundedButton getBtnSignup() {
        return btnSignup;
    }

    public void clearInput() {
        usernameInput.setText("");
        passwordInput.setText("");
    }

    public void setErrorVisible(boolean b) {
        lblError.setVisible(b);
    }

    public void setListener (MouseListener listener)
	{
		btnLogin.addMouseListener (listener);
        btnSignup.addMouseListener(listener);
	}
}
