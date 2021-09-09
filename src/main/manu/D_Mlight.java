
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;

public class D_Mlight extends JFrame implements ActionListener{
	private JTable table;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	public D_Mlight(String manuId) {
		D_Mlight.this.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 955, 622);
		this.setTitle("Manu-dataView-light");
		getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		JPanel panel = new JPanel();
		
		JLabel idLabel = new JLabel("DeviceID:");
		panel.add(idLabel);
		
		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel stLabel = new JLabel("StartTime:");
		panel.add(stLabel);
		
		textField_1 = new JTextField();
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel etLabel = new JLabel("EndTime:");
		panel.add(etLabel);
		
		textField_2 = new JTextField();
		panel.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel minLightLabel = new JLabel("minLight:");
		panel.add(minLightLabel);
		
		JComboBox<String> minLightcomboBox = new JComboBox<String>();
		minLightcomboBox.setModel(new DefaultComboBoxModel(new String[] {"", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}));
		panel.add(minLightcomboBox);
		
		JLabel maxLightLabel = new JLabel("maxLight:");
		panel.add(maxLightLabel);
		getContentPane().add(panel);
		
		JComboBox<String> maxLightcomboBox = new JComboBox<String>();
		maxLightcomboBox.setModel(new DefaultComboBoxModel(new String[] {"", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}));
		panel.add(maxLightcomboBox);
		
		JPanel panel_3 = new JPanel();
		getContentPane().add(panel_3);
		
		JButton searchButton = new JButton("Search");
		panel_3.add(searchButton);
		
		JButton searchAllButton = new JButton("Search All");
		panel_3.add(searchAllButton);
		
		JPanel panel_2 = new JPanel();
		getContentPane().add(panel_2);
		
		JLabel sumLabel = new JLabel("totally:");
		panel_2.add(sumLabel);
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1);
		
		JButton returnButton = new JButton("RETURN");
		panel_1.add(returnButton);
		returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				D_Mlight.this.setVisible(false);
				new C_MDetailSearch(manuId);
			}
		});
		returnButton.setFont(new Font("Georgia", Font.BOLD, 12));
		
		JButton exitButton = new JButton("EXIT");
		panel_1.add(exitButton);
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		exitButton.setFont(new Font("Georgia", Font.BOLD, 12));
		
		
		JTable table_1 = new JTable();
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
			},
			new String[] {
				"deviceID", "time", "huminity", 
			}
		));
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(table_1);	
		getContentPane().add(scrollPane);
		
		
		
		
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
