
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;

public class D_Mtemphumi extends JFrame implements ActionListener{
	private JTable table;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_5;
	private JTextField textField_7;
	private JTextField textField_9;

	public D_Mtemphumi(String manuId) {
		D_Mtemphumi.this.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 807, 622);
		this.setTitle("Manu-dataView-temphumi");
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
		getContentPane().add(panel);
		
		JPanel panel_4 = new JPanel();
		getContentPane().add(panel_4);
		
		JLabel lblMintemprature = new JLabel("minTemprature:");
		panel_4.add(lblMintemprature);
		
		textField_7 = new JTextField();
		panel_4.add(textField_7);
		textField_7.setColumns(10);
		
		JLabel lblMaxtemprature = new JLabel("maxTemprature:");
		panel_4.add(lblMaxtemprature);
		
		textField_9 = new JTextField();
		panel_4.add(textField_9);
		textField_9.setColumns(10);
		
		JPanel panel_5 = new JPanel();
		getContentPane().add(panel_5);
		
		JLabel minhumiLabel = new JLabel("minHuminity:");
		panel_5.add(minhumiLabel);
		
		textField_3 = new JTextField();
		panel_5.add(textField_3);
		textField_3.setColumns(10);
		
		JLabel maxhumiLabel = new JLabel("maxHuminity:");
		panel_5.add(maxhumiLabel);
		
		textField_5 = new JTextField();
		panel_5.add(textField_5);
		textField_5.setColumns(10);
		
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
				D_Mtemphumi.this.setVisible(false);
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
				"deviceID", "time", "temprature", "huminity" 
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
