import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;

public class D_Mdoor extends JFrame implements ActionListener{
	private int count = 0;
	private JLabel sumLabel = new JLabel();
	
	private JButton showAllButton;
	private JButton searchButton;
	private JTable table;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	
	private Object[] columnNames = new Object[]{"deviceId", "time", "state"};
	private Object[][] rowData = new Object[0][];
	private DefaultTableModel model;

	public D_Mdoor(String manuId) {
		D_Mdoor.this.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 622);
		this.setTitle("Manu-dataView-door&window");
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
		
		JLabel minhumiLabel = new JLabel("State:");
		panel.add(minhumiLabel);
		getContentPane().add(panel);
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"0 (open)", "1 (close)"}));
		panel.add(comboBox);
		
		JPanel panel_3 = new JPanel();
		getContentPane().add(panel_3);
		
		
		searchButton = new JButton("Search");
		panel.add(searchButton);
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textField.getText() == "") {
					JOptionPane.showMessageDialog(getContentPane(), "Please fill in some information", "warning", JOptionPane.WARNING_MESSAGE);
				} else {
					model.setRowCount(0);
					count = 0;
					try {                                                                              
						Connection conn = DriverManager.getConnection(Main.URL, Main.USER, Main.PASSWORD);
						Statement stmt = conn.createStatement();
				        ResultSet rs = stmt.executeQuery("SELECT * FROM door WHERE do_deviceId = \"" + textField.getText() + 
				        														"\" AND do_time BETWEEN \"" + textField_1.getText() + "\" AND \"" + textField_2.getText()+ 
				        														"\" AND do_state = " + (int)comboBox.getSelectedIndex());
						while(rs.next()){
				            if(rs.getInt("do_isDeleted")==0) {
				            	String[] newRow = {rs.getString("do_deviceId"), rs.getString("do_time"), rs.getString("do_state")};
					            model.addRow(newRow);
					            count++;
				            }
				        }
				        sumLabel.setText("totally " + count + " records");
					} catch(Exception e1) {
						System.out.println("Connection fails: " + e1.getMessage());
					}
				}
			}
		});
		JPanel panel_2 = new JPanel();
		getContentPane().add(panel_2);
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1);
		
		JButton returnButton = new JButton("RETURN");
		panel_1.add(returnButton);
		returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				D_Mdoor.this.setVisible(false);
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
		
		model = new DefaultTableModel(rowData, columnNames);
		
		JTable table_1 = new JTable(model);
		try {    		
			count = 0;
			
			Connection conn = DriverManager.getConnection(Main.URL, Main.USER, Main.PASSWORD);
			Statement stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery("SELECT * FROM door");
	        
	        while(rs.next()){
	        	if(rs.getInt("do_isDeleted")==0) {
	        		String[] newRow = {rs.getString("do_deviceId"), rs.getString("do_time"), rs.getString("do_state")};
	        		model.addRow(newRow);
	        		count++;
	        	}
	        }   
	        sumLabel.setText("totally " + count + " records");
		} catch(Exception e1) {
			System.out.println("Connection fails: " + e1.getMessage());
		}
		panel.add(sumLabel);
		
		showAllButton = new JButton("Show All Records");
		showAllButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				count = 0;
				model.setRowCount(0);
				
				try {    					
					Connection conn = DriverManager.getConnection(Main.URL, Main.USER, Main.PASSWORD);
					Statement stmt = conn.createStatement();
			        ResultSet rs = stmt.executeQuery("SELECT * FROM door");
			        
			        while(rs.next()){
			        	if(rs.getInt("do_isDeleted")==0) {
			        		String[] newRow = {rs.getString("do_deviceId"), rs.getString("do_time"), rs.getString("do_state")};
			        		model.addRow(newRow);
			        		count++;
			        	}
			        }   
			        sumLabel.setText("totally " + count + " records");
				} catch(Exception e1) {
					System.out.println("Connection fails: " + e1.getMessage());
				}
			}
		});
		panel.add(showAllButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(table_1);	
		getContentPane().add(scrollPane);
		
		
		
		
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
