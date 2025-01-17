package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.time.ZoneId;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import com.toedter.calendar.JDateChooser;

import dao.ClassDao;
import dao.LoginDao;
import dao.StudentDao;
import model.Student;
import model.StudentManager;
public class StudentManagementView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	StudentManager model;
	private JPanel panelList;
	private JPanel panelView;
	private JLabel lblStudentList;
	private JScrollPane scrollPane;
	private JTable table;
	private JLabel lblNewLabel_1;
	private JLabel lblId;
	private JTextField textField_nameView;
	private JLabel lblName;
	private JTextField textField_idView;
	private JLabel lblDateOfBirth;
	private JLabel lblAddressView;
	private JLabel lblGender;
	private JRadioButton rdbtnMale;
	private JRadioButton rdbtnFemale;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JButton btnAdd;
	private JButton btnDelete;
	private JButton btnUpdate;
	private JButton btnLoad;
	private JButton btnCancel;
	private JDateChooser dateChooser;
	private JLabel lblClass;
	private JTextField textField_addressView;
	private JComboBox<String> IDClassComboBox;

	private void clearInputFields() {
		textField_idView.setText("");
		textField_idView.setEnabled(true);
		textField_nameView.setText("");
		dateChooser.setDate(null);
		textField_addressView.setText("");
		rdbtnMale.setSelected(true);
		IDClassComboBox.setSelectedItem(0);
		btnAdd.setEnabled(true);
		btnDelete.setEnabled(false);
		btnUpdate.setEnabled(false);
	}

	private void loadData() {
			var dao = StudentDao.getInstance();
			var studentList = dao.selectall();
			var model = new DefaultTableModel();
			model.addColumn("ID");
			model.addColumn("NAME");
			model.addColumn("DBO");
			model.addColumn("ADDRESS");
			model.addColumn("GENDER");
			model.addColumn("IDCLASS");
			studentList.forEach(student -> {
				model.addRow(new Object[] { student.getId(), student.getName(), student.getDob(), student.getDiaChi(),
						student.isGender() ? "Male" : "Female", student.getIdclass() });
			});

			table.setModel(model);
			var sorter = new TableRowSorter<>(model);
			table.setRowSorter(sorter);
	}


	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(() -> {
//			try {
//				var frame = new StudentManagementView();
//				frame.setVisible(true);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public StudentManagementView() {
		setTitle("Student Manager");
		this.model = new StudentManager();
		setBounds(100, 100, 758, 726);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 128, 128));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		panelList = new JPanel();
		panelList.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelList.setBounds(10, 25, 722, 289);
		contentPane.add(panelList);
		panelList.setLayout(new BorderLayout());

		lblStudentList = new JLabel("Students List");
		lblStudentList.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblStudentList.setHorizontalAlignment(SwingConstants.CENTER);
		panelList.add(lblStudentList, BorderLayout.NORTH);
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				tableMousePressed(e);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				tableMouseClicked(e);
			}

		});
		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panelList.add(scrollPane, BorderLayout.CENTER);
		scrollPane.setViewportView(table);
		loadData();
		panelView = new JPanel();
		panelView.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelView.setBounds(10, 339, 722, 318);
		contentPane.add(panelView);
		panelView.setLayout(null);

		lblNewLabel_1 = new JLabel("Student Information");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNewLabel_1.setBounds(0, 0, 722, 25);
		panelView.add(lblNewLabel_1);


		lblId = new JLabel("ID");
		lblId.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblId.setBounds(10, 32, 100, 25);
		panelView.add(lblId);

		textField_nameView = new JTextField();
		textField_nameView.setToolTipText("Enter Name Student have less 5 characters ");
		textField_nameView.setBounds(110, 73, 150, 25);
		panelView.add(textField_nameView);
		lblName = new JLabel("Name");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblName.setBounds(10, 69, 58, 25);
		panelView.add(lblName);
		textField_nameView.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				var input = textField_nameView.getText();
				if (input.matches(".*\\d.*")) {
					JOptionPane.showMessageDialog(panelView, "Name not have number.");
					textField_nameView.setText("");
				}
			}
		});

		textField_idView = new JTextField();
		textField_idView.setToolTipText("Enter ID Student have 5 characters ");
		textField_idView.setBounds(110, 36, 150, 25);
		panelView.add(textField_idView);
		textField_idView.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				var input = textField_idView.getText();
				if (input.length() > 5) {
					JOptionPane.showMessageDialog(panelView, "ID no more 5 characters.");
					textField_idView.setText("");

				}
			}
		});

		lblDateOfBirth = new JLabel("DoB");
		lblDateOfBirth.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblDateOfBirth.setBounds(10, 109, 58, 25);
		panelView.add(lblDateOfBirth);


		lblAddressView = new JLabel("Address");
		lblAddressView.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblAddressView.setBounds(10, 149, 100, 25);
		panelView.add(lblAddressView);

		lblGender = new JLabel("Gender");
		lblGender.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblGender.setBounds(10, 185, 100, 25);
		panelView.add(lblGender);

		rdbtnMale = new JRadioButton("Male");
		rdbtnMale.setSelected(true);
		buttonGroup.add(rdbtnMale);
		rdbtnMale.setBounds(110, 189, 70, 25);
		panelView.add(rdbtnMale);

		rdbtnFemale = new JRadioButton("Female");
		buttonGroup.add(rdbtnFemale);
		rdbtnFemale.setBounds(182, 189, 80, 25);
		panelView.add(rdbtnFemale);

		btnAdd = new JButton("Add");
		btnAdd.addActionListener(this::btnAddActionPerformed);
		var iconAdd = new ImageIcon(this.getClass().getResource("/add1.png")).getImage();
		btnAdd.setIcon(new ImageIcon(iconAdd));
		btnAdd.setBackground(new Color(255, 255, 255));
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnAdd.setBounds(10, 238, 110, 40);
		panelView.add(btnAdd);

		btnDelete = new JButton("Delete");
		var iconDel = new ImageIcon(this.getClass().getResource("/delete2.png")).getImage();
		btnDelete.setIcon(new ImageIcon(iconDel));
		btnDelete.addActionListener(this::btnDeleteActionPerformed);
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnDelete.setBounds(158, 238, 110, 40);
		btnDelete.setEnabled(false);
		panelView.add(btnDelete);

		btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(this::btnUpdateActionPerformed);
		var iconUpdate = new ImageIcon(this.getClass().getResource("/update3.png")).getImage();
		btnUpdate.setIcon(new ImageIcon(iconUpdate));
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnUpdate.setBounds(308, 238, 110, 40);
		btnUpdate.setEnabled(false);
		panelView.add(btnUpdate);

		btnLoad = new JButton("Load");
		btnLoad.addActionListener(this::btnLoadActionPerformed);
		var iconSave = new ImageIcon(this.getClass().getResource("/save1.png")).getImage();
		btnLoad.setIcon(new ImageIcon(iconSave));
		btnLoad.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnLoad.setBounds(454, 238, 110, 40);
		panelView.add(btnLoad);

		btnCancel = new JButton("Reset");
		var iconCancel = new ImageIcon(this.getClass().getResource("/cancel2.png")).getImage();
		btnCancel.setIcon(new ImageIcon(iconCancel));
		btnCancel.addActionListener(this::btnCancelActionPerformed);
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnCancel.setBounds(602, 238, 110, 40);
		panelView.add(btnCancel);

		dateChooser = new JDateChooser();
		dateChooser.setDateFormatString("yyyy-MM-dd");
		dateChooser.getCalendarButton();
		dateChooser.setBounds(110, 113, 150, 25);
		panelView.add(dateChooser);

		lblClass = new JLabel("IDClass");
		lblClass.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblClass.setBounds(410, 36, 100, 25);
		panelView.add(lblClass);

		IDClassComboBox = new JComboBox<>();
		IDClassComboBox.setBounds(520, 36, 150, 25);
		panelView.add(IDClassComboBox);
		loadClassComboBox();
		textField_addressView = new JTextField();
		textField_addressView.setBounds(110, 153, 150, 25);
		panelView.add(textField_addressView);

		this.setVisible(true);
	}

	private void loadClassComboBox() {
		var idclass = ClassDao.getInstance();
		var comboBoxModel = new DefaultComboBoxModel<String>();
		idclass.loadIDClassData(comboBoxModel);
		IDClassComboBox.setModel(comboBoxModel);
		IDClassComboBox.setSelectedIndex(0);
	}


	protected void btnCancelActionPerformed(ActionEvent e) {
		clearInputFields();
		JOptionPane.showMessageDialog(this, "All input fields have been cleared.", "Cancel",
				JOptionPane.INFORMATION_MESSAGE);
	}

	protected void btnDeleteActionPerformed(ActionEvent e) {
		var idstu = textField_idView.getText().trim();
		if (!idstu.isEmpty()) {
			var confirm = JOptionPane.showConfirmDialog(this,
					"Do you want to delete IDStu: " + idstu + "?", "Confirm ",
					JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				var dao = StudentDao.getInstance();
				dao.delete(idstu);
				loadData();
				clearInputFields();
				JOptionPane.showMessageDialog(this, "Successfully deleted!");
			}
		} else {
			JOptionPane.showMessageDialog(this, "Please input ID or select row", "Notification",
					JOptionPane.WARNING_MESSAGE);
		}
	}


	protected void btnLoadActionPerformed(ActionEvent e) {
		var dao = StudentDao.getInstance();
		var model = new DefaultTableModel();
		model.addColumn("ID");
		model.addColumn("NAME");
		model.addColumn("DBO");
		model.addColumn("ADDRESS");
		model.addColumn("GENDER");
		model.addColumn("IDCLASS");
		dao.selectall().stream().forEach(stu -> {
//			var facultyName = pro.getFacultyInfo() != null ? pro.getFacultyInfo().getNameFac() : "null";
			model.addRow(new Object[] {
					stu.getId(), stu.getName(), stu.getDob(), stu.getDiaChi(), stu.isGender() ? "Male" : "Female",
					stu.getIdclass()
//					, stu.getFacultyInfo().getNameFac()
			});
		});
		table.setModel(model);
	}

	protected void tableMouseClicked(MouseEvent e) {
		var row = table.getSelectedRow();
		textField_idView.setText(table.getValueAt(row, 0).toString());
		textField_idView.setEnabled(false);
		textField_nameView.setText(table.getValueAt(row, 1).toString());
		try {
			dateChooser.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(table.getValueAt(row, 2).toString()));
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		textField_addressView.setText(table.getValueAt(row, 3).toString());
		var gender = table.getValueAt(row, 4).toString();
		if (gender.equals("Male")) {
			rdbtnMale.setSelected(true);
		} else if (gender.equals("Female")) {
			rdbtnFemale.setSelected(true);
		}
		IDClassComboBox.setSelectedItem(table.getValueAt(row, 5).toString());
		btnAdd.setEnabled(false);
		btnDelete.setEnabled(true);
		btnUpdate.setEnabled(true);
	}

	protected void btnAddActionPerformed(ActionEvent e) {
		var add = StudentDao.getInstance();
		var stu = new Student();
		if (textField_idView.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Input ID.");
			return;
		}
		var id = textField_idView.getText();
		if (id.length() != 5) {
			JOptionPane.showMessageDialog(this, "ID have exactly 6 characters.");
			textField_idView.setText("");
			return;
		}
		if (add.isIDStu(id)) {
			JOptionPane.showMessageDialog(this, "ID Student already exists!", "Error", JOptionPane.ERROR_MESSAGE);
			textField_idView.setText("");
			return;
		}
		stu.setId(textField_idView.getText());

		if (textField_nameView.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Input Name Again!.");
			return;
		}
		var name = textField_nameView.getText();
		if (name.length() < 5) {
			JOptionPane.showMessageDialog(this, "Name have less 5 characters.");
			return;
		}
		stu.setName(name);

		var selectedDate = dateChooser.getDate();
		if (selectedDate == null) {
			JOptionPane.showMessageDialog(this, "Input Date.");
			return;
		}
		stu.setDob(selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

		if (textField_addressView.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Input Address.");
			return;
		}
		stu.setDiaChi(textField_addressView.getText());

		if (rdbtnMale.isSelected()) {
			stu.setGender(true);
		} else {
			stu.setGender(false);
		}

		var selectedClass = (String) IDClassComboBox.getSelectedItem();
		stu.setIdclass(selectedClass);
		add.insert(stu);

		var model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		add.selectall().forEach(p -> {
			model.addRow(new Object[] { p.getId(), p.getName(), p.getDob(), p.getDiaChi(),
					p.isGender() ? "Male" : "Female", p.getIdclass() });
		});

		clearInputFields();
		var loginDao = LoginDao.getInstance();
		var loginId = stu.getId();
		var password = "1";
		var role = "User";
		loginDao.insertLogin(loginId, loginId, password, role, loginId);
		JOptionPane.showMessageDialog(this, "Student and login account added successfully!");
	}



	protected void tableMousePressed(MouseEvent e) {
		var popup = new JPopupMenu("delete");
		var item = new JMenuItem("delete row");
		item.addActionListener(this::deleteRow);
		popup.add(item);
		if (e.getButton() == MouseEvent.BUTTON3) {
			var row = table.rowAtPoint(e.getPoint());
			table.setRowSelectionInterval(row, row);
			popup.show(table, e.getX(), e.getY());
		}
	}

	private void deleteRow(ActionEvent actionEvent) {
		var result = JOptionPane.showConfirmDialog(null, "Are you sure", "delete", JOptionPane.YES_NO_OPTION);
		if (result == JOptionPane.YES_OPTION) {
			var dao = StudentDao.getInstance();
			var model = (DefaultTableModel) table.getModel();
			var row = table.getSelectedRow();
			var id = (String) model.getValueAt(row, 0);
			dao.delete(id);
			model.removeRow(row);
		}
	}

	protected void btnUpdateActionPerformed(ActionEvent e) {
		var update = StudentDao.getInstance();
		var stu = new Student();
		stu.setId(textField_idView.getText());
		if (textField_nameView.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Input Name Again!.");
			return;
		}
		var name = textField_nameView.getText();
		if (name.length() < 5) {
			JOptionPane.showMessageDialog(this, "Name have less 5 characters.");
			return;
		}
		stu.setName(name);

		var selectedDate = dateChooser.getDate();
		if (selectedDate == null) {
			JOptionPane.showMessageDialog(this, "Input Date.");
			return;
		}
		stu.setDob(selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

		if (textField_addressView.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Input Address.");
			return;
		}
		stu.setDiaChi(textField_addressView.getText());

		if (rdbtnMale.isSelected()) {
			stu.setGender(true);
		} else {
			stu.setGender(false);
		}

		var selectedClass = (String) IDClassComboBox.getSelectedItem();
		stu.setIdclass(selectedClass);
		update.update(stu);
		var model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		update.selectall().stream().forEach(p -> {
			model.addRow(new Object[] { p.getId(), p.getName(), p.getDob(), p.getDiaChi(),
					p.isGender() ? "Male" : "Female", p.getIdclass() });
		});
		clearInputFields();
	}
}