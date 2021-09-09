import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;

public class E_TempTHSearch extends JFrame implements ActionListener{
	private int count = 0;
	private JLabel sumLabel = new JLabel();
	
	private JTable table_1;
	private JTextField textField,textField_1;
	
	private JPanel panel;
	private JLabel minLabel, maxLabel;
	private JButton searchButton;
	private JButton showAllButton;
	private JButton returnButton;
	private JButton exitButton;
	private JButton mapButton;
	private JScrollPane scrollPane;
	
	private Object[] columnNames = new Object[]{"deviceId", "time", "temperature", "humidity"};
	private Object[][] rowData = new Object[0][];
	private DefaultTableModel model;


	public E_TempTHSearch() {
		E_TempTHSearch.this.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 575);
		this.setTitle("Admin-DataManagement-TempHumi-TemperatureSearch");
		getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panel = new JPanel();
		
		minLabel = new JLabel("MinTemperature:");
		panel.add(minLabel);
		
		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(10);
		
		maxLabel = new JLabel("   MaxTemperature:");
		panel.add(maxLabel);
		
		textField_1 = new JTextField();
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		searchButton = new JButton("Search");
		panel.add(searchButton);
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textField.getText().equals("")) {
					JOptionPane.showMessageDialog(getContentPane(), "Please fill in some information", "warning", JOptionPane.WARNING_MESSAGE);
				} else {
					count = 0;
					model.setRowCount(0);
					try {                                                                              
						Connection conn = DriverManager.getConnection(Main.URL, Main.USER, Main.PASSWORD);
						Statement stmt = conn.createStatement();
				        ResultSet rs = stmt.executeQuery("SELECT * FROM temphumi WHERE th_temperature BETWEEN " + textField.getText() + " AND " + textField_1.getText());
				        
				        while(rs.next()){
				        	if(rs.getInt("th_isDeleted")==0) {
				        		String[] newRow = {rs.getString("th_deviceId"), rs.getString("th_time"), rs.getString("th_temperature"), rs.getString("th_humidity")};
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
		
		returnButton = new JButton("RETURN");
		returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				E_TempTHSearch.this.setVisible(false);
				new D_TempHumi();
			}
		});
		
		mapButton = new JButton("Show Graph");
		panel.add(mapButton);
		mapButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<List<String>> x = new ArrayList<List<String>>();
				List<List<Double>> y = new ArrayList<List<Double>>();
				List<String> lineName = new ArrayList<String>();
				
				try {
					Connection conn = DriverManager.getConnection(Main.URL, Main.USER, Main.PASSWORD);
					Statement stmt = conn.createStatement();
			        ResultSet rs = stmt.executeQuery("SELECT th_temperature, COUNT(*) as count FROM temphumi WHERE th_temperature BETWEEN \"" + textField.getText() + "\" AND \"" + textField_1.getText() + "\" GROUP BY th_temperature");

			        
			        List<String> type = new ArrayList<String>();
			        List<Double> number = new ArrayList<Double>();
			        
			        while(rs.next()){
			        	type.add(rs.getString("th_temperature"));
			            
			        	number.add((double)rs.getInt("count"));
			        }
			        
			        new O_PieChart("Temperature and Humidity in Certain Range of Temperature", "Temperature and Humidity", type, number);
			        
				} catch(Exception e1) {
					System.out.println("connection fails: " + e1.getMessage());
				}
			}
		});
		
		returnButton.setFont(new Font("Georgia", Font.BOLD, 12));
		panel.add(returnButton);
		
		exitButton = new JButton("EXIT");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		exitButton.setFont(new Font("Georgia", Font.BOLD, 12));
		panel.add(exitButton);
		getContentPane().add(panel);
		
		model = new DefaultTableModel(rowData, columnNames);
		table_1 = new JTable(model);
		
		try {          
			count = 0;
			
			Connection conn = DriverManager.getConnection(Main.URL, Main.USER, Main.PASSWORD);
			Statement stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery("SELECT * FROM temphumi");
	        
	        while(rs.next()){
	        	if(rs.getInt("th_isDeleted")==0) {
	        		String[] newRow = {rs.getString("th_deviceId"), rs.getString("th_time"), rs.getString("th_temperature"), rs.getString("th_humidity")};
	        		model.addRow(newRow);
	        		count++;
	        	}
	        }          
	        sumLabel.setText("totally " + count + " records");
		} catch(Exception e) {
			System.out.println("Connection fails: " + e.getMessage());
		}
		
		panel.add(sumLabel);
		
		showAllButton = new JButton("Show All Records");
		showAllButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				count = 0;
				model.setRowCount(0);
				
				try {          
					count = 0;
					
					Connection conn = DriverManager.getConnection(Main.URL, Main.USER, Main.PASSWORD);
					Statement stmt = conn.createStatement();
			        ResultSet rs = stmt.executeQuery("SELECT * FROM temphumi");
			        
			        while(rs.next()){
			        	if(rs.getInt("th_isDeleted")==0) {
			        		String[] newRow = {rs.getString("th_deviceId"), rs.getString("th_time"), rs.getString("th_temperature"), rs.getString("th_humidity")};
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
		
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(table_1);	
		getContentPane().add(scrollPane);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
