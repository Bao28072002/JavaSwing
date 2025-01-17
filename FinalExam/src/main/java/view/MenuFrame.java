package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class MenuFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel leftPanel;
	private JButton btnHome;
	private JButton btnManagenment;
	private JButton btnScore;
	private JButton btnLogout_3;

	private JDesktopPane desktopPane;

	/**
	 * Launch the application. //
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(() -> {
//			try {
//				var frame = new MenuFrame();
//				frame.setVisible(true);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public MenuFrame() {
		setTitle("Menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1292, 729);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 128, 128));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		leftPanel = new JPanel();
		leftPanel.setBackground(new Color(255, 128, 128));
		leftPanel.setBounds(35, 18, 215, 636);
		contentPane.add(leftPanel);
		leftPanel.setLayout(null);

		btnHome = new JButton("Home\r\n");
		btnHome.addActionListener(this::btnHomeActionPerformed);
		btnHome.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnHome.setBounds(10, 63, 193, 53);
		leftPanel.add(btnHome);

		btnManagenment = new JButton("Management");
		btnManagenment.addActionListener(this::btnManagementActionPerformed);
		btnManagenment.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnManagenment.setBounds(10, 319, 193, 54);
		leftPanel.add(btnManagenment);

		btnScore = new JButton("Score");
		btnScore.addActionListener(this::btnScoreActionPerformed);
		btnScore.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnScore.setBounds(10, 449, 193, 55);
		leftPanel.add(btnScore);

		btnLogout_3 = new JButton("Logout");
		btnLogout_3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnLogout_3.setBounds(10, 515, 193, 55);
		btnLogout_3.addActionListener(this::btnLogoutActionPerformed);
		leftPanel.add(btnLogout_3);

		btnClass = new JButton("Class");
		btnClass.addActionListener(this::btnClassActionPerformed);
		btnClass.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnClass.setBounds(10, 191, 193, 53);
		leftPanel.add(btnClass);

		btnSearch = new JButton("Search");
		btnSearch.addActionListener(this::btnSearchActionPerformed);
		btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnSearch.setBounds(10, 384, 193, 54);
		leftPanel.add(btnSearch);

		btnFalcuty = new JButton("Faculty");
		btnFalcuty.setBounds(10, 127, 193, 53);
		leftPanel.add(btnFalcuty);
		btnFalcuty.addActionListener(this::btnFalcutyActionPerformed);
		btnFalcuty.setFont(new Font("Tahoma", Font.PLAIN, 20));

		btnSubject = new JButton("Subject");
		btnSubject.addActionListener(this::btnSubjectActionPerformed);
		btnSubject.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnSubject.setBounds(10, 255, 193, 53);
		leftPanel.add(btnSubject);

		desktopPane = new JDesktopPane();
		desktopPane.setBackground(new Color(255, 128, 128));
		desktopPane.setBounds(282, 18, 923, 636);
		contentPane.add(desktopPane);

		btnHomeActionPerformed(null);
	}

	private StudentManagementView managementView;
	private JButton btnClass;
	private JButton btnSearch;
	private JButton btnFalcuty;
	private JButton btnSubject;

	protected void btnManagementActionPerformed(ActionEvent e) {
		if (managementView == null || !managementView.isDisplayable()) {
			managementView = new StudentManagementView();
			managementView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			managementView.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					managementView = null;
				}
			});
			managementView.setLocationRelativeTo(this);
			managementView.setVisible(true);
		} else {
			managementView.toFront();
		}
	}

	protected void btnLogoutActionPerformed(ActionEvent e) {

		if (managementView != null && managementView.isDisplayable()) {
			managementView.dispose();
		}
		var loginFrame = new LoginFrame();
		loginFrame.setVisible(true);

		this.dispose();
	}

	protected void btnClassActionPerformed(ActionEvent e) {
		for (var component : desktopPane.getComponents()) {
			try {
				if (component instanceof JInternalFrame frame) {
					frame.dispose();
					desktopPane.remove(frame);
				}
			} catch (Exception ex) {
				System.err.println("Lỗi khi đóng hoặc xóa frame: " + ex.getMessage());
			}
		}

		for (var component : desktopPane.getComponents()) {
			if (component instanceof JLabel && "title".equals(component.getName())) {
				desktopPane.remove(component);
			}
		}

		var titleLabel = new JLabel("CLASS MANAGEMENT", JLabel.CENTER);
		titleLabel.setName("title");
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setBounds(0, 0, desktopPane.getWidth(), 50);
		desktopPane.add(titleLabel);

		var classFrame = new ClassInfo();
		classFrame.setBounds(0, 50, desktopPane.getWidth(), desktopPane.getHeight() - 50);
		classFrame.setResizable(true);
		desktopPane.add(classFrame);

		classFrame.setVisible(true);
		desktopPane.repaint();
	}

	protected void btnSearchActionPerformed(ActionEvent e) {

		for (var component : desktopPane.getComponents()) {
			if (component instanceof JInternalFrame frame) {
				frame.dispose();
				desktopPane.remove(frame);
			}
		}

		for (var component : desktopPane.getComponents()) {
			if (component instanceof JLabel && "title".equals(component.getName())) {
				desktopPane.remove(component);
			}
		}

		var titleLabel = new JLabel("SEARCH STUDENT", JLabel.CENTER);
		titleLabel.setName("title");
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setBounds(0, 0, desktopPane.getWidth(), 50);
		desktopPane.add(titleLabel);

		var searchFrame = new StudentsSearch();
		searchFrame.setBounds(0, 50, desktopPane.getWidth(), desktopPane.getHeight() - 50);
		searchFrame.setResizable(true);
		desktopPane.add(searchFrame);

		searchFrame.setVisible(true);
		desktopPane.repaint();

	}

	protected void btnFalcutyActionPerformed(ActionEvent e) {
		for (var component : desktopPane.getComponents()) {
			try {
				if (component instanceof JInternalFrame frame) {
					frame.dispose();
					desktopPane.remove(frame);
				}
			} catch (Exception ex) {
				System.err.println("Lỗi khi đóng hoặc xóa frame: " + ex.getMessage());
			}
		}
		for (var component : desktopPane.getComponents()) {
			if (component instanceof JLabel && "title".equals(component.getName())) {
				desktopPane.remove(component);
			}
		}
		var titleLabel = new JLabel("FACULTY MANAGEMENT", JLabel.CENTER);
		titleLabel.setName("title");
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setBounds(0, 0, desktopPane.getWidth(), 50);
		desktopPane.add(titleLabel);
		var facultyFrame = new FacultyInfo();
		facultyFrame.setBounds(0, 50, desktopPane.getWidth(), desktopPane.getHeight() - 50);
		facultyFrame.setResizable(true);
		desktopPane.add(facultyFrame);

		facultyFrame.setVisible(true);
		desktopPane.repaint();

	}

	protected void btnHomeActionPerformed(ActionEvent e) {
		for (var component : desktopPane.getComponents()) {
			try {
				desktopPane.remove(component);
			} catch (Exception ex) {
				System.err.println("Lỗi khi xóa thành phần: " + ex.getMessage());
			}
		}
		var defaultLabel = new JLabel("WELCOME TO STUDENT MANAGEMENT SYSTEM", JLabel.CENTER);
		defaultLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		defaultLabel.setForeground(Color.WHITE);
		defaultLabel.setBounds(0, 0, desktopPane.getWidth(), desktopPane.getHeight());
		defaultLabel.setName("homeLabel");
		desktopPane.add(defaultLabel);
		desktopPane.repaint();
		desktopPane.revalidate();
	}

	protected void btnScoreActionPerformed(ActionEvent e) {
		for (var component : desktopPane.getComponents()) {
			try {
				if (component instanceof JInternalFrame frame) {
					frame.dispose();
					desktopPane.remove(frame);
				}
			} catch (Exception ex) {
				System.err.println("Lỗi khi đóng hoặc xóa frame: " + ex.getMessage());
			}
		}
		for (var component : desktopPane.getComponents()) {
			if (component instanceof JLabel && "title".equals(component.getName())) {
				desktopPane.remove(component);
			}
		}
		var titleLabel = new JLabel("SCORE MANAGEMENT", JLabel.CENTER);
		titleLabel.setName("title");
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setBounds(0, 0, desktopPane.getWidth(), 50);
		desktopPane.add(titleLabel);
		var scoreFrame = new StudentScore();
		scoreFrame.setBounds(0, 50, desktopPane.getWidth(), desktopPane.getHeight() - 50);
		scoreFrame.setResizable(true);
		desktopPane.add(scoreFrame);

		scoreFrame.setVisible(true);
		desktopPane.repaint();
	}

	protected void btnSubjectActionPerformed(ActionEvent e) {
		for (var component : desktopPane.getComponents()) {
			try {
				if (component instanceof JInternalFrame frame) {
					frame.dispose();
					desktopPane.remove(frame);
				}
			} catch (Exception ex) {
				System.err.println("Lỗi khi đóng hoặc xóa frame: " + ex.getMessage());
			}
		}
		for (var component : desktopPane.getComponents()) {
			if (component instanceof JLabel && "title".equals(component.getName())) {
				desktopPane.remove(component);
			}
		}
		var titleLabel = new JLabel("SUBJECT MANAGEMENT", JLabel.CENTER);
		titleLabel.setName("title");
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setBounds(0, 0, desktopPane.getWidth(), 50);
		desktopPane.add(titleLabel);
		var subFrame = new SubInfo();
		subFrame.setBounds(0, 50, desktopPane.getWidth(), desktopPane.getHeight() - 50);
		subFrame.setResizable(true);
		desktopPane.add(subFrame);

		subFrame.setVisible(true);
		desktopPane.repaint();
	}
}
