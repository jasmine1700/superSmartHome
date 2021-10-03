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

public class E_LightLSearch extends JFrame implements ActionListener{
	private int count = 0;
	private JLabel sumLabel = new JLabel();
	
	private JPanel panel;
	private JLabel minLabel, maxLabel;
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
	
	private Object[] columnNames = new Object[]{"deviceId", "time", "light"};
	private Object[][] rowData = new Object[0][];
	private DefaultTableModel model;

	public E_LightLSearch(String manuId,int flag) {
		E_LightLSearch.this.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 550);
		if(flag == 0) {
			this.setTitle("Admin-DataManagement-Light-LightSearch");
		}
		else {
			this.setTitle("Manu-ViewData-Light-LightSearch");
		}
		getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panel = new JPanel();
		
		minLabel = new JLabel("MinLight");
		panel.add(minLabel);
		
		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(10);
		
		maxLabel = new JLabel("   MaxLight");
		panel.add(maxLabel);
		
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
				        if(flag ==0) {
				        	rs = stmt.executeQuery("SELECT * FROM light WHERE l_light BETWEEN \"" + textField.getText() + "\" AND \"" + textField_1.getText()+ "\"");				        
				        }
				        else {
				        	rs = stmt.executeQuery("SELECT * FROM light,device WHERE l_light BETWEEN \"" + textField.getText() + "\" AND \"" + textField_1.getText()+ "\" AND d_manuID = \"" + manuId + "\" AND l_deviceId = d_deviceId");				        
				        }
				        while(rs.next()){
				            if(rs.getInt("l_isDeleted")==0) {
				        		String[] newRow = {rs.getString("l_deviceId"), rs.getString("l_time"), rs.getString("l_light")};
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
				E_LightLSearch.this.setVisible(false);
				if(flag == 0) {
					new D_Light();
				}
				else {
					new D_Mlight(manuId);
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
			        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS lightCount, l_light FROM light WHERE l_light BETWEEN \"" + textField.getText() + "\" AND \"" + textField_1.getText()+ "\"" + "GROUP BY l_light");

			        lineName.add("light");
			        List<String> x1 = new ArrayList<String>();
			        List<Double> y1 = new ArrayList<Double>();
			        
			        while(rs.next()){
			            x1.add(rs.getInt("l_light") + "");
			            y1.add((double) rs.getInt("lightCount"));
			        }
			        x.add(x1);
			        y.add(y1);
			        
					new O_BarChart("Light Distribution Between the Given Min And Max Value", "Light Distribution", "Light", "Number", lineName, x, y);
					
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
		    if(flag ==0) {
		       	rs = stmt.executeQuery("SELECT * FROM light ");				        
		    }
	        else {
		    	rs = stmt.executeQuery("SELECT * FROM light,device where d_manuId = \"" + manuId + "\" and l_deviceId = d_deviceId;");					        
	   	    }
	        
	        while(rs.next()){
	        	if(rs.getInt("l_isDeleted")==0) {
	        		String[] newRow = {rs.getString("l_deviceId"), rs.getString("l_time"), rs.getString("l_light")};
	        		model.addRow(newRow);
	        		count++;
	        	}
	        }   
	        sumLabel.setText("totally " + count + " records");
		} catch(Exception e) {
			System.out.println("Connection fails: " + e.getMessage());
		}
		
		panel.add(sumLabel);
		
//		showAllButton = new JButton("Show All Records");
//		showAllButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				count = 0;
//				model.setRowCount(0);
//				
//				try {    					
//					Connection conn = DriverManager.getConnection(Main.URL, Main.USER, Main.PASSWORD);
//					Statement stmt = conn.createStatement();
//					ResultSet rs = null;
//				    if(flag ==0) {
//				       	rs = stmt.executeQuery("SELECT * FROM light ");				        
//				    }
//			        else {
//				    	rs = stmt.executeQuery("SELECT * FROM light,device where d_manuId = \"" + manuId + "\" and l_deviceId = d_deviceId;");					        
//			   	    }
//			        
//			        while(rs.next()){
//			        	if(rs.getInt("l_isDeleted")==0) {
//			        		String[] newRow = {rs.getString("l_deviceId"), rs.getString("l_time"), rs.getString("l_light")};
//			        		model.addRow(newRow);
//			        		count++;
//			        	}
//			        }   
//			        sumLabel.setText("totally " + count + " records");
//				} catch(Exception e1) {
//					System.out.println("Connection fails: " + e1.getMessage());
//				}
//			}
//		});
//		panel.add(showAllButton);
		
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(table_1);	
		getContentPane().add(scrollPane);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}

