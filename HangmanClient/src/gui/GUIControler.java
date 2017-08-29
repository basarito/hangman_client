package gui;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.EventQueue;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import clients.Client;


public class GUIControler extends Thread {

	private static WelcomeWindow welcomeWindow;
	private static ConnectingWindow connectingWindow ;
	private static MainWindow mainWindow;

	static JDialog dialog = null;
	static JDialog timeoutDialog = null;
	static JDialog gameOverDialog = null;
	static boolean answered = false;

	public static String word="";
	public static String category="";
	public static String newW="";
	public static String letter;
	public static int errorCount=0;
	public static int lettersCorrect=0;
	public static int end=0;
	public static boolean acceptedGame = false;

	static String usernameToValidate = "";
	static String tryOpponent = "";
	private static JDialog dialogForWord;
	public static JDialog wordInputDialog=null;
	
	public static boolean messageAdded = false;
	
	public static boolean giving = true;


	@Override 
	public void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					welcomeWindow = new WelcomeWindow();
					welcomeWindow.setVisible(true);
					welcomeWindow.setLocationRelativeTo(null);

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
		int option = JOptionPane.showConfirmDialog(mainWindow, "Are you sure you want to quit the game?",
				"Leaving the game", JOptionPane.YES_NO_OPTION);

		if (option == JOptionPane.YES_OPTION) {
			mainWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			Client.sendQuitTheGameSignal(Client.getOpponent());
			resetVariables(Client.getOpponent());
			connectingWindow.setLocationRelativeTo(mainWindow);
			connectingWindow.setVisible(true);
			connectingWindow.setEnabled(true);
			mainWindow.setVisible(false);
			return;

		}else if(option == JOptionPane.NO_OPTION){
			mainWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		}

	}
	//Show Connecting window
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
			welcomeWindow.getTextField().requestFocusInWindow();
		} else if(!username.matches("[A-Za-z0-9]+")) {
			JOptionPane.showMessageDialog(welcomeWindow, "Incorrect username. Please use only letters a-z and/or numbers 0-9", "Try again :(", JOptionPane.ERROR_MESSAGE);
			welcomeWindow.getTextField().setText("");
			welcomeWindow.getTextField().requestFocusInWindow();
		} else if(username.length()>10) {
			JOptionPane.showMessageDialog(welcomeWindow, "Username too long. Please use up to 10 characters.", "Try again :(", JOptionPane.ERROR_MESSAGE);
			welcomeWindow.getTextField().setText("");
			welcomeWindow.getTextField().requestFocusInWindow();
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
			JOptionPane.showMessageDialog(connectingWindow.getContentPane(), user+" is already playing a game. Try a different user or try again later.",
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

		if(response.equals("ACCEPTED")) {			
			Client.setOpponent(name);
			//Client.changeGameStatus("true");
			dialog.setVisible(false);
			Client.sentRequestForGame=1;
			startGame();
			//Client.gameActive = true;
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

	//Choosing an opponent at random
	public static void chooseRandom() {
		if(Client.onlineLista.isEmpty() || (Client.onlineLista.size()==Client.activeGames.size())){
			JOptionPane.showMessageDialog(connectingWindow.getContentPane(), "There are no available players at the moment!");						
		} else {
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

	//Update online users
	public static void updateTable() {
		connectingWindow.refreshTable();
	}	

	//Set Hangman picture
	public static void setHangmanImage(String imgPath){

		ImageIcon img = new ImageIcon(MainWindow.class.getResource(imgPath));
		Image img1 = img.getImage();
		Image img2 = img1.getScaledInstance(250, 250, Image.SCALE_SMOOTH);
		img = new ImageIcon(img2);
		mainWindow.getLblSlika().setIcon(img);

	}
	
	// Place guessed and not guessed letters on Main Window
	//Game logic
	public static void placeTheLetter(String text) {
		letter = text.toLowerCase();
		String w =word.toLowerCase();
		if(letter.matches("[a-z]")) {

			if (!w.contains(letter)) {	
			MainWindow.getTxtpnABC().setText(MainWindow.getTxtpnABC().getText()+letter +"\n");
				errorCount++;
				switch (errorCount) {
				case 1: 
					changeHangmnPicAndPlaceWrongLetter("/icons/state-1.png", Client.getOpponent(), letter);
					break;

				case 2: 
					changeHangmnPicAndPlaceWrongLetter("/icons/state-2.png", Client.getOpponent(), letter);
					break;

				case 3:
					changeHangmnPicAndPlaceWrongLetter("/icons/state-3.png", Client.getOpponent(), letter);
					break;

				case 4:
					changeHangmnPicAndPlaceWrongLetter("/icons/state-4.png", Client.getOpponent(), letter);
					break;
				case 5:
					changeHangmnPicAndPlaceWrongLetter("/icons/state-5.png", Client.getOpponent(), letter);
					break;
				case 6:
					changeHangmnPicAndPlaceWrongLetter("/icons/state-6.png", Client.getOpponent(), letter);
					Client.setNumOfLosses(Client.getNumOfLosses()+1);
					set2VictoriesEndGame(Client.getNumOfLosses(), "You lost :(", "YOU WON!", 0,
										"You haven't gueesed the word. \n It's your turn to set a word for ");
					break; 
				default : break;
				}
			} else if(newW!=null && newW.contains(letter)) {
				return;
			} else {
				for (int i=0; i<w.length(); i++){

					if (letter.charAt(0)==w.charAt(i)){
						MainWindow.listOfButtons.get(i).setText(letter.toUpperCase());
						Client.changeRigthLetterSignal(letter, Client.getOpponent(), i+"");

						newW=newW+letter;
						lettersCorrect++;

					}
				}

				if(lettersCorrect==w.length()){
					Client.setNumOfWins(Client.getNumOfWins()+1);
					set2VictoriesEndGame(Client.getNumOfWins(), "YOU WON!", "You lost :(", 1, 
							"You guessed the word. \n "+ "It's your turn to set a word for ");
				}
			}
		}
		else {
			JOptionPane.showMessageDialog(mainWindow, "Enter a letter", "Error", JOptionPane.ERROR_MESSAGE );
		}


	}
	public static void set2VictoriesEndGame(int wLNum, String msg1, String msg2, int gmRqNum, String msg3) {
		if(end<2) { 
			if(Client.getNumOfLosses()>end ) {
				end=wLNum;

				if(end==2){
					sendingResultNClearingValues(Client.getOpponent(), Client.getNumOfWins()+"", Client.getNumOfLosses()+"");
					checkGameSentRq();
					gameOver(Client.getOpponent(), msg1, msg2 );
				}
				else{
					switchMainWindow(Client.getOpponent(), Client.sentRequestForGame+"" , gmRqNum+"" , msg3+
							Client.getOpponent()+".", Client.getNumOfWins()+"", Client.getNumOfLosses()+"" );
				}

			}
			else {
				switchMainWindow(Client.getOpponent(), Client.sentRequestForGame+"" , gmRqNum+"", msg3+
						Client.getOpponent()+".", Client.getNumOfWins()+"", Client.getNumOfLosses()+"" );
			}

		}   
		
	}

	private static void checkGameSentRq() {
		if(Client.sentRequestForGame==1){
			Client.sendRqGmNum(Client.getOpponent(), 0+"");
		}
		if(Client.sentRequestForGame==0){
			Client.sendRqGmNum(Client.getOpponent(), 1+"");
		}

	}

	// Sending gameOver signal to opponent and launching gameOver JOptionPane
	private static void gameOver(String opponent, String message, String msgOpp) {
		Client.sendGameOverSignal(opponent, msgOpp);
		gameOverWindow(message);

	}

	// Showing gameOver dialog to the players and asking them if they want to play again
	public static void gameOverWindow(String message){
				
		JButton btnAgain = new JButton("Play again");
		JButton btnExit = new JButton("Exit game");
		
		Object[] options = new Object[] {btnAgain, btnExit};
		JOptionPane paneGameOver = new JOptionPane(message, JOptionPane.QUESTION_MESSAGE,  JOptionPane.YES_NO_OPTION, null, options);

		gameOverDialog = paneGameOver.createDialog(mainWindow.getContentPane(), "Game Status!");
		gameOverDialog.setModalityType(Dialog.ModalityType.MODELESS);
		gameOverDialog.setVisible(true);

		gameOverDialog.addWindowListener(new WindowAdapter() 
		{
		  public void windowClosing(WindowEvent e)
		  {
			  int option = JOptionPane.showConfirmDialog(mainWindow, "Are you sure you want to quit the game?",
						"Leaving the game", JOptionPane.YES_NO_OPTION);

				if (option == JOptionPane.YES_OPTION) {
					Client.sendQuitTheGameSignal(Client.getOpponent());
					resetVariables(Client.getOpponent());
					connectingWindow.setLocationRelativeTo(mainWindow);
					connectingWindow.setEnabled(true);
					connectingWindow.setVisible(true);
					mainWindow.setVisible(false);
					return;
				}else if(option == JOptionPane.NO_OPTION){
					gameOverDialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				}
		  }
		});
		
		btnAgain.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Client.setNumOfLosses(0);
				Client.setNumOfWins(0);
				errorCount = 0;
				lettersCorrect = 0;
				newW = "";
				mainWindow.listOfButtons.clear();
				end = 0;
				
	
				startGame();
			}
		}); 
		
		btnExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				resetVariables(Client.getOpponent());
				Client.sendQuitTheGameSignal(Client.getOpponent());
				connectingWindow.setLocationRelativeTo(mainWindow);
				connectingWindow.setEnabled(true);
				connectingWindow.setVisible(true);
				mainWindow.setVisible(false);
				return;
			}
		}); 
	}

	// Changing Hangman picture of player and opponent and placing letter that is not guessed on opponent Main Window
	public static void changeHangmnPicAndPlaceWrongLetter(String url, String opponent, String letter1) {
		setHangmanImage(url);
		Client.changeHangmanPictureSignal(url, opponent);
		Client.changeWrongLettersSignal(letter1, opponent);

	}

	// Calculate number of letters in word that should be guessed
	public static int numberOfLettersInAWord(String word, String l) {
		int count=0;
		for(int i=0; i<word.length(); i++){
			if(word.charAt(i)==l.charAt(0)){
				count++;
			}
		}
		return count;

	}

	// Sending signal for changing result and showing game status window on opponent's Main Window
	// Switching the users roles (one who was guessing is now setting the word)
	public static void sendingResultNClearingValues(String opponent, String r1, String r2){
		Client.sentRequestForGame=0;
		mainWindow.getlblResult().setText("Result: "+r1+":"+r2);
		Client.sendChangeResult(opponent, r2, r1);
		errorCount=0; 
		lettersCorrect=0; 
		newW="";  
		mainWindow.listOfButtons.clear();
	}

	// Switch appearance of Main window for both players when the word is guessed/hangman is drawn
	public static void switchMainWindow(String opponent, String gameRqNum, String result, String message, String r1, String r2) {
		sendingResultNClearingValues(opponent, r1, r2);
		Client.sendGameStatusWindow(opponent, gameRqNum , result);

		int input;
		do {
			input = JOptionPane.showOptionDialog(mainWindow.getContentPane(), message, "Status", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
		} while(input==-1);
		if(input==0) {
			startGame();

		}
	}

	//Connecting two players in a game
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
			} else if (value == JOptionPane.CLOSED_OPTION) {
				answered=true;
				Client.rejectInvite(name);
			}
		} catch (NumberFormatException e1) {
			answered=false;
			return;
		} catch (Exception e) {
			return;
		}
	}
	
	//Start the game between two players
	public static void startGame() {
		//Client.gameActive = true;
		if(mainWindow!=null){
			giving = true;
			mainWindow.setVisible(false);
			mainWindow= new MainWindow();
			mainWindow.setLocationRelativeTo(connectingWindow);
			connectingWindow.setVisible(false);
			mainWindow.setVisible(true);
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

		if(Client.sentRequestForGame==1) { //ceka na rec
			mainWindow.getlblResult().setText("Result: "+Client.getNumOfWins()+":"+Client.getNumOfLosses());
			waitingMainWindow();
		} else {
			mainWindow.getlblResult().setText("Result: "+Client.getNumOfWins()+":"+Client.getNumOfLosses());
			givingWordMainWindow();
		}

	}
	
	//Setting the look of the window for the player who is waiting for the opponent to set a word 
	private static void waitingMainWindow() {
		//loading screen:
		dialogForWord = new JDialog();
		JLabel label = new JLabel("Waiting on opponent to set the word to guess...", JLabel.CENTER);
		dialogForWord.setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/icons/h.png")));
		dialogForWord.setTitle("Please Wait...");
		dialogForWord.add(label);
		dialogForWord.setPreferredSize(new Dimension(400, 90));
		dialogForWord.setResizable(false);
		dialogForWord.pack();
		dialogForWord.setLocationRelativeTo(mainWindow);
		dialogForWord.setVisible(true);
		
		dialogForWord.addWindowListener(new WindowAdapter() 
		{
		  public void windowClosing(WindowEvent e)
		  {
			  int option = JOptionPane.showConfirmDialog(mainWindow, "Are you sure you want to quit the game?",
						"Leaving the game", JOptionPane.YES_NO_OPTION);

				if (option == JOptionPane.YES_OPTION) {
					resetVariables(Client.getOpponent());
					Client.sendQuitTheGameSignal(Client.getOpponent());
					connectingWindow.setLocationRelativeTo(mainWindow);
					connectingWindow.setEnabled(true);
					connectingWindow.setVisible(true);
					mainWindow.setVisible(false);
					return;
				}else if(option == JOptionPane.NO_OPTION){
					dialogForWord.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				}
		  }
		});
		
		mainWindow.setEnabled(false);
	}

	//Getting word and category that the player set for his opponent
	private static void givingWordMainWindow() {
		String w="";
		String c="";
		String[] options = {"OK"};
		JPanel panelWord = new JPanel();
		panelWord.setPreferredSize(new Dimension(80, 100));
		JLabel lbl = new JLabel("Enter a word: ");
		JTextField txt = new JTextField(15);
		JLabel lbl2 = new JLabel("Enter a category:");
		JTextField txt2 = new JTextField(15);
		panelWord.add(lbl);
		panelWord.add(txt);
		panelWord.add(lbl2);
		panelWord.add(txt2);

		do {
			int selectedOption = JOptionPane.showOptionDialog(mainWindow, panelWord, "It's your turn to give a word", JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE, null, options , options[0]);
			
			if(giving == true){
				if(selectedOption==JOptionPane.CLOSED_OPTION){
					int option = JOptionPane.showConfirmDialog(mainWindow, "Are you sure you want to quit the game?",
							"Leaving the game", JOptionPane.YES_NO_OPTION);
					
					if (option == JOptionPane.YES_OPTION) {
						Client.sendQuitTheGameSignal(Client.getOpponent());
						resetVariables(Client.getOpponent());
						connectingWindow.setVisible(true);
						connectingWindow.setLocationRelativeTo(mainWindow);
						connectingWindow.setEnabled(true);
						mainWindow.setVisible(false);
						return;
					}
				}
				
			}else{
				return;
			}
			if(selectedOption==JOptionPane.OK_OPTION){   
				w = txt.getText();
				c = txt2.getText();

				if(w.isEmpty() || c.isEmpty())
					JOptionPane.showMessageDialog(mainWindow, "You have to enter both word and category!", "Please fill out everything", JOptionPane.ERROR_MESSAGE);
				else if(!w.matches("[A-Za-z]+") || !c.matches("[A-Za-z][A-Za-z ]*")) {
					JOptionPane.showMessageDialog(mainWindow, "Use only a-z characters!", "Not a word", JOptionPane.ERROR_MESSAGE);
					if(!w.matches("[A-Za-z]+"))
						txt.setText("");
					if(!c.matches("[A-Za-z]+"))
						txt2.setText("");
				} else {
					word = w;
					category=c;
					break;				
				}
			}
		} while(true);	

		Client.sendWordSetSignal(Client.getOpponent(), word, category);
		setOpponentMainWindow();
	}

	public static void receiveSignalWordSet(String w, String c) {
		dialogForWord.setVisible(false);
		setPlayerMainWindow(w, c);
	}


	//Main Window of player guessing the word
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
		mainWindow.getlblResult().setVisible(true);
	}

	// Main Window of player who is setting the word
	public static void setOpponentMainWindow() { 

		mainWindow.remove(mainWindow.getBtnGuess());
		mainWindow.remove(mainWindow.getTextField());
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
		mainWindow.getlblResult().setVisible(true);		
		
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


	//Add message to the chatbox
	public static void addMessage(String username, String message) {
		String newMsg = username + ":\n" + message;
		Client.chatHistory.addElement(newMsg);	
		messageAdded=true;
	}


	//Recieve the popup when your opponent has left the game
	public static void receiveQuitTheGameSignal(String name) {

		if(dialogForWord!=null)
			dialogForWord.setVisible(false);
		giving=false;		
		
		if(!connectingWindow.isVisible())
			JOptionPane.showMessageDialog(mainWindow, name+" has quit the game. Please choose another player to play with.");
		resetVariables(name);
		connectingWindow.setLocationRelativeTo(mainWindow);
		connectingWindow.setEnabled(true);
		connectingWindow.setVisible(true);
		mainWindow.setVisible(false);
		

	}
	//Pop up that comes up when someone guessed the word
	public static void receiveSignalStatusWindow(String gameRqNum, String result) {
		String message="";
		int r=Integer.parseInt(result);

		Client.sentRequestForGame=Integer.parseInt(gameRqNum);

		if(r==1){
			message="Your opponent guessed the word";

		}
		else {
			message="Your opponent didn't guess the word. ";
		}

		int option;
		do{
			option=JOptionPane.showOptionDialog(mainWindow, message+"\n It's your turn to guess", "Status", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

		} while(option==-1);
		if(option==0)
			startGame();


	}


	// Change result on opponent's Main Window
	public static void receiveSignalResultChanged(String r1, String r2) {
		mainWindow.getlblResult().setText("Result: "+r1+":"+r2);
		Client.setNumOfLosses(Integer.parseInt(r2));
		Client.setNumOfWins(Integer.parseInt(r1));

	}

	public static void receiveGameOverSignal(String msg) {
		gameOverWindow(msg);

	}

	//Reset variables from the game played, so that it can be played again
	public static void resetVariables(String opponent){
		Client.setNumOfLosses(0);
		Client.setNumOfWins(0);

		errorCount = 0;
		lettersCorrect = 0;
		newW = "";
		mainWindow.listOfButtons.clear();
		end = 0;
		Client.sentRequestForGame=0;
		Client.sendSignalResetWinsLosses(opponent);
		Client.chatHistory.clear();
	
		}

	public static void receiveSignalResetWinsLosses() {
		Client.setNumOfLosses(0);
		Client.setNumOfWins(0);
	}
	//Game request number determines who is giving the word and who is guessing it
	public static void receiveGameRqNum(String num) {
		Client.sentRequestForGame=Integer.parseInt(num);

	}


}







