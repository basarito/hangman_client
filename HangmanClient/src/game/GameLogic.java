package game;

import gui.GUIControler;
import gui.MainWindow;

public class GameLogic {
	
	//public static String word = "Despeytnmgdf";
	public static String letter;
	
	
	public static void insertButtonsForLetters() {
		for (int i=0; i<GUIControler.word.length(); i++) {
			
			MainWindow.getPanel_5().add(MainWindow.getBtnLetter());
			
		}
		
	}
	
	
	
	
	
	
}
