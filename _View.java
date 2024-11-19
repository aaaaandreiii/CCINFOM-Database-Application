import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;

public class _View implements ActionListener {
    private JFrame frame;
    private JPanel parentPanel;
    private CardLayout panelSwitcher;

    private View_1_LogInPage LogInPage;
    private View_2_MainMenu MainMenu;
    
    private View_x_ResupplyShipped ResupplyShipped;

    private static Color blue1 = new Color(48, 93, 122);
    private static Color blue2 = new Color(48, 93, 122);
    private static Color blue3 = new Color(48, 93, 122);

    private static ImageIcon logo = new ImageIcon("./src/photos/Logo.png");

    public _View() {
        this.LogInPage = new View_1_LogInPage();
        // this.MainMenu = new View_2_MainMenu();
        
        // this.ResupplyShipped = new View_x_ResupplyShipped(null);
        
        frame = new JFrame();
        parentPanel = new JPanel();
        panelSwitcher = new CardLayout();

        parentPanel.setBackground(blue1);
        parentPanel.setBorder(null);
        parentPanel.setLayout(panelSwitcher);

        
        // parentPanel.add(LogInPage.getParentPanel(), "1");
        // parentPanel.add(MainMenu.getParentPanel(), "2");
        

        // addMouseListenerToButton(this.SimulateBooking.getBtnAdminMode(), darkBlue1, darkBlue2, darkBlue3);
        // addMouseListenerToButton(this.SimulateBooking.getBtnSearchButton(), darkBlue1, darkBlue2, darkBlue3);
        // addMouseListenerToButton(this.SimulateBookingP2.getBtnnextButton(), darkBlue1, darkBlue2, darkBlue3);
        // addMouseListenerToButton(this.SimulateBookingP3.getBtnnextButton(), darkBlue1, darkBlue2, darkBlue3);

        // addMouseListenerToButton(this.MainMenu.getlogoButton(), null, null, null);
        // addMouseListenerToButton(this.MainMenu.getBtnCreateHotel(), paleGreen1, paleGreen2, paleGreen3);
        // addMouseListenerToButton(this.MainMenu.getBtnViewHotel(), paleGreen1, paleGreen2, paleGreen3);
        // addMouseListenerToButton(this.MainMenu.getBtnManageHotel(), paleGreen1, paleGreen2, paleGreen3);
        // addMouseListenerToButton(this.MainMenu.getBtnSimulateBooking(), paleGreen1, paleGreen2, paleGreen3);

        // addMouseListenerToButton(this.CreateHotel.getsubmitButton(), darkBlue1, darkBlue2, darkBlue3);
        // addMouseListenerToButton(this.CreateHotel.getMainMenuButton(), darkBlue1, darkBlue2, darkBlue3);

        // addMouseListenerToButton(this.ViewHotel.getBtnViewHighLevelHotelInformation(), paleGreen1, paleGreen2, paleGreen3);
        // addMouseListenerToButton(this.ViewHotel.getBtnViewHotelsAvailableandBookedRooms(), paleGreen1, paleGreen2, paleGreen3);
        // addMouseListenerToButton(this.ViewHotel.getBtnViewInfoOfSelectedReservation(), paleGreen1, paleGreen2, paleGreen3);
        // addMouseListenerToButton(this.ViewHotel.getBtnViewInfoOfSelectedRoom(), paleGreen1, paleGreen2, paleGreen3);
        // addMouseListenerToButton(this.ViewHotel.getMainMenuButton(), darkBlue1, darkBlue2, darkBlue3);

        // addMouseListenerToButton(this.ManageHotel.getBtnChangeNameofHotel(), paleGreen1, paleGreen2, paleGreen3);
        // addMouseListenerToButton(this.ManageHotel.getBtnAddRooms(), paleGreen1, paleGreen2, paleGreen3);
        // addMouseListenerToButton(this.ManageHotel.getBtnRemoveRooms(), paleGreen1, paleGreen2, paleGreen3);
        // addMouseListenerToButton(this.ManageHotel.getBtnUpdateBasePriceofRooms(), paleGreen1, paleGreen2, paleGreen3);
        // addMouseListenerToButton(this.ManageHotel.getRemoveReservation(), paleGreen1, paleGreen2, paleGreen3);
        // addMouseListenerToButton(this.ManageHotel.getRemoveHotel(), paleGreen1, paleGreen2, paleGreen3);
        // addMouseListenerToButton(this.ManageHotel.getMainMenuButton(), darkBlue1, darkBlue2, darkBlue3);
        
        setPlaceholder(this.LogInPage.getUsernameInput(), "username/email", Color.WHITE, 17, "./fonts/WorkSans-Medium.ttf", "./fonts/WorkSans-Light.ttf");
        setPlaceholder(this.LogInPage.getPasswordInput(), "password123", Color.WHITE, 17, "./fonts/WorkSans-Medium.ttf", "./fonts/WorkSans-Light.ttf");
        


        frame.setTitle("Opulence Oasis");
        frame.add(parentPanel);
        frame.setIconImage(logo.getImage());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1920, 1080));
        frame.setMaximumSize(new Dimension(1920, 1080));
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);                          //supposed to be set to full screen, but does not work

        frame.pack();
        frame.getContentPane().setBackground(blue1);
        frame.setLocationRelativeTo(null);
        //TODO set visible true
        frame.setVisible(false);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ((JFrame) e.getSource()).dispose();
            }
        });
    }

    public static void addMouseListenerToButton(JButton btn, Color color1, Color color2, Color color3) {        
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                String currentText = btn.getText();
                if (!currentText.startsWith("<html>"))
                    btn.setText("<html><b>" + currentText + "</b></html>");
                else
                    btn.setText(currentText.replace("<html>", "<html><b>").replace("</html>", "</b></html>"));
                // btn.setSize(btn.getSize().width + 5, btn.getSize().height + 5);
                btn.setBackground(color2);
            }
                
            public void mouseExited(MouseEvent e) {
                String currentText = btn.getText();
                if (currentText.contains("<b>") && currentText.contains("</b>")) 
                    btn.setText(currentText.replace("<html><b>", "<html>").replace("</b></html>", "</html>"));
                else 
                    btn.setText("<html>" + currentText.replace("<b>", "").replace("</b>", "") + "</html>");

                // btn.setSize(btn.getSize().width - 5, btn.getSize().height - 5);
                btn.setBackground(color1);
            }

            public void mouseClicked(MouseEvent e) {
                btn.setBackground(color1);
            }

            public void mousePressed(MouseEvent e) {
                btn.setBackground(color3);
            }
        });
    }

    // TODO fix, rounded buttons arent glowing anymore at hover
    public static void addMouseListenerToButton(Object_RoundedButton btn, Color color1, Color color2, Color color3) {        
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                String currentText = btn.getText();
                if (!currentText.startsWith("<html>"))
                    btn.setText("<html><b>" + currentText + "</b></html>");
                else
                    btn.setText(currentText.replace("<html>", "<html><b>").replace("</html>", "</b></html>"));
                // btn.setSize(btn.getSize().width + 5, btn.getSize().height + 5);
                btn.setBackground(color2);
            }
                
            public void mouseExited(MouseEvent e) {
                String currentText = btn.getText();
                if (currentText.contains("<b>") && currentText.contains("</b>")) 
                    btn.setText(currentText.replace("<html><b>", "<html>").replace("</b></html>", "</html>"));
                else 
                    btn.setText("<html>" + currentText.replace("<b>", "").replace("</b>", "") + "</html>");

                // btn.setSize(btn.getSize().width - 5, btn.getSize().height - 5);
                btn.setBackground(color1);
            }

            public void mouseClicked(MouseEvent e) {
                btn.setBackground(color1);
            }

            public void mousePressed(MouseEvent e) {
                btn.setBackground(color3);
            }
        });
    }

    public void setPlaceholder(JTextField textField, String placeholder, Color color, float size, String fontWhenFocusGainedPath, String fontWhenFocusLostPath) {
        textField.setText(placeholder);
        textField.setForeground(color);

        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent fe) {
                try {
                    File fontStyle = new File(fontWhenFocusGainedPath);
                    Font font = Font.createFont(Font.TRUETYPE_FONT, fontStyle).deriveFont(size);
                    textField.setFont(font);
                } catch (Exception e) {
                    e.printStackTrace();
                    Font font = new Font("Serif", Font.PLAIN, (int) size);
                    textField.setFont(font);
                }
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(color);
                }
            }

            @Override
            public void focusLost(FocusEvent fe) {
                try {
                    File fontStyle = new File(fontWhenFocusLostPath);
                    Font font = Font.createFont(Font.TRUETYPE_FONT, fontStyle).deriveFont(size);
                    textField.setFont(font);
                } catch (Exception e) {
                    e.printStackTrace();
                    Font font = new Font("Serif", Font.PLAIN, (int) size);
                    textField.setFont(font);
                }
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(color);
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
    }

    public void exitApplication() {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public JPanel getParentPanel() {
		return parentPanel;
	}

	public void setParentPanel(JPanel parentPanel) {
		this.parentPanel = parentPanel;
	}

	public CardLayout getPanelSwitcher() {
		return panelSwitcher;
	}

	public void setPanelSwitcher(CardLayout panelSwitcher) {
		this.panelSwitcher = panelSwitcher;
	}

    public View_1_LogInPage get_1_LogInPage(){
        return LogInPage;
    }
    // public ViewHotelGUI getViewHotel(){
    //     return ViewHotel;
    // }
    // public ManageHotelGUI getManageHotel(){
    //     return ManageHotel;
    // }
    // public SimulateBookingGUI getSimulateBooking(){
    //     return SimulateBooking;
    // }

    // public SimulateBookingGUIPage2 getSimulateBookingP2() {
    //     return SimulateBookingP2;
    // }

    // public SimulateBookingGUIPage3 getSimulateBookingP3() {
    //     return SimulateBookingP3;
    // }

	// public void setMainMenu(MainMenuGUI mainMenu) {
	// 	MainMenu = mainMenu;
	// }
	// public void setCreateHotel(CreateHotelGUI createHotel) {
	// 	CreateHotel = createHotel;
	// }
	// public void setViewHotel(ViewHotelGUI viewHotel) {
	// 	ViewHotel = viewHotel;
	// }
	// public void setManageHotel(ManageHotelGUI manageHotel) {
	// 	ManageHotel = manageHotel;
	// }
	// public void setSimulateBooking(SimulateBookingGUI simulateBooking) {
	// 	SimulateBooking = simulateBooking;
	// }
}