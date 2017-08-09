package clients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import gui.GUIControler;

public class Client {

	
	static Socket communicationSocket = null;
	static PrintStream serverOutput = null;
	static BufferedReader serverInput = null;
	static BufferedReader console = null;
	static boolean end = false;
	
public static void main(String[] args) {
		
		try {
			int port = 6666;
			
			if(args.length>0)
				port = Integer.parseInt(args[0]);
			
			communicationSocket = new Socket("localhost", port);
			
			
			console = new BufferedReader(new InputStreamReader(System.in));
			serverOutput = new PrintStream(communicationSocket.getOutputStream());
			serverInput = new BufferedReader(new InputStreamReader(communicationSocket.getInputStream()));
			
			GUIControler gui = new GUIControler();
			gui.start();
			
			//communicationSocket.close();
					
		} catch (Exception e) {
			e.printStackTrace();
		}
						
	}
	
	public static boolean validateUsername(String username) {
		boolean available = false;
			try {
					serverOutput.println(username);
					String input = serverInput.readLine();
					if(input.equals("true")) {
						available = true;
					} else {
						available = false;
					}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			return available;	
	}

}
