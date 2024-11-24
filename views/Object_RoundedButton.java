package views;
import java.awt.*;
import javax.swing.*;

public class Object_RoundedButton extends JButton {

    public int index = -1;

    public Object_RoundedButton(String text) {
        super(text);
        setContentAreaFilled(false);
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
        super.paintComponent(g2d);
        g2d.dispose();
    }

    protected void paintBorder(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(getForeground());
        g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
        g2d.dispose();
    }
}