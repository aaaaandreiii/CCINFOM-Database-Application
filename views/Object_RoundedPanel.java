package views;
// New object to create ROUNDED JPanels

import java.awt.*;
import javax.swing.*;

public class Object_RoundedPanel extends JPanel {

    private int arcRadius = 75; // Adjust the radius as needed

    public Object_RoundedPanel() {
        super();
        setOpaque(false);
    }

    public Object_RoundedPanel(LayoutManager layout) {
        super(layout, true);
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), arcRadius, arcRadius);
        g2d.dispose();
        super.paintComponent(g);
    }
}