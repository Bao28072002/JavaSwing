package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import model.Faculty;
import service.ConnectDB;

public class FacultyDao implements DAOInterface<Faculty> {

	private static FacultyDao instance;

	// Singleton Pattern
	public static FacultyDao getInstance() {
		if (instance == null) {
			instance = new FacultyDao();
		}
		return instance;
	}

	@Override
	public void insert(Faculty faculty) {
		if (faculty.getIDFac() == null || faculty.getIDFac().isEmpty() || faculty.getNameFac() == null
				|| faculty.getNameFac().isEmpty()) {
			JOptionPane.showMessageDialog(null, "ID and Name cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		try (var con = ConnectDB.getCon(); var cs = con.prepareCall("{call insertFaculty(?, ?)}")) {
			cs.setString(1, faculty.getIDFac());
			cs.setNString(2, faculty.getNameFac());
			cs.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void update(Faculty faculty) {
		if (faculty.getIDFac() == null || faculty.getIDFac().isEmpty() || faculty.getNameFac() == null
				|| faculty.getNameFac().isEmpty()) {
			JOptionPane.showMessageDialog(null, "ID and Name cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		try (var con = ConnectDB.getCon();
				var ps = con.prepareStatement("UPDATE faculty SET NameFac = ? WHERE IDFac = ?")) {
			ps.setString(1, faculty.getNameFac());
			ps.setString(2, faculty.getIDFac());
			var rowsUpdated = ps.executeUpdate();

			if (rowsUpdated == 0) {
				JOptionPane.showMessageDialog(null, "No Faculty found with the given ID!", "Warning",
						JOptionPane.WARNING_MESSAGE);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void delete(String id) {
		if (id == null || id.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Invalid Faculty ID!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		try (var con = ConnectDB.getCon(); var cs = con.prepareCall("{call deleteFaculty(?)}")) {
			cs.setString(1, id);
			var rowsDeleted = cs.executeUpdate();

			if (rowsDeleted == 0) {
				JOptionPane.showMessageDialog(null, "No Faculty found with ID: " + id, "Warning",
						JOptionPane.WARNING_MESSAGE);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public int count() {
		var count = 0;
		try (var con = ConnectDB.getCon();
				var cs = con.prepareCall("{call countFaculty}");
				var rs = cs.executeQuery()) {
			if (rs.next()) {
				count = rs.getInt("total");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public List<Faculty> select(int pageNumber, int rowsPerPage) {
		List<Faculty> list = new ArrayList<>();
		try (var con = ConnectDB.getCon(); var cs = con.prepareCall("{call selectFaculty(?, ?)}")) {
			cs.setInt(1, pageNumber);
			cs.setInt(2, rowsPerPage);
			var rs = cs.executeQuery();
			while (rs.next()) {
				var faculty = new Faculty();
				faculty.setIDFac(rs.getString("IDFac"));
				faculty.setNameFac(rs.getNString("NameFac"));
				list.add(faculty);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Faculty> selectall() {
		List<Faculty> list = new ArrayList<>();
		try (var con = ConnectDB.getCon();
				var stmt = con.createStatement();
				var rs = stmt.executeQuery("SELECT * FROM faculty")) {
			while (rs.next()) {
				var faculty = new Faculty();
				faculty.setIDFac(rs.getString("IDFac"));
				faculty.setNameFac(rs.getNString("NameFac"));
				list.add(faculty);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Faculty selectById(String id) {
		if (id == null || id.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Invalid Faculty ID!", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}

		try (var con = ConnectDB.getCon(); var cs = con.prepareCall("{call selectFacultyById(?)}")) {
			cs.setString(1, id);
			var rs = cs.executeQuery();
			if (rs.next()) {
				var faculty = new Faculty();
				faculty.setIDFac(rs.getString("IDFac"));
				faculty.setNameFac(rs.getNString("NameFac"));
				return faculty;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Faculty> search(String id, String name) {
		List<Faculty> list = new ArrayList<>();
		var sql = "SELECT * FROM faculty WHERE IDFac = ? OR NameFac LIKE ?";
		try (var con = ConnectDB.getCon(); var ps = con.prepareStatement(sql)) {
			ps.setString(1, id.isEmpty() ? null : id);
			ps.setString(2, "%" + name + "%");
			var rs = ps.executeQuery();
			while (rs.next()) {
				var faculty = new Faculty();
				faculty.setIDFac(rs.getString("IDFac"));
				faculty.setNameFac(rs.getNString("NameFac"));
				list.add(faculty);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
		}
		return list;
	}

	public void loadFacultyData(DefaultComboBoxModel<String> comboBoxModel) {
		var sql = "SELECT NameFac FROM faculty";
		try (var conn = ConnectDB.getCon(); var stmt = conn.createStatement(); var rs = stmt.executeQuery(sql)) {
			comboBoxModel.removeAllElements();
			while (rs.next()) {
				comboBoxModel.addElement(rs.getNString("NameFac"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
		}
	}

	@Override
	public List<Faculty> selectid(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
