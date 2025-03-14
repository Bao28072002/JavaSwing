package dao;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import model.StudentAll;
import service.ConnectDB;

public class ScoreDao implements DAOInterface<StudentAll> {
	public static ScoreDao getInstance() {
		return new ScoreDao();
	}
//insert
	@Override
	public void insert(StudentAll t) {
		var qr = "{call InsertScore(?, ?, ?, ?, ?)}";

		try (var con = ConnectDB.getCon(); PreparedStatement ps = con.prepareCall(qr)) {

			ps.setString(1, t.getIdStu());
			ps.setString(2, t.getIdSub());
			ps.setBigDecimal(3, t.getSc1());
			ps.setBigDecimal(4, t.getSc2());
			ps.setBigDecimal(5, t.getSc3());

			var rowsUpdated = ps.executeUpdate();
			if (rowsUpdated <= 0) {
				JOptionPane.showMessageDialog(null, "Subject score already!");
			} else {
				JOptionPane.showMessageDialog(null, "Insert Success");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//select all
	@Override
	public List<StudentAll> selectall() {
		List<StudentAll> list = new ArrayList<>();
		try (var con = ConnectDB.getCon(); var cs = con.prepareCall("{call AllScores()}"); var rs = cs.executeQuery()) {

			while (rs.next()) {
				var score = new StudentAll();
				score.setIdStu(rs.getString("IDStu"));
				score.setName(rs.getString("NameStu"));
				score.setIdSub(rs.getString("IDSub"));
				score.setNameSub(rs.getString("NameSub"));
				score.setSc1(rs.getBigDecimal("Sc1"));
				score.setSc2(rs.getBigDecimal("Sc2"));
				score.setSc3(rs.getBigDecimal("Sc3"));
				score.setScTotal(rs.getBigDecimal("Sctotal"));
				score.setRate(rs.getString("Rate"));
				list.add(score);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

//update
	@Override
	public void update(StudentAll t) {
		var qr = "{call UpdateScore(?, ?, ?, ?, ?)}";
		try (var con = ConnectDB.getCon(); PreparedStatement cs = con.prepareCall(qr)) {
			cs.setBigDecimal(1, t.getSc1());
			cs.setBigDecimal(2, t.getSc2());
			cs.setBigDecimal(3, t.getSc3());
			cs.setString(4, t.getIdStu());
			cs.setString(5, t.getIdSub());

			cs.executeUpdate();
			JOptionPane.showMessageDialog(null, "Update Success");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//delete
	public boolean delete(String idStu, String idSub) {
		var qr = "{call DeleteScore(?, ?)}";
		try (var con = ConnectDB.getCon(); var pr = con.prepareCall(qr)) {
			pr.setString(1, idStu);
			pr.setString(2, idSub);

			var rowsUpdated = pr.executeUpdate();
			return rowsUpdated > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}



//Socre user
	public List<StudentAll> getScoresStudent(String IDStu) {
		List<StudentAll> scores = new ArrayList<>();
		var qr = "{call getScoresStudent(?)}";

		try (var con = ConnectDB.getCon(); var pr = con.prepareCall(qr)) {
			pr.setString(1, IDStu);

			try (var rs = pr.executeQuery()) {
				while (rs.next()) {
					var student = new StudentAll();
					student.setId(rs.getString("IDSub"));
					student.setNameSub(rs.getString("NameSub"));
					student.setSc1(rs.getBigDecimal("Sc1"));
					student.setSc2(rs.getBigDecimal("Sc2"));
					student.setSc3(rs.getBigDecimal("Sc3"));
					student.setScTotal(rs.getBigDecimal("Sctotal"));
					student.setRate(rs.getString("Rate"));
					scores.add(student);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return scores;
	}

	@Override
	public StudentAll selectById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StudentAll> selectid(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
