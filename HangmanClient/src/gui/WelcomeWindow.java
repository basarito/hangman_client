package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import clients.Client;

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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@SuppressWarnings("serial")
public class WelcomeWindow extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPanel main;
	private JPanel north;
	private JPanel center;
	private JTextArea txtrWelcomeToHangman;
	private JTextArea txtrPleaseEnter;
	private JPanel south;
	private JTextArea txtruseOnlyLetters;
	private JButton btnBegin;

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
		contentPane.add(getMain(), BorderLayout.CENTER);
		getMain().add(getNorth(), BorderLayout.NORTH);
		getMain().add(getCenter(), BorderLayout.SOUTH);
		getCenter().add(getTxtrWelcomeToHangman());
		getCenter().add(getTxtrPleaseEnter());
		contentPane.add(getSouth(), BorderLayout.SOUTH);
		getSouth().add(getTxtruseOnlyLetters());
		getSouth().add(getTextField());
		getSouth().add(getBtnBegin());
	}

	public JPanel getMain() {
		if(main == null){
			main = new JPanel();
			main.setLayout(new BorderLayout(0, 0));
		}

		return main;
	}

	public JPanel getNorth() {
		if(north == null){
			north = new JPanel();
			north.setPreferredSize(new Dimension(140, 40));
		}

		return north;
	}

	public JPanel getCenter() {
		if(center == null){
			center = new JPanel();
			center.setPreferredSize(new Dimension(140, 110));
			center.setLayout(null);
		}

		return center;
	}

	public JTextArea getTxtrWelcomeToHangman() {
		if(txtrWelcomeToHangman == null){

			txtrWelcomeToHangman = new JTextArea();
			txtrWelcomeToHangman.setEditable(false);
			txtrWelcomeToHangman.setBounds(124, 0, 173, 60);
			txtrWelcomeToHangman.setForeground(new Color(153, 50, 204));
			txtrWelcomeToHangman.setFont(new Font("Arial", Font.BOLD, 25));
			txtrWelcomeToHangman.setBackground(SystemColor.control);
			txtrWelcomeToHangman.setText("WELCOME TO \r\n   HANGMAN!");

		}

		return txtrWelcomeToHangman;
	}

	public JTextArea getTxtrPleaseEnter() {
		if(txtrPleaseEnter == null){
			txtrPleaseEnter = new JTextArea();
			txtrPleaseEnter.setEditable(false);
			txtrPleaseEnter.setBounds(77, 89, 282, 21);
			txtrPleaseEnter.setForeground(new Color(153, 50, 204));
			txtrPleaseEnter.setFont(new Font("Arial", Font.BOLD, 14));
			txtrPleaseEnter.setBackground(SystemColor.control);
			txtrPleaseEnter.setText("Please enter a unique username below.");
		}

		return txtrPleaseEnter;
	}

	public JPanel getSouth() {
		if(south == null){
			south = new JPanel();
			south.setPreferredSize(new Dimension(140, 120));
			south.setLayout(null);
		}

		return south;
	}

	public JTextArea getTxtruseOnlyLetters() {
		if(txtruseOnlyLetters == null){
			txtruseOnlyLetters = new JTextArea();
			txtruseOnlyLetters.setEditable(false);
			txtruseOnlyLetters.setBounds(97, 0, 230, 19);
			txtruseOnlyLetters.setForeground(new Color(153, 50, 204));
			txtruseOnlyLetters.setFont(new Font("Arial", Font.PLAIN, 12));
			txtruseOnlyLetters.setBackground(SystemColor.control);
			txtruseOnlyLetters.setText("(Use only letters a-z and/or numbers 0-9)");	
		}		
		return txtruseOnlyLetters;
	}

	public JTextField getTextField() {
		if(textField == null){
			textField = new JTextField();
			textField.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode() == KeyEvent.VK_ENTER){
						String name=getTextField().getText().toLowerCase();
						if(GUIControler.validateUsernameLocally(name)) {
							Client.sendUsernameToServer(name);
						}
					}
				}
			});
			textField.setPreferredSize(new Dimension(6, 30));
			textField.setBounds(92, 30, 246, 28);
			textField.setColumns(50);
		}
		return textField;
	}

	public JButton getBtnBegin() {
		if(btnBegin == null){
			btnBegin = new JButton("BEGIN");
			btnBegin.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					String name=getTextField().getText().toLowerCase();
					if(GUIControler.validateUsernameLocally(name)) {
						Client.sendUsernameToServer(name);
					}
				}
			});
			btnBegin.setBounds(160, 78, 100, 31);
			btnBegin.setAlignmentX(Component.CENTER_ALIGNMENT);
			btnBegin.setForeground(Color.WHITE);
			btnBegin.setFont(new Font("Arial", Font.BOLD, 16));
			btnBegin.setBackground(new Color(221, 160, 221));
			btnBegin.setContentAreaFilled(false);
			btnBegin.setOpaque(true);
		}

		return btnBegin;
	}
}
