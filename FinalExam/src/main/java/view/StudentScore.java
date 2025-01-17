package view;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import dao.ClassDao;
import dao.ScoreDao;
import dao.StudentDao;
import dao.SubDao;
import model.StudentAll;

public class StudentScore extends JInternalFrame {
	private JTextField txtStudentID, txtStudentName, txtScore1, txtScore2, txtScore3;
	private JTable table;
	private DefaultTableModel tableModel;
	private JScrollPane scrollPaneStu;
	private JTable tableStu;
	private JComboBox<String> IDClassComboBox;
	private JPanel panelStudentInfo;
	private JLabel IdSub;
	private JComboBox<String> iDsubComboBox;
	private JPanel panelScores;
	private JComboBox<String> Classcombox;
	private JScrollPane tableScrollPane;
	private JPanel panelTable;
	private JButton btnAdd;

	private void clearFields() {
		txtStudentID.setText("");
		txtStudentName.setText("");
		IDClassComboBox.setSelectedIndex(0);
		IDClassComboBox.setEnabled(true);
		iDsubComboBox.setSelectedIndex(0);
		iDsubComboBox.setEnabled(true);
		txtScore1.setText("");
		txtScore2.setText("");
		txtScore3.setText("");
		btnAdd.setEnabled(true);
	}
	private void loadClassComboBox() {
		var idclass = ClassDao.getInstance();
		var comboBoxModel = new DefaultComboBoxModel<String>();
		idclass.loadIDClassData(comboBoxModel);
		IDClassComboBox.setModel(comboBoxModel);
	}

	private void ClassComboBox() {
		var idclass = ClassDao.getInstance();
		var comboBoxModel = new DefaultComboBoxModel<String>();
		idclass.loadIDClassData(comboBoxModel);
		Classcombox.setModel(comboBoxModel);
	}

	private void loadSubComboBox() {
		var idsub = new SubDao();
		var comboBoxModel = new DefaultComboBoxModel<String>();
		idsub.loadIDSub(comboBoxModel);
		iDsubComboBox.setModel(comboBoxModel);
	}
	private void loadData() {
		var dao = StudentDao.getInstance();
		var studentList = dao.selectall();
		var model = new DefaultTableModel();
		model.addColumn("ID");
		model.addColumn("NAME");
		model.addColumn("IDCLASS");
		studentList.forEach(student -> {
			model.addRow(new Object[] { student.getId(), student.getName(), student.getIdclass() });
		});

		tableStu.setModel(model);
		var sorter = new TableRowSorter<>(model);
		tableStu.setRowSorter(sorter);
	}

	private void loadScore() {
		var dao = ScoreDao.getInstance();
		var scoreList = dao.selectall();
		var model = new DefaultTableModel();
		model.addColumn("ID Student");
		model.addColumn("Name");
		model.addColumn("ID Subject");
		model.addColumn("Name Subject");
		model.addColumn("Score 1");
		model.addColumn("Score 2");
		model.addColumn("Score 3");
		model.addColumn("Final Score");
		model.addColumn("Rate");
		scoreList.forEach(score -> {
			model.addRow(new Object[] { score.getIdStu(), score.getName(), score.getIdSub(), score.getNameSub(),
					score.getSc1(), score.getSc2(),
					score.getSc3(), score.getScTotal(), score.getRate() });
		});

		table.setModel(model);
		var sorter = new TableRowSorter<>(model);
		table.setRowSorter(sorter);
	}

