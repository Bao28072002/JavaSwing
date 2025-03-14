package dao;

import service.ConnectDB;

public class UserDAO {
	public static boolean validateUser(String username, String password) {
		var isValid = false;
		var qr = "SELECT * FROM login WHERE Username = ? AND Pass = ?";
		try (var con = ConnectDB.getCon(); var ps = con.prepareStatement(qr)) {
			ps.setString(1, username);
			ps.setString(2, password);

			var rs = ps.executeQuery();
			if (rs.next()) {
				isValid = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return isValid;
	}

	public static String getUserRole(String username, String password) {
		var role = "";
		var qr = "SELECT JobRole FROM login WHERE Username = ? AND Pass = ?";

		try (var con = ConnectDB.getCon(); var ps = con.prepareStatement(qr)) {

			ps.setString(1, username);
			ps.setString(2, password);

			var rs = ps.executeQuery();

			if (rs.next()) {
				role = rs.getString("JobRole");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return role;
	}

	public String getStudentId(String username, String password) {
		var studentId = "";
		var qr = "SELECT IDStu FROM login WHERE Username = ? AND Pass = ?";

		try (var con = ConnectDB.getCon(); var ps = con.prepareStatement(qr)) {
			ps.setString(1, username);
			ps.setString(2, password);
			try (var rs = ps.executeQuery()) {
				if (rs.next()) {
					studentId = rs.getString("IDStu");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return studentId;
	}

}