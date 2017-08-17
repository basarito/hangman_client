package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import clients.Client;


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
import java.awt.Insets;

import javax.swing.JTextArea;
import java.awt.Rectangle;
import javax.swing.JList;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {

	private JPanel contentPane;
	private static JTextField textField;
	private JScrollPane scrollPane;
	private JLabel lblChatbox;
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
	private JLabel lblCategory;
	private static JLabel lblWord;
	private JScrollPane scrollPane_1;
	private JTextArea txtMessage;
	private JButton btnSend;
	private JList<String> list;
	private JLabel lblTip;
	private JLabel lblResult;
	

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		setResizable(false);
		setMinimumSize(new Dimension(800, 480));
		
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				GUIControler.closeApp3();
			}
		});

		setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/icons/h.png")));
		setTitle("Hangman");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 320);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getScrollPane());
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
		//GameLogic.insertButtonsForLetters();
		
		getPanel_2().add(getPanel_6(), BorderLayout.CENTER);
		getPanel_6().add(getLblSlika());
		contentPane.add(getPanel_7());
		getPanel_4().add(getlblResult());
		getPanel_4().add(getLblCategory());
		contentPane.add(getScrollPane_1_1());
		contentPane.add(getBtnSend());
		getLblCategory().setVisible(false);
		getlblResult().setVisible(false);
	}

	
	public static void setIconImage(){
		
	}
	
	

	public JLabel getLblWord() {
		if (lblWord==null){
			lblWord = new JLabel();
			lblWord.setPreferredSize(new Dimension(200, 30));
			lblWord.setFont(new Font("Arial", Font.BOLD, 20));
		}
		return lblWord;
	}

	public JScrollPane getScrollPane() {

		if(scrollPane == null){
			scrollPane = new JScrollPane();
			scrollPane.setBorder(null);
			scrollPane.setBackground(Color.BLACK);
			scrollPane.setBounds(584, 11, 200, 351);
			scrollPane.setColumnHeaderView(getLblChatbox());
			//scrollPane.setViewportView(getChatbox());
			scrollPane.setViewportView(getList());
			
			scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {  
		        public void adjustmentValueChanged(AdjustmentEvent e) { 
		        	if (GUIControler.messageAdded) {
		        		e.getAdjustable().setValue(e.getAdjustable().getMaximum()); 
		        		GUIControler.messageAdded=false;
		        	}
		        }
		    });

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
			textField = new JTextField(){
				
			};
			textField.setFont(new Font("Arial Black", Font.PLAIN, 20));
			textField.setSize(new Dimension(20, 20));
			textField.setMinimumSize(new Dimension(20, 20));
			textField.setMaximumSize(new Dimension(100, 20));
			textField.setColumns(3);
			
			
			
			textField.addKeyListener(new KeyAdapter(){
				 public void keyTyped(KeyEvent e) { 
					 
					 if(textField.getText().length() > 0) {
					 		e.consume();
					 }
		               
				            
				 }
				 @Override
					public void keyPressed(KeyEvent e) {
						if(e.getKeyCode() == KeyEvent.VK_ENTER){
							if(textField.getText()!=null && textField.getText()!=""){
								GUIControler.placeTheLetter();
							}
						}
					}
});
			

			
				
			
			
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
							getTextField().requestFocusInWindow();
						
						
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
			panel_4.setPreferredSize(new Dimension(10, 20));
			panel_4.setLayout(null);
			
		
			
		}

		return panel_4;
	}
	
	public JLabel getlblResult(){
		if(lblResult == null){
			lblResult = new JLabel();
			lblResult.setHorizontalAlignment(SwingConstants.RIGHT);
			lblResult.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 13));
			lblResult.setForeground(new Color(0, 0, 0));
			
			lblResult.setBounds(433, 0, 121, 20);
		}
		return lblResult;
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
			btnLetter = new JButton("__");
			btnLetter.setBorderPainted( false );
			btnLetter.setFocusPainted( false );
			btnLetter.setContentAreaFilled( false );
			btnLetter.setFont(new Font("Arial Black", Font.PLAIN, 20));
			btnLetter.setMargin(new Insets(0, 0, 0, 0));
			btnLetter.setVisible(true);
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
			panel_7.setLayout(null);
			panel_7.add(getUserVsUser());
		}

		return panel_7;
	}


	public JLabel getUserVsUser() {
		if(lblUserVsUser == null){
			lblUserVsUser = new JLabel(Client.getUsername()+" VS. "+Client.getOpponent());
			lblUserVsUser.setHorizontalAlignment(SwingConstants.CENTER);
			lblUserVsUser.setBounds(103, 0, 461, 22);
			lblUserVsUser.setForeground(new Color(153, 50, 204));
			lblUserVsUser.setFont(new Font("Arial", Font.BOLD, 15));
			
		}

		return lblUserVsUser;
	}
	public JLabel getLblCategory() {
		if (lblCategory == null) {
			lblCategory = new JLabel("Category: ");
			lblCategory.setHorizontalAlignment(SwingConstants.LEFT);
			lblCategory.setBounds(104, 0, 205, 17);
			lblCategory.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 13));
			lblCategory.setForeground(new Color(0, 0, 0));
			lblUserVsUser.setFont(new Font("Arial", Font.BOLD, 18));
		}
		return lblCategory;
	}
	
	
	/***************CHATBOX**********************/
	
