import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.File;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;


public class View_1_LogInPage extends JPanel {
    private JPanel parentPanel;

    private Object_RoundedPanel panelLeft;
    private JPanel panelLeftTop;
    private JPanel panelLeftBottom;
    private JPanel panelLeftBottom1;
    private JPanel panelLeftBottom2;
    private JPanel panelLeftBottom3;
    private JLabel usernameText;
    private Object_RoundedTextField usernameInput;
    private JLabel passwordText;
    private Object_RoundedTextField passwordInput;
    private Object_RoundedButton loginButton;

    private JPanel panelRight;
    private JLabel logoPhoto;

    private static Color blue1 = new Color(48, 93, 122);
    private static Color blue2 = new Color(53, 87, 108);
    private static Color blue3 = new Color(48, 93, 122);
    private static Color paleBlue = new Color(135, 152, 163);
    private static Color orange = new Color(225, 106, 51);
    
    private static ImageIcon fullLogoImageIcon = new ImageIcon("./photos/ClickCollect_Logo.png");

    public View_1_LogInPage() {
        parentPanel = new JPanel();

        // Image fullLogoImage = fullLogoImageIcon.getImage();
        // Image scaledfullLogoIcon = fullLogoImage.getScaledInstance(275, 275,  java.awt.Image.SCALE_SMOOTH);
        // fullLogoImageIcon = new ImageIcon(scaledfullLogoIcon);
        
        parentPanel.setBorder(new EmptyBorder(0,0, 0, 0));
        parentPanel.setBackground(blue1);

        
        panelLeft = new Object_RoundedPanel();
        panelLeftTop = new JPanel();
        panelLeftBottom = new JPanel();
        panelLeftBottom1 = new JPanel();
        panelLeftBottom2 = new JPanel();
        panelLeftBottom3 = new JPanel();
        usernameText = new JLabel("Username: ");
        usernameInput = new Object_RoundedTextField("username/email", 25);
        passwordText = new JLabel("Password: ");
        passwordInput = new Object_RoundedTextField("password123", 25);
        loginButton = new Object_RoundedButton("Login");

        panelRight = new JPanel();
        logoPhoto = new JLabel();
        logoPhoto.setIcon(fullLogoImageIcon);

        panelLeft.setBackground(blue2);
        panelLeftTop.setBackground(blue2);
        panelLeftBottom.setBackground(blue2);
        panelLeftBottom1.setBackground(blue2);
        panelLeftBottom2.setBackground(blue2);
        panelLeftBottom3.setBackground(blue2);

        panelRight.setBorder(new EmptyBorder(0, 175, 0, -160));
        panelLeft.setBorder(new EmptyBorder(100, 100, 100, 100));
        panelLeftTop.setBorder(new EmptyBorder(100, 0, 100, 0));
        panelLeftBottom1.setBorder(new EmptyBorder(10, 0, 10, 0));
        panelLeftBottom2.setBorder(new EmptyBorder(10, 0, 0, 0));
        panelLeftBottom3.setBorder(new EmptyBorder(0, 0, 0, 0));
        
        panelRight.setBackground(blue1);
        usernameInput.setBackground(paleBlue);
        passwordInput.setBackground(paleBlue);
        loginButton.setBorderPainted(false);
        loginButton.setBackground(orange);

        panelLeft.setLayout(new GridLayout(2, 1));
        panelLeftBottom.setLayout(new GridLayout(3, 1));
        panelLeftBottom1.setLayout(new GridLayout(2, 1));
        panelLeftBottom2.setLayout(new GridLayout(2, 1));
        panelLeftBottom3.setLayout(new GridLayout(3, 3));

        
        try {
			File fontStyle = new File("./fonts/horizon.otf");
			Font font = Font.createFont(Font.TRUETYPE_FONT, fontStyle).deriveFont(17f);
			usernameText.setFont(font);
            usernameText.setForeground(Color.white);
            passwordText.setFont(font);
            passwordText.setForeground(Color.white);
            loginButton.setFont(font);
            loginButton.setForeground(Color.white);
            loginButton.setHorizontalAlignment(SwingConstants.CENTER);
		} catch (Exception e) {
			e.printStackTrace();
			Font font = new Font("Serif", Font.PLAIN, 17);
            usernameText.setFont(font);
            usernameText.setForeground(Color.white);
            passwordText.setFont(font);
            passwordText.setForeground(Color.white);
            loginButton.setFont(font);
            loginButton.setForeground(Color.white);
            loginButton.setHorizontalAlignment(SwingConstants.CENTER);
        }

        try {
			File fontStyle = new File("./fonts/WorkSans-Light.ttf");
			Font font = Font.createFont(Font.TRUETYPE_FONT, fontStyle).deriveFont(17f);
            usernameInput.setFont(font);
            usernameInput.setForeground(Color.white);
            passwordInput.setFont(font);
            passwordInput.setForeground(Color.white);
		} catch (Exception e) {
			e.printStackTrace();
			Font font = new Font("Serif", Font.PLAIN, 17);
            usernameInput.setFont(font);
            usernameInput.setForeground(Color.white);
            passwordInput.setFont(font);
            passwordInput.setForeground(Color.white);
        }


        panelLeftBottom1.add(usernameText);
        panelLeftBottom1.add(usernameInput);
        panelLeftBottom2.add(passwordText);
        panelLeftBottom2.add(passwordInput);

        JPanel blue2Panel1 = new JPanel();
        blue2Panel1.setBackground(blue2);
        blue2Panel1.setBorder(new EmptyBorder(0, 0, -100, 0));
        JPanel blue2Panel2 = new JPanel();
        blue2Panel2.setBackground(blue2);
        JPanel blue2Panel3 = new JPanel();
        blue2Panel3.setBackground(blue2);
        JPanel blue2Panel4 = new JPanel();
        blue2Panel4.setBackground(blue2);
        JPanel blue2Panel5 = new JPanel();
        blue2Panel5.setBackground(blue2);
        JPanel blue2Panel6 = new JPanel();
        blue2Panel6.setBackground(blue2);
        JPanel blue2Panel7 = new JPanel();
        blue2Panel7.setBackground(blue2);
        JPanel blue2Panel8 = new JPanel();
        blue2Panel8.setBackground(blue2);
        panelLeftBottom3.add(blue2Panel1);
        panelLeftBottom3.add(blue2Panel2);
        panelLeftBottom3.add(blue2Panel3);
        panelLeftBottom3.add(blue2Panel4);
        panelLeftBottom3.add(loginButton);
        panelLeftBottom3.add(blue2Panel5);
        panelLeftBottom3.add(blue2Panel6);
        panelLeftBottom3.add(blue2Panel7);
        panelLeftBottom3.add(blue2Panel8);

        panelLeftBottom.add(panelLeftBottom1);
        panelLeftBottom.add(panelLeftBottom2);
        panelLeftBottom.add(panelLeftBottom3);

        panelLeft.add(Box.createVerticalStrut(20));
        panelLeft.add(panelLeftBottom, BorderLayout.SOUTH);

        panelRight.add(logoPhoto);

        parentPanel.add(panelLeft, BorderLayout.EAST);
        parentPanel.add(panelRight, BorderLayout.WEST);
    }

