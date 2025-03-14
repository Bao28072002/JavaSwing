package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import model.Subject;
import service.ConnectDB;

public class SubDao {

	private static SubDao instance;

	// Singleton Pattern
	public static SubDao getInstance() {
		if (instance == null) {
			instance = new SubDao();
		}
		return instance;
	}

	// Thêm một môn học mới
	public void insert(Subject subject) {
		if (subject.getiDSub() == null || subject.getiDSub().isEmpty() || subject.getNameSub() == null
				|| subject.getNameSub().isEmpty()) {
			JOptionPane.showMessageDialog(null, "ID, Name, and Credits cannot be empty!", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		var sql = "INSERT INTO subject (IDSub, NameSub, NumCredits) VALUES (?, ?, ?)";
		try (var con = ConnectDB.getCon(); var ps = con.prepareStatement(sql)) {
			ps.setString(1, subject.getiDSub());
			ps.setString(2, subject.getNameSub());
			ps.setInt(3, subject.getNumcredits());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error inserting subject: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	// Sửa thông tin môn học
	public void update(Subject subject) {
		if (subject.getiDSub() == null || subject.getiDSub().isEmpty() || subject.getNameSub() == null
				|| subject.getNameSub().isEmpty()) {
			JOptionPane.showMessageDialog(null, "ID, Name, and Credits cannot be empty!", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		var sql = "UPDATE subject SET NameSub = ?, NumCredits = ? WHERE IDSub = ?";
		try (var con = ConnectDB.getCon(); var ps = con.prepareStatement(sql)) {
			ps.setString(1, subject.getNameSub());
			ps.setInt(2, subject.getNumcredits());
			ps.setString(3, subject.getiDSub());
			var rowsUpdated = ps.executeUpdate();
			if (rowsUpdated == 0) {
				JOptionPane.showMessageDialog(null, "No subject found with the given ID!", "Warning",
						JOptionPane.WARNING_MESSAGE);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error updating subject: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public void deactivate(String id) {
		var sql = "UPDATE subject SET sub_status = 0 WHERE IDSub = ?";
		try (var con = ConnectDB.getCon(); var ps = con.prepareStatement(sql)) {
			ps.setString(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error deactivating subject: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public List<Subject> search(String id, String name) {
		List<Subject> subjects = new ArrayList<>();
		var sql = new StringBuilder("SELECT * FROM subject WHERE 1=1");

		if (id != null && !id.isEmpty()) {
			sql.append(" AND IDSub LIKE ?");
		}
		if (name != null && !name.isEmpty()) {
			sql.append(" AND NameSub LIKE ?");
		}

		try (var con = ConnectDB.getCon(); var ps = con.prepareStatement(sql.toString())) {
			var paramIndex = 1;

			if (id != null && !id.isEmpty()) {
				ps.setString(paramIndex++, "%" + id + "%");
			}
			if (name != null && !name.isEmpty()) {
				ps.setString(paramIndex++, "%" + name + "%");
			}

			var rs = ps.executeQuery();
			while (rs.next()) {
				var subject = new Subject();
				subject.setiDSub(rs.getString("IDSub"));
				subject.setNameSub(rs.getString("NameSub"));
				subject.setNumcredits(rs.getInt("NumCredits"));
				subjects.add(subject);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error searching subjects: " + e.getMessage());
		}
		return subjects;
	}

	// Lấy danh sách tất cả môn học
	public List<Subject> selectAll() {
		List<Subject> list = new ArrayList<>();
		var sql = "SELECT * FROM subject";
		try (var con = ConnectDB.getCon(); var ps = con.prepareStatement(sql); var rs = ps.executeQuery()) {
			while (rs.next()) {
				var subject = new Subject();
				subject.setiDSub(rs.getString("IDSub"));
				subject.setNameSub(rs.getString("NameSub"));
				subject.setNumcredits(rs.getInt("NumCredits"));
				list.add(subject);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error loading subjects: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		return list;
	}

	public void loadIDSub(DefaultComboBoxModel<String> comboBoxModel) {
		var sql = "SELECT IDSub FROM subject";
		try (var conn = ConnectDB.getCon(); var stmt = conn.createStatement(); var rs = stmt.executeQuery(sql)) {
			comboBoxModel.removeAllElements(); // Xóa sạch dữ liệu cũ
			while (rs.next()) {
				comboBoxModel.addElement(rs.getString("IDSub")); // Thêm IDSub vào model
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error loading subject IDs: " + e.getMessage());
		}
	}

	public void updateStatus(String id, int status) {
		var sql = "UPDATE subject SET sub_status = ? WHERE IDSub = ?";
		try (var con = ConnectDB.getCon(); var ps = con.prepareStatement(sql)) {
			ps.setInt(1, status);
			ps.setString(2, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error updating subject status: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public List<Subject> selectActive() {
		List<Subject> list = new ArrayList<>();
		var sql = "SELECT * FROM subject WHERE sub_status = 1";
		try (var con = ConnectDB.getCon(); var ps = con.prepareStatement(sql); var rs = ps.executeQuery()) {
			while (rs.next()) {
				var subject = new Subject();
				subject.setiDSub(rs.getString("IDSub"));
				subject.setNameSub(rs.getString("NameSub"));
				subject.setNumcredits(rs.getInt("Numcredits"));
				list.add(subject);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error fetching active subjects: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		return list;
	}

}