	public StudentScore() {
		setTitle("Student Score Management");
		setBounds(100, 100, 800, 600); // Kích thước của InternalFrame
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		getContentPane().setLayout(null); // Absolute Layout

		// Panel thông tin sinh viên
		panelStudentInfo = new JPanel();
		panelStudentInfo.setLayout(null);
		panelStudentInfo.setBorder(BorderFactory.createTitledBorder("Student Information"));
		panelStudentInfo.setBounds(10, 10, 760, 150);
		getContentPane().add(panelStudentInfo);

		// Mã sinh viên
		var lblStudentID = new JLabel("ID");
		lblStudentID.setBounds(20, 30, 100, 25);
		panelStudentInfo.add(lblStudentID);

		txtStudentID = new JTextField();
		txtStudentID.setBounds(64, 30, 150, 25);
		panelStudentInfo.add(txtStudentID);

		// Tên sinh viên
		var lblStudentName = new JLabel("Name");
		lblStudentName.setBounds(20, 70, 100, 25);
		panelStudentInfo.add(lblStudentName);

		txtStudentName = new JTextField();
		txtStudentName.setBounds(64, 70, 150, 25);
		panelStudentInfo.add(txtStudentName);

		// Mã môn học
		var lblClassID = new JLabel("Class");
		lblClassID.setBounds(20, 106, 100, 25);
		panelStudentInfo.add(lblClassID);
		IDClassComboBox = new JComboBox<>();
		// IDClassComboBox.addActionListener(this::iDClassComboBoxActionPerformed);
		IDClassComboBox.setBounds(64, 106, 150, 25);
		panelStudentInfo.add(IDClassComboBox);

		Classcombox = new JComboBox<>();
		Classcombox.addActionListener(this::iDClassComboBoxActionPerformed);
		Classcombox.setBounds(264, 30, 91, 25);
		panelStudentInfo.add(Classcombox);
		ClassComboBox();
		loadClassComboBox();

		scrollPaneStu = new JScrollPane();
		scrollPaneStu.setBounds(368, 11, 353, 128);
		panelStudentInfo.add(scrollPaneStu);
		tableStu = new JTable();
		tableStu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tableStuMouseClicked(e);
			}
		});
		scrollPaneStu.setViewportView(tableStu);
		loadData();

		// Panel điểm kiểm tra
		panelScores = new JPanel();
		panelScores.setLayout(null);
		panelScores.setBorder(BorderFactory.createTitledBorder("Scores"));
		panelScores.setBounds(10, 170, 760, 80);
		getContentPane().add(panelScores);

		// Điểm kiểm tra 1
		var lblScore1 = new JLabel("Subject 1");
		lblScore1.setBounds(213, 30, 55, 25);
		panelScores.add(lblScore1);

		txtScore1 = new JTextField();
		txtScore1.setBounds(296, 30, 80, 25);
		panelScores.add(txtScore1);

		// Điểm kiểm tra 2
		var lblScore2 = new JLabel("Subject 2");
		lblScore2.setBounds(386, 30, 55, 25);
		panelScores.add(lblScore2);

		txtScore2 = new JTextField();
		txtScore2.setBounds(469, 30, 80, 25);
		panelScores.add(txtScore2);

		// Điểm kiểm tra 3
		var lblScore3 = new JLabel("Subject 3");
		lblScore3.setBounds(574, 30, 55, 25);
		panelScores.add(lblScore3);

		txtScore3 = new JTextField();
		txtScore3.setBounds(653, 30, 80, 25);
		panelScores.add(txtScore3);

		IdSub = new JLabel("Subject");
		IdSub.setBounds(10, 30, 44, 25);
		panelScores.add(IdSub);

		iDsubComboBox = new JComboBox<>();
		iDsubComboBox.setBounds(62, 30, 122, 25);
		panelScores.add(iDsubComboBox);
		loadSubComboBox();
		// Nút chức năng
		btnAdd = new JButton("Add");
		btnAdd.addActionListener(this::btnAddActionPerformed);
		btnAdd.setBounds(20, 270, 120, 30);
		getContentPane().add(btnAdd);

		var btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(this::btnUpdateActionPerformed);
		btnUpdate.setBounds(160, 270, 120, 30);
		getContentPane().add(btnUpdate);

		var btnReset = new JButton("Reset");
		btnReset.addActionListener(this::btnResetActionPerformed);
		btnReset.setBounds(300, 270, 120, 30);
		getContentPane().add(btnReset);

		var btnDelete = new JButton("Delete");
		btnDelete.addActionListener(this::btnDeleteActionPerformed);
		btnDelete.setBounds(440, 270, 120, 30);
		getContentPane().add(btnDelete);

		// Bảng dữ liệu
		panelTable = new JPanel();
		panelTable.setLayout(null);
		panelTable.setBorder(BorderFactory.createTitledBorder("Student Scores"));
		panelTable.setBounds(10, 310, 760, 250);
		getContentPane().add(panelTable);
		table = new JTable(tableModel);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tableMouseClicked(e);
			}
		});
		tableScrollPane = new JScrollPane(table);
		tableScrollPane.setBounds(10, 20, 740, 220);
		panelTable.add(tableScrollPane);
		tableScrollPane.setViewportView(table);
		loadScore();
	}


	private boolean isScoreValid(String scoreText) {
		if (scoreText == null || scoreText.trim().isEmpty() || !scoreText.trim().matches("\\d+(\\.\\d+)?")) {
			return false;
		}
		var scoreValue = new BigDecimal(scoreText.trim());
		return scoreValue.compareTo(BigDecimal.ZERO) >= 0 && scoreValue.compareTo(new BigDecimal(10)) <= 0;
	}

	protected void btnAddActionPerformed(ActionEvent e) {
		var add = ScoreDao.getInstance();
		var sc = new StudentAll();
		sc.setIdStu(txtStudentID.getText());
		var selectedSub = (String) iDsubComboBox.getSelectedItem();
		sc.setIdSub(selectedSub);
		if (!isScoreValid(txtScore1.getText().trim()) || !isScoreValid(txtScore2.getText().trim())
				|| !isScoreValid(txtScore3.getText().trim())) {

			JOptionPane.showMessageDialog(this, "Check Score and Score (0-10)");

			if (!isScoreValid(txtScore1.getText().trim())) {
				txtScore1.setText("");
			}
			if (!isScoreValid(txtScore2.getText().trim())) {
				txtScore2.setText("");
			}
			if (!isScoreValid(txtScore3.getText().trim())) {
				txtScore3.setText("");
			}
			return;
		}
		sc.setSc1(new BigDecimal(txtScore1.getText().trim()));
		sc.setSc2(new BigDecimal(txtScore2.getText().trim()));
		sc.setSc3(new BigDecimal(txtScore3.getText().trim()));
		add.insert(sc);
		var model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);

		add.selectall().forEach(score -> {
			model.addRow(new Object[] { score.getIdStu(), score.getName(), score.getIdSub(), score.getNameSub(),
					score.getSc1(), score.getSc2(),
					score.getSc3(), score.getScTotal(), score.getRate() });
		});

		clearFields();
	}

	protected void tableMouseClicked(MouseEvent e) {
		var row = table.getSelectedRow();
		txtStudentID.setText(table.getValueAt(row, 0).toString());
		txtStudentName.setText(table.getValueAt(row, 1).toString());
		iDsubComboBox.setSelectedItem(table.getValueAt(row, 2).toString());
		txtScore1.setText(table.getValueAt(row, 4).toString());
		txtScore2.setText(table.getValueAt(row, 5).toString());
		txtScore3.setText(table.getValueAt(row, 6).toString());
		txtStudentID.setEditable(false);
		txtStudentName.setEditable(false);
		iDsubComboBox.setEnabled(false);
		IDClassComboBox.setEnabled(false);
		btnAdd.setEnabled(false);

	}
	protected void btnUpdateActionPerformed(ActionEvent e) {
		var add = ScoreDao.getInstance();
		var sc = new StudentAll();
		sc.setIdStu(txtStudentID.getText());
		var selectedSub = (String) iDsubComboBox.getSelectedItem();
		sc.setIdSub(selectedSub);
		if (!isScoreValid(txtScore1.getText().trim()) || !isScoreValid(txtScore2.getText().trim())
				|| !isScoreValid(txtScore3.getText().trim())) {

			JOptionPane.showMessageDialog(this, "Check Score and Score (0-10)");

			if (!isScoreValid(txtScore1.getText().trim())) {
				txtScore1.setText("");
			}
			if (!isScoreValid(txtScore2.getText().trim())) {
				txtScore2.setText("");
			}
			if (!isScoreValid(txtScore3.getText().trim())) {
				txtScore3.setText("");
			}

			return;
		}
		sc.setSc1(new BigDecimal(txtScore1.getText().trim()));
		sc.setSc2(new BigDecimal(txtScore2.getText().trim()));
		sc.setSc3(new BigDecimal(txtScore3.getText().trim()));
		add.update(sc);
		var model = (DefaultTableModel) table.getModel();
		model.setRowCount(0); // Clear existing rows

		add.selectall().forEach(score -> {
			model.addRow(new Object[] { score.getIdStu(), score.getName(), score.getIdSub(), score.getNameSub(),
					score.getSc1(), score.getSc2(),
					score.getSc3(), score.getScTotal(), score.getRate() });
		});

		clearFields();
	}

	protected void btnResetActionPerformed(ActionEvent e) {
		clearFields();
		JOptionPane.showMessageDialog(this, "All input fields have been cleared.", "Cancel",
				JOptionPane.INFORMATION_MESSAGE);
	}

	protected void btnDeleteActionPerformed(ActionEvent e) {
		var idstu = txtStudentID.getText().trim();
		var idsub = (String) iDsubComboBox.getSelectedItem();

		if (!idstu.isEmpty() && !idsub.isEmpty()) {
			var confirm = JOptionPane.showConfirmDialog(this,
					"Do you want to delete for IDStu: " + idstu + " and IDSub: " + idsub + "?", "Confirm ",
					JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				var dao = ScoreDao.getInstance();
				var Isdeleted = dao.delete(idstu, idsub);
				if (Isdeleted) {
					loadScore();
					clearFields();
					JOptionPane.showMessageDialog(this, "Delete Success!");
				} else {
					JOptionPane.showMessageDialog(this, "Subject does not have score!", "Notification",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		} else {
			JOptionPane.showMessageDialog(this, "Please input ID or select row", "Notification",
					JOptionPane.WARNING_MESSAGE);
		}
	}



	protected void tableStuMouseClicked(MouseEvent e) {
		var row = tableStu.getSelectedRow();
		txtStudentID.setText(tableStu.getValueAt(row, 0).toString());
		txtStudentName.setText(tableStu.getValueAt(row, 1).toString());
		IDClassComboBox.setSelectedItem(tableStu.getValueAt(row, 2).toString());
		txtStudentID.setEditable(false);
		txtStudentName.setEditable(false);
		IDClassComboBox.setEnabled(false);
		iDsubComboBox.setEnabled(true);
		txtScore1.setText("");
		txtScore2.setText("");
		txtScore3.setText("");

	}

	protected void iDClassComboBoxActionPerformed(ActionEvent e) {
		var selectedClassID = (String) Classcombox.getSelectedItem();
			updateStudentTable(selectedClassID);
	}

	private void updateStudentTable(String classID) {
		var tableModel = new DefaultTableModel();
		tableModel.addColumn("ID");
		tableModel.addColumn("NAME");
		tableModel.addColumn("IDCLASS");
		var dao = StudentDao.getInstance();
		var studentList = dao.selectStudentsByClassId(classID);
		studentList.forEach(student -> tableModel
				.addRow(new Object[] { student.getId(), student.getName(), student.getIdclass() }));
		tableStu.setModel(tableModel);
		var sorter = new TableRowSorter<>(tableModel);
		tableStu.setRowSorter(sorter);
	}

}
