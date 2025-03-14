package view;

import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import dao.ScoreDao;
import dao.StudentDao;

public class InfoStu extends JFrame {

	private static final long serialVersionUID = 1L;
	private static String username;
	private JPanel contentPane;
	private JPanel panel;
	private JLabel lblID;
	private JLabel lblname;
	private JLabel lblGender;
	private JLabel lblbirth;
	private JLabel lblAddress;
	private JLabel lblFac;
	private JScrollPane scrollPane;
	private JTable table;
	private JLabel lblBirth;
	private JTextField id;
	private JTextField name;
	private JTextField gender;
	private JTextField dob;
	private JTextField address;
	private JTextField fac;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(() -> {
//			try {
//				var frame = new InfoStu(username);
//				frame.setVisible(true);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		});
//	}

	public void StudentData(String IDStu) {
		System.out.println(IDStu);
		var dao = StudentDao.getInstance();
		var students = dao.IsUser(IDStu);
		students.forEach(student -> {
			id.setText(student.getId());
			id.setEditable(false);
			name.setText(student.getName());
			name.setEditable(false);
			gender.setText(student.isGender() ? "Male" : "Female");
			gender.setEditable(false);
			dob.setText(student.getDob().toString());
			dob.setEditable(false);
			address.setText(student.getDiaChi());
			address.setEditable(false);
			fac.setText(student.getNameFac());
			fac.setEditable(false);
		});
	}

	private void loadScore(String IDStu) {
		var dao = ScoreDao.getInstance();
		var scoreList = dao.getScoresStudent(IDStu);
		var model = new DefaultTableModel();
		model.addColumn("IDSub");
		model.addColumn("NameSub");
		model.addColumn("Sc1");
		model.addColumn("Sc2");
		model.addColumn("Sc3");
		model.addColumn("Sctotal");
		model.addColumn("Rate");
		scoreList.forEach(sc -> {
			model.addRow(new Object[] { sc.getId(), sc.getNameSub(), sc.getSc1(), sc.getSc2(), sc.getSc3(),
					sc.getScTotal(), sc.getRate() });
		});
		table.setModel(model);
		var sorter = new TableRowSorter<>(model);
		table.setRowSorter(sorter);
	}

	public InfoStu(String IDStu) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 839, 614);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);

		panel = new JPanel();
		panel.setBounds(23, 60, 770, 493);
		contentPane.add(panel);
		panel.setLayout(null);

		lblID = new JLabel("ID");
		lblID.setBounds(10, 11, 51, 26);
		lblID.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel.add(lblID);

		lblname = new JLabel("NAME");
		lblname.setBounds(10, 48, 115, 26);
		lblname.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel.add(lblname);

		lblGender = new JLabel("GENDER");
		lblGender.setBounds(10, 85, 115, 26);
		lblGender.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel.add(lblGender);

		lblBirth = new JLabel("DOB");
		lblBirth.setBounds(387, 17, 115, 26);
		lblBirth.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel.add(lblBirth);

		lblAddress = new JLabel("ADDRESS");
		lblAddress.setBounds(387, 54, 115, 26);
		lblAddress.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel.add(lblAddress);

		lblFac = new JLabel("FACULTY");
		lblFac.setBounds(387, 91, 115, 26);
		lblFac.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel.add(lblFac);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(44, 161, 689, 300);
		panel.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		id = new JTextField();
		id.setBounds(82, 14, 86, 20);
		panel.add(id);
		id.setColumns(10);

		name = new JTextField();
		name.setColumns(10);
		name.setBounds(82, 51, 86, 20);
		panel.add(name);

		gender = new JTextField();
		gender.setColumns(10);
		gender.setBounds(82, 88, 86, 20);
		panel.add(gender);

		dob = new JTextField();
		dob.setColumns(10);
		dob.setBounds(449, 20, 199, 20);
		panel.add(dob);

		address = new JTextField();
		address.setColumns(10);
		address.setBounds(449, 54, 199, 20);
		panel.add(address);

		fac = new JTextField();
		fac.setColumns(10);
		fac.setBounds(449, 91, 199, 20);
		panel.add(fac);

		// Title label
		var titleLabel = new JLabel("Thông Tin Sinh Viên", SwingConstants.CENTER);
		titleLabel.setBounds(316, 11, 176, 52);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
		titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
		contentPane.add(titleLabel);
		var fieldsPanel = new JPanel();
		fieldsPanel.setLayout(null);
		StudentData(IDStu);
		loadScore(IDStu);
		setContentPane(contentPane);
	}

}
