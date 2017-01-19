package network.client;

import java.net.*;

import network.NetworkParticipant;
import playingfield.PlayingField;

import java.io.*;
import javax.swing.JFrame;

public class Client extends NetworkParticipant {

    private String address = "127.0.0.1";
    private int port = 50004;
    private String code = "";
	private String playerName;

    public Client(String address, int port, String playerName){

        this.address = address;
        this.port = port;
		this.playerName = playerName;
    }




    public void start() {
        NetworkParticipant me = this;
        Runnable runner = new Runnable() {
            public void run(){
                try{
                    clientSocket = new Socket(address, port);

                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    out = new OutputStreamWriter(clientSocket.getOutputStream());

                    if(clientSocket.isConnected()) {
                        System.out.println("Client: Connected!");
                     // Eingabestrom

                        //startPlayingField(false);
                        sendCommand("NEWGAME " + playerName);
                        meThread = new Thread(me);
                        meThread.start();
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
    
    public void stop(boolean initiated){
    	stopped = true;
    	if(initiated){
    		sendCommand("");
    	}
    	else{
    		closeWindow();
    	}
    	try{
    		closeIO();
    		meThread.join();
    	}
    	catch(InterruptedException e){
    		e.printStackTrace();
    	}
    	System.out.println("Client: stop called!");
    	
    	
    	try {
    		System.out.println("Client: Stopping connection!");
    		if(clientSocket != null && !clientSocket.isClosed()){
				clientSocket.shutdownOutput();
				clientSocket.close();
    		}
	        System.out.println("Client: Connection stopped!");
	    } catch (IOException e) {
	        System.err.println("Client: Shutdown failed.");
	        e.printStackTrace();
	    }
    }

    public void sendCode(String code){
    	this.code = code;
    	sendCommand("CHECK " + code);
    }
    
    protected void executeCommand(String command){
        final String[] args = command.split(" ");
        if(args[0].equals("SETUP")){
        	codeLength = Integer.parseInt(args[1]);
            colors = args[2];
            startPlayingField(false, "Start to guess (click on 'Help' in the MainWindow if you need)");
        }
        else if(args[0].equals("GUESS")){
        	playingField.activateSendButton();
        }
        else if(args[0].equals("RESULT")){
            String result = args[1];
            playingField.addToHistory(code, result);
        }
		else if(args[0].equals("GAMEOVER")){
            String result = args[1];
            if(result.equals("WIN")){
				playingField.setStatusText("Congratulations! You have won the game!");
			}
			else if(result.equals("LOSE")){
				playingField.setStatusText("Sorry! You have lost the game!");
			}
			else{
				playingField.setStatusText("Gameover without a valid reason!");
			}
			
        }
		else{
			System.err.println("Unknow command received!");
		}
    }
    
}
