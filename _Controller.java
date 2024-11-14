//methods and functions that take info from view 
//button listeners etc

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class _Controller {
    private _View viewClass;
    private _Model modelClass;
    
    private static Color darkBlue1 = new Color(96, 102, 118);
    
    public _Controller(_Model model, _View view) {
        this.modelClass = model;
        this.viewClass = view;

        populateHotelList(this.viewClass.getSimulateBooking().getHotelList());

        this.viewClass.getViewHotel().getBtnViewHighLevelHotelInformation().addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
                
			}
		});

        


        this.viewClass.getSimulateBooking().getBtnSearchButton().addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
                if (modelClass.getHRS().isHotelsInHRSEmpty()) {
                    JTextPane textPane = new JTextPane();
                    textPane.setContentType("text/html");
                    textPane.setEditable(false);
                    textPane.setText("Apologies. There are no existing hotels yet. Please create a hotel first.");
                    JOptionPane.showMessageDialog(null, new JScrollPane(textPane), "Hotel Booking - Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (viewClass.getSimulateBooking().getcheckInField().getText().equals(" DD-MM-YYYY") || 
                        viewClass.getSimulateBooking().getcheckOutField().getText().equals(" DD-MM-YYYY") ||
                        viewClass.getSimulateBooking().getcheckInField().getText().equals(null) || 
                        viewClass.getSimulateBooking().getcheckOutField().getText().equals(null) ||
                        viewClass.getSimulateBooking().getcheckOutField().getText().trim().isEmpty()) {
                        JTextPane textPane = new JTextPane();
                        textPane.setContentType("text/html");
                        textPane.setEditable(false);
                        textPane.setText("Apologies. Please fill in the required field first.");
                        JOptionPane.showMessageDialog(null, new JScrollPane(textPane), "Hotel Booking - Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        if (modelClass.checkIFDateValid(viewClass.getSimulateBooking().getcheckInField().getText(), viewClass.getSimulateBooking().getcheckOutField().getText()) == false) {
                            JTextPane textPane = new JTextPane();
                            textPane.setContentType("text/html");
                            textPane.setEditable(false);
                            textPane.setText("Apologies. Please re-check your Check-In and Check-Out dates as they violate some constraints.");
                            JOptionPane.showMessageDialog(null, new JScrollPane(textPane), "Hotel Booking - Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            if(!modelClass.getHRS().areThereAnyRoomsAvailableAtTheHotel(viewClass.getSimulateBooking().getHotelList().getSelectedIndex(), 
                                viewClass.getSimulateBooking().getcheckInField().getText(), viewClass.getSimulateBooking().getcheckOutField().getText())) {
                                JTextPane textPane = new JTextPane();
                                textPane.setContentType("text/html");
                                textPane.setEditable(false);
                                textPane.setText("Apologies. There are no more rooms available. Please contact the hotel to book a reservation directly.");
                                JOptionPane.showMessageDialog(null, new JScrollPane(textPane), "Hotel Booking - Error", JOptionPane.ERROR_MESSAGE);
                            } else {
                                populateHotelList(viewClass.getSimulateBookingP2().getHotelList());
                                viewClass.getSimulateBookingP2().getHotelList().setSelectedItem(viewClass.getSimulateBooking().getHotelList().getSelectedItem());
                                viewClass.getSimulateBookingP2().getcheckInField().setText(viewClass.getSimulateBooking().getcheckInField().getText());
                                viewClass.getSimulateBookingP2().getcheckOutField().setText(viewClass.getSimulateBooking().getcheckOutField().getText());
                                viewClass.getPanelSwitcher().show(viewClass.getParentPanel(), "1.2");
                            }
                        }
                    }
                }
                
			}
		});

        this.viewClass.getSimulateBookingP3().getdiscountTextField().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateLabel();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                updateLabel();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                updateLabel();
            }
            
            private void updateLabel() {
                viewClass.getSimulateBookingP3().gettotalPriceTextField().setText("PHP " + String.format("%.2f", 
                    modelClass.computeDTotalPriceOfBooking(modelClass.findHotelIndexFromUserSelection(viewClass.getSimulateBooking().getHotelList().getSelectedIndex()).getdBasePricePerNight(),
                    modelClass.findHotelIndexFromUserSelection(viewClass.getSimulateBooking().getHotelList().getSelectedIndex()).getdatePriceModifierMultiplier(),
                    modelClass.findHotelIndexFromUserSelection(viewClass.getSimulateBooking().getHotelList().getSelectedIndex()).getHotelRooms().get(viewClass.getSimulateBookingP2().getavailableRoomsComboBox().getSelectedIndex()).getfRroomTypeMultiplier(),
                    viewClass.getSimulateBookingP3().getdiscountTextField().getText(),
                    viewClass.getSimulateBookingP3().getcheckInField().getText(),
                    viewClass.getSimulateBookingP3().getcheckOutField().getText())));
            }
        });


        this.viewClass.getSimulateBookingP2().getBtnnextButton().addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
                if (viewClass.getSimulateBookingP2().getcheckInField().getText().equals("DD-MM-YYYY") || 
                        viewClass.getSimulateBookingP2().getcheckOutField().getText().equals("DD-MM-YYYY") ||
                        viewClass.getSimulateBookingP2().getcheckInField().getText().equals(null) || 
                        viewClass.getSimulateBookingP2().getcheckOutField().getText().equals(null)) {
                        JTextPane textPane = new JTextPane();
                        textPane.setContentType("text/html");
                        textPane.setEditable(false);
                        textPane.setText("Apologies. Please fill in the required field first.");
                        JOptionPane.showMessageDialog(null, new JScrollPane(textPane), "Hotel Booking - Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (modelClass.checkIFDateValid(viewClass.getSimulateBooking().getcheckInField().getText(), viewClass.getSimulateBooking().getcheckOutField().getText()) == false) {
                        JTextPane textPane = new JTextPane();
                        textPane.setContentType("text/html");
                        textPane.setEditable(false);
                        textPane.setText("Apologies. Please re-check your Check-In and Check-Out dates as they violate some constraints.");
                        JOptionPane.showMessageDialog(null, new JScrollPane(textPane), "Hotel Booking - Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        populateHotelList(viewClass.getSimulateBookingP2().getHotelList());
                        viewClass.getSimulateBookingP3().getHotelList().setSelectedItem(viewClass.getSimulateBookingP2().getHotelList().getSelectedItem());
                        viewClass.getSimulateBookingP3().getcheckInField().setText(viewClass.getSimulateBookingP2().getcheckInField().getText());
                        viewClass.getSimulateBookingP3().getcheckOutField().setText(viewClass.getSimulateBookingP2().getcheckOutField().getText());
                        viewClass.getSimulateBookingP3().getguestNameLabel().setText("Guest " + viewClass.getSimulateBookingP2().getguestNameTextField().getText());  
                        viewClass.getSimulateBookingP3().gettotalPriceTextField().setText("PHP " + String.format("%.2f", 
                            modelClass.computeDTotalPriceOfBooking(modelClass.findHotelIndexFromUserSelection(viewClass.getSimulateBooking().getHotelList().getSelectedIndex()).getdBasePricePerNight(),
                            modelClass.findHotelIndexFromUserSelection(viewClass.getSimulateBooking().getHotelList().getSelectedIndex()).getdatePriceModifierMultiplier(),
                            modelClass.findHotelIndexFromUserSelection(viewClass.getSimulateBooking().getHotelList().getSelectedIndex()).getHotelRooms().get(viewClass.getSimulateBookingP2().getavailableRoomsComboBox().getSelectedIndex()).getfRroomTypeMultiplier(),
                            viewClass.getSimulateBookingP3().getdiscountTextField().getText(),
                            viewClass.getSimulateBookingP3().getcheckInField().getText(),
                            viewClass.getSimulateBookingP3().getcheckOutField().getText())));
                        viewClass.getPanelSwitcher().show(viewClass.getParentPanel(), "1.3");
                    }
                }    
	        }
		});

        this.viewClass.getSimulateBookingP3().getBtnnextButton().addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
                if (viewClass.getSimulateBooking().getcheckInField().getText().equals("DD-MM-YYYY") || 
                        viewClass.getSimulateBooking().getcheckOutField().getText().equals("DD-MM-YYYY") ||
                        viewClass.getSimulateBooking().getcheckInField().getText().equals(null) || 
                        viewClass.getSimulateBooking().getcheckOutField().getText().equals(null)) {
                        JTextPane textPane = new JTextPane();
                        textPane.setContentType("text/html");
                        textPane.setEditable(false);
                        textPane.setText("Apologies. Please fill in the required field first.");
                        JOptionPane.showMessageDialog(null, new JScrollPane(textPane), "Hotel Booking - Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (modelClass.checkIFDateValid(viewClass.getSimulateBooking().getcheckInField().getText(), viewClass.getSimulateBooking().getcheckOutField().getText()) == false) {
                        JTextPane textPane = new JTextPane();
                        textPane.setContentType("text/html");
                        textPane.setEditable(false);
                        textPane.setText("Apologies. Please re-check your Check-In and Check-Out dates as they violate some constraints.");
                        JOptionPane.showMessageDialog(null, new JScrollPane(textPane), "Hotel Booking - Error", JOptionPane.ERROR_MESSAGE);
                    } 
                    
                    String guestName = viewClass.getSimulateBookingP2().getguestNameTextField().getText(); 
                    int hotelChoice = viewClass.getSimulateBookingP3().getHotelList().getSelectedIndex(); 
                    int roomChoice = viewClass.getSimulateBookingP2().getavailableRoomsComboBox().getSelectedIndex(); 
                    double basePrice = modelClass.findHotelIndexFromUserSelection(viewClass.getSimulateBooking().getHotelList().getSelectedIndex()).getdBasePricePerNight();
                    double[] datePriceModifierMultiplier = modelClass.findHotelIndexFromUserSelection(viewClass.getSimulateBooking().getHotelList().getSelectedIndex()).getdatePriceModifierMultiplier();
                    double roomTypeMultiplier = modelClass.findHotelIndexFromUserSelection(viewClass.getSimulateBooking().getHotelList().getSelectedIndex()).getHotelRooms().get(viewClass.getSimulateBookingP2().getavailableRoomsComboBox().getSelectedIndex()).getfRroomTypeMultiplier();
                    String date1 = viewClass.getSimulateBookingP3().getcheckInField().getText(); 
                    String date2 = viewClass.getSimulateBookingP3().getcheckOutField().getText(); 
                    String discountCode = viewClass.getSimulateBookingP3().getdiscountTextField().getText();

                    if (modelClass.SimulateBooking(guestName, hotelChoice, roomChoice, basePrice, datePriceModifierMultiplier, roomTypeMultiplier, date1, date2, discountCode)) {
                        JTextPane textPane = new JTextPane();
                        textPane.setContentType("text/html");
                        textPane.setEditable(false);
                        textPane.setText("Success! You have successfully booked a hotel reservation!");
                        JOptionPane.showMessageDialog(null, new JScrollPane(textPane), "Hotel Booking - Success", JOptionPane.INFORMATION_MESSAGE);
                        viewClass.getPanelSwitcher().show(viewClass.getParentPanel(), "1");
                    } else {
                        JTextPane textPane = new JTextPane();
                        textPane.setContentType("text/html");
                        textPane.setEditable(false);
                        textPane.setText("Apologies. Reservation was not booked. Please contact the hotel to make a reservation directly.");
                        JOptionPane.showMessageDialog(null, new JScrollPane(textPane), "Hotel Booking - Error", JOptionPane.ERROR_MESSAGE);
                    }
                    
                    
                }    
	        }
		});

        this.viewClass.getSimulateBookingP2().getlogoButton().addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
                viewClass.getPanelSwitcher().show(viewClass.getParentPanel(), "1");
			}
		});

        this.viewClass.getSimulateBookingP3().getlogoButton().addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
                viewClass.getPanelSwitcher().show(viewClass.getParentPanel(), "1");
			}
		});

        this.viewClass.getMainMenu().getlogoButton().addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
                viewClass.getPanelSwitcher().show(viewClass.getParentPanel(), "1");
			}
		});

        this.viewClass.getCreateHotel().getlogoButton().addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
                viewClass.getPanelSwitcher().show(viewClass.getParentPanel(), "1");
			}
		});

        this.viewClass.getViewHotel().getlogoButton().addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
                viewClass.getPanelSwitcher().show(viewClass.getParentPanel(), "1");
			}
		});

        this.viewClass.getManageHotel().getlogoButton().addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
                viewClass.getPanelSwitcher().show(viewClass.getParentPanel(), "1");
			}
		});

        this.viewClass.getCreateHotel().getMainMenuButton().addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent ae) {
                try {
                    File fontStyle = new File("./src/fonts/NotoSerif-ThinItalic.ttf");
                    Font font = Font.createFont(Font.TRUETYPE_FONT, fontStyle).deriveFont(25f);
                    viewClass.getCreateHotel().getnewHotelTextField().setFont(font);
                } catch (Exception e) {
                    e.printStackTrace();
                    Font font = new Font("Serif", Font.PLAIN, 25);
                    viewClass.getCreateHotel().getnewHotelTextField().setFont(font);
                }
                populateHotelList(viewClass.getSimulateBooking().getHotelList());
                viewClass.getCreateHotel().getnewHotelTextField().setText(null);
                viewClass.setPlaceholder(viewClass.getCreateHotel().getnewHotelTextField(), " Enter name of new hotel here: ", darkBlue1, 30);
                viewClass.getPanelSwitcher().show(viewClass.getParentPanel(), "1");
			}
		});

        this.viewClass.getViewHotel().getMainMenuButton().addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
                populateHotelList(viewClass.getSimulateBooking().getHotelList());
                viewClass.getPanelSwitcher().show(viewClass.getParentPanel(), "1");
			}
		});
        
        this.viewClass.getManageHotel().getMainMenuButton().addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
                populateHotelList(viewClass.getSimulateBooking().getHotelList());
                viewClass.getPanelSwitcher().show(viewClass.getParentPanel(), "1");
			}
		});

        this.viewClass.getSimulateBooking().getBtnAdminMode().addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
                viewClass.getPanelSwitcher().show(viewClass.getParentPanel(), "2");
			}
		});

        this.viewClass.getMainMenu().getBtnCreateHotel().addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
                viewClass.getPanelSwitcher().show(viewClass.getParentPanel(), "3");
			}
		});

        this.viewClass.getMainMenu().getBtnViewHotel().addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
                if (modelClass.getHRS().isHotelsInHRSEmpty()) {
                    JTextPane textPane = new JTextPane();
                    textPane.setContentType("text/html");
                    textPane.setEditable(false);
                    textPane.setText("Apologies. There are no existing hotels yet. Please create a hotel first.");
                    JOptionPane.showMessageDialog(null, new JScrollPane(textPane), "Create Hotel - Error", JOptionPane.ERROR_MESSAGE);
                } else 
                    viewClass.getPanelSwitcher().show(viewClass.getParentPanel(), "4");
			}
		});

        this.viewClass.getMainMenu().getBtnManageHotel().addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
                if (modelClass.getHRS().isHotelsInHRSEmpty()) {
                    JTextPane textPane = new JTextPane();
                    textPane.setContentType("text/html");
                    textPane.setEditable(false);
                    textPane.setText("Apologies. There are no existing hotels yet. Please create a hotel first.");
                    JOptionPane.showMessageDialog(null, new JScrollPane(textPane), "Create Hotel - Error", JOptionPane.ERROR_MESSAGE);
                } else 
                    viewClass.getPanelSwitcher().show(viewClass.getParentPanel(), "5");
			}
		});

        this.viewClass.getMainMenu().getBtnSimulateBooking().addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
                if (modelClass.getHRS().isHotelsInHRSEmpty()) {
                    JTextPane textPane = new JTextPane();
                    textPane.setContentType("text/html");
                    textPane.setEditable(false);
                    textPane.setText("Apologies. There are no existing hotels yet. Please create a hotel first.");
                    JOptionPane.showMessageDialog(null, new JScrollPane(textPane), "Create Hotel - Error", JOptionPane.ERROR_MESSAGE);
                } else 
                    viewClass.getPanelSwitcher().show(viewClass.getParentPanel(), "1");
			}
		});

        this.viewClass.getCreateHotel().getsubmitButton().addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent ae) {
                JTextPane textPane = new JTextPane();
                textPane.setContentType("text/html");
                textPane.setEditable(false);
                try {
                    File fontStyle = new File("./src/fonts/NotoSerifEthiopic_Condensed-Regular.ttf");
                    Font font = Font.createFont(Font.TRUETYPE_FONT, fontStyle).deriveFont(20f);
                    textPane.setFont(font);
                } catch (Exception e) {
                    e.printStackTrace();
                    Font font = new Font("Serif", Font.PLAIN, 20);
                    textPane.setFont(font);
                }

                String newHotelName = viewClass.getCreateHotel().getnewHotelTextField().getText();
                
                if (newHotelName.length() > 20 || newHotelName.equals(null) || newHotelName.trim().isEmpty() || newHotelName.equals(" Enter name of new hotel here: ")){
                    textPane.setText("Error. This is an invalid hotel name.");
                    JOptionPane.showMessageDialog(null, new JScrollPane(textPane), "Create Hotel - Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (modelClass.createHotel(newHotelName)) {
                        viewClass.setPlaceholder(viewClass.getCreateHotel().getnewHotelTextField(), " Enter name of new hotel here: ", darkBlue1, 30);
                        textPane.setText("<html> Hotel <b>" + newHotelName + "</b> created!</html>");
                        JOptionPane.showMessageDialog(null, new JScrollPane(textPane), "Create Hotel", JOptionPane.PLAIN_MESSAGE);
                    } else {
                        textPane.setText("There already exists a hotel with this name.");
                        JOptionPane.showMessageDialog(null, new JScrollPane(textPane), "Create Hotel - Error", JOptionPane.ERROR_MESSAGE);
                    }
                        

                populateHotelList(viewClass.getSimulateBooking().getHotelList());
                populateRoomList(viewClass.getSimulateBookingP2().getavailableRoomsComboBox());
                populateHotelList(viewClass.getSimulateBookingP2().getHotelList());
                populateHotelList(viewClass.getSimulateBookingP3().getHotelList());
                }
			}
		});

    }

    public void populateHotelList(JComboBox<String> hotelList) {
        hotelList.removeAllItems();
        
        for (Hotel i : modelClass.getHRS().getHotelsInHRS()) {
            hotelList.addItem(i.getsHotelName());
        }
    }

    public void populateRoomList(JComboBox<String> roomList) {
        roomList.removeAllItems();

        int userSelectedHotel = viewClass.getSimulateBooking().getHotelList().getSelectedIndex();
        Hotel chosenHotel = modelClass.findHotelIndexFromUserSelection(userSelectedHotel);
        String RoomPlusTypePlusPrice;
        

        if (chosenHotel != null) {
            for (Room i : chosenHotel.getHotelRooms()) {
                RoomPlusTypePlusPrice = "Room " + i.getsRoomName() + "       " + modelClass.findRoomType(i) 
                                        + "          PHP" + String.format("%.2f", chosenHotel.getdBasePricePerNight());
                roomList.addItem(RoomPlusTypePlusPrice);
            }
        }
    }
}
