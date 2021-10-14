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


public class C_MDeviceManage extends JFrame implements ActionListener{
	private int count = 0;
	private JLabel sumLabel = new JLabel();
	
	private JTable table_1;
	private JTextField textField;
	private JPanel panel;
	private JComboBox<String> comboBox = new JComboBox<String>();
	private JButton searchButton;
	private JButton showAllButton;
	private JButton addButton;
	private JButton deleteButton;
	private JButton reviseButton;
	private JButton returnButton;
	private JButton exitButton;
	
	private Object[] columnNames = new Object[]{"d_deviceId", "d_deviceName", "d_classId", "d_specification", "d_manuId", "d_familyId"};
	private Object[][] rowData = new Object[0][];
	private DefaultTableModel model;
	

	public C_MDeviceManage(String manuId) {
		C_MDeviceManage.this.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 908, 627);
		this.setTitle("Manu-deviceManagement");
		getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panel = new JPanel();
		
		comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"deviceId", "deviceName", "classId", "sepcification", "manuId", "familyId"}));
		panel.add(comboBox);
		
		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(10);
		
		searchButton = new JButton("Search");
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
						ResultSet rs = stmt.executeQuery("SELECT * FROM device");
						
						while(rs.next()){
				            if(rs.getString("d_" + (String)comboBox.getSelectedItem()).equals(textField.getText()) && rs.getString("d_manuId").equals(manuId)) {
				            	String[] newRow = {rs.getString("d_deviceId"), rs.getString("d_deviceName"), rs.getString("d_classId"), rs.getString("d_specification"), rs.getString("d_manuId"), rs.getString("d_familyId")};
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
		panel.add(searchButton);
		
		addButton = new JButton("Add");
		panel.add(addButton);
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							P_AddADevice frame = new P_AddADevice(manuId);
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
							if(table_1.getSelectedRow()==-1) {
								JOptionPane.showMessageDialog(getContentPane(), "Please click a data in table to choose the record you want to delete. ", "warning", JOptionPane.WARNING_MESSAGE);
							} else {
					        Q_DeleteTheDevice.delete(table_1, model);
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
				C_MDeviceManage.this.setVisible(false);
				new B_Manu();
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
	        ResultSet rs = stmt.executeQuery("SELECT * FROM device");
	        
	        while(rs.next()){
	        	if(rs.getInt("d_isDeleted")==0 && rs.getString("d_manuId").equals(manuId)) {
		            String[] newRow = {rs.getString("d_deviceId"), rs.getString("d_deviceName"), rs.getString("d_classId"), rs.getString("d_specification"), rs.getString("d_manuId"), rs.getString("d_familyId"), };
	        		model.addRow(newRow);
	        		count++;
	        	}
	        } 
	        sumLabel.setText("totally " + count + " records");
		} catch(Exception e) {
			System.out.println("Connection fails: " + e.getMessage());
		}
		sumLabel = new JLabel("totally " + count + " records");
		panel.add(sumLabel);
		
		showAllButton = new JButton("Show All Records");
		panel.add(showAllButton);
		showAllButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setRowCount(0);
				count = 0;
				
				try {                                                                              
					Connection conn = DriverManager.getConnection(Main.URL, Main.USER, Main.PASSWORD);
					Statement stmt = conn.createStatement();
			        ResultSet rs = stmt.executeQuery("SELECT * FROM device");
			        
			        while(rs.next()){
			        	if(rs.getInt("d_isDeleted")==0 && rs.getString("d_manuId").equals(manuId)) {
				            String[] newRow = {rs.getString("d_deviceId"), rs.getString("d_deviceName"), rs.getString("d_classId"), rs.getString("d_specification"), rs.getString("d_manuId"), rs.getString("d_familyId"), };
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
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(table_1);	
		getContentPane().add(scrollPane);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

}