//	public JTextArea getChatbox() {
//		if (textArea == null) {
//			textArea = new JTextArea();
//			textArea.setWrapStyleWord(true);
//			textArea.setLineWrap(true);
//			textArea.setForeground(Color.WHITE);
//			textArea.setBackground(Color.DARK_GRAY);
//		}
//		return textArea;
//	}
	
	
	public JScrollPane getScrollPane_1_1() {
		if (scrollPane_1 == null) {
			scrollPane_1 = new JScrollPane();
			scrollPane_1.setBounds(584, 362, 200, 56);
			scrollPane_1.setViewportView(getMessage());
		}
		return scrollPane_1;
	}

	public JTextArea getMessage() {
		if (txtMessage == null) {
			txtMessage = new JTextArea();
			txtMessage.setFont(new Font("Monospaced", Font.PLAIN, 11));
			txtMessage.setLineWrap(true);
			txtMessage.setWrapStyleWord(true);
			txtMessage.setText("Write your message here...");
			txtMessage.setForeground(new Color(216, 191, 216));
			txtMessage.setMargin(new Insets(5, 5, 5, 5));
			
			txtMessage.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					txtMessage.setText("");
					txtMessage.setForeground(new Color(0, 0, 0));
				}
			});	
			
			txtMessage.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode() == KeyEvent.VK_ENTER){
						String message = getMessage().getText();
						if(!message.equals("")) {
							Client.sendMessage(message);
							GUIControler.addMessage(Client.getUsername(), message);
						}
						txtMessage.setText("Write your message here...");
						txtMessage.setForeground(new Color(216, 191, 216)); 
						
					}
				}
			});
		}
		return txtMessage;
	}
	private JButton getBtnSend() {
		if (btnSend == null) {
			btnSend = new JButton("SEND");
			btnSend.setBackground(new Color(221, 160, 221));
			btnSend.setForeground(Color.WHITE);
			btnSend.setFont(new Font("Arial", Font.BOLD, 13));
			btnSend.setBounds(584, 417, 200, 23);
			
			btnSend.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					String message = getMessage().getText();
					if(!message.equals("")) {
						Client.sendMessage(message);
						GUIControler.addMessage(Client.getUsername(), message);
					}
					txtMessage.setText("Write your message here...");
					txtMessage.setForeground(new Color(216, 191, 216)); 
					
				}
			});
			
		}
		return btnSend;
	}
	private JList getList() {
		if (list == null) {
			list = new JList(Client.chatHistory);
			
//			getScrollPane().getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {  
//		        public void adjustmentValueChanged(AdjustmentEvent e) {  
//		            e.getAdjustable().setValue(e.getAdjustable().getMaximum());  
//		        }
//		    });
			
			list.setBackground(Color.DARK_GRAY);
			
			//custom rendering of cells:
			list.setCellRenderer(new MyCellRenderer());

		    ComponentListener l = new ComponentAdapter() {
		        @Override
		        public void componentResized(ComponentEvent e) {
		            // for core: force cache invalidation by temporarily setting fixed height
		            list.setFixedCellHeight(10);
		            list.setFixedCellHeight(-1);
		        }
		    };
		    list.addComponentListener(l);			
		}
		return list;
	}


	public JLabel getLblTip() {
		if (lblTip==null){
			lblTip= new JLabel();
			lblTip.setPreferredSize(new Dimension(300, 10));
			lblTip.setFont(new Font("Arial", Font.ITALIC, 10));
		}
		return lblTip;
	}
}
