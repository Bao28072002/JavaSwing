package dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import model.Student;
import model.StudentAll;
import service.ConnectDB;

public class StudentDao implements DAOInterface<Student> {
	public static StudentDao getInstance() {
		return new StudentDao();
	}
	public List<Student> select(int pageNumber, int rowsPerPage) {
		List<Student> list = new ArrayList<>();
		try (var con = ConnectDB.getCon(); var cs = con.prepareCall("{call selectStudent(?, ?)}");) {
			cs.setInt(1, pageNumber);
			cs.setInt(2, rowsPerPage);
			var rs = cs.executeQuery();
			while (rs.next()) {
				var stu = new Student();
				stu.setId(rs.getString("id"));
				stu.setName(rs.getString("name"));
				stu.setGender(rs.getBoolean("gender"));
				stu.setDob(rs.getDate("dob").toLocalDate());
				stu.setDiaChi(rs.getString("diaChi"));
				list.add(stu);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public int count() {
		var count = 0;
		try (var con = ConnectDB.getCon();
				var cs = con.prepareCall("{call countStudent}");
				var rs = cs.executeQuery();) {
			while (rs.next()) {
				count = rs.getInt("total");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}



//insert
	@Override
	public void insert(Student t) {
		try {
			if (isIDStu(t.getId())) {
				JOptionPane.showMessageDialog(null, "ID Student have already!", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			try (var con = ConnectDB.getCon(); var cs = con.prepareCall("{call insertStu(?,?,?,?,?,?)}")) {
				cs.setString(1, t.getId());
				cs.setString(2, t.getName());
				cs.setString(3, t.getDiaChi());
				cs.setDate(4, java.sql.Date.valueOf(t.getDob()));
				cs.setBoolean(5, t.isGender());
				cs.setString(6, t.getIdclass());
				cs.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

// check idStu
		public boolean isIDStu(String id) {
			try (var con = ConnectDB.getCon();
					var ps = con.prepareStatement("SELECT COUNT(*) FROM student WHERE IDStu = ?")) {
				ps.setString(1, id);
				try (var rs = ps.executeQuery()) {
					if (rs.next()) {
						return rs.getInt(1) > 0;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}

//update
	@Override
	public void update(Student t) {
		try {
			if (!isIDStu(t.getId())) {
				JOptionPane.showMessageDialog(null, "Student ID not find ", "Error", JOptionPane.ERROR_MESSAGE);
				return;
	        }
			try (var con = ConnectDB.getCon(); var cs = con.prepareCall("{call updateStu(?,?,?,?,?,?)}")) {
				cs.setString(1, t.getName());
				cs.setString(2, t.getDiaChi());
				cs.setDate(3, java.sql.Date.valueOf(t.getDob()));
				cs.setBoolean(4, t.isGender());
				cs.setString(5, t.getIdclass());
				cs.setString(6, t.getId());
				cs.executeUpdate();
				JOptionPane.showMessageDialog(null, "Update Success");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//selcet all Student
	@Override
	public List<Student> selectall() {
		List<Student> list = new ArrayList<>();
		try (var con = ConnectDB.getCon();
				PreparedStatement cs = con.prepareCall("{call SelectStudent()}");
				var rs = cs.executeQuery();) {
			while (rs.next()) {
				var stu = new Student();
				stu.setId(rs.getString("IDStu"));
				stu.setName(rs.getString("NameStu"));
				stu.setDiaChi(rs.getString("Home_Address"));
				stu.setDob(rs.getDate("Birth").toLocalDate());
				stu.setGender(rs.getBoolean("Gender"));
				stu.setIdclass(rs.getString("IDClass"));
//				var facultyInfo = new Faculty();
//				facultyInfo.setNameFac(rs.getString("NameFac")); // Chỉ lấy NameFac
//				stu.setFacultyInfo(facultyInfo);
				list.add(stu);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

//delete
	public void delete(String id) {
		try (var con = ConnectDB.getCon(); var cs = con.prepareCall("{call deleteStu(?)}");) {
			cs.setString(1, id);
			cs.executeUpdate();
			JOptionPane.showMessageDialog(null, "Delete Succes");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//search stu
	@Override
	public List<Student> selectid(String id) {
		List<Student> list = new ArrayList<>();
		var query = "SELECT * FROM student WHERE IDStu LIKE ?";

		try (var con = ConnectDB.getCon(); var ps = con.prepareStatement(query)) {
			ps.setString(1, "%" + id + "%");

			try (var rs = ps.executeQuery()) {
				while (rs.next()) {
					var student = new Student();
					student.setId(rs.getString("IDStu"));
					student.setName(rs.getString("NameStu"));
					student.setDiaChi(rs.getString("Home_Address"));
					student.setDob(rs.getDate("Birth").toLocalDate());
					student.setGender(rs.getBoolean("Gender"));
					student.setIdclass(rs.getString("IDClass"));
					list.add(student);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<Student> selectStudentsByClassId(String idClass) {
		List<Student> list = new ArrayList<>();
		var query = "SELECT * FROM student WHERE IDClass = ?";

		try (var con = ConnectDB.getCon(); var ps = con.prepareStatement(query)) {
			ps.setString(1, idClass);

			try (var rs = ps.executeQuery()) {
				while (rs.next()) {
					var student = new Student();
					student.setId(rs.getString("IDStu"));
					student.setName(rs.getString("NameStu"));
					student.setDiaChi(rs.getString("Home_Address"));
					student.setDob(rs.getDate("Birth").toLocalDate());
					student.setGender(rs.getBoolean("Gender"));
					student.setIdclass(rs.getString("IDClass"));
					list.add(student);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<StudentAll> IsUser(String username) {
		List<StudentAll> result = new ArrayList<>();

		var sql = "{call validateUser(?)}"; // Stored procedure gọi bằng cú pháp CallableStatement

		try (var con = ConnectDB.getCon(); var stmt = con.prepareCall(sql)) {

			stmt.setString(1, username);

			try (var rs = stmt.executeQuery()) {
				while (rs.next()) {
					var stu = new StudentAll();
					stu.setId(rs.getString("IDStu"));
					stu.setName(rs.getString("NameStu"));
					stu.setDiaChi(rs.getString("Home_Address"));
					stu.setDob(rs.getDate("Birth").toLocalDate());
					stu.setGender(rs.getBoolean("Gender"));
					stu.setIdclass(rs.getString("IDClass"));
					stu.setNameClass(rs.getString("NameClass"));
					stu.setIDFac(rs.getString("IDFac"));
					stu.setNameFac(rs.getString("NameFac"));
					result.add(stu);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public Student selectById(String id) {
		// TODO Auto-generated method stub
		return null;
	}



}
