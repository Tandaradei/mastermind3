package network.client;

import java.net.*;

import network.NetworkParticipant;
import playingfield.PlayingField;

import java.io.*;

public class Client extends NetworkParticipant {

	private String address = "127.0.0.1";
	private int port = 50004;
	
	public Client(PlayingField playingField, String address, int port){
		super(playingField);
		this.address = address;
		this.port = port;
	}
	
	public void start() {
		Runnable runner = new Runnable() {
        	public void run(){
				try{
			        clientSocket = new Socket(address, port);
			        
			        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			        out = new OutputStreamWriter(clientSocket.getOutputStream());
			        
			        if(clientSocket.isConnected()) {
			            System.out.println("Verbindung hergestellt!");
			         // Eingabestrom
				        
				        playingField.isConnected();
			        } else {
			            System.out.println("Verbindung konnte nicht hergestellt werden!");
			        }
			     
				} catch(IOException e){
					e.printStackTrace();
				}
        	}
		};
		
		Thread clientThread = new Thread(runner);
		clientThread.start();
		
	}
    
}
