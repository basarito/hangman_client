package clients;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.LinkedList;

import javax.swing.DefaultListModel;

import gui.GUIControler;

public class Client {

	
	static Socket communicationSocket = null;
	static PrintStream serverOutput = null;
	static BufferedReader serverInput = null;
	//static BufferedReader console = null;
    
	public static LinkedList<String> onlineLista = new LinkedList<String>();
	public static LinkedList<String> activeGames = new LinkedList<String>();
    
    public static ListenerThread listener = null;
	static boolean end = false;
	
	
	static String opponent = "";
	static String playerUsername = "";
	static int numOfWins=0;
	static int numOfLosses=0;
	
	public static int sentRequestForGame=0;
	
	public static DefaultListModel<String> chatHistory = new DefaultListModel<>();
	
	//static boolean gameActive = false;
	
	public static int getNumOfLosses() {
		return numOfLosses;
	}
	
	public static void setNumOfLosses(int num) {
		numOfLosses=num;
	} 
	
	public static int getNumOfWins() {
		return numOfWins;
	} 
	
	public static void setNumOfWins(int num) {
		numOfWins=num;
	} 
	
	public static String getUsername() {
		return playerUsername;
	}
	
	public static void setUsername(String name) {
		playerUsername = name;
	}
	
	public static String getOpponent() {
		return opponent;
	}
	
	public static void setOpponent(String name) {
		opponent = name;
	}
	
public static void main(String[] args) {
		
		try {
			int port = 6666;
			
			if(args.length>0)
				port = Integer.parseInt(args[0]);
			
			communicationSocket = new Socket("localhost", port);
				
			//console = new BufferedReader(new InputStreamReader(System.in));
			serverOutput = new PrintStream(communicationSocket.getOutputStream());
			serverInput = new BufferedReader(new InputStreamReader(communicationSocket.getInputStream()));
			
			GUIControler gui = new GUIControler();
			gui.start();
			
			listener = new ListenerThread(serverInput);
			listener.start();
			
			if(end==true) {
				communicationSocket.close();
			}
					
		} catch (Exception e) {
			System.out.println("Can't connect to server, it appears to be down.");
		}
						
	}

	//Username validation
	public static void sendUsernameToServer(String username) {
		serverOutput.println("/USERNAME:"+username);
	}	
	
	public static void sendExitSignal() {
		serverOutput.println("/EXIT"); 
	}
	
	public static void sendWordSetSignal(String receiver, String word, String category){
		serverOutput.println("/WORD:SET:"+receiver+":"+word+":"+category);
	}

	public static void inviteUserToPlay(String user) {			
			serverOutput.println("/INVITE:"+user);
	}

	public static synchronized LinkedList<String> parseList(String usernames) {
		LinkedList<String> pomocna = new LinkedList<>();
		String[] userarray = usernames.split(";");
		for (int i = 0; i < userarray.length; i++) {
			if(userarray[i].equals(getUsername())) {
				continue;
			}
			pomocna.add(userarray[i]);
		}
		for(String s : pomocna) {
			System.out.println(s);
		}
		System.out.println("------------");
		return pomocna;
	}

	public static void acceptInvite(String name) {
		serverOutput.println("/RSVPTO:"+name+":ACCEPTED");
		setOpponent(name);		
		GUIControler.startGame();
		
	}

	public static void rejectInvite(String name) {
		serverOutput.println("/RSVPTO:"+name+":REJECTED");
	}

	public static void changeGameStatus(String status) {
		serverOutput.println("/STATUS:"+status);
	}
	
	
	
	public static void changeHangmanPictureSignal(String imgURL, String name) {
		serverOutput.println("/PIC:"+name+":"+imgURL);
		
	}

	public static void changeWrongLettersSignal(String letter, String opponent) {
		
		serverOutput.println("/LETTER:"+letter+":"+opponent);
	}

	public static void changeRigthLetterSignal(String letter, String opponent, String index) {
		serverOutput.println("/GUESSED_LETTER:"+letter+":"+opponent+":"+index);
		
	}

	/********CHATBOX**********/

	public static void sendMessage(String message) {
		serverOutput.println("/CHATSEND:"+getOpponent()+":"+message);
	}

	public static void sendQuitTheGameSignal(String opponent){
		serverOutput.println("/QUIT:"+opponent);
	}
	

	public static void sendGameStatusWindow(String opponent, String gameRqNum, String result){
		serverOutput.println("/STATUS_WND:"+opponent+":"+gameRqNum+":"+result);
		
	}

	public static void switchOpponentMainWindow(String opponent) {
		
		serverOutput.println("/SWITCH_WND:"+opponent);
	
	}

	public static void sendChangeResult(String opponent, String r1, String r2) {
		serverOutput.println("/CHNG_RSLT:"+opponent+":"+r1+":"+r2);
		
	}
	

}