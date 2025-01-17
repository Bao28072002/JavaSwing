package view;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import dao.FacultyDao;
import model.Faculty;

public class FacultyInfo extends JInternalFrame {
	private JTextField txtFacultyCode;
	private JTextField txtFacultyName;
	private JTable table;
	private DefaultTableModel tableModel;
	private JButton btnCancel;

	public FacultyInfo() {
		setBounds(100, 100, 881, 662);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		getContentPane().setLayout(null);

		// Panel cho các trường thông tin
		var fieldPanel = new JPanel();
		fieldPanel.setLayout(null);
		fieldPanel.setBorder(BorderFactory.createTitledBorder("Faculty Information"));
		fieldPanel.setBounds(153, 9, 560, 120);
		getContentPane().add(fieldPanel);

		// Label "ID"
		var lblID = new JLabel("ID:");
		lblID.setBounds(20, 30, 80, 25);
		fieldPanel.add(lblID);

		// TextField "ID"
		txtFacultyCode = new JTextField();
		txtFacultyCode.setBounds(100, 30, 150, 25);
		fieldPanel.add(txtFacultyCode);

		// Label "Name"
		var lblName = new JLabel("Name:");
		lblName.setBounds(20, 70, 80, 25);
		fieldPanel.add(lblName);

		// TextField "Name"
		txtFacultyName = new JTextField();
		txtFacultyName.setBounds(100, 70, 150, 25);
		fieldPanel.add(txtFacultyName);

		// Button "Load"
		var btnLoad = new JButton("Load");
		btnLoad.setBounds(323, 30, 80, 25);
		fieldPanel.add(btnLoad);

		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(this::btnCancelActionPerformed);
		btnCancel.setBounds(323, 70, 80, 25);
		fieldPanel.add(btnCancel);

		// Panel cho bảng
		var tablePanel = new JPanel();
		tablePanel.setLayout(null);
		tablePanel.setBorder(BorderFactory.createTitledBorder("Faculty List"));
		tablePanel.setBounds(10, 140, 845, 292);
		getContentPane().add(tablePanel);

		// Table
		tableModel = new DefaultTableModel(new Object[] { "ID", "Name" }, 0);
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
		buttonPanel.setBounds(153, 443, 560, 80);
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

		// Button Action Listeners
		btnAdd.addActionListener(e -> addFaculty());
		btnEdit.addActionListener(e -> editFaculty());
		btnDelete.addActionListener(e -> deleteFaculty());
		btnSearch.addActionListener(e -> searchFaculty());
		btnLoad.addActionListener(e -> loadData());

		loadData();
	}

	private void addFaculty() {
		var id = txtFacultyCode.getText().trim();
		var name = txtFacultyName.getText().trim();

		if (id.isEmpty() || name.isEmpty()) {
			JOptionPane.showMessageDialog(this, "ID and Name cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		var faculty = new Faculty(id, name);
		var dao = FacultyDao.getInstance();

		try {
			dao.insert(faculty);
			JOptionPane.showMessageDialog(this, "Faculty added successfully!", "Success",
					JOptionPane.INFORMATION_MESSAGE);
			loadData();
			btnCancelActionPerformed(null);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error adding faculty: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void editFaculty() {
		var id = txtFacultyCode.getText().trim();
		var name = txtFacultyName.getText().trim();

		if (id.isEmpty() || name.isEmpty()) {
			JOptionPane.showMessageDialog(this, "ID and Name cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		var faculty = new Faculty(id, name);
		var dao = FacultyDao.getInstance();

		try {
			dao.update(faculty);
			JOptionPane.showMessageDialog(this, "Faculty updated successfully!", "Success",
					JOptionPane.INFORMATION_MESSAGE);
			loadData();
			btnCancelActionPerformed(null);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error updating faculty: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void deleteFaculty() {
		var selectedRow = table.getSelectedRow();

		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(this, "Please select a row to delete!", "Warning",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		var id = tableModel.getValueAt(selectedRow, 0).toString();

		var confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete Faculty ID: " + id + "?",
				"Confirm Delete", JOptionPane.YES_NO_OPTION);

		if (confirm == JOptionPane.YES_OPTION) {
			try {
				var dao = FacultyDao.getInstance();
				dao.delete(id);
				loadData();
				btnCancelActionPerformed(null);
				JOptionPane.showMessageDialog(this, "Faculty deleted successfully!", "Success",
						JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "Error deleting Faculty: " + e.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void searchFaculty() {
		var inputID = txtFacultyCode.getText().trim();
		var inputName = txtFacultyName.getText().trim();

		if (inputID.isEmpty() && inputName.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Please enter ID or Name to search!", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		var dao = FacultyDao.getInstance();
		var result = dao.search(inputID, inputName);

		tableModel.setRowCount(0);

		if (result.isEmpty()) {
			JOptionPane.showMessageDialog(this, "No matching data found!", "Info", JOptionPane.INFORMATION_MESSAGE);
		} else {
			for (var faculty : result) {
				tableModel.addRow(new Object[] { faculty.getIDFac(), faculty.getNameFac() });
			}
		}
	}

	private void loadData() {
		tableModel.setRowCount(0);

		try {
			var dao = new FacultyDao();
			var faculties = dao.selectall();

			for (var faculty : faculties) {
				tableModel.addRow(new Object[] { faculty.getIDFac(), faculty.getNameFac() });
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	protected void tableMouseClicked(MouseEvent e) {
		var row = table.getSelectedRow();

		txtFacultyCode.setText(table.getValueAt(row, 0).toString());
		txtFacultyName.setText(table.getValueAt(row, 1).toString());
	}

	protected void btnCancelActionPerformed(ActionEvent e) {
		txtFacultyCode.setText("");
		txtFacultyName.setText("");
		table.clearSelection();
	}
}
