import java.awt.BorderLayout;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.SwingConstants;
import javax.swing.JButton;

import java.awt.Dimension;
import java.awt.Font;

/**
 * 
 * @author XinyaoYu
 *
 */
public class P_AddAUser extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JTextField textField3;
	private JTextField textField1;
	private JTextField textField2;
	private JPanel panel3;
	private JPanel panel2;
	private JPanel panel1;
	private JPanel panel4;
	private JPanel panel5;
	private JLabel label4;
	private JPanel panel;
	private JButton newButton1;
	private JButton newButton2;
	private JButton newButton3;

	/**
	 * Create the frame.
	 */
	public P_AddAUser() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(3, 1, 0, 0));
		
		label4 = new JLabel("Please Add A User");
		label4.setPreferredSize(new Dimension(60, 40));
		label4.setHorizontalAlignment(SwingConstants.CENTER);
		label4.setHorizontalTextPosition(SwingConstants.CENTER);
		label4.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 20));
		contentPane.add(label4);
		
		panel5 = new JPanel();
		panel5.setMinimumSize(new Dimension(10, 70));
		contentPane.add(panel5);
		panel5.setLayout(new GridLayout(3, 1, 0, 5));
		
		panel1 = new JPanel();
		panel5.add(panel1);
		panel1.setLayout(new BorderLayout(0, 0));
		
		JLabel label1 = new JLabel("Phone Number: ");
		panel1.add(label1, BorderLayout.WEST);
		
		textField1 = new JTextField();
		panel1.add(textField1, BorderLayout.EAST);
		textField1.setColumns(30);
		
		panel3 = new JPanel();
		panel5.add(panel3);
		panel3.setLayout(new BorderLayout(0, 0));
		
		JLabel label3 = new JLabel("Password: ");
		panel3.add(label3, BorderLayout.WEST);
		
		textField3 = new JTextField();
		panel3.add(textField3, BorderLayout.EAST);
		textField3.setColumns(30);
		
		panel2 = new JPanel();
		panel5.add(panel2);
		panel2.setLayout(new BorderLayout(0, 0));
		
		JLabel label2 = new JLabel("User Avatar: ");
		panel2.add(label2, BorderLayout.WEST);
		
		textField2 = new JTextField();
		panel2.add(textField2, BorderLayout.EAST);
		textField2.setColumns(30);
		
		panel4 = new JPanel();
		contentPane.add(panel4);
		panel4.setLayout(new BorderLayout(0, 0));
		
		panel = new JPanel();
		panel4.add(panel, BorderLayout.SOUTH);
		
		newButton1 = new JButton("Clear");
		newButton1.setActionCommand("Clear");
		panel.add(newButton1);
		
		newButton2 = new JButton("Return");
		panel.add(newButton2);
		
		newButton3 = new JButton("Confirm");
		panel.add(newButton3);
		
		// listening register 
        newButton1.addActionListener(this);
        newButton2.addActionListener(this);
        newButton3.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
        //When the JButton "Clear" is clicked, the program will erase the entered information
        if (e.getActionCommand().equals("Clear")) { 
            textField3.setText(""); 
            textField1.setText(""); 
            textField2.setText(""); 
        } 
        //When the JButton "Return" is clicked, the program will exit
        if (e.getActionCommand().equals("Return")) {
            this.setVisible(false);
        }       
        //When the JButton "Confirm" is clicked, the program will go to another frame
        if (e.getActionCommand().equals("Confirm")) {
            try
            {
              // create a mysql database connection
              Connection conn = DriverManager.getConnection(Main.URL, Main.USER, Main.PASSWORD);
              Statement stmt = conn.createStatement();
    		  ResultSet rs = stmt.executeQuery("SELECT u_phone FROM user");
//    		  if(rs.getString("u_phone").equals(textField1.getText())) {
//				  JOptionPane.showMessageDialog(this, "Sorry, this phone number has been registered, please change another one!");
//    		  }
    		  while(rs.next()){
    			  if(rs.getString("u_phone").equals(textField1.getText())) {
    				  JOptionPane.showMessageDialog(this, "Sorry, this phone number has been registered, please change another one!");
    			  }
    		  }

              // the mysql insert statement
              String query = " insert into user (u_phone, u_password, u_avatar, u_isDeleted)"
                + " values (?, ?, ?, ?)";

              // create the mysql insert preparedstatement
              PreparedStatement preparedStmt = conn.prepareStatement(query);
              preparedStmt.setString (1, textField1.getText());
              preparedStmt.setString (2, ParseMD5.parseStrToMd5L32(textField3.getText()));
              preparedStmt.setString(3, textField2.getText());
              preparedStmt.setInt(4, 0);

              // execute the preparedstatement
              preparedStmt.execute();
              
              conn.close();
			  JOptionPane.showMessageDialog(this, "You have successfully add a user! ");
              this.setVisible(false);
            }
            catch (Exception eEnterUser)
            {
              System.err.println("Got an exception!");
              System.err.println(eEnterUser.getMessage());
            }
        }
	}
}
