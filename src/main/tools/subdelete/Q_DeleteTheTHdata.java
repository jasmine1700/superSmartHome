import java.sql.*;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class Q_DeleteTheTHdata {
	public Q_DeleteTheTHdata(JTable table,DefaultTableModel model) throws SQLException {
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
			String time = (String) model.getValueAt(numrow,1);
			model.removeRow(numrow);

			String sql = "update temphumi set th_isDeleted = 1 where th_deviceId = '" + id + "'and th_time = '" + time + "';";
			stmt.executeUpdate(sql);
			JOptionPane.showMessageDialog(null, "Delete successful!", "Completion",JOptionPane.PLAIN_MESSAGE);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		
	}

}
