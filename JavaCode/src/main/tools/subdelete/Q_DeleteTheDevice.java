import java.sql.*;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class Q_DeleteTheDevice {
	public static void delete(JTable table,DefaultTableModel model) throws SQLException {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(Main.URL, Main.USER, Main.PASSWORD);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		Statement stmt;
		try {
			stmt = conn.createStatement();
			int numrow=table.getSelectedRow();
			String id = (String) model.getValueAt(numrow,0);
			model.removeRow(numrow);
			
	
//			String sql = "select * from device;";
//			ResultSet rs = stmt.executeQuery(sql);
//			while(rs.next()) {
//				if(rs.getString("d_deviceId").equals(id)) {
//					match = true;
//					break;
//				}
//			}

			String sql = "update device set d_isDeleted = 1 where d_deviceId = '" + id + "';";
			String sql1 = "update temphumi set th_isDeleted = 1 where th_deviceId = '" + id + "';";
			String sql2 = "update light set l_isDeleted = 1 where l_deviceId = '" + id + "';";
			String sql3 = "update door set do_isDeleted = 1 where do_deviceId = '" + id + "'";
			stmt.executeUpdate(sql);
			stmt.executeUpdate(sql1);
			stmt.executeUpdate(sql2);
			stmt.executeUpdate(sql3);
			JOptionPane.showMessageDialog(null, "Delete successfully!", "Completion",JOptionPane.PLAIN_MESSAGE);
			
			ResultSet rs = stmt.executeQuery("SELECT * from device where d_isDeleted = 0");
			
			while(rs.next()){
	            
				String[] newRow = {rs.getString("d_deviceId"), rs.getString("d_deviceName"), rs.getString("d_classId"), rs.getString("d_specification"), rs.getString("d_manuId"), rs.getString("d_familyId")};
	            model.addRow(newRow);
	            
	        }
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		
	}
		
		
	

}
