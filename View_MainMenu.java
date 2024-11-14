import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class View_MainMenu extends JPanel {
    private JPanel parentPanel;

    private JPanel panelLeft;
    private JPanel panelLeftTop;
    private JPanel panelLeftBottom;
    private JLabel usernameText;
    private JTextField usernameInput;
    private JLabel passwordText;
    private JTextField passwordInput;
    private JButton loginButton;

    private JPanel panelRight;
    private JLabel logoPhoto;

    private static Color blue1 = new Color(48, 93, 122);
    private static Color blue2 = new Color(53, 87, 108);
    private static Color blue3 = new Color(48, 93, 122);
    private static Color paleBlue = new Color(135, 152, 163);
    private static Color orange = new Color(225, 106, 51);

    private static Color beige = new Color(112, 136, 113);
    
    private static ImageIcon fullLogoImageIcon = new ImageIcon("./photos/ClickCollect_Logo.png");

    public View_MainMenu() {
        parentPanel = new JPanel();

        // Image fullLogoImage = fullLogoImageIcon.getImage();
        // Image scaledfullLogoIcon = fullLogoImage.getScaledInstance(275, 275,  java.awt.Image.SCALE_SMOOTH);
        // fullLogoImageIcon = new ImageIcon(scaledfullLogoIcon);
        
        parentPanel.setBorder(new EmptyBorder(0,0, 0, 0));
        parentPanel.setBackground(blue1);

        
        panelLeft = new JPanel();
        panelLeftTop = new JPanel();
        panelLeftBottom = new JPanel();
        usernameText = new JLabel("Username: ");
        usernameInput = new JTextField("username/email");
        passwordText = new JLabel("Password: ");
        passwordInput = new JTextField("password123");
        loginButton = new JButton("Login");

        panelRight = new JPanel();
        logoPhoto = new JLabel();
        logoPhoto.setIcon(fullLogoImageIcon);

        panelLeft.setBackground(blue2);
        panelLeftTop.setBackground(blue2);
        panelLeftBottom.setBackground(blue2);

        usernameInput.setBackground(paleBlue);
        passwordInput.setBackground(paleBlue);
        panelRight.setBackground(blue1);
        loginButton.setBorderPainted(false);
        loginButton.setBackground(orange);
        loginButton.setBorder(null);

        panelLeft.setLayout(new GridLayout(2, 1));
        panelLeftBottom.setLayout(new GridLayout(5, 1));

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

        // panelSelection = new JPanel();
        
        // panelSelection.setBorder(new EmptyBorder(125, 0, 0, 0));
        // panelSelection.setBackground(blue1);
        // panelSelection.setLayout(new GridLayout(4, 1));
        // try {
		// 	File fontStyle = new File("./src/fonts/NotoSerifEthiopic_ExtraCondensed-Regular.ttf");
		// 	Font font = Font.createFont(Font.TRUETYPE_FONT, fontStyle).deriveFont(30f);
        //     // CreateHotel.setFont(font);
        //     // ViewHotel.setFont(font);
        //     // ManageHotel.setFont(font);
        //     // SimulateBooking.setFont(font);
		// } catch (Exception e) {
		// 	e.printStackTrace();
		// 	Font font = new Font("Serif", Font.PLAIN, 30);
        //     // CreateHotel.setFont(font);
        //     // ViewHotel.setFont(font);
        //     // ManageHotel.setFont(font);
        //     // SimulateBooking.setFont(font);
        // }

        // // CreateHotel.setBackground(blue1);
        // // CreateHotel.setBorder(null);
        // // CreateHotel.setForeground(beige);
        // // ViewHotel.setBackground(blue1);
        // // ViewHotel.setBorder(null);
        // // ViewHotel.setForeground(beige);
        // // ManageHotel.setBackground(blue1);
        // // ManageHotel.setBorder(null);
        // // ManageHotel.setForeground(beige);
        // // SimulateBooking.setBackground(blue1);
        // // SimulateBooking.setBorder(null);
        // // SimulateBooking.setForeground(beige);
        // // CreateHotel.setPreferredSize(new Dimension(700, 60));
        // // CreateHotel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        // // ViewHotel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        // // ManageHotel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        // // SimulateBooking.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelLeftBottom.add(usernameText);
        panelLeftBottom.add(usernameInput);
        panelLeftBottom.add(passwordText);
        panelLeftBottom.add(passwordInput);
        panelLeftBottom.add(loginButton);

        panelLeft.add(panelLeftTop, BorderLayout.NORTH);
        panelLeft.add(panelLeftBottom, BorderLayout.SOUTH);

        panelRight.add(logoPhoto);

        parentPanel.add(panelLeft, BorderLayout.EAST);
        parentPanel.add(panelRight, BorderLayout.WEST);
    }


    public JButton getLoginButton() {
        return loginButton;
    }

    public JPanel getParentPanel(){
        return parentPanel;
    }
}
