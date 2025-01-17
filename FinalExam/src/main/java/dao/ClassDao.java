package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import model.Class;
import service.ConnectDB;

public class ClassDao implements DAOInterface<Class> {

	private static ClassDao instance;

	// Singleton Pattern
	public static ClassDao getInstance() {
		if (instance == null) {
			instance = new ClassDao();
		}
		return instance;
	}

	public void loadIDClassData(DefaultComboBoxModel<String> comboBoxModel) {
		var sql = "select IDClass from Class";
		try (var conn = ConnectDB.getCon(); var stmt = conn.createStatement(); var rs = stmt.executeQuery(sql)) {
			comboBoxModel.removeAllElements();
			while (rs.next()) {
				var IDClass = rs.getString("IDClass");
				comboBoxModel.addElement(IDClass);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
		}
	}

	@Override
	public void insert(Class classModel) {
		if (classModel.getIDclass() == null || classModel.getIDclass().isEmpty() || classModel.getNameClass() == null
				|| classModel.getNameClass().isEmpty() || classModel.getIDFac() == null
				|| classModel.getIDFac().isEmpty()) {
			JOptionPane.showMessageDialog(null, "ID, Name, and Faculty ID cannot be empty", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		try (var con = ConnectDB.getCon(); var cs = con.prepareCall("{call insertClass(?, ?, ?)}")) {
			cs.setString(1, classModel.getIDclass());
			cs.setNString(2, classModel.getNameClass());
			cs.setString(3, classModel.getIDFac());
			cs.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void update(Class classModel) {
		if (classModel.getIDclass() == null || classModel.getIDclass().isEmpty() || classModel.getNameClass() == null
				|| classModel.getNameClass().isEmpty()) {
			JOptionPane.showMessageDialog(null, "ID and Name cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		try (var con = ConnectDB.getCon();
				var ps = con.prepareStatement("UPDATE class SET NameClass = ? WHERE IDClass = ?")) {
			ps.setString(1, classModel.getNameClass());
			ps.setString(2, classModel.getIDclass());
			var rowsUpdated = ps.executeUpdate();

			if (rowsUpdated == 0) {
				JOptionPane.showMessageDialog(null, "No Class found with the given ID!", "Warning",
						JOptionPane.WARNING_MESSAGE);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void delete(String id) {
		if (id == null || id.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Invalid Class ID!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		try (var con = ConnectDB.getCon(); var cs = con.prepareCall("{call deleteClass(?)}")) {
			cs.setString(1, id);
			var rowsDeleted = cs.executeUpdate();

			if (rowsDeleted == 0) {
				JOptionPane.showMessageDialog(null, "No Class found with ID: " + id, "Warning",
						JOptionPane.WARNING_MESSAGE);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public int count() {
		var count = 0;
		try (var con = ConnectDB.getCon(); var cs = con.prepareCall("{call countClass}"); var rs = cs.executeQuery()) {
			if (rs.next()) {
				count = rs.getInt("total");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public List<Class> select(int pageNumber, int rowsPerPage) {
		List<Class> list = new ArrayList<>();
		try (var con = ConnectDB.getCon(); var cs = con.prepareCall("{call selectClass(?, ?)}")) {
			cs.setInt(1, pageNumber);
			cs.setInt(2, rowsPerPage);
			var rs = cs.executeQuery();
			while (rs.next()) {
				var classModel = new Class();
				classModel.setIDclass(rs.getString("IDClass"));
				classModel.setNameClass(rs.getNString("NameClass"));
				list.add(classModel);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Class> selectall() {
		List<Class> list = new ArrayList<>();
		try (var con = ConnectDB.getCon();
				var stmt = con.createStatement();
				var rs = stmt.executeQuery("SELECT * FROM class")) {
			while (rs.next()) {
				var classModel = new Class();
				classModel.setIDclass(rs.getString("IDClass"));
				classModel.setNameClass(rs.getNString("NameClass"));
				classModel.setIDFac(rs.getString("IDFac"));
				list.add(classModel);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Class selectById(String id) {
		if (id == null || id.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Invalid Class ID!", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}

		try (var con = ConnectDB.getCon(); var cs = con.prepareCall("{call selectClassById(?)}")) {
			cs.setString(1, id);
			var rs = cs.executeQuery();
			if (rs.next()) {
				var classModel = new Class();
				classModel.setIDclass(rs.getString("IDClass"));
				classModel.setNameClass(rs.getNString("NameClass"));
				return classModel;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Class> advancedSearch(String idClass, String nameClass, String idFac) {
		List<Class> list = new ArrayList<>();
		var sql = "SELECT * FROM class WHERE 1=1";

		if (idClass != null && !idClass.isEmpty()) {
			sql += " AND IDClass LIKE ?";
		}
		if (nameClass != null && !nameClass.isEmpty()) {
			sql += " AND NameClass LIKE ?";
		}
		if (idFac != null && !idFac.isEmpty()) {
			sql += " AND IDFac LIKE ?";
		}

		try (var con = ConnectDB.getCon(); var ps = con.prepareStatement(sql)) {
			var index = 1;
			if (idClass != null && !idClass.isEmpty()) {
				ps.setString(index++, "%" + idClass + "%");
			}
			if (nameClass != null && !nameClass.isEmpty()) {
				ps.setString(index++, "%" + nameClass + "%");
			}
			if (idFac != null && !idFac.isEmpty()) {
				ps.setString(index++, "%" + idFac + "%");
			}

			var rs = ps.executeQuery();

			while (rs.next()) {
				var classModel = new Class();
				classModel.setIDclass(rs.getString("IDClass"));
				classModel.setNameClass(rs.getNString("NameClass"));
				classModel.setIDFac(rs.getString("IDFac"));
				list.add(classModel);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}

		return list;
	}

	public void loadClassData(DefaultComboBoxModel<String> comboBoxModel) {
		var sql = "SELECT NameClass FROM class";
		try (var conn = ConnectDB.getCon(); var stmt = conn.createStatement(); var rs = stmt.executeQuery(sql)) {
			comboBoxModel.removeAllElements();
			while (rs.next()) {
				comboBoxModel.addElement(rs.getNString("NameClass"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
		}
	}

	@Override
	public List<Class> selectid(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
