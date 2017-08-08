package clients;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class Client implements Runnable{

	
	static Socket communicationSocket = null;
	static PrintStream serverOutput = null;
	static BufferedReader serverInput = null;
	static BufferedReader console = null;
	static boolean end = false;
	
public static void main(String[] args) {
		
		try {
			int port = 47556;
			
			if(args.length>0)
				port = Integer.parseInt(args[0]);
			
			communicationSocket = new Socket("localhost", port);
			
			
			console = new BufferedReader(new InputStreamReader(System.in));
			serverOutput = new PrintStream(communicationSocket.getOutputStream());
			serverInput = new BufferedReader(new InputStreamReader(communicationSocket.getInputStream()));
			
			
			
			new Thread(new Client()).start();
			
			
//			while(!end){
//			}
			
			communicationSocket.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
						
	}
	
	@Override
	public void run() {
		
		String messageFromServer;
	}

}
