package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.server.ServerCloneException;
import java.util.Random;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.plaf.basic.BasicTreeUI.SelectionModelPropertyChangeHandler;

import clients.Client;


public class GUIControler extends Thread {

	private static WelcomeWindow welcomeWindow;
	private static ConnectingWindow connectingWindow ;
	private static MainWindow mainWindow;

	static JDialog dialog = null;
	static JDialog timeoutDialog = null;
	static boolean answered = false;

	//public static boolean goodbye = false;
	public static String word="";
	public static String category="";
	public static String newW=null;
	public static String letter;
	public static int errorCount=0;
	public static int lettersCorrect=0;

	public static boolean acceptedGame = false;

	static String usernameToValidate = "";
	static String tryOpponent = "";
	private static JDialog dialogForWord;


	@Override 
	public void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					welcomeWindow = new WelcomeWindow();
					welcomeWindow.setVisible(true);
					welcomeWindow.setLocationRelativeTo(null);
					welcomeWindow.getTextField().requestFocusInWindow();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//Closing WelcomeWindow
	public static void closeApp1() {
		int option = JOptionPane.showConfirmDialog(welcomeWindow.getContentPane(), "Are you sure you want to close the game?",
				"Closing app", JOptionPane.YES_NO_OPTION);

		if (option == JOptionPane.YES_OPTION) {
			Client.sendExitSignal();
			System.exit(0);
		} else if(option == JOptionPane.NO_OPTION){
			welcomeWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		}
	}

	//Closing ConnectingWindow
	public static void closeApp2() {
		int option = JOptionPane.showConfirmDialog(connectingWindow.getContentPane(), "Are you sure you want to close the game?",
				"Closing app", JOptionPane.YES_NO_OPTION);

		if (option == JOptionPane.YES_OPTION) {
			Client.sendExitSignal();
			System.exit(0);
		} else if(option == JOptionPane.NO_OPTION){
			connectingWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		}
	}

	//Closing MainWindow
	public static void closeApp3() {
		int option = JOptionPane.showConfirmDialog(mainWindow.getContentPane(), "Are you sure you want to close the game?",
				"Closing app", JOptionPane.YES_NO_OPTION);

		if (option == JOptionPane.YES_OPTION) {
			Client.sendExitSignal();
			System.exit(0);
		} else if(option == JOptionPane.NO_OPTION){
			mainWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		}

	}

	public static void showConnectingWindow() {
		connectingWindow = new ConnectingWindow();
		connectingWindow.setLocationRelativeTo(welcomeWindow);		
		welcomeWindow.setVisible(false);
		connectingWindow.setVisible(true);
	}

	//Username validation	
	public static boolean validateUsernameLocally(String username) {
		usernameToValidate =username;
		boolean valid=false;
		if (username.isEmpty()) {
			JOptionPane.showMessageDialog(welcomeWindow, "You have to enter a username!", "Try again :(", JOptionPane.ERROR_MESSAGE);
		} else if(!username.matches("[A-Za-z0-9]+")) {
			JOptionPane.showMessageDialog(welcomeWindow, "Incorrect username. Please use only letters a-z and/or numbers 0-9", "Try again :(", JOptionPane.ERROR_MESSAGE);

		} else if(username.length()>10) {
			JOptionPane.showMessageDialog(welcomeWindow, "Username too long. Please use up to 10 characters.", "Try again :(", JOptionPane.ERROR_MESSAGE);
		} else {
			valid=true;
		}
		return valid;
	}		
	public static void validateUsernameFromServer(String msg) {
		if (!msg.equals("OK")) {
			JOptionPane.showMessageDialog(welcomeWindow, "Username already taken. Please choose a different one.", "Try again :(", JOptionPane.ERROR_MESSAGE);			
		} else {
			Client.setUsername(usernameToValidate);
			System.out.println("******"+Client.getUsername().toUpperCase()+"******");
			showConnectingWindow();
		}

	}	

	//Personalized welcome message 
	public static JLabel welcomeUser() {
		return new JLabel("Welcome, " + Client.getUsername() + "!");
	}

