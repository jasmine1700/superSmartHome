
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;

public class E_IDLSearch extends JFrame implements ActionListener{
	private int count = 0;
	private JLabel sumLabel = new JLabel();
	
	private JTable table_1;
	private JTextField textField;
	
	private JPanel panel;
	private JLabel idLabel;
	private JButton searchButton;
	private JButton showAllButton;
	private JButton returnButton;
	private JButton exitButton;
	private JScrollPane scrollPane;
	
	private Object[] columnNames = new Object[]{"deviceId", "time", "light"};
	private Object[][] rowData = new Object[0][];
	private DefaultTableModel model;

	public E_IDLSearch(String manuId,int flag) {
		E_IDLSearch.this.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 730, 539);
		if(flag == 0) {
			this.setTitle("Admin-DataManagement-Light-DeviceIDSearch");
		}
		else {
			this.setTitle("Manu-ViewData-Light-DeviceIDSearch");
		}
		getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panel = new JPanel();
		
		idLabel = new JLabel("Device ID:");
		panel.add(idLabel);
		
		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(10);
		
		searchButton = new JButton("Search");
		panel.add(searchButton);
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textField.getText().equals("")) {
					JOptionPane.showMessageDialog(getContentPane(), "Please complete the information", "warning", JOptionPane.WARNING_MESSAGE);
				} else {
					model.setRowCount(0);
					count = 0;
				
					try {                                                                              
						Connection conn = DriverManager.getConnection(Main.URL, Main.USER, Main.PASSWORD);
						Statement stmt = conn.createStatement();
						ResultSet rs = null;
						if(flag == 0) {
							rs = stmt.executeQuery("SELECT * FROM light");
						}
						else {
							rs = stmt.executeQuery("SELECT * FROM light,device where d_manuId = \"" + manuId + "\" and l_deviceId = d_deviceId;");					        
						}
						
						while(rs.next()){
				            if(rs.getString("l_deviceId").equals(textField.getText()) && rs.getInt("l_isDeleted") == 0) {
				            	String[] newRow = {rs.getString("l_deviceId"), rs.getString("l_time"), rs.getString("l_light")};
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
				E_IDLSearch.this.setVisible(false);
				if(flag == 0) {
					new D_Light();
				}
				else {
					new D_Mlight(manuId);
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
				rs = stmt.executeQuery("SELECT * FROM light");
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
						rs = stmt.executeQuery("SELECT * FROM light");
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
				} catch(Exception e2) {
					System.out.println("Connection fails: " + e2.getMessage());
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
		
		
	}

}
