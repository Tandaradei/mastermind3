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
                    System.out.println("Try to connect to " + address + ":" + port);
                    if(clientSocket.isConnected()) {
                        System.out.println("Connected");
                        meThread = new Thread(me);
                        meThread.start();
                        sendCommand("NEWGAME " + playerName);
                        
                    } else {
                        System.err.println("Couldn't connect to server");
                    }

                } catch(IOException e){
                        System.err.println(e.getMessage());
                }
            }
        };

        Thread clientThread = new Thread(runner);
        clientThread.start();

    }
	
	public void restart(){
           start();
	}
    
    public void stop(STOPTYPE stopType){
        System.out.println("Client: stop called");
        System.out.println("Client: STOPTYPE: " + stopType);
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
        /*
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
    	*/
    	
    	try {
            if(clientSocket != null && !clientSocket.isClosed()){
                clientSocket.shutdownOutput();
                clientSocket.close();
            }
	    } catch (IOException e) {
	        System.err.println(e.getMessage());
	    }
    }

    public void sendCode(String code){
    	this.code = code;
    	sendCommand("CHECK " + code);
    }
    
    protected void executeCommand(String command){
        if(command == null){
            command = "";
        }
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
                // If AI wasn't initialized yet, wait short time
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
                if(playingField != null){
                    playingField.activateSendButton();
                }
                else{
                    System.err.println("Error with playingfield");
                }
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
