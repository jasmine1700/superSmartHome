import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class C_UserManage extends JFrame implements ActionListener{
	
	private int count = 0;
	
	private JTextField textField;
	private JLabel sumLabel;
	private JButton searchButton;
	private JButton showAllButton;
	private JButton addButton;
	private JButton deleteButton;
	private JButton reviseButton;
	private JButton returnButton;
	private JButton exitButton;
	private JComboBox<String> comboBox = new JComboBox<String>();
	private JTable table_1;
	
	private Object[] columnNames = new Object[]{"phone number", "avatar"};
	private Object[][] rowData = new Object[0][];
	private DefaultTableModel model = new DefaultTableModel(rowData, columnNames);

	public C_UserManage() {
		C_UserManage.this.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 535);
		this.setTitle("Admin-UserManagement");
		getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		JPanel panel = new JPanel();
		
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"phone", "avatar"}));
		panel.add(comboBox);
		
		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(10);
		
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
						ResultSet rs = stmt.executeQuery("SELECT * FROM user");
						
						while(rs.next()){
				            if(rs.getString("u_" + (String)comboBox.getSelectedItem()).equals(textField.getText()) && rs.getInt("u_isDeleted")==0) {
				            	String[] newRow = {rs.getString("u_phone"), rs.getString("u_avatar")};
					            model.addRow(newRow);
					            count++;
				            }
				        }
				        sumLabel.setText("totally " + count + " records");
					} catch(Exception e1) {
						System.out.println("Connection fails: " + e1.getMessage());
					}				}
			}
		});
		
		addButton = new JButton("Add");
		panel.add(addButton);
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							P_AddAUser frame = new P_AddAUser();
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
					        Q_DeleteTheUser.delete(table_1, model);
							}
						} catch (SQLException e1) {
					         e1.printStackTrace();
						}
					}
				});
			}
		});
		panel.add(deleteButton);
		
		reviseButton = new JButton("Revise");
		panel.add(reviseButton);
		reviseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							
							if(table_1.getSelectedRow()==-1) {
								JOptionPane.showMessageDialog(getContentPane(), "Please click a data in table to choose the record you want to revise. \nAnd this user's password will be changed randomly. ", "warning", JOptionPane.WARNING_MESSAGE);
							} else {
								new ReviseTheUserPsw(table_1, model);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		
		returnButton = new JButton("RETURN");
		returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				C_UserManage.this.setVisible(false);
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
		
		sumLabel = new JLabel();
		
		table_1 = new JTable(model);
		
		try {      
			count = 0;
			Connection conn = DriverManager.getConnection(Main.URL, Main.USER, Main.PASSWORD);
			Statement stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery("SELECT u_phone, u_avatar, u_isDeleted FROM user ORDER BY u_phone");
	        
	        while(rs.next()){
	        	
	        	if(rs.getInt("u_isDeleted")==0) {
	        		String[] newRow = {rs.getString("u_phone"), rs.getString("u_avatar")};
	        		model.addRow(newRow);
	        		count++;
	        	}
	        }   
	        sumLabel.setText("totally " + count + " records");
		} catch(Exception e) {
			System.out.println("Connection fails: " + e.getMessage());
		}
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(table_1);	
		getContentPane().add(scrollPane);
		
		panel.add(sumLabel);
		
		showAllButton = new JButton("Show All Records");
		panel.add(showAllButton);
		showAllButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				count = 0;
				model.setRowCount(0);
				
				try {
					count = 0;
					Connection conn = DriverManager.getConnection(Main.URL, Main.USER, Main.PASSWORD);
					Statement stmt = conn.createStatement();
			        ResultSet rs = stmt.executeQuery("SELECT u_phone, u_avatar, u_isDeleted FROM user ORDER BY u_phone");
			        
			        while(rs.next()){
			        	
			        	if(rs.getInt("u_isDeleted")==0) {
			        		String[] newRow = {rs.getString("u_phone"), rs.getString("u_avatar")};
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
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
