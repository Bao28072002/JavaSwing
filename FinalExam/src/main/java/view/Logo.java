package view;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

public class Logo extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private Point mousepoint;
	private Timer timer;
	private JProgressBar progressBar;
	private Image backgroundImage;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(() -> {
			try {
				var frame = new Logo();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Logo() {

		var imageURL = Logo.class.getResource("/chill_guy1.png");
		if (imageURL == null) {
			System.err.println("Không tìm thấy tệp ảnh: /images.png");
			return;
		}
		backgroundImage = new ImageIcon(imageURL).getImage();

		setUndecorated(true);
		setBackground(new Color(0, 0, 0, 0));

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 850, 830);
		setLocationRelativeTo(null);

		contentPane = new JPanel() {
			@Override
			protected void paintComponent(java.awt.Graphics g) {
				super.paintComponent(g);
				g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
			}
		};
		contentPane.setBackground(new Color(0, 0, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				thisMousePressed(e);
			}
		});
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				thisMouseDragged(e);
			}
		});

		progressBar = new JProgressBar();
		progressBar.setForeground(new Color(0, 255, 0));
		progressBar.setStringPainted(true);
		progressBar.setBounds(0, 816, 850, 14);
		contentPane.add(progressBar);

		timer = new Timer(40, e -> {
			progressBar.setValue(progressBar.getValue() + 1);
			if (progressBar.getValue() == 100) {
				timer.stop();
				this.dispose();
				new LoginFrame().setVisible(true);
			}
		});
		timer.start();
	}

	protected void thisMousePressed(MouseEvent e) {
		mousepoint = e.getPoint();
	}

	protected void thisMouseDragged(MouseEvent e) {
		var current = e.getLocationOnScreen();
		setLocation(current.x - mousepoint.x, current.y - mousepoint.y);
	}
}
