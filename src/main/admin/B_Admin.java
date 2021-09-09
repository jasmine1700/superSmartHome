import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class B_Admin extends JFrame {

	private JPanel contentPane;


	/**
	 * Create the frame.
	 */
	public B_Admin() {
		B_Admin.this.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 420);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(7, 1, 0, 0));
		
		JPanel textpanel = new JPanel();
		contentPane.add(textpanel);
		
		JLabel lblNewLabel = new JLabel("Hello, administrator! ");
		lblNewLabel.setFont(new Font("Georgia", Font.BOLD, 18));
		textpanel.add(lblNewLabel);
		
		JPanel userManagepanel = new JPanel();
		contentPane.add(userManagepanel);
		
		JButton UMButton = new JButton("manage USER");
		UMButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				B_Admin.this.setVisible(false);
				new C_UserManage();
			}
		});
		UMButton.setFont(new Font("Georgia", Font.PLAIN, 14));
		userManagepanel.add(UMButton);
		
		JPanel familyManagepanel = new JPanel();
		contentPane.add(familyManagepanel);
		
		JButton btnFamilyManage = new JButton("manage FAMILY");
		btnFamilyManage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				B_Admin.this.setVisible(false);
				new C_FamilyManage();
			}
		});
		btnFamilyManage.setFont(new Font("Georgia", Font.PLAIN, 14));
		familyManagepanel.add(btnFamilyManage);
		
		JPanel deviceManagepanel = new JPanel();
		contentPane.add(deviceManagepanel);
		
		JButton btnDeviceManage = new JButton("manage DEVICE");
		btnDeviceManage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				B_Admin.this.setVisible(false);
				new C_DeviceManage();
			}
		});
		btnDeviceManage.setFont(new Font("Georgia", Font.PLAIN, 14));
		deviceManagepanel.add(btnDeviceManage);
		
		JPanel deviceClasspanel = new JPanel();
		contentPane.add(deviceClasspanel);
		
		JButton btnManageDeviceClass = new JButton("manage device CLASS");
		btnManageDeviceClass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				B_Admin.this.setVisible(false);
				new C_DeviceClassManage();
			}
		});
		btnManageDeviceClass.setFont(new Font("Georgia", Font.PLAIN, 14));
		deviceClasspanel.add(btnManageDeviceClass);
		
		JPanel measurementManagepanel = new JPanel();
		contentPane.add(measurementManagepanel);
		
		JButton btnMeasurementManage = new JButton("manage MEASUREMENT");
		btnMeasurementManage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				B_Admin.this.setVisible(false);
				new C_DataManage();
			}
		});
		btnMeasurementManage.setFont(new Font("Georgia", Font.PLAIN, 14));
		measurementManagepanel.add(btnMeasurementManage);
		
		JPanel buttonpanel = new JPanel();
		contentPane.add(buttonpanel);
		
		JButton exitButton = new JButton("EXIT");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		exitButton.setFont(new Font("Georgia", Font.BOLD, 16));
		buttonpanel.add(exitButton);
		
		JButton returnButton = new JButton("RETURN");
		returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				B_Admin.this.setVisible(false);
				new A_Login();
			}
		});
		returnButton.setFont(new Font("Georgia", Font.BOLD, 16));
		buttonpanel.add(returnButton);
	}

}
