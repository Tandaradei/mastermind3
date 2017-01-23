package network.client;

import java.net.*;

import network.NetworkParticipant;
import network.STOPTYPE;
import playingfield.PlayingField;
import ai.MastermindAI;

import java.io.*;
import javax.swing.JFrame;

public class Client extends NetworkParticipant {

    private boolean aiEnabled;
    private MastermindAI mmAI;
    private String address = "127.0.0.1";
    private int port = 50004;
    private String code = "";
    private String response = "";
    private String playerName;

    public Client(boolean aiEnabled, String address, int port, String playerName){
        this.aiEnabled = aiEnabled;
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
	
	public void restart(){
		System.out.println("Client: Restart");
	}
    
    public void stop(STOPTYPE stopType){
    	stopped = true;
    	if(stopType == STOPTYPE.WINDOWCLOSED){
    		sendCommand("QUIT");
    	}
		else if(stopType == STOPTYPE.QUIT){
    		sendCommand("QUIT");
			closeWindow();
    	}
    	else{
    		closeWindow();
    	}
    	try{
    		closeIO();
			stop = true;
			if(meThread != null){
				meThread.join();
			}
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
            if(aiEnabled){
                mmAI = new MastermindAI(codeLength, colors);
            }
        }
        else if(args[0].equals("GUESS")){
            if(aiEnabled){
                if(mmAI == null){
                    try{
                        Thread.sleep(200);
                    }
                    catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }
                sendCode(mmAI.next2(response));
            }
            else{
                playingField.activateSendButton();
            }
        }
        else if(args[0].equals("RESULT")){
            response = args[1];
            playingField.addToHistory(code, response);
            if(aiEnabled){
                sendCode(mmAI.next2(response));
            }
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
			playingField.openAgainWindow();
			
        }
		else if(args[0].equals("QUIT")){
			stop(STOPTYPE.RECEIVED);
		}
		else{
			System.err.println("Unknown command received!");
		}
    }
    
}
