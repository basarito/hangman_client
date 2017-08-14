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
					Client.onlineLista = Client.parseList(usernames);
					GUIControler.updateTable();
				}
				if(input.startsWith("/INVITEDBY")) {
					String name = input.split(":")[1];
					GUIControler.receiveInvite(name);
				}
				if(input.startsWith("/RSVPBY")) {
					String name = input.split(":")[1];
					response = input.split(":")[2];
					GUIControler.receiveResponseToInvite(name, response);
				}
				if(input.startsWith("/WORD_SET")){
					String word = input.split(":")[1];
					String category = input.split(":")[2];
					GUIControler.receiveSignalWordSet(word, category);
				}
				if(input.startsWith("/ACTIVEGAMES")) {
					String usernames = input.split(":")[1];
					if(usernames.equals("/EMPTY")) 
						continue;
					Client.activeGames = Client.parseList(usernames);

				}
								
			} catch (IOException e) {
				System.out.println("Server is down.");
				end=true;
				return;
			} catch(NullPointerException npe) {
				return;
			}
		}	
	}
}
