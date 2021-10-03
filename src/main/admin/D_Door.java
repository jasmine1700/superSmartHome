
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

public class D_Door extends JFrame implements ActionListener{
	private int count = 0;
	private JLabel sumLabel = new JLabel();
	
	private JPanel panel;
	private JComboBox<String> comboBox;
	private JButton searchButton;
	private JButton showAllButton;
	private JButton deleteButton;
	private JButton returnButton;
	private JButton exitButton;
	private JTable table_1;
	private JScrollPane scrollPane; 
	
	private Object[] columnNames = new Object[]{"deviceId", "time", "state"};
	private Object[][] rowData = new Object[0][];
	private DefaultTableModel model;


	public D_Door() {
		D_Door.this.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 730, 539);
		this.setTitle("Admin-DoorManagement-Door");
		getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panel = new JPanel();
		
		comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"deviceID", "time", "state"}));
		panel.add(comboBox);
		
		searchButton = new JButton("Search");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = comboBox.getSelectedIndex();
				if(index == 0) {
					D_Door.this.setVisible(false);
					new E_IDDSearch(null,0);
					
				}
				else if(index == 1) {
					D_Door.this.setVisible(false);
					new E_TimeDSearch(null,0);
				}
				else {
					D_Door.this.setVisible(false);
					new E_StateDSearch(null,0);
				}
			}
		});
		panel.add(searchButton);
		
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							if(table_1.getSelectedRow()==-1) {
								JOptionPane.showMessageDialog(getContentPane(), "Please click a data in table to choose the record you want to delete. ", "warning", JOptionPane.WARNING_MESSAGE);
							} else {
								new Q_DeleteTheDoordata(table_1, model);
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
				D_Door.this.setVisible(false);
				new C_DataManage();
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
		
//		showAllButton = new JButton("Show All Records");
//		showAllButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				count = 0;
//				model.setRowCount(0);
//
//				try {
//					Connection conn = DriverManager.getConnection(Main.URL, Main.USER, Main.PASSWORD);
//					Statement stmt = conn.createStatement();
//			        ResultSet rs = stmt.executeQuery("SELECT * FROM door");
//
//			        while(rs.next()){
//			        	if(rs.getInt("do_isDeleted")==0) {
//			        		String[] newRow = {rs.getString("do_deviceId"), rs.getString("do_time"), rs.getString("do_state")};
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
