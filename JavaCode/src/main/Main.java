import java.awt.EventQueue;

public class Main {

	static String driver = "com.mysql.jdbc.Driver";
	
	public static final String URL = "jdbc:mysql://127.0.0.1:3306/mydb";
    public static final String USER = "root";
    public static final String PASSWORD = "***";
	
	public static void main(String[] args) {
		
		try {
        	Class.forName(driver);
    		//Class.forName("com.mysql.cj.jdbc.Driver");
		} catch(java.lang.ClassNotFoundException e) {
			System.out.println("Sorry, driver is not available. ");
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new A_Login();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
