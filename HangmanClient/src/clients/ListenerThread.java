package clients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import gui.GUIControler;

public class ListenerThread extends Thread {
	
	BufferedReader serverInput = null;
	static boolean end = false;
	
	public ListenerThread(BufferedReader input) {
		this.serverInput=input;
	}
	
	@Override
	public void run() {
		String response="";
		while(!end) {
			//add exit part
			try {
				String input = serverInput.readLine();
				if(input.startsWith("/USERNAME")) {
					response = input.split(":")[1];
					GUIControler.validateUsernameFromServer(response);
				}
				if(input.startsWith("/LIST")) {
					String usernames = input.substring(6);
					//System.out.println(usernames);
					Client.onlineLista = Client.parseOnlineList(usernames);
					GUIControler.updateTable();
				}
			
					
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Error: "+ e);
			}catch(NullPointerException npe) {
				return;
			}
		}	
	}
}
