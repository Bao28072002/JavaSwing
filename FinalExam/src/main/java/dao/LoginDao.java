package dao;

import javax.swing.JOptionPane;

import service.ConnectDB;

public class LoginDao {

	private static LoginDao instance;

	// Sử dụng Singleton Pattern
	public static LoginDao getInstance() {
		if (instance == null) {
			instance = new LoginDao();
		}
		return instance;
	}

	public void insertLogin(String id, String username, String password, String jobRole, String idStu) {
		var sql = "INSERT INTO login (ID, Username, Pass, JobRole, IDStu) VALUES (?, ?, ?, ?, ?)";
		try (var conn = ConnectDB.getCon(); var ps = conn.prepareStatement(sql)) {
			ps.setString(1, id);
			ps.setString(2, username);
			ps.setNString(3, password);
			ps.setNString(4, jobRole);
			ps.setString(5, idStu);
			ps.executeUpdate();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error adding login user: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
}
