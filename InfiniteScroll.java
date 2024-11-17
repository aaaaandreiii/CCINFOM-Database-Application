import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class InfiniteScroll {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(InfiniteScroll::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Infinite Scroll Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 600);

        // Main panel to hold dynamically added content
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        // Add initial content
        ArrayList<JLabel> labels = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            JLabel label = new JLabel("Item " + i);
            labels.add(label);
            contentPanel.add(label);
        }

        // Wrap the content panel in a scroll pane
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // Add a scroll listener for infinite scrolling
        scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            private boolean loading = false;

            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                if (!loading && !e.getValueIsAdjusting()) {
                    JScrollBar scrollBar = (JScrollBar) e.getSource();
                    int maxValue = scrollBar.getMaximum() - scrollBar.getVisibleAmount();
                    if (scrollBar.getValue() >= maxValue - 10) { // Close to the bottom
                        loading = true;
                        SwingUtilities.invokeLater(() -> {
                            // Simulate adding new content
                            int currentSize = labels.size();
                            for (int i = currentSize + 1; i <= currentSize + 10; i++) {
                                JLabel newLabel = new JLabel("Item " + i);
                                labels.add(newLabel);
                                contentPanel.add(newLabel);
                            }
                            contentPanel.revalidate();
                            loading = false;
                        });
                    }
                }
            }
        });

        frame.add(scrollPane);
        frame.setVisible(true);
    }
}
