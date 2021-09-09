
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;

public class C_DataManage extends JFrame implements ActionListener{
	

	public C_DataManage() {
		C_DataManage.this.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 510, 470);
		this.setTitle("Admin-dataManagement");
		getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel textLabel = new JLabel("Hello,Administrator!");
		textLabel.setHorizontalAlignment(SwingConstants.CENTER);
		textLabel.setFont(new Font("Georgia", Font.BOLD, 25));
		getContentPane().add(textLabel);
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JButton tempHumiButton = new JButton("TempHumi");
		tempHumiButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				C_DataManage.this.setVisible(false);
				new D_TempHumi();
			}
		});
		tempHumiButton.setBounds(120, 20, 200, 30);
		panel_1.add(tempHumiButton);
		
		JButton lightButton = new JButton("Light");
		lightButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				C_DataManage.this.setVisible(false);
				new D_Light();
			}
		});
		lightButton.setBounds(120, 60, 200, 30);
		panel_1.add(lightButton);
		
		JButton doorButton = new JButton("Door");
		doorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				C_DataManage.this.setVisible(false);
				new D_Door();
			}
		});
		doorButton.setBounds(120, 100, 200, 30);
		panel_1.add(doorButton);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton exitButton = new JButton("EXIT");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		exitButton.setFont(new Font("Georgia", Font.BOLD, 12));
		exitButton.setBounds(125, 80, 95, 25);
		panel.add(exitButton);
		
		JButton returnButton = new JButton("RETURN");
		returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				C_DataManage.this.setVisible(false);
				new B_Admin();
			}
		});
		returnButton.setFont(new Font("Georgia", Font.BOLD, 12));
		returnButton.setBounds(230, 80, 95, 25);
		panel.add(returnButton);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
