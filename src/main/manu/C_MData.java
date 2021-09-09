import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;

public class C_MData extends JFrame implements ActionListener{
	private JTable table_1;
	private JPanel panel;
	private JButton searchButton;
	private JButton returnButton;
	private JButton exitButton;
	
	private Object[] columnNames = new Object[]{};
	private Object[][] rowData = new Object[0][];
	private DefaultTableModel model;
	
	private JLabel sumLabel;

	public C_MData(String manuId) {
		C_MData.this.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 730, 527);
		this.setTitle("Manu-dataView");
		getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panel = new JPanel();
		
		searchButton = new JButton("Search For Detail");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				C_MData.this.setVisible(false);
				new D_MDetailSearch(manuId);
			}
		});
		panel.add(searchButton);
		
		returnButton = new JButton("RETURN");
		returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				C_MData.this.setVisible(false);
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
			Connection conn = DriverManager.getConnection(Main.URL, Main.USER, Main.PASSWORD);
			Statement stmt = conn.createStatement();
			String sql = "SELECT th_deviceId, th_deviceName, d_classId, d_specification, d_manuId, d_familyId FROM device ";
	        ResultSet rs = stmt.executeQuery(sql);
	        
	        while(rs.next()){
	        	if(rs.getString("d_manuId").equals(manuId)) {
	        		String[] newRow = {rs.getString("d_deviceId"), rs.getString("d_deviceName"), rs.getString("d_classId"), rs.getString("d_specification"), rs.getString("d_manuId"), rs.getString("d_familyId"), };
	        		model.addRow(newRow);
	        	}
	        }          
		} catch(Exception e) {
			System.out.println("Connection fails: " + e.getMessage());
		}
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(table_1);	
		getContentPane().add(scrollPane);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
