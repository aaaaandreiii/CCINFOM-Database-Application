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
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class GUIMainMenu extends JPanel {
    private JPanel parentPanel;

    private JPanel panelTitle;
    private JButton logoButton;
    private JLabel title;

    private JPanel panelSelection;
    private JButton CreateHotel;
    private JButton ViewHotel;
    private JButton ManageHotel;
    private JButton SimulateBooking;

    private static Color beige = new Color(254, 243, 226);
    private static Color paleGreen1 = new Color(112, 136, 113);
    private static ImageIcon fullLogoImageIcon = new ImageIcon("./src/photos/OpulenceOasisFullLogo.png");
    public GUIMainMenu() {
        parentPanel = new JPanel();

        Image fullLogoImage = fullLogoImageIcon.getImage();
        Image scaledfullLogoIcon = fullLogoImage.getScaledInstance(275, 275,  java.awt.Image.SCALE_SMOOTH);
        fullLogoImageIcon = new ImageIcon(scaledfullLogoIcon);
        
        parentPanel.setBorder(new EmptyBorder(0,0, 0, 0));
        parentPanel.setBackground(paleGreen1);

        panelTitle = new JPanel();
        logoButton = new JButton();
        title = new JLabel("Admin Mode");
        panelTitle.setBackground(beige);
        panelTitle.setBorder(new EmptyBorder(75, 250, 75, 250));
        panelTitle.setBackground(beige);
        // panelTitle.setLayout(new GridLayout(1, 2));
        logoButton.setIcon(fullLogoImageIcon);
        logoButton.setBorderPainted(false);
        logoButton.setBackground(null);
        logoButton.setBorder(null);
        try {
			File fontStyle = new File("./src/fonts/NotoSerifEthiopic_Condensed-Regular.ttf");
			Font font = Font.createFont(Font.TRUETYPE_FONT, fontStyle).deriveFont(90f);
			title.setFont(font);
            title.setHorizontalAlignment(SwingConstants.RIGHT);
		} catch (Exception e) {
			e.printStackTrace();
			Font font = new Font("Serif", Font.PLAIN, 90);
            title.setFont(font);
            title.setHorizontalAlignment(SwingConstants.RIGHT);
        }
        

        panelSelection = new JPanel();
        CreateHotel = new JButton("Create Hotel");
        ViewHotel = new JButton("View Hotel");
        ManageHotel = new JButton("Manage Hotel");
        SimulateBooking = new JButton("Simulate Booking");
        panelSelection.setBorder(new EmptyBorder(125, 0, 0, 0));
        panelSelection.setBackground(paleGreen1);
        panelSelection.setLayout(new GridLayout(4, 1));
        try {
			File fontStyle = new File("./src/fonts/NotoSerifEthiopic_ExtraCondensed-Regular.ttf");
			Font font = Font.createFont(Font.TRUETYPE_FONT, fontStyle).deriveFont(30f);
            CreateHotel.setFont(font);
            ViewHotel.setFont(font);
            ManageHotel.setFont(font);
            SimulateBooking.setFont(font);
		} catch (Exception e) {
			e.printStackTrace();
			Font font = new Font("Serif", Font.PLAIN, 30);
            CreateHotel.setFont(font);
            ViewHotel.setFont(font);
            ManageHotel.setFont(font);
            SimulateBooking.setFont(font);
        }

        CreateHotel.setBackground(paleGreen1);
        CreateHotel.setBorder(null);
        CreateHotel.setForeground(beige);
        ViewHotel.setBackground(paleGreen1);
        ViewHotel.setBorder(null);
        ViewHotel.setForeground(beige);
        ManageHotel.setBackground(paleGreen1);
        ManageHotel.setBorder(null);
        ManageHotel.setForeground(beige);
        SimulateBooking.setBackground(paleGreen1);
        SimulateBooking.setBorder(null);
        SimulateBooking.setForeground(beige);
        CreateHotel.setPreferredSize(new Dimension(700, 60));
        CreateHotel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        ViewHotel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        ManageHotel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        SimulateBooking.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        panelTitle.add(logoButton);
        panelTitle.add(title);

        panelSelection.add(CreateHotel);
        panelSelection.add(ViewHotel);
        panelSelection.add(ManageHotel);
        panelSelection.add(SimulateBooking);

        parentPanel.add(panelTitle, BorderLayout.NORTH);
        parentPanel.add(panelSelection, BorderLayout.CENTER);
    }


    public JButton getlogoButton() {
        return logoButton;
    }

    public JPanel getParentPanel(){
        return parentPanel;
    }

    public JButton getBtnCreateHotel() {
        return CreateHotel;
    }
    public JButton getBtnViewHotel() {
        return ViewHotel;
    }
    public JButton getBtnManageHotel() {
        return ManageHotel;
    }
    public JButton getBtnSimulateBooking() {
        return SimulateBooking;
    }
}
