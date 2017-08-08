package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Font;
import java.awt.Image;

import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class WelcomeWindow extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	



	/**
	 * Create the frame.
	 */
	public WelcomeWindow() {
		setResizable(false);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				GUIControler.closeApp1();
			}
		});
		
		setIconImage(Toolkit.getDefaultToolkit().getImage((WelcomeWindow.class.getResource("/icons/h.png"))));
		setTitle("Hangman");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 320);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel main = new JPanel();
		contentPane.add(main, BorderLayout.CENTER);
		main.setLayout(new BorderLayout(0, 0));

		JPanel north = new JPanel();
		main.add(north, BorderLayout.NORTH);
		north.setPreferredSize(new Dimension(140, 40));

		JPanel center = new JPanel();
		main.add(center, BorderLayout.SOUTH);
		center.setPreferredSize(new Dimension(140, 110));
		center.setLayout(null);

		JTextArea txtrWelcomeToHangman = new JTextArea();
		txtrWelcomeToHangman.setEditable(false);
		txtrWelcomeToHangman.setBounds(124, 0, 173, 60);
		txtrWelcomeToHangman.setForeground(new Color(153, 50, 204));
		txtrWelcomeToHangman.setFont(new Font("Arial", Font.BOLD, 25));
		txtrWelcomeToHangman.setBackground(SystemColor.control);
		txtrWelcomeToHangman.setText("WELCOME TO \r\n   HANGMAN!");
		center.add(txtrWelcomeToHangman);

		JTextArea txtrPleaseEnter = new JTextArea();
		txtrPleaseEnter.setEditable(false);
		txtrPleaseEnter.setBounds(77, 89, 282, 21);
		center.add(txtrPleaseEnter);
		txtrPleaseEnter.setForeground(new Color(153, 50, 204));
		txtrPleaseEnter.setFont(new Font("Arial", Font.BOLD, 14));
		txtrPleaseEnter.setBackground(SystemColor.control);
		txtrPleaseEnter.setText("Please enter a unique username below.");

		JPanel south = new JPanel();
		contentPane.add(south, BorderLayout.SOUTH);
		south.setPreferredSize(new Dimension(140, 120));
		south.setLayout(null);

		JTextArea txtruseOnlyLetters = new JTextArea();
		txtruseOnlyLetters.setEditable(false);
		txtruseOnlyLetters.setBounds(97, 0, 230, 19);
		txtruseOnlyLetters.setForeground(new Color(153, 50, 204));
		txtruseOnlyLetters.setFont(new Font("Arial", Font.PLAIN, 12));
		txtruseOnlyLetters.setBackground(SystemColor.control);
		txtruseOnlyLetters.setText("(Use only letters A-Z and/or numbers 0-9)");
		south.add(txtruseOnlyLetters);

		textField = new JTextField();
		textField.setPreferredSize(new Dimension(6, 30));
		textField.setBounds(92, 30, 246, 28);
		south.add(textField);
		textField.setColumns(50);

		JButton btnBegin = new JButton("BEGIN");
		btnBegin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GUIControler.sendUsername(textField.getText());
			}
		});
		btnBegin.setBounds(160, 78, 100, 31);
		btnBegin.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnBegin.setForeground(Color.WHITE);
		btnBegin.setFont(new Font("Arial", Font.BOLD, 16));
		btnBegin.setBackground(new Color(221, 160, 221));
		south.add(btnBegin);
		btnBegin.setContentAreaFilled(false);
		btnBegin.setOpaque(true);







	}
}
