
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

public class E_TimeTHSearch extends JFrame implements ActionListener{
	private int count = 0;
	private JLabel sumLabel = new JLabel();
	
	private JPanel panel;
	private JLabel stLabel, etLabel;
	private JTextField textField, textField_1;
	private JComboBox<String> comboBox;
	private JButton searchButton;
	private JButton showAllButton;
	private JButton addButton;
	private JButton deleteButton;
	private JButton reviseButton; 
	private JButton returnButton;
	private JButton exitButton;
	private JButton mapButton;
	private JTable table_1;
	private JScrollPane scrollPane; 
	
	private Object[] columnNames = new Object[]{"deviceId", "time", "temperature", "humidity"};
	private Object[][] rowData = new Object[0][];
	private DefaultTableModel model;

	public E_TimeTHSearch(String manuId,int flag) {
		E_TimeTHSearch.this.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 550);
		if(flag == 0) {
			this.setTitle("Admin-DataManagement-TempHumi-TimeSearch");
		}
		else {
			this.setTitle("Manu-ViewData-TempHumi-TimeSearch");
		}
		getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panel = new JPanel();
		
		stLabel = new JLabel("Start Time:");
		panel.add(stLabel);
		
		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel etLabel = new JLabel("   End Time:");
		panel.add(etLabel);
		
		textField_1 = new JTextField();
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		searchButton = new JButton("Search");
		panel.add(searchButton);
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textField.getText().equals("") || textField_1.getText().equals("")) {
					JOptionPane.showMessageDialog(getContentPane(), "Please complete the information", "warning", JOptionPane.WARNING_MESSAGE);
				} else {
					model.setRowCount(0);
					count = 0;
					try {
						Connection conn = DriverManager.getConnection(Main.URL, Main.USER, Main.PASSWORD);
						Statement stmt = conn.createStatement();
				        ResultSet rs = null;
				        if(flag == 0) {
				        	rs = stmt.executeQuery("SELECT * FROM temphumi WHERE th_time BETWEEN \"" + textField.getText() + "\" AND \"" + textField_1.getText()+ "\"");
				        }
				        else {
				        	rs = stmt.executeQuery("SELECT * FROM temphumi,device WHERE th_time BETWEEN \"" + textField.getText() + "\" AND \"" + textField_1.getText()+ "\" AND d_manuId = \"" + manuId + "\" AND d_deviceId = th_deviceId;");
				        }
				        
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
				E_TimeTHSearch.this.setVisible(false);
				if(flag == 0) {
					new D_TempHumi();
				}
				else {
					new D_Mtemphumi(manuId);
				}
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
			        ResultSet rs = stmt.executeQuery("SELECT * FROM temphumi WHERE th_time BETWEEN \"" + textField.getText() + "\" AND \"" + textField_1.getText()+ "\"");
			        
			        lineName.add("temperature");
					List<String> x1 = new ArrayList<String>();
			        List<Double> y1 = new ArrayList<Double>();
			        
			        lineName.add("humidity");
			        List<String> x2 = new ArrayList<String>();
			        List<Double> y2 = new ArrayList<Double>();
			        
			        while(rs.next()){
			            x1.add(rs.getString("th_time"));
			            y1.add(rs.getDouble("th_temperature"));
			            
			            x2.add(rs.getString("th_time"));
			            y2.add(rs.getDouble("th_humidity"));
			        }
			        x.add(x1);
			        y.add(y1);
			        
			        x.add(x2);
			        y.add(y2);
			        
					new O_LineChart("Temperature and Humidity in Certain Piece of Time", "Temperature and Humidity", "time", "value", lineName, x, y);
					
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
			ResultSet rs = null;
		    if(flag == 0) {
		        	rs = stmt.executeQuery("SELECT * FROM temphumi ");
		    }
            else {
		        	rs = stmt.executeQuery("SELECT * FROM temphumi,device where d_manuId = \"" + manuId + "\" and d_deviceId = th_deviceId;");						     
			}
	        
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
					ResultSet rs = null;
				    if(flag == 0) {
				        	rs = stmt.executeQuery("SELECT * FROM temphumi ");
				    }
			        else {
				        	rs = stmt.executeQuery("SELECT * FROM temphumi,device where d_manuId = \"" + manuId + "\" and d_deviceId = th_deviceId;");
					}

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
