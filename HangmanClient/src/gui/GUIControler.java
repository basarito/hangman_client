package gui;

import java.awt.EventQueue;

import javax.swing.JOptionPane;


public class GUIControler {

	private static WelcomeWindow welcomeWindow;
	private static ConnectingWindow connectingWindow ;
	private static MainWindow mainWindow;
	
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
		int option = JOptionPane.showConfirmDialog(welcomeWindow.getContentPane(), "Do yo want to close the game?",
				"Closing app", JOptionPane.YES_NO_OPTION);

		if (option == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}
	
	public static void closeApp2() {
		int option = JOptionPane.showConfirmDialog(connectingWindow.getContentPane(), "Do yo want to close the game?",
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

	
	
}
