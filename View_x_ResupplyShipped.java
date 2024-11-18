import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.table.*;

public class View_x_ResupplyShipped {
    private JFrame frame;
    private JPanel parentPanel;

    private JLabel windowTitleLabel;
    private JTable itemTable;
    private JScrollPane scroll;
    private Object_RoundedPanel pendingLabel; 
    private Object_RoundedPanel pendingShipped; 
    private Object_RoundedPanel pendingCancelled; 
    private Object_RoundedPanel pendingReturned; 

    private static Color blue2 = new Color(53, 87, 108);
    
    public View_x_ResupplyShipped(ArrayList<String> itemList) {
        frame = new JFrame();
        parentPanel = new JPanel();
        windowTitleLabel = new JLabel("Resupply: Shipped");


        parentPanel.setBorder(null);
        parentPanel.setBackground(blue2);



        DefaultTableModel model = new DefaultTableModel(
                new Object[][]{
                        {"<b>Bold Text</b>", "<font color='red'>Red Text</font>"},
                        {"<html>Multiple<br>Lines</html>", "<i>Italic Text</i>"}
                },
                new String[]{"Column 1", "Column 2"} // Dummy column names
                    );
        
        JTable table = new JTable(model);
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER); // Optional: Center-align text
        renderer.setOpaque(true); // Optional: Enable background color
        table.setDefaultRenderer(Object.class, renderer);

        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane);

        // // ArrayList<String> itemList = new ArrayList<>();
        // String[] columnNames = { "Item Name", "Amount", "Payment Status" };
        // String[][] data = {
        //     { "Item Name: <br>Supplier Name: <br>Supplier Address: ", "Amount: \nMode of Payment: ", "XXXX PHP\nPayment Status" },
        //     { "Item Name: <br>Supplier Name: <br>Supplier Address: ", "Amount: \nMode of Payment: ", "XXXX PHP\nPayment Status" },
        //     { "Item Name: <br>Supplier Name: <br>Supplier Address: ", "Amount: \nMode of Payment: ", "XXXX PHP\nPayment Status" },
        //     { "Item Name: <br>Supplier Name: <br>Supplier Address: ", "Amount: \nMode of Payment: ", "XXXX PHP\nPayment Status" }
        // };
        // itemTable = new JTable(data, columnNames);
        // itemTable.getColumnModel().getColumn(0).setPreferredWidth(200);
        // itemTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        // itemTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        // itemTable.getTableHeader().setUI(null);
        // itemTable.setPreferredScrollableViewportSize(itemTable.getPreferredSize());
        // itemTable.setFillsViewportHeight(true);


        // scroll = new JScrollPane(itemTable);

        // parentPanel.add(windowTitleLabel, BorderLayout.NORTH);
        // parentPanel.add(scroll);

        frame.setTitle("Resupply: Shipped");
        frame.add(parentPanel);
        // frame.setIconImage(logo.getImage());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(900, 900));
        frame.setMaximumSize(new Dimension(900, 900));
        frame.pack();
        frame.getContentPane().setBackground(blue2);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ((JFrame) e.getSource()).dispose();
            }
        });
    }
}