	//Select user from the list to send invite
	public static void choose(String user) {
		if(Client.activeGames.contains(user)) {
			JOptionPane.showMessageDialog(connectingWindow, user+" is already playing a game. Try a different user or try again later.",
					"User unavailable", JOptionPane.ERROR_MESSAGE);
			return;
		}
		int option = JOptionPane.showConfirmDialog(connectingWindow.getContentPane(), "Are you sure you want to play with "+user+ " ?",
				"Connecting", JOptionPane.YES_NO_OPTION);
		tryOpponent = user;

		if(option == JOptionPane.YES_OPTION){

			//loading screen:
			dialog = new JDialog();
			JLabel label = new JLabel("Sending invite to "+tryOpponent+"...", JLabel.CENTER);
			dialog.setIconImage(Toolkit.getDefaultToolkit().getImage(ConnectingWindow.class.getResource("/icons/h.png")));
			dialog.setTitle("Please Wait...");
			dialog.add(label);
			dialog.setPreferredSize(new Dimension(200, 90));
			dialog.setResizable(false);
			dialog.pack();
			dialog.setLocationRelativeTo(connectingWindow);
			dialog.setVisible(true);
			connectingWindow.setEnabled(false);

			dialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

			Client.inviteUserToPlay(tryOpponent);
		} else {
			SwingUtilities.updateComponentTreeUI(connectingWindow);
		}
	}

	//Receive and handle response to invite
	public static void receiveResponseToInvite(String name, String response) {
		//		if(response.equals("BUSY")) {
		//			dialog.setVisible(false);
		//			connectingWindow.setEnabled(true);
		//			JOptionPane.showMessageDialog(connectingWindow, "User "+name+" is already playing a game. Try a different user or try again later.",
		//					"Connection failed", JOptionPane.ERROR_MESSAGE);
		//		}
		if(response.equals("ACCEPTED")) {			
			Client.setOpponent(name);
			//Client.changeGameStatus("true");
			dialog.setVisible(false);
			Client.sentRequestForGame=1;
			startGame();
		}
		else if(response.equals("REJECTED")) {
			dialog.setVisible(false);
			connectingWindow.setEnabled(true);
			JOptionPane.showMessageDialog(connectingWindow, "Connection to "+name+" was unsuccessful or they rejected your invite. Try a different user.",
					"Connection failed", JOptionPane.ERROR_MESSAGE);
		} else {
			dialog.setVisible(false);
			connectingWindow.setEnabled(true);
		}
	}

	//Random button functionality 
	public static void chooseRandom() {
		if(Client.onlineLista.isEmpty() || (Client.onlineLista.size()==Client.activeGames.size())){
			JOptionPane.showMessageDialog(connectingWindow, "There are no available players at the moment!");						
		}else{
			Random randomizer = new Random();
			String random = "";

			while(true) {
				random = Client.onlineLista.get(randomizer.nextInt(Client.onlineLista.size()));
				if(Client.activeGames.contains(random)) 
					continue;
				else 
					break;
			}

			int option = JOptionPane.showConfirmDialog(connectingWindow.getContentPane(), random+" is available. Do you want to play with them? ",
					"Connecting", JOptionPane.YES_NO_OPTION);


			if(option == JOptionPane.YES_OPTION){
				tryOpponent = random;
				dialog = new JDialog();
				JLabel label = new JLabel("Sending invite to "+tryOpponent+"...", JLabel.CENTER);
				dialog.setIconImage(Toolkit.getDefaultToolkit().getImage(ConnectingWindow.class.getResource("/icons/h.png")));
				dialog.setTitle("Please Wait...");
				dialog.add(label);
				dialog.setPreferredSize(new Dimension(200, 90));
				dialog.setResizable(false);
				dialog.pack();
				dialog.setLocationRelativeTo(connectingWindow);
				dialog.setVisible(true);
				connectingWindow.setEnabled(false);
				Client.inviteUserToPlay(tryOpponent);
			}else{
				SwingUtilities.updateComponentTreeUI(connectingWindow);
			}
		}	
	} 

	public static void updateTable() {
		connectingWindow.refreshTable();
	}	

	// ******************** game logic methods ***************** 


	public static void setHangmanImage(String imgPath){

		ImageIcon img = new ImageIcon(MainWindow.class.getResource(imgPath));
		Image img1 = img.getImage();
		Image img2 = img1.getScaledInstance(250, 250, Image.SCALE_SMOOTH);
		img = new ImageIcon(img2);
		mainWindow.getLblSlika().setIcon(img);


	} 	

