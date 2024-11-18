import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;
import java.math.BigDecimal;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Object_MainMenuItems extends JPanel{
    // private JPanel mainPanel;
    private JLabel itemPhoto;
    private JLabel itemName;
    private JLabel itemPrice;
    private JLabel itemSupplier;
    private JLabel itemNumAvailable;
    
    private static Color blue2 = new Color(53, 87, 108);

    public Object_MainMenuItems(String itemPhotoPath, String itemName, BigDecimal itemPrice, String itemSupplier, Integer itemNumAvailable) {
        // mainPanel = new JPanel();

        ImageIcon fullLogoImageIcon = new ImageIcon(itemPhotoPath);
        this.itemPhoto = new JLabel();
        this.itemPhoto.setIcon(fullLogoImageIcon);
        
        this.itemName = new JLabel(itemName);
        this.itemPrice = new JLabel(itemPrice.toString());
        this.itemSupplier = new JLabel(itemSupplier);
        this.itemNumAvailable = new JLabel(itemNumAvailable.toString() + " items left!");

        try {
            File fontStyle = new File("./fonts/horizon.otf");
			Font font = Font.createFont(Font.TRUETYPE_FONT, fontStyle).deriveFont(17f);
			this.itemName.setFont(font);
            this.itemName.setForeground(Color.white);
            this.itemPrice.setFont(font);
            this.itemPrice.setForeground(Color.white);
            this.itemSupplier.setFont(font);
            this.itemSupplier.setForeground(Color.white);
            this.itemNumAvailable.setFont(font);
            this.itemNumAvailable.setForeground(Color.white);
        } catch (Exception e) {
			e.printStackTrace();
			Font font = new Font("Serif", Font.PLAIN, 17);
            this.itemName.setFont(font);
            this.itemName.setForeground(Color.white);
            this.itemPrice.setFont(font);
            this.itemPrice.setForeground(Color.white);
            this.itemSupplier.setFont(font);
            this.itemSupplier.setForeground(Color.white);
            this.itemNumAvailable.setFont(font);
            this.itemNumAvailable.setForeground(Color.white);
        }

        this.setLayout(new GridLayout(5,1));
        this.add(this.itemPhoto);
        this.add(this.itemName);
        this.add(this.itemPrice);
        this.add(this.itemSupplier);
        this.add(this.itemNumAvailable);
        // mainPanel.add(this.itemName);
        // mainPanel.add(this.itemPrice);
        // mainPanel.add(this.itemSupplier);
        // mainPanel.add(this.itemNumAvailable);
        
    }

    // public void setMainPanel(JPanel mainPanel) {
    //     this.mainPanel = mainPanel;
    // }

    // public JPanel getMaiPanel(){
    //     return mainPanel;
    // }
    
    public void setItemPhoto(JLabel itemPhoto) {
        this.itemPhoto = itemPhoto;
    }

    public void setItemName(JLabel itemName) {
        this.itemName = itemName;
    }

    public void setItemPrice(JLabel itemPrice) {
        this.itemPrice = itemPrice;
    }

    public void setItemSupplier(JLabel itemSupplier) {
        this.itemSupplier = itemSupplier;
    }

    public void setItemNumAvailable(JLabel itemNumAvailable) {
        this.itemNumAvailable = itemNumAvailable;
    }

    public JLabel getItemPhoto() {
        return itemPhoto;
    }

    public JLabel getItemName() {
        return itemName;
    }

    public JLabel getItemPrice() {
        return itemPrice;
    }

    public JLabel getItemSupplier() {
        return itemSupplier;
    }

    public JLabel getItemNumAvailable() {
        return itemNumAvailable;
    }
}
