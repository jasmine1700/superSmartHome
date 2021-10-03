
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

public class D_Mlight extends JFrame implements ActionListener{
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
	
	private Object[] columnNames = new Object[]{"deviceId", "time", "light"};
	private Object[][] rowData = new Object[0][];
	private DefaultTableModel model;

	public D_Mlight(String manuId) {
		D_Mlight.this.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 730, 537);
		this.setTitle("Manu-ViewData-Light");
		getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panel = new JPanel();
		
		comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"deviceID", "time", "light"}));
		panel.add(comboBox);
		
		searchButton = new JButton("Search");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = comboBox.getSelectedIndex();
				if(index == 0) {
					D_Mlight.this.setVisible(false);
					new E_IDLSearch(manuId,1);
					
				}
				else if(index == 1) {
					D_Mlight.this.setVisible(false);
					new E_TimeLSearch(manuId,1);
				}
				else {
					D_Mlight.this.setVisible(false);
					new E_LightLSearch(manuId,1);
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
								new Q_DeleteTheLightdata(table_1, model);
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
				D_Mlight.this.setVisible(false);
				new C_MData(manuId);
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
	        ResultSet rs = stmt.executeQuery("SELECT * FROM light,device where d_manuId = \"" + manuId + "\" and l_deviceId = d_deviceId;");
	        
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
//					ResultSet rs = stmt.executeQuery("SELECT * FROM light,device where d_manuId = \"" + manuId + "\" and l_deviceId = d_deviceId;");
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
		if(e.getSource() == deleteButton) {
			
		}
		
	}

}
