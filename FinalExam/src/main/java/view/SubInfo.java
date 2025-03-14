package view;

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

import dao.SubDao;
import model.Subject;

public class SubInfo extends JInternalFrame {
	private JTextField txtSubID;
	private JTextField txtSubName;
	private JTable table;
	private DefaultTableModel tableModel;
	private JLabel lblCredit;
	private JTextField txtCredit;

	public SubInfo() {
		setBounds(100, 100, 881, 662);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		getContentPane().setLayout(null);

		// Panel cho các trường thông tin
		var fieldPanel = new JPanel();
		fieldPanel.setLayout(null);
		fieldPanel.setBorder(BorderFactory.createTitledBorder("Class Information"));
		fieldPanel.setBounds(153, 9, 560, 145);
		getContentPane().add(fieldPanel);

		// Label "ID"
		var lblID = new JLabel("ID");
		lblID.setBounds(20, 30, 80, 25);
		fieldPanel.add(lblID);

		// TextField "ID"
		txtSubID = new JTextField();
		txtSubID.setBounds(100, 30, 150, 25);
		fieldPanel.add(txtSubID);

		// Label "Name"
		var lblName = new JLabel("Name");
		lblName.setBounds(20, 70, 80, 25);
		fieldPanel.add(lblName);

		// TextField "Name"
		txtSubName = new JTextField();
		txtSubName.setBounds(100, 70, 150, 25);
		fieldPanel.add(txtSubName);

		var btnLoad = new JButton("Load");
		btnLoad.setBounds(323, 40, 80, 25);
		fieldPanel.add(btnLoad);

		var btnCancel = new JButton("Cancel");
		btnCancel.setBounds(323, 88, 80, 25);
		fieldPanel.add(btnCancel);

		lblCredit = new JLabel("Credit");
		lblCredit.setBounds(20, 106, 80, 25);
		fieldPanel.add(lblCredit);

		txtCredit = new JTextField();
		txtCredit.setBounds(100, 106, 150, 25);
		fieldPanel.add(txtCredit);

		// Panel cho bảng
		var tablePanel = new JPanel();
		tablePanel.setLayout(null);
		tablePanel.setBorder(BorderFactory.createTitledBorder("Class List"));
		tablePanel.setBounds(10, 165, 845, 292);
		getContentPane().add(tablePanel);

		// Table
		tableModel = new DefaultTableModel(new Object[] { "ID", "Name", "Credits" }, 0);
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
		buttonPanel.setBounds(153, 468, 560, 80);
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

		btnAdd.addActionListener(e -> addSubject());
		btnEdit.addActionListener(e -> editSubject());
		btnDelete.addActionListener(e -> deleteSubject());
		btnSearch.addActionListener(e -> searchSubject());
		btnCancel.addActionListener(e -> cancelAction());

		loadSubjects();
	}

	private void addSubject() {
		var id = txtSubID.getText().trim();
		var name = txtSubName.getText().trim();
		var numCreditsText = txtCredit.getText().trim();

		if (id.isEmpty() || name.isEmpty() || numCreditsText.isEmpty()) {
			JOptionPane.showMessageDialog(this, "ID, Name, and Credits cannot be empty!", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		int numCredits;
		try {
			numCredits = Integer.parseInt(numCreditsText);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Credits must be a valid number!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		var subject = new Subject(id, name, numCredits);

		var dao = SubDao.getInstance();
		dao.insert(subject);

		tableModel.addRow(new Object[] { id, name, numCredits });

		JOptionPane.showMessageDialog(this, "Subject added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

		txtSubID.setText("");
		txtSubName.setText("");
		txtCredit.setText("");
	}

	private void loadSubjects() {
		tableModel.setRowCount(0);

		try {
			var dao = SubDao.getInstance();
			var subjects = dao.selectActive();

			for (var subject : subjects) {
				tableModel.addRow(new Object[] { subject.getiDSub(), subject.getNameSub(), subject.getNumcredits() });
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	private void editSubject() {
		var selectedRow = table.getSelectedRow();

		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(this, "Please select a subject to edit!", "Warning",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		var id = txtSubID.getText().trim();
		var name = txtSubName.getText().trim();
		var numCreditsText = txtCredit.getText().trim();

		if (id.isEmpty() || name.isEmpty() || numCreditsText.isEmpty()) {
			JOptionPane.showMessageDialog(this, "ID, Name, and Credits cannot be empty!", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		int numCredits;
		try {
			numCredits = Integer.parseInt(numCreditsText);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Credits must be a valid number!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		var subject = new Subject(id, name, numCredits);
		var dao = SubDao.getInstance();
		dao.update(subject);

		tableModel.setValueAt(name, selectedRow, 1);
		tableModel.setValueAt(numCredits, selectedRow, 2);

		JOptionPane.showMessageDialog(this, "Subject updated successfully!", "Success",
				JOptionPane.INFORMATION_MESSAGE);

		txtSubID.setText("");
		txtSubName.setText("");
		txtCredit.setText("");
		table.clearSelection();
	}

	protected void tableMouseClicked(MouseEvent e) {

		var row = table.getSelectedRow();

		txtSubID.setText(table.getValueAt(row, 0).toString());
		txtSubName.setText(table.getValueAt(row, 1).toString());

		try {
			txtCredit.setText(table.getValueAt(row, 2).toString());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void deleteSubject() {
		var selectedRow = table.getSelectedRow();

		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(this, "Please select a subject to delete!", "Warning",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		var id = table.getValueAt(selectedRow, 0).toString();

		var confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to deactivate this subject?",
				"Confirm Delete", JOptionPane.YES_NO_OPTION);
		if (confirm != JOptionPane.YES_OPTION) {
			return;
		}

		try {
			var dao = SubDao.getInstance();
			dao.updateStatus(id, 0);

			tableModel.removeRow(selectedRow);
			JOptionPane.showMessageDialog(this, "Subject deactivated successfully!", "Success",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error deactivating subject: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	private void searchSubject() {
		var id = txtSubID.getText().trim();
		var name = txtSubName.getText().trim();

		if (id.isEmpty() && name.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Please enter either ID or Name to search!", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		try {
			var dao = SubDao.getInstance();
			var result = dao.search(id, name);

			tableModel.setRowCount(0);

			if (result.isEmpty()) {
				JOptionPane.showMessageDialog(this, "No matching subjects found!", "Info",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			for (var subject : result) {
				tableModel.addRow(new Object[] { subject.getiDSub(), subject.getNameSub(), subject.getNumcredits() });
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error searching subjects: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	private void cancelAction() {
		txtSubID.setText("");
		txtSubName.setText("");
		txtCredit.setText("");

		table.clearSelection();

		JOptionPane.showMessageDialog(this, "Fields cleared and selection reset!", "Info",
				JOptionPane.INFORMATION_MESSAGE);
	}

}