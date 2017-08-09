package game;

import gui.MainWindow;

public class GameLogic {
	
	public static String word = "Desperados";
	public static String opponent = "opponent";

	
	/*public static void main(String[] args) {
		
		MainWindow mainWindow = new MainWindow();
		
		
	}*/

	public static void insertLetters() {
		for (int i=0;i<word.length();i++) {
			MainWindow.getPanel_5().add(MainWindow.getBtnLetter());
		}
		
	}
	
}