	public static void placeTheLetter() {
		letter = MainWindow.getTextField().getText().toLowerCase();
		MainWindow.getTextField().setText("");
		if(letter.matches("[a-z]")) {


			String w =word.toLowerCase();
			if (!(w.contains(letter)) || (newW!=null && numberOfLettersInAWord(newW, letter)==numberOfLettersInAWord(w, letter))){
				MainWindow.getTxtpnABC().setText(MainWindow.getTxtpnABC().getText()+letter +"\n");
				errorCount++;
				switch (errorCount) {
				case 1: 
					setHangmanImage("/icons/state-1.png");
					Client.changeHangmanPictureSignal("/icons/state-1.png", Client.getOpponent());
					Client.changeWrongLettersSignal(letter, Client.getOpponent());
					break;

				case 2: 
					setHangmanImage("/icons/state-2.png");
					Client.changeHangmanPictureSignal("/icons/state-2.png", Client.getOpponent());
					Client.changeWrongLettersSignal(letter, Client.getOpponent());
					break;
				case 3:
					setHangmanImage("/icons/state-3.png");
					Client.changeHangmanPictureSignal("/icons/state-3.png", Client.getOpponent());
					Client.changeWrongLettersSignal(letter, Client.getOpponent());
					break;

				case 4:
					setHangmanImage("/icons/state-4.png");
					Client.changeHangmanPictureSignal("/icons/state-4.png", Client.getOpponent());
					Client.changeWrongLettersSignal(letter, Client.getOpponent());
					break;
				case 5:
					setHangmanImage("/icons/state-5.png");
					Client.changeHangmanPictureSignal("/icons/state-5.png", Client.getOpponent());
					Client.changeWrongLettersSignal(letter, Client.getOpponent());
					break;
				case 6:
					setHangmanImage("/icons/state-6.png");
					Client.changeHangmanPictureSignal("/icons/state-6.png", Client.getOpponent());
					Client.changeWrongLettersSignal(letter, Client.getOpponent());
					errorCount=0; lettersCorrect=0; newW=null; mainWindow.listOfButtons.clear();
					JOptionPane.showMessageDialog(null, "You haven't gueesed the word. \n It's your turn to set a word for "+Client.getOpponent()+".", "Status ", JOptionPane.INFORMATION_MESSAGE);
					Client.sendGameStatusWindow(Client.getOpponent(), 0+"" , 0+"");
					//Client.sentRequestForGame=1;
					startGame();
					Client.switchOpponentMainWindow(Client.getOpponent());
					break;
				default : break;
				}
			}
			else {
				for (int i=0; i<w.length(); i++){

					if (letter.charAt(0)==w.charAt(i)){
						MainWindow.listOfButtons.get(i).setText(letter.toUpperCase());
						Client.changeRigthLetterSignal(letter, Client.getOpponent(), i+"");
						
						newW=newW+letter;
						lettersCorrect++;

					}
				}
				if(lettersCorrect==w.length()){
					Client.sendGameStatusWindow(Client.getOpponent(), 0+"" , 1+"");
					errorCount=0; lettersCorrect=0; newW=null;  mainWindow.listOfButtons.clear();
					JOptionPane.showMessageDialog(null, "You guessed the word. \n It's your turn to set a word for "+Client.getOpponent()+".", "Status", JOptionPane.INFORMATION_MESSAGE);
					startGame();
					Client.switchOpponentMainWindow(Client.getOpponent());
				}

			}


		}
		else {
			JOptionPane.showMessageDialog(null, "Enter a letter", "Error", JOptionPane.ERROR_MESSAGE );
		}


	}

	public static int numberOfLettersInAWord(String word, String l) {
		int count=0;
		for(int i=0; i<word.length(); i++){
			if(word.charAt(i)==l.charAt(0)){
				count++;
			}
		}
		return count;

	}



	/******Connecting two players in a game**************/


