package gui;

import java.awt.EventQueue;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;


public class GUIControler {

	private static WelcomeWindow welcomeWindow;
	private static ConnectingWindow connectingWindow ;
	private static MainWindow mainWindow;
	public static LinkedList<String> onlineLista = new LinkedList<String>(Arrays.asList("pera123","mikamagija","zika90"));
	private static String playerUsername="";
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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

	public static void closeApp1() {
		int option = JOptionPane.showConfirmDialog(welcomeWindow.getContentPane(), "Are you sure you want to close the game?",
				"Closing app", JOptionPane.YES_NO_OPTION);

		if (option == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}
	
	public static void closeApp2() {
		int option = JOptionPane.showConfirmDialog(connectingWindow.getContentPane(), "Are you sure you want to close the game?",
				"Closing app", JOptionPane.YES_NO_OPTION);

		if (option == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}

	public static void showConnectingWindow() {
		connectingWindow = new ConnectingWindow();
		welcomeWindow.setVisible(false);
		connectingWindow.setVisible(true);
		connectingWindow.setLocationRelativeTo(null);
	}

	//Username validation
	public static void sendUsername(String username) {
		if (username.isEmpty()) {
			JOptionPane.showMessageDialog(welcomeWindow, "You have to enter a username!", "Try again :(", JOptionPane.ERROR_MESSAGE);
		} else if(!username.matches("[A-Za-z0-9]+")) {
			JOptionPane.showMessageDialog(welcomeWindow, "Incorrect username. Please use only letters A-Z and/or numbers 0-9", "Try again :(", JOptionPane.ERROR_MESSAGE);
		} else if(username.length()>10) {
			JOptionPane.showMessageDialog(welcomeWindow, "Username too long. Please use up to 10 characters.", "Try again :(", JOptionPane.ERROR_MESSAGE);
		} else if (onlineLista.contains(username)) {
			JOptionPane.showMessageDialog(welcomeWindow, "Username already taken. Please choose a different one.", "Try again :(", JOptionPane.ERROR_MESSAGE);			
		}
		else {
			//JOptionPane.showMessageDialog(welcomeWindow, "Okie dokie smokie");
			//onlineLista.add(username);
			GUIControler.playerUsername = username;
			showConnectingWindow();
		}
	}

	//Personalized welcome message 
	public static JLabel welcomeUser() {
		return new JLabel("Welcome, " + GUIControler.playerUsername + "!");
	}

	public static void choose(String user) {
		int option = JOptionPane.showConfirmDialog(connectingWindow.getContentPane(), "Are you sure you want to play with "+user+ " ?",
				"Connecting", JOptionPane.YES_NO_OPTION);
		
		if(option == JOptionPane.YES_OPTION){

				connectingWindow.setVisible(false);
				mainWindow = new MainWindow();
				mainWindow.setVisible(true);
				mainWindow.setLocationRelativeTo(null);

		}else{
			SwingUtilities.updateComponentTreeUI(connectingWindow);
		}
	}

	public static void typeAUser(String text) {
		
		if(onlineLista.contains(text)){
			int option = JOptionPane.showConfirmDialog(connectingWindow.getContentPane(), "Are you sure you want to play with "+text+ " ?",
					"Connecting", JOptionPane.YES_NO_OPTION);


			if(option == JOptionPane.YES_OPTION){

				connectingWindow.setVisible(false);
				mainWindow = new MainWindow();
				mainWindow.setVisible(true);
				mainWindow.setLocationRelativeTo(null);
			}else{
				SwingUtilities.updateComponentTreeUI(connectingWindow);
			}
		}else{
			JOptionPane.showMessageDialog(connectingWindow, "The user does not exist.");

		}

	}

	public static void chooseRandom() {
		Random randomizer = new Random();
		String random = onlineLista.get(randomizer.nextInt(onlineLista.size()));

		int option = JOptionPane.showConfirmDialog(connectingWindow.getContentPane(), random+" is available. Do you want to play with him? ",
				"Connecting", JOptionPane.YES_NO_OPTION);


		if(option == JOptionPane.YES_OPTION){

			connectingWindow.setVisible(false);
			mainWindow = new MainWindow();
			mainWindow.setVisible(true);
			mainWindow.setLocationRelativeTo(null);
		}else{
			SwingUtilities.updateComponentTreeUI(connectingWindow);
		}
	}
}



	
	

