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
					System.out.println("online:");
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
					if(usernames.equals("/EMPTY")) {
						Client.activeGames.clear();
						System.out.println("No active players");
						continue;
					}
					System.out.println("active players:");
					Client.activeGames = Client.parseList(usernames);

				}
				
				if(input.startsWith("/PIC_CHANGED")){
					String url=input.split(":")[1];
					GUIControler.receiveSignalHnagmanPicChanged(url);
					
				}
				
				if(input.startsWith("/WRONG_LETTER")) {
					String letter=input.split(":")[1];
					GUIControler.receiveSignalWrongLetter(letter);
				}
				
				if(input.startsWith("/RIGHT_LETTER")) {
					String letter=input.split(":")[1];
					String index=input.split(":")[2];
					GUIControler.receiveSignalRightLetter(letter, index);
				}
				
				if(input.startsWith("/STATUS_WND_RCV")) {
					String gameRqNum=input.split(":")[1];
					String result=input.split(":")[2];
					GUIControler.receiveSignalStatusWindow(gameRqNum, result);
				}
				
				if(input.startsWith("/RSLT_CHNGD")) {
					String r1=input.split(":")[1];
					String r2=input.split(":")[2];
					GUIControler.receiveSignalResultChanged(r1, r2);
				}
				
				
				if(input.startsWith("/RCV_SWITCH_WND:")) {
					
					GUIControler.receiveSignalSwitchWindow();
				}

				if(input.startsWith("/CHATRCV")) {
					String name = input.split(":")[1];
					String message = input.split(":")[2];
					GUIControler.addMessage(name, message);
				}
				
				if(input.startsWith("/QUIT_SENT")){
					String name=input.split(":")[1];
					GUIControler.recieveQuitTheGameSignal(name);
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
