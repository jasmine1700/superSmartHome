import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;


public class C_DeviceClassManage extends JFrame implements ActionListener{
	private JTable table;
	private JTextField textField;
	private JPanel panel;
	private JComboBox<String> comboBox = new JComboBox<String>();
	private JButton searchButton;
	private JButton showAllButton;
	private JButton addButton;
	private JButton deleteButton;
	private JButton returnButton;
	private JButton exitButton;
	private JScrollPane scrollPane;
	
	private Object[] columnNames = new Object[]{"classId", "className"};
	private Object[][] rowData = new Object[0][];
	private DefaultTableModel model;

	public C_DeviceClassManage() {
		C_DeviceClassManage.this.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 916, 556);
		this.setTitle("Admin-deviceClassManagement");
		getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panel = new JPanel();
		
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"classId", "className"}));
		panel.add(comboBox);
		
		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(10);
		
		searchButton = new JButton("Search");
		panel.add(searchButton);
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {                                                                              
					Connection conn = DriverManager.getConnection(Main.URL, Main.USER, Main.PASSWORD);
					Statement stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery("SELECT * FROM class");
					
					while(rs.next()){
			            if(rs.getString("c_" + (String)comboBox.getSelectedItem()).equals(textField.getText()) && rs.getInt("c_isDeleted") == 0) {
			            	String[] newRow = {rs.getString("c_classId"), rs.getString("c_className")};
				            model.addRow(newRow);
			            }
			        }
				} catch(Exception e1) {
					System.out.println("Connection fails: " + e1.getMessage());
				}
			}
		});
		
		addButton = new JButton("Add");
		panel.add(addButton);
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							P_AddAClass frame = new P_AddAClass();
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							if(table.getSelectedRow()==-1) {
								JOptionPane.showMessageDialog(getContentPane(), "Please click a data in table to choose the record you want to delete. ", "warning", JOptionPane.WARNING_MESSAGE);
							} else {
							new	Q_DeleteTheDeviceClass(table, model);
							}
						} catch (SQLException e1) {
					         e1.printStackTrace();
						}
					}
				});
			}
		});
		panel.add(deleteButton);
		
		returnButton = new JButton("RETURN");
		returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				C_DeviceClassManage.this.setVisible(false);
				new B_Admin();
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
		JTable table_1 = new JTable(model);
		
		try {                                                                              
			Connection conn = DriverManager.getConnection(Main.URL, Main.USER, Main.PASSWORD);
			Statement stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery("SELECT * FROM class");
	        
	        while(rs.next()){
	        	
	        	if(rs.getInt("c_isDeleted")==0) {
	        		String[] newRow = {rs.getString("c_classId"), rs.getString("c_className")};
	        		model.addRow(newRow);
	        	}
	        }           
		} catch(Exception e) {
			System.out.println("Connection fails: " + e.getMessage());
		}
		
		showAllButton = new JButton("Show All Records");
		panel.add(showAllButton);
		showAllButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setRowCount(0);
				
				try {
					Connection conn = DriverManager.getConnection(Main.URL, Main.USER, Main.PASSWORD);
					Statement stmt = conn.createStatement();
			        ResultSet rs = stmt.executeQuery("SELECT * FROM class");
			        
			        while(rs.next()){
			        	
			        	if(rs.getInt("c_isDeleted")==0) {
			        		String[] newRow = {rs.getString("c_classId"), rs.getString("c_className")};
			        		model.addRow(newRow);
			        	}
			        }   
				} catch(Exception e1) {
					System.out.println("Connection fails: " + e1.getMessage());
				}
			}
		});
		
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(table_1);	
		getContentPane().add(scrollPane);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
