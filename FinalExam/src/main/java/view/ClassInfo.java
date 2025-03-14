package view;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import dao.ClassDao;
import dao.FacultyDao;
import model.Class;

public class ClassInfo extends JInternalFrame {
	private JTextField txtClassID;
	private JTextField txtClassName;
	private JTable table;
	private DefaultTableModel tableModel;
	private JComboBox<String> comboFacultyID;

	public ClassInfo() {
		setBounds(100, 100, 881, 662);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		getContentPane().setLayout(null);

		// Panel cho các trường thông tin
		var fieldPanel = new JPanel();
		fieldPanel.setLayout(null);
		fieldPanel.setBorder(BorderFactory.createTitledBorder("Class Information"));
		fieldPanel.setBounds(153, 9, 560, 147);
		getContentPane().add(fieldPanel);

		// Label "ID"
		var lblID = new JLabel("ID");
		lblID.setBounds(20, 30, 80, 25);
		fieldPanel.add(lblID);

		// TextField "ID"
		txtClassID = new JTextField();
		txtClassID.setBounds(100, 30, 150, 25);
		fieldPanel.add(txtClassID);

		// Label "Name"
		var lblName = new JLabel("Name");
		lblName.setBounds(20, 70, 80, 25);
		fieldPanel.add(lblName);

		// TextField "Name"
		txtClassName = new JTextField();
		txtClassName.setBounds(100, 70, 150, 25);
		fieldPanel.add(txtClassName);

		var btnLoad = new JButton("Load");
		btnLoad.setBounds(323, 40, 80, 25);
		fieldPanel.add(btnLoad);

		var btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(this::btnCancelActionPerformed);
		btnCancel.setBounds(323, 88, 80, 25);
		fieldPanel.add(btnCancel);

		// Label "Faculty"
		var lblFaculty = new JLabel("Faculty");
		lblFaculty.setBounds(20, 106, 80, 25);
		fieldPanel.add(lblFaculty);

		// ComboBox "Faculty ID"
		comboFacultyID = new JComboBox<>();
		comboFacultyID.setBounds(100, 106, 150, 25);
		fieldPanel.add(comboFacultyID);

		// Panel cho bảng
		var tablePanel = new JPanel();
		tablePanel.setLayout(null);
		tablePanel.setBorder(BorderFactory.createTitledBorder("Class List"));
		tablePanel.setBounds(10, 167, 845, 292);
		getContentPane().add(tablePanel);

		// Table
		tableModel = new DefaultTableModel(new Object[] { "ID", "Name", "Faculty" }, 0);
		table = new JTable(tableModel);
		var tableScrollPane = new JScrollPane(table);
		tableScrollPane.setBounds(10, 20, 814, 247);
		tablePanel.add(tableScrollPane);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tableMouseClicked(e);
			}
		});

		// Panel cho các nút
		var buttonPanel = new JPanel();
		buttonPanel.setLayout(null);
		buttonPanel.setBorder(new TitledBorder(null, "Buttons", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		buttonPanel.setBounds(153, 461, 560, 80);
		getContentPane().add(buttonPanel);

		// Các nút
		var btnAdd = new JButton("Add");
		btnAdd.setBounds(54, 30, 80, 25);
		buttonPanel.add(btnAdd);

		var btnEdit = new JButton("Edit");
		btnEdit.setBounds(188, 30, 80, 25);
		buttonPanel.add(btnEdit);

		var btnDelete = new JButton("Delete");
		btnDelete.setBounds(318, 30, 80, 25);
		buttonPanel.add(btnDelete);

		var btnSearch = new JButton("Search");
		btnSearch.setBounds(443, 30, 80, 25);
		buttonPanel.add(btnSearch);

		btnAdd.addActionListener(e -> addClass());
		btnEdit.addActionListener(e -> editClass());
		btnDelete.addActionListener(e -> deleteClass());
		btnSearch.addActionListener(e -> searchClass());
		btnLoad.addActionListener(e -> loadClassData());

		loadFacultyIDs();
		loadClassData();
	}

	private void addClass() {
		var idClass = txtClassID.getText().trim();
		var nameClass = txtClassName.getText().trim();
		var idFac = (String) comboFacultyID.getSelectedItem();

		if (idClass.isEmpty() || nameClass.isEmpty() || idFac == null || idFac.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Class ID, Name, and Faculty ID cannot be empty!", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		var classModel = new Class(idClass, nameClass, idFac);

		var dao = ClassDao.getInstance();
		dao.insert(classModel);

		tableModel.addRow(new Object[] { idClass, nameClass, idFac });

		JOptionPane.showMessageDialog(this, "Class added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

		txtClassID.setText("");
		txtClassName.setText("");
		comboFacultyID.setSelectedIndex(-1);

		table.clearSelection();
	}

	private void loadClassData() {
		try {
			tableModel.setRowCount(0);

			var dao = ClassDao.getInstance();
			var classes = dao.selectall();

			if (classes.isEmpty()) {
				JOptionPane.showMessageDialog(this, "No class data found!", "Info", JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			for (var classInfo : classes) {
				tableModel.addRow(
						new Object[] { classInfo.getIDclass(), classInfo.getNameClass(), classInfo.getIDFac() });
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error loading class data: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	private void loadFacultyIDs() {
		comboFacultyID.removeAllItems();

		try {
			var dao = new FacultyDao();
			var faculties = dao.selectall();

			for (var faculty : faculties) {
				comboFacultyID.addItem(faculty.getIDFac());
			}

			comboFacultyID.setSelectedIndex(-1);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error loading faculty data: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	protected void tableMouseClicked(MouseEvent e) {
		var row = table.getSelectedRow();

		txtClassID.setText(table.getValueAt(row, 0).toString());
		txtClassName.setText(table.getValueAt(row, 1).toString());

		try {
			comboFacultyID.setSelectedItem(table.getValueAt(row, 2).toString());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void editClass() {
		var idClass = txtClassID.getText().trim();
		var nameClass = txtClassName.getText().trim();
		var idFac = (String) comboFacultyID.getSelectedItem();

		if (idClass.isEmpty() || nameClass.isEmpty() || idFac == null || idFac.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Class ID, Name, and Faculty ID cannot be empty!", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		try {
			var classModel = new Class(idClass, nameClass, idFac);

			var dao = ClassDao.getInstance();
			dao.update(classModel);

			var selectedRow = table.getSelectedRow();
			if (selectedRow != -1) {
				tableModel.setValueAt(nameClass, selectedRow, 1);
				tableModel.setValueAt(idFac, selectedRow, 2);
			}

			JOptionPane.showMessageDialog(this, "Class updated successfully!", "Success",
					JOptionPane.INFORMATION_MESSAGE);

			txtClassID.setText("");
			txtClassName.setText("");
			comboFacultyID.setSelectedIndex(-1);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error updating class: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	private void deleteClass() {
		var selectedRow = table.getSelectedRow();

		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(this, "Please select a class to delete!", "Warning",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		var confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this class?",
				"Confirm Delete", JOptionPane.YES_NO_OPTION);
		if (confirm != JOptionPane.YES_OPTION) {
			return;
		}

		var idClass = table.getValueAt(selectedRow, 0).toString();

		try {
			var dao = ClassDao.getInstance();
			dao.delete(idClass);

			tableModel.removeRow(selectedRow);

			JOptionPane.showMessageDialog(this, "Class deleted successfully!", "Success",
					JOptionPane.INFORMATION_MESSAGE);

			txtClassID.setText("");
			txtClassName.setText("");
			comboFacultyID.setSelectedIndex(-1);

			table.clearSelection();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error deleting class: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	private void searchClass() {
		var idClass = txtClassID.getText().trim();
		var nameClass = txtClassName.getText().trim();
		var idFac = (String) comboFacultyID.getSelectedItem();

		if (idClass.isEmpty() && nameClass.isEmpty() && (idFac == null || idFac.isEmpty())) {
			JOptionPane.showMessageDialog(this, "Please enter at least one field (ID, Name, or Faculty) to search!",
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		var dao = ClassDao.getInstance();
		var result = dao.advancedSearch(idClass, nameClass, idFac);

		tableModel.setRowCount(0);
		if (result.isEmpty()) {
			JOptionPane.showMessageDialog(this, "No matching data found!", "Info", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		for (var classInfo : result) {
			tableModel.addRow(new Object[] { classInfo.getIDclass(), classInfo.getNameClass(), classInfo.getIDFac() });
		}
	}

	protected void btnCancelActionPerformed(ActionEvent e) {
		txtClassID.setText("");
		txtClassName.setText("");
		comboFacultyID.setSelectedIndex(-1);
		table.clearSelection();
	}
}
