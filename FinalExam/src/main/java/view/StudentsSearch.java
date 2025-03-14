package view;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import dao.StudentDao;

public class StudentsSearch extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTable tblStudent; // JTable
	private DefaultTableModel model;
	private JTextField txtId;

	private void clearFields() {
		txtId.setText("");
	}
	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public StudentsSearch() {
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setBounds(100, 100, 749, 559);
		getContentPane().setLayout(null);

		var lblId = new JLabel("ID");
		lblId.setBounds(38, 20, 61, 25);
		getContentPane().add(lblId);

		txtId = new JTextField();
		txtId.setBounds(109, 20, 272, 25); // Đặt vị trí và kích thước cho txtId
		getContentPane().add(txtId);

		var btnSearch = new JButton("Search");
		btnSearch.addActionListener(this::btnTimKiemActionPerformed);
		btnSearch.setBounds(406, 20, 100, 25);
		getContentPane().add(btnSearch);

		var lblTotal = new JLabel("Total of students");
		lblTotal.setBounds(32, 103, 100, 25);
		getContentPane().add(lblTotal);

		model = new DefaultTableModel();
		model.addColumn("ID");
		model.addColumn("Name");
		model.addColumn("Class");

		// Khởi tạo JTable và set model cho nó
		tblStudent = new JTable(model);

		// Tạo JScrollPane và thêm JTable vào đó
		var scrollPane = new JScrollPane(tblStudent);
		scrollPane.setBounds(32, 139, 653, 292); // Vị trí và kích thước của JScrollPane
		getContentPane().add(scrollPane); // Thêm JScrollPane vào frame
	}

	public static void main(String[] args) {
		var frame = new JFrame("Chương trình quản lý điểm");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.getContentPane().setLayout(null);
		var desktopPane = new JDesktopPane();
		frame.getContentPane().add(desktopPane);
		var studentSearch = new StudentsSearch();
		desktopPane.add(studentSearch);
		studentSearch.setVisible(true);
		frame.setVisible(true);
	}

	protected void btnTimKiemActionPerformed(ActionEvent e) {
		var id = txtId.getText();
		if (id.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Please enter an ID to search.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		var model = new DefaultTableModel();
		model.addColumn("ID");
		model.addColumn("Name");
		model.addColumn("Class");
		var dao = StudentDao.getInstance();
		var students = dao.selectid(id);
		if (students == null) {
			JOptionPane.showMessageDialog(null, "Can not find ID.", "Information",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			//model.addRow(new Object[] { student.getId(), student.getName(), student.getIdclass() });
			students.forEach(p -> model.addRow(new Object[] { p.getId(), p.getName(), p.getIdclass() }));
		tblStudent.setModel(model);
	}
	txtId.setText("");
}


}
