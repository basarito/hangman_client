package clients;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.LinkedList;


import gui.GUIControler;

public class Client {

	
	static Socket communicationSocket = null;
	static PrintStream serverOutput = null;
	static BufferedReader serverInput = null;
	//static BufferedReader console = null;
    
	public static LinkedList<String> onlineLista = new LinkedList<String>();
    
    public static ListenerThread listener = null;
	static boolean end = false;
	
	
	static String opponent = "";
	
	static String playerUsername = "";
	
	public static int sentRequestForGame=0;
	
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

	public static synchronized LinkedList<String> parseOnlineList(String usernames) {
		LinkedList<String> pomocna = new LinkedList<>();
		String[] userarray = usernames.split(";");
		for (int i = 0; i < userarray.length; i++) {
			if(userarray[i].equals(getUsername())) {
				continue;
			}
			pomocna.add(userarray[i]);
		}
		System.out.println("Available online users:");
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

}