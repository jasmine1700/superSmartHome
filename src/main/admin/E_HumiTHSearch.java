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

public class E_HumiTHSearch extends JFrame implements ActionListener{
	private int count = 0;
	private JLabel sumLabel = new JLabel();
	
	private JPanel panel;
	private JLabel minLabel, maxLabel;
	private JTextField textField, textField_1;
	private JButton searchButton;
	private JButton showAllButton;
	private JButton returnButton;
	private JButton exitButton;
	private JButton mapButton;
	private JTable table_1;
	private JScrollPane scrollPane; 
	
	private Object[] columnNames = new Object[]{"deviceId", "time", "temperature", "humidity"};
	private Object[][] rowData = new Object[0][];
	private DefaultTableModel model;

	public E_HumiTHSearch() {
		E_HumiTHSearch.this.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 550);
		this.setTitle("Admin-DataManagement-TempHumi-TimeSearch");
		getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panel = new JPanel();
		
		minLabel = new JLabel("minHumidity");
		panel.add(minLabel);
		
		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel maxLabel = new JLabel("   maxHumidity");
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
					model.setRowCount(0);
					count = 0;
					try {
						Connection conn = DriverManager.getConnection(Main.URL, Main.USER, Main.PASSWORD);
						Statement stmt = conn.createStatement();
				        ResultSet rs = stmt.executeQuery("SELECT * FROM temphumi WHERE th_humidity BETWEEN \"" + textField.getText() + "\" AND \"" + textField_1.getText()+ "\"");
				        
				        while(rs.next()){
				            if(rs.getInt("th_isDeleted")==0) {
				        		String[] newRow = {rs.getString("th_deviceId"), rs.getString("th_time"), rs.getString("th_temperature"), rs.getString("th_humidity")};
				        		model.addRow(newRow);
				        		count++;
				        	}
				            
				        }
				        sumLabel.setText("totally " + count + " records");
					} catch(Exception e1) {
						System.out.println("connection fails: " + e1.getMessage());
					} 
				}
				
			}
		});
				
		returnButton = new JButton("RETURN");
		returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				E_HumiTHSearch.this.setVisible(false);
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
			        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS humiCount, th_humidity FROM temphumi WHERE th_humidity BETWEEN \"" + textField.getText() + "\" AND \"" + textField_1.getText()+ "\"" + "GROUP BY th_humidity");

			        lineName.add("humidity");
			        List<String> x1 = new ArrayList<String>();
			        List<Double> y1 = new ArrayList<Double>();
			        
			        while(rs.next()){
			            x1.add(rs.getInt("th_humidity") + "");
			            y1.add((double) rs.getInt("humiCount"));
			        }
			        x.add(x1);
			        y.add(y1);
			        
					new O_BarChart("Humidity Distribution Between the Given Min And Max Value", "Humidity Distribution", "Humidity", "Number", lineName, x, y);
					
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