    public JPanel getParentPanel() {
        return parentPanel;
    }

    public Object_RoundedPanel getPanelLeft() {
        return panelLeft;
    }

    public JPanel getPanelLeftTop() {
        return panelLeftTop;
    }

    public JPanel getPanelLeftBottom() {
        return panelLeftBottom;
    }

    public JPanel getPanelLeftBottom1() {
        return panelLeftBottom1;
    }

    public JPanel getPanelLeftBottom2() {
        return panelLeftBottom2;
    }

    public JPanel getPanelLeftBottom3() {
        return panelLeftBottom3;
    }

    public JLabel getUsernameText() {
        return usernameText;
    }

    public Object_RoundedTextField getUsernameInput() {
        return usernameInput;
    }
    public String getUsername() {
        return usernameInput.getText();
    }

    public JLabel getPasswordText() {
        return passwordText;
    }

    public Object_RoundedTextField getPasswordInput() {
        return passwordInput;
    }

    public String getPassword() {
        return passwordInput.getText();
    }

    public Object_RoundedButton getLoginButton() {
        return loginButton;
    }

    public JPanel getPanelRight() {
        return panelRight;
    }

    public JLabel getLogoPhoto() {
        return logoPhoto;
    }
    public void setListener (ActionListener listener)
	{
		loginButton.addActionListener (listener);
	}

    


}