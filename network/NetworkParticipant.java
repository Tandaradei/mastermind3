package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

public abstract class NetworkParticipant implements Runnable {
	protected Queue<String> commands;
	protected BufferedReader in;
	protected Writer out;
	protected Socket clientSocket;
	protected playingfield.PlayingField playingField;
	
	public NetworkParticipant(playingfield.PlayingField playingField){
		commands = new LinkedList<String>();
		this.playingField = playingField;
	}
	
	public abstract void start();
	
	public boolean commandsEmpty(){
		return commands.isEmpty();
	}
	
	public String getCommand() {
		return commands.poll();
	}
	
	public void sendCommand(String command) {
		System.out.println("Sending: " + command);
		try{
			if(out == null){
				System.out.println("out is null!");
			}
			out.write(String.format("%s%n", command));
			out.flush();
			
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void run() {
    	try{
	        while (true) {
				// Eingabestrom lesen
	            String line = in.readLine();
	            if (line == null) {
	                break;
	            }
	            System.out.println("Received: " + line);
	            commands.add(line);
	            playingField.newCommand();
	        }
    	} catch(IOException e){
    		e.printStackTrace();
    	}
    }
}
