package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Dimension;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JList;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Color;

public class ConnectingWindow extends JFrame {

	private JPanel contentPane;
	private JTextField txtFindASpecific;

	
	/**
	 * Create the frame.
	 */
	public ConnectingWindow() {
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				GUIControler.closeApp2();
			}
		});
		
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:/Users/ValeVale/git/hangman/HangmanClient/resources/icons/h.png"));
		setTitle("Hangman");
		setMinimumSize(new Dimension(450, 320));
		setSize(new Dimension(800, 600));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblWelcomeUser = new JLabel("Welcome, user_var!");
		lblWelcomeUser.setForeground(new Color(153, 50, 204));
		lblWelcomeUser.setFont(new Font("Arial", Font.BOLD, 18));
		panel.add(lblWelcomeUser);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.WEST);
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.EAST);
		
		JPanel panel_3 = new JPanel();
		contentPane.add(panel_3, BorderLayout.SOUTH);
		panel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		txtFindASpecific = new JTextField();
		txtFindASpecific.setForeground(new Color(216, 191, 216));
		txtFindASpecific.setFont(new Font("Arial", Font.ITALIC, 12));
		txtFindASpecific.setText("Find a specific user...");
		panel_3.add(txtFindASpecific);
		txtFindASpecific.setColumns(14);
		
		JLabel lblOr = new JLabel("OR");
		panel_3.add(lblOr);
		
		JButton btnRandom = new JButton("RANDOM");
		btnRandom.setBackground(new Color(221, 160, 221));
		btnRandom.setForeground(new Color(255, 255, 255));
		btnRandom.setFont(new Font("Arial", Font.BOLD, 13));
		panel_3.add(btnRandom);
		
		JPanel panel_4 = new JPanel();
		contentPane.add(panel_4, BorderLayout.CENTER);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_5 = new JPanel();
		panel_4.add(panel_5, BorderLayout.NORTH);
		
		JLabel lblPleaseSelectAn = new JLabel("Please select an online player from the list bellow:");
		lblPleaseSelectAn.setForeground(new Color(186, 85, 211));
		lblPleaseSelectAn.setFont(new Font("Arial", Font.PLAIN, 13));
		panel_5.add(lblPleaseSelectAn);
		
		JPanel panel_6 = new JPanel();
		panel_4.add(panel_6, BorderLayout.CENTER);
		panel_6.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_7 = new JPanel();
		panel_6.add(panel_7, BorderLayout.SOUTH);
		
		JLabel lblYouCanAlso = new JLabel("You can also search for a specific player, or find one random:");
		lblYouCanAlso.setForeground(new Color(186, 85, 211));
		lblYouCanAlso.setFont(new Font("Arial", Font.PLAIN, 13));
		panel_7.add(lblYouCanAlso);
		
		JList list = new JList();
		panel_6.add(list, BorderLayout.CENTER);
	}

}
