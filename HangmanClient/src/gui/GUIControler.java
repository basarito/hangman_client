package gui;

import java.awt.Dimension;
import java.awt.EventQueue;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicTreeUI.SelectionModelPropertyChangeHandler;

import clients.Client;

public class GUIControler extends Thread {

	private static WelcomeWindow welcomeWindow;
	private static ConnectingWindow connectingWindow ;
	private static MainWindow mainWindow;

	//public static String playerUsername="";
	//public static String opponent1="";
	//public static String opponent2="";

	static JDialog dialog = null;

	//public static boolean goodbye = false;
	public static String word = "Desperados";
	public static String newW=null;
	public static String letter;
	public static int errorCount=0;
	public static int lettersCorrect=0;

	public static boolean acceptedGame = false;
	
	static String usernameToValidate = "";
	static String tryOpponent = "";


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
		welcomeWindow.setVisible(false);
		connectingWindow.setVisible(true);
		connectingWindow.setLocationRelativeTo(null);		
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
			showConnectingWindow();
		}

	}	

	//Personalized welcome message 
	public static JLabel welcomeUser() {
		return new JLabel("Welcome, " + Client.getUsername() + "!");
	}

	//Select user from the list to send invite
	public static void choose(String user) {
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
			Client.inviteUserToPlay(tryOpponent);
		} else {
			SwingUtilities.updateComponentTreeUI(connectingWindow);
		}
	}

	//Receive and handle response to invite
	public static void receiveResponseToInvite(String name, String response) {
			if(response.equals("ACCEPTED")) {			
				Client.setOpponent(name);
				dialog.setVisible(false);
				startGame();
			}
			else if(response.equals("REJECTED")) {
				dialog.setVisible(false);
				connectingWindow.setEnabled(true);
				JOptionPane.showMessageDialog(connectingWindow, "Connection to "+name+" was unsuccessful or they rejected your invite. Try a different user.", "Connection failed", JOptionPane.ERROR_MESSAGE);
			} else {
				dialog.setVisible(false);
				connectingWindow.setEnabled(true);
			}
	}

	//Random button functionality 
	public static void chooseRandom() {
		if(Client.onlineLista.isEmpty()){
			JOptionPane.showMessageDialog(connectingWindow, "There are no online players at the moment!");			

		}else{
			Random randomizer = new Random();

			String random = Client.onlineLista.get(randomizer.nextInt(Client.onlineLista.size()));

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
					break;

				case 2: 
					setHangmanImage("/icons/state-2.png");
					break;
				case 3:
					setHangmanImage("/icons/state-3.png");
					break;

				case 4:
					setHangmanImage("/icons/state-4.png");
					break;
				case 5:
					setHangmanImage("/icons/state-5.png");
					break;
				case 6:
					setHangmanImage("/icons/state-6.png");
					JOptionPane.showMessageDialog(null, "You have lost the game", "Game over ", JOptionPane.INFORMATION_MESSAGE);
					break;
				default : break;
				}
			}
			else {
				for (int i=0; i<w.length(); i++){

					if (letter.charAt(0)==w.charAt(i)){
						MainWindow.listOfButtons.get(i).setText(letter);
						newW=newW+letter;
						lettersCorrect++;

					}
				}
				if(lettersCorrect==w.length()){
					JOptionPane.showMessageDialog(null, "You won", "Game status", JOptionPane.INFORMATION_MESSAGE);
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
		int option = JOptionPane.showConfirmDialog(connectingWindow.getContentPane(), name+" has invited you to play with them. Do you want to accept?",
				"Received an invitation!", JOptionPane.YES_NO_OPTION);
		if (option == JOptionPane.YES_OPTION) {
			Client.acceptInvite(name);
		} else {
			Client.rejectInvite(name);
			//SwingUtilities.updateComponentTreeUI(connectingWindow);
		}
	}

	public static void startGame() {
		connectingWindow.setVisible(false);
		mainWindow = new MainWindow();
		mainWindow.setVisible(true);
		mainWindow.setLocationRelativeTo(null);	
	}
	
}







