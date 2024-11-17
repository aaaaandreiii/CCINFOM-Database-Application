import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;;

public class sample {
	public static void main (String[] args){
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);

		ArrayList<String> hotels = new ArrayList<>();
		hotels.add("a");
		hotels.add("b");
		hotels.add("c");
		hotels.add("d");
		hotels.add("e");
		hotels.add("f");



		count all entries
		gridlayout for rows and columns = count
		maybe limit it by 50
		and increment number of possible pages by quotient of total number of entries by 50 
			=> 211/50 = 4
				if 211%50 > 1
					=> + 1 page


		

		JPanel panelNorth = new JPanel();
		panelNorth.setLayout(new FlowLayout());
		JLabel lblTitle = new JLabel("Manage Hotel");
		lblTitle.setFont(new Font("Arial", Font.BOLD, 25));
		panelNorth.add(lblTitle);
		frame.add(panelNorth, BorderLayout.NORTH);
		
		//Center
		JPanel panelCenter = new JPanel();
		panelCenter.setLayout(new BorderLayout());
		
		JPanel panelName = new JPanel();
		panelName.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		JLabel lblName = new JLabel("Name");
		panelName.add(lblName);
		JTextField tfName = new JTextField(20);
		panelName.add(tfName);
		panelCenter.add(panelName, BorderLayout.NORTH);
		
		JPanel panelList = new JPanel();
		panelList.setLayout(new BoxLayout(panelList, BoxLayout.Y_AXIS));
		JLabel lblHotels = new JLabel("Available Hotels: ");
		lblHotels.setForeground(Color.BLUE);
		lblHotels.setAlignmentX(Component.CENTER_ALIGNMENT); 
		panelList.add(lblHotels);
		
		for(int i = 0; i < hotels.size(); i++) {
			JLabel lblList = new JLabel(hotels.get(i));
			lblList.setForeground(Color.BLUE);
			lblList.setAlignmentX(Component.CENTER_ALIGNMENT); 
			panelList.add(lblList);
		}
		panelCenter.add(panelList, BorderLayout.CENTER);
		JScrollPane scroll = new JScrollPane(panelCenter);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		frame.add(scroll, BorderLayout.CENTER);
	}
}
