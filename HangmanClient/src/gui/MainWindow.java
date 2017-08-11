package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import game.GameLogic;

import java.awt.Dimension;
import java.awt.Event;

import javax.swing.JScrollPane;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JTextArea;

public class MainWindow extends JFrame {

	private JPanel contentPane;
	private static JTextField textField;
	private JScrollPane scrollPane;
	private JLabel lblChatbox;
	private JTextPane textPane;
	private JPanel panel;
	private JPanel panel_1;
	private static JButton btnGuess;
	private JPanel panel_2;
	private static JPanel panel_3;
	private static JTextPane txtpnABC;
	private JTextArea txtrLettersYou;
	private JPanel panel_4;
	private static JPanel panel_5;
	private static JButton btnLetter;
	private JPanel panel_6;
	private JLabel lblSlika;
	private JPanel panel_7;
	private JLabel lblUserVsUser;
	public static List<JButton> listOfButtons = new ArrayList<JButton>();

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		setResizable(false);
		setMinimumSize(new Dimension(800, 480));

		setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/icons/h.png")));
		setTitle("Hangman");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 320);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getScrollPane());
		getScrollPane().setColumnHeaderView(getLblChatbox());
		getScrollPane().setViewportView(getTextPane());
		contentPane.add(getPanel());
		getPanel().add(getPanel_1(), BorderLayout.SOUTH);
		getPanel_1().add(getTextField());
		getPanel_1().add(getBtnGuess());
		getPanel().add(getPanel_2(), BorderLayout.CENTER);
		getPanel_2().add(getPanel_3(), BorderLayout.WEST);
		getPanel_3().add(getTxtpnABC(), BorderLayout.CENTER);
		getPanel_3().add(getTxtrLettersYou(), BorderLayout.NORTH);
		getPanel_2().add(getPanel_4(), BorderLayout.NORTH);
		getPanel_2().add(getPanel_5(), BorderLayout.SOUTH);
		//getPanel_5().add(getBtnLetter());
		GameLogic.insertButtonsForLetters();
		
		getPanel_2().add(getPanel_6(), BorderLayout.CENTER);
		getPanel_6().add(getLblSlika());
		contentPane.add(getPanel_7());
		getPanel_7().add(getUserVsUser());
		
	}

	
	public static void setIconImage(){
		
	}


	public JScrollPane getScrollPane() {

		if(scrollPane == null){
			scrollPane = new JScrollPane();
			scrollPane.setBackground(Color.BLACK);
			scrollPane.setBounds(584, 34, 200, 404);

		}

		return scrollPane;
	}

	public JLabel getLblChatbox() {
		if(lblChatbox == null){
			lblChatbox = new JLabel("CHATBOX");
			lblChatbox.setVisible(true);
			lblChatbox.setForeground(new Color(255, 255, 255));
			lblChatbox.setOpaque(true);
			lblChatbox.setFont(new Font("Arial", Font.BOLD, 14));
			lblChatbox.setBackground(new Color(221, 160, 221));
			lblChatbox.setHorizontalAlignment(SwingConstants.CENTER);
		}

		return lblChatbox;
	}

	public JTextPane getTextPane() {
		if(textPane == null){
			textPane = new JTextPane();
			textPane.setBorder(null);
			textPane.setEditable(false);
			
		}

		return textPane;

	}

	public JPanel getPanel() {
		if(panel == null){
			panel = new JPanel();
			panel.setBounds(10, 34, 564, 404);
			panel.setLayout(new BorderLayout(0, 0));
		}

		return panel;
	}

	public JPanel getPanel_1() {
		if(panel_1 == null){
			panel_1 = new JPanel();
			panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		}

		return panel_1;
	}

	public static JTextField getTextField() {
		if(textField == null){
			textField = new JTextField();
			textField.setFont(new Font("Arial Black", Font.PLAIN, 30));
			textField.setSize(new Dimension(20, 20));
			textField.setMinimumSize(new Dimension(20, 20));			
			textField.setColumns(2);
			
			
			textField.addKeyListener(new KeyAdapter(){
				 public void keyTyped(KeyEvent e) { 
					 
					 if(textField.getText().length() > 0) {
					 		e.consume();
					 }
		               
				            
				 }});
				
			
			
		}
		return textField;
	}
	

	public JButton getBtnGuess() {
		if(btnGuess == null){
			btnGuess = new JButton("GUESS!");
			btnGuess.setBackground(new Color(221, 160, 221));
			btnGuess.setForeground(new Color(255, 255, 255));
			btnGuess.setFont(new Font("Arial", Font.BOLD, 13));
			
			if(textField.getText() == null){
			    btnGuess.setEnabled(false);
			}
			else {
				btnGuess.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
							GUIControler.placeTheLetter();
						
						
					}
				});
			
			}
		}

		return btnGuess;
	}


	public JPanel getPanel_2() {
		if(panel_2 == null){
			panel_2 = new JPanel();
			panel_2.setLayout(new BorderLayout(0, 0));
		}

		return panel_2;
	}

	public JPanel getPanel_3() {
		if(panel_3 ==null){
			panel_3 = new JPanel();
			panel_3.setLayout(new BorderLayout(0,0));
			
			
			
		}

		return panel_3;
	}

	public static JTextPane getTxtpnABC() {
		if(txtpnABC == null){
			txtpnABC = new JTextPane();
			txtpnABC.setForeground(new Color(153, 50, 204));
			txtpnABC.setOpaque(false);
			txtpnABC.setBackground(new Color(255, 255, 255));
			txtpnABC.setFont(new Font("Viner Hand ITC", Font.BOLD, 20));
			txtpnABC.setEditable(false);
			txtpnABC.setText(" ");
			
			
		}

		return txtpnABC;
	}

	public JTextArea getTxtrLettersYou() {
		if(txtrLettersYou == null){
			txtrLettersYou = new JTextArea();
			txtrLettersYou.setForeground(new Color(186, 85, 211));
			txtrLettersYou.setOpaque(false);
			txtrLettersYou.setBackground(new Color(255, 255, 255));
			txtrLettersYou.setEditable(false);
			txtrLettersYou.setFont(new Font("Arial", Font.BOLD, 13));
			txtrLettersYou.setWrapStyleWord(true);
			txtrLettersYou.setLineWrap(true);
			txtrLettersYou.setText("\r\nLetters you got wrong:\r\n");
			
		}

		return txtrLettersYou;
	}

	public JPanel getPanel_4() {
		if(panel_4 == null){
			panel_4 = new JPanel();
			
		}

		return panel_4;
	}

	public static JPanel getPanel_5() {
		if(panel_5 == null){
			panel_5 = new JPanel();
			panel_5.setOpaque(false);
			panel_5.setBackground(new Color(255, 255, 255));
			panel_5.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		}

		return panel_5;
	}

	public static JButton getBtnLetter() {
			btnLetter = new JButton("_");
			btnLetter.setFont(new Font("Arial", Font.PLAIN, 15));
			listOfButtons.add(btnLetter);
			

		return btnLetter;
	}
	
	

	public JPanel getPanel_6() {
		if(panel_6 == null){
			panel_6 = new JPanel();
			panel_6.setBackground(new Color(255, 255, 255));
		}

		return panel_6;
	}


	public JLabel getLblSlika() {
		if(lblSlika == null){
			lblSlika = new JLabel("");
			lblSlika.setPreferredSize(new Dimension(200, 270));
			ImageIcon img = new ImageIcon(MainWindow.class.getResource("/icons/state-0.png"));
			Image img1 = img.getImage();
			Image img2 = img.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
			img = new ImageIcon(img2);
			lblSlika.setIcon(img);
			lblSlika.setSize(300, 300);
			//GUIControler.setHangmanImage();
		}

		return lblSlika;
	}



	public JPanel getPanel_7() {
		if(panel_7 == null){
			panel_7 = new JPanel();
			panel_7.setBounds(10, 11, 774, 23);
			panel_7.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		}

		return panel_7;
	}


	public JLabel getUserVsUser() {
		if(lblUserVsUser == null){
			lblUserVsUser = new JLabel(GUIControler.playerUsername+" VS. "+GUIControler.opponent);
			lblUserVsUser.setForeground(new Color(153, 50, 204));
			lblUserVsUser.setFont(new Font("Arial", Font.BOLD, 15));
			
		}

		return lblUserVsUser;
	}
}