	public static void receiveInvite(String name) {
		JOptionPane pane = null;
		answered=false;

		Timer timer = new Timer(7000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!answered) {
					timeoutDialog.setVisible(false);
					answered=false;
					Client.rejectInvite(name);
					//System.out.println("timer went off");
					return;
				}
			}
		});
		timer.start();
		timer.setRepeats(false);

		pane = new JOptionPane(name+" has invited you to play with them. Do you want to accept?", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
		timeoutDialog = pane.createDialog(connectingWindow.getContentPane(), "Received an invitation!");
		timeoutDialog.setVisible(true);

		try {
			Object selectedValue = pane.getValue();
			int value = 0;

			if(selectedValue == null)
				value = JOptionPane.CLOSED_OPTION;      
			else
				value = Integer.parseInt(selectedValue.toString());

			if(value == JOptionPane.YES_OPTION) {
				answered=true;
				Client.acceptInvite(name);
			} else if (value == JOptionPane.NO_OPTION) {
				answered=true;
				Client.rejectInvite(name);
				//System.out.println("clicked no");
			} else if (value == JOptionPane.CLOSED_OPTION) {
				answered=true;
				Client.rejectInvite(name);
				//System.out.println("clicked X");
			}
		} catch (NumberFormatException e1) {
			answered=false;
			//System.out.println("did nothing");
			return;
		} catch (Exception e) {
			return;
		}
	}

	public static void startGame() {
		if(mainWindow!=null){
			mainWindow.setVisible(false);
			mainWindow= new MainWindow();
			mainWindow.getLblWord().setText("");
			mainWindow.getTxtpnABC().setText("");
			mainWindow.getPanel_5().removeAll();
		}
		
		else {
			mainWindow = new MainWindow();
			mainWindow.setLocationRelativeTo(connectingWindow);	 
			connectingWindow.setVisible(false);
		}
		
		mainWindow.getBtnGuess().setVisible(false);
		mainWindow.getTextField().setVisible(false);
		mainWindow.setVisible(true);

		if(Client.sentRequestForGame==1) {
			waitingMainWindow();
		} else {
			givingWordMainWindow();
		}

	}

	private static void waitingMainWindow() {
		//loading screen:
		dialogForWord = new JDialog();
		JLabel label = new JLabel("Waiting on opponent to set the word to guess...", JLabel.CENTER);
		dialogForWord.setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/icons/h.png")));
		dialogForWord.setTitle("Please Wait...");
		dialogForWord.add(label);
		dialogForWord.setPreferredSize(new Dimension(200, 90));
		dialogForWord.setResizable(false);
		dialogForWord.pack();
		dialogForWord.setLocationRelativeTo(mainWindow);
		dialogForWord.setVisible(true);
		mainWindow.setEnabled(false);
	}

		
	private static void givingWordMainWindow() {
		String w="";
		String[] options = {"OK"};
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(80, 50));
		JLabel lbl = new JLabel("Enter a word: ");
		JTextField txt = new JTextField(15);
		panel.add(lbl);
		panel.add(txt);


		do {
			int selectedOption = JOptionPane.showOptionDialog(mainWindow, panel, "It's your turn to give a word", JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE, null, options , null);

			if(selectedOption==JOptionPane.CLOSED_OPTION){
				int option = JOptionPane.showConfirmDialog(mainWindow, "Are you sure you want to quit the game?",
						"Leaving the game", JOptionPane.YES_NO_OPTION);

				if (option == JOptionPane.YES_OPTION) {
					Client.sendQuitTheGameSignal(Client.getOpponent());
					System.out.println("quit signal sent");
					connectingWindow.setVisible(true);
					connectingWindow.setLocationRelativeTo(mainWindow);
					connectingWindow.setEnabled(true);
					mainWindow.setVisible(false);
					return;
				}
			}
			if(selectedOption==0){   
				w = txt.getText();
				if(w.equals("")){
					JOptionPane.showMessageDialog(mainWindow, "You have to type something!", "Not a word", JOptionPane.ERROR_MESSAGE);
				}else if(!w.matches("[A-Za-z]+")){
					JOptionPane.showMessageDialog(mainWindow, "Use only a-z caracters!", "Not a word", JOptionPane.ERROR_MESSAGE);
					txt.setText("");
				}else{
					word=w;
					break;
				}

			}

		} while (true);
							
		
		
		//KATEGORIJU BI TREBALO ODVOJITI KAO POSEBNU METODU
		
		String c="";
		lbl.setText("Enter word category");
		txt.setText("");

		do {
			int selectedOption1 = JOptionPane.showOptionDialog(mainWindow, panel, "Now give us word category!", JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options , null);
			if(selectedOption1==JOptionPane.CLOSED_OPTION){
				int option = JOptionPane.showConfirmDialog(mainWindow, "Are you sure you want to quit the game?",
						"Leaving the game", JOptionPane.YES_NO_OPTION);

				if (option == JOptionPane.YES_OPTION) {
					Client.sendQuitTheGameSignal(Client.getOpponent());
					connectingWindow.setVisible(true);
					connectingWindow.setLocationRelativeTo(mainWindow);
					connectingWindow.setEnabled(true);
					mainWindow.setVisible(false);
					return;
				}
			}
			if(selectedOption1==0){   
				c = txt.getText();
				if(c.equals("") || c.equals(" ")){
					JOptionPane.showMessageDialog(mainWindow, "You have to type something!", "Not a word", JOptionPane.ERROR_MESSAGE);
				}else if(!c.matches("[A-Za-z ]+")){
					JOptionPane.showMessageDialog(mainWindow, "Use only a-z caracters!", "Not a word", JOptionPane.ERROR_MESSAGE);
				}else{
					break;
				}
			}
		} while (true);


		if(c!=null){
			category = c;
			Client.sendWordSetSignal(Client.getOpponent(), word, category);
			setOpponentMainWindow();
			
		}
		
	}


	//		if (Client.sentRequestForGame==1) { //wait for opponent to tell a word
	//			dialogForWord = new JDialog();
	//			JLabel label = new JLabel("Waiting on opponent to set the word to guess...");
	//			dialogForWord.setIconImage(Toolkit.getDefaultToolkit().getImage(ConnectingWindow.class.getResource("/icons/h.png")));
	//			dialogForWord.setTitle("Please Wait...");
	//			dialogForWord.add(label);
	//			dialogForWord.setPreferredSize(new Dimension(400, 90));
	//			dialogForWord.pack();
	//			dialogForWord.setLocationRelativeTo(mainWindow);
	//			dialogForWord.setVisible(true);
	//
	//		}
	//		else {
	//			String w="";
	//			String[] options = {"OK"};
	//			JPanel panel = new JPanel();
	//			panel.setPreferredSize(new Dimension(80, 50));
	//			JLabel lbl = new JLabel("Enter a word: ");
	//			JTextField txt = new JTextField(15);
	//			panel.add(lbl);
	//			panel.add(txt);
	//			
	//			do {
	//
	//				selectedOption = JOptionPane.showOptionDialog(mainWindow, panel, "It's your turn to give a word!", JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options , options[0]);
	//				if(selectedOption==JOptionPane.CLOSED_OPTION){
	//					int option = JOptionPane.showConfirmDialog(mainWindow, "Are you sure you want to quit the game?",
	//							"Leaving the game", JOptionPane.YES_NO_OPTION);
	//
	//					if (option == JOptionPane.YES_OPTION) {
	//						connectingWindow.setVisible(true);
	//						connectingWindow.setLocationRelativeTo(mainWindow);
	//						connectingWindow.setEnabled(true);
	//						mainWindow.setVisible(false);
	//						Client.sendQuitTheGameSignal(Client.getOpponent());
	//						return;
	//						
	//					}
	//				} 
	//
	//				if(selectedOption==0){   
	//					w = txt.getText();
	//					if(w.equals("")){
	//						JOptionPane.showMessageDialog(mainWindow, "You have to type something!", "Not a word", JOptionPane.ERROR_MESSAGE);
	//					}else if(!w.matches("[A-Za-z]+")){
	//						JOptionPane.showMessageDialog(mainWindow, "Use only a-z caracters!", "Not a word", JOptionPane.ERROR_MESSAGE);
	//					}else{
	//						break;
	//					}
	//				}
	//			} while (true);
	//							
	//			
	//			if(w!=null){
	//				word=w;
	//				String c="";
	//				lbl.setText("Enter word category");
	//				txt.setText("");
	//				
	//				do {
	//
	//					selectedOption1 = JOptionPane.showOptionDialog(mainWindow, panel, "Now give us word category!", JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options , options[0]);
	//					if(selectedOption1==JOptionPane.CLOSED_OPTION){
	//						int option = JOptionPane.showConfirmDialog(mainWindow, "Are you sure you want to quit the game?",
	//								"Leaving the game", JOptionPane.YES_NO_OPTION);
	//
	//						if (option == JOptionPane.YES_OPTION) {
	//							connectingWindow.setVisible(true);
	//							connectingWindow.setLocationRelativeTo(mainWindow);
	//							connectingWindow.setEnabled(true);
	//							mainWindow.setVisible(false);
	//							Client.sendQuitTheGameSignal(Client.getOpponent());
	//							return;
	//						}
	//					}
	//					if(selectedOption1==0){   
	//						c = txt.getText();
	//						if(c.equals("") || c.equals(" ")){
	//							JOptionPane.showMessageDialog(mainWindow, "You have to type something!", "Not a word", JOptionPane.ERROR_MESSAGE);
	//						}else if(!c.matches("[A-Za-z ]+")){
	//							JOptionPane.showMessageDialog(mainWindow, "Use only a-z caracters!", "Not a word", JOptionPane.ERROR_MESSAGE);
	//						}else{
	//							break;
	//						}
	//					}
	//				} while (true);
	//				
	//				
	//				if(c!=null){
	//					category = c;
	//					Client.sendWordSetSignal(Client.getOpponent(), word, category);
	//					setOpponentMainWindow();
	//				}
	//			}
	//
	//
	//		}


	public static void receiveSignalWordSet(String w, String c) {

		dialogForWord.setVisible(false);
		setPlayerMainWindow(w, c);
	}

	public static void setPlayerMainWindow(String w, String c){ 

		dialogForWord.setVisible(false);
		mainWindow.setEnabled(true);
		word=w;
		category=c;
		mainWindow.getBtnGuess().setVisible(true);
		mainWindow.getTextField().setVisible(true);
		for (int i=0; i<word.length(); i++) {
			mainWindow.getPanel_5().add(mainWindow.getBtnLetter());
			mainWindow.getPanel_5().revalidate();
			mainWindow.getPanel_5().repaint();

		} 

		mainWindow.getPanel_1().revalidate();
		mainWindow.getLblCategory().setVisible(true);
		mainWindow.getLblCategory().setText(mainWindow.getLblCategory().getText()+" "+category);

	}

	public static void setOpponentMainWindow() { //samo rec umesto guess i dugmica

		mainWindow.remove(mainWindow.getBtnGuess());
		mainWindow.remove(mainWindow.getTextField());
		//mainWindow.getTextField().setText(word);
		//mainWindow.getTextField().setEditable(false);
		mainWindow.getLblWord().setText(word.toLowerCase()); 
		mainWindow.getLblWord().setHorizontalAlignment(SwingConstants.CENTER);
		mainWindow.getLblTip().setText("*Capital letters in word represent ones opponent has guessed.");
		mainWindow.getPanel_5().add(mainWindow.getLblWord());
		mainWindow.getPanel_5().revalidate();
		mainWindow.getPanel_5().repaint();
		mainWindow.getPanel_1().add(mainWindow.getLblTip());
		mainWindow.getPanel_1().revalidate();
		mainWindow.getPanel_1().repaint();
		mainWindow.getLblCategory().setVisible(true);
		mainWindow.getLblCategory().setText(mainWindow.getLblCategory().getText()+" "+category);


	}


	public static void receiveSignalHnagmanPicChanged(String url) {
		setHangmanImage(url);;

	}

	public static void receiveSignalWrongLetter(String letter) {
		mainWindow.getTxtpnABC().setText(MainWindow.getTxtpnABC().getText()+letter +"\n");

	}

	public static void receiveSignalRightLetter(String letter, String index) {
		String w= mainWindow.getLblWord().getText();
	
		w =  w.replace(w.charAt(Integer.parseInt(index))+"", letter.toUpperCase()) ;
		mainWindow.getLblWord().setText(w);
				
			
		

	}



	/**************CHATBOX********************/

	public static void addMessage(String username, String message) {
		String newMsg = username + ":\n" + message;
		Client.chatHistory.addElement(newMsg);		
	}

	//	public static void showMessage(String username, String message) {
	//		chatMessages += username + ":\n" + message + "\n\n";
	//		if(username.equals(Client.getUsername())) {
	//			mainWindow.getChatbox().setText(chatMessages);
	//		}
	//		
	//	}


	public static void recieveQuitTheGameSignal(String name) {
		dialogForWord.setVisible(false);
		JOptionPane.showMessageDialog(mainWindow, name+" has quit the game; Please choose a new one to play with.");
		connectingWindow.setVisible(true);
		connectingWindow.setEnabled(true);
		connectingWindow.setLocationRelativeTo(mainWindow);
		mainWindow.setVisible(false);

	}

	public static void receiveSignalStatusWindow(String gameRqNum, String result) {
		String message="";
		int r=Integer.parseInt(result);
		Client.sentRequestForGame=Integer.parseInt(gameRqNum);
		if(r==1){
			 message="Your opponent guessed the word";
			
		}
		else {
			message="Your opponent didn't guess the word";
		}
		
		JOptionPane.showMessageDialog(null, message+ " \n It's your turn to guess now!", "Status", JOptionPane.INFORMATION_MESSAGE);
		
		
	}

	public static void receiveSignalSwitchWindow() {
		startGame();
		
	}


}







