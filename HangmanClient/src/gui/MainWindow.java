package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Dimension;
import javax.swing.JScrollPane;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTextArea;

public class MainWindow extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		setResizable(false);
		setMinimumSize(new Dimension(800, 480));
		
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:/Users/ValeVale/git/hangmannn/HangmanClient/resources/icons/h.png"));
		setTitle("Hangman");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 320);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBackground(Color.BLACK);
		scrollPane.setBounds(584, 34, 200, 404);
		contentPane.add(scrollPane);
		
		JLabel lblChatbox = new JLabel("CHATBOX");
		lblChatbox.setForeground(new Color(255, 255, 255));
		lblChatbox.setOpaque(true);
		lblChatbox.setFont(new Font("Arial", Font.BOLD, 14));
		lblChatbox.setBackground(new Color(221, 160, 221));
		lblChatbox.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane.setColumnHeaderView(lblChatbox);
		
		JTextPane textPane = new JTextPane();
		textPane.setBorder(null);
		textPane.setEditable(false);
		scrollPane.setViewportView(textPane);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 34, 564, 404);
		contentPane.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		textField = new JTextField();
		textField.setFont(new Font("Arial Black", Font.PLAIN, 30));
		textField.setSize(new Dimension(20, 20));
		textField.setMinimumSize(new Dimension(20, 20));
		panel_1.add(textField);
		textField.setColumns(2);
		
		JButton btnGuess = new JButton("GUESS!");
		btnGuess.setBackground(new Color(221, 160, 221));
		btnGuess.setForeground(new Color(255, 255, 255));
		btnGuess.setFont(new Font("Arial", Font.BOLD, 13));
		panel_1.add(btnGuess);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_3 = new JPanel();
		panel_2.add(panel_3, BorderLayout.WEST);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JTextPane txtpnABC = new JTextPane();
		txtpnABC.setForeground(new Color(153, 50, 204));
		txtpnABC.setOpaque(false);
		txtpnABC.setBackground(new Color(255, 255, 255));
		txtpnABC.setFont(new Font("Viner Hand ITC", Font.BOLD, 20));
		txtpnABC.setEditable(false);
		txtpnABC.setText("A, B, C");
		panel_3.add(txtpnABC, BorderLayout.CENTER);
		
		JTextArea txtrLettersYou = new JTextArea();
		txtrLettersYou.setForeground(new Color(186, 85, 211));
		txtrLettersYou.setOpaque(false);
		txtrLettersYou.setBackground(new Color(255, 255, 255));
		txtrLettersYou.setEditable(false);
		txtrLettersYou.setFont(new Font("Arial", Font.BOLD, 13));
		txtrLettersYou.setWrapStyleWord(true);
		txtrLettersYou.setLineWrap(true);
		txtrLettersYou.setText("\r\nLetters you got wrong:\r\n");
		panel_3.add(txtrLettersYou, BorderLayout.NORTH);
		
		JPanel panel_4 = new JPanel();
		panel_2.add(panel_4, BorderLayout.NORTH);
		
		JPanel panel_5 = new JPanel();
		panel_5.setOpaque(false);
		panel_5.setBackground(new Color(255, 255, 255));
		panel_2.add(panel_5, BorderLayout.SOUTH);
		panel_5.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnA = new JButton("A");
		btnA.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel_5.add(btnA);
		
		JButton btnB = new JButton("B");
		btnB.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel_5.add(btnB);
		
		JButton btnC = new JButton("C");
		btnC.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel_5.add(btnC);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBackground(new Color(255, 255, 255));
		panel_2.add(panel_6, BorderLayout.CENTER);
		
		JLabel lblSlika = new JLabel("");
		lblSlika.setIcon(new ImageIcon("C:\\Users\\Ana\\git\\hangman_client\\HangmanClient\\resources\\icons\\hgmn.jpg"));
		lblSlika.setSize(300, 300);
		panel_6.add(lblSlika);
		
		JPanel panel_7 = new JPanel();
		panel_7.setBounds(10, 11, 774, 23);
		contentPane.add(panel_7);
		panel_7.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblBaskeVsComputer = new JLabel("USER_VAR1 VS. USER_VAR2");
		lblBaskeVsComputer.setForeground(new Color(153, 50, 204));
		lblBaskeVsComputer.setFont(new Font("Arial", Font.BOLD, 15));
		panel_7.add(lblBaskeVsComputer);
	}
}
