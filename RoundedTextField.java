import javax.swing.*;
import java.awt.*;

public class RoundedTextField extends JTextField {

    private int arcRadius = 30;
    private Insets textInsets = new Insets(5, 10, 5, 10);

    public RoundedTextField(int columns) {
        super(columns);
        setOpaque(false);
        setMargin(textInsets);
    }

    public RoundedTextField(String text, int columns) {
        super(null, text, columns);
        setOpaque(false); 
        setMargin(textInsets);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arcRadius, arcRadius);
        super.paintComponent(g2d);
        g2d.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(getForeground());
        g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arcRadius, arcRadius);
        g2d.dispose();
    }
}