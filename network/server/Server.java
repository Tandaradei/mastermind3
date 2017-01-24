
package network.server;

import java.net.*;
import java.io.*;
import java.util.Random;

import network.NetworkParticipant;
import network.GAMESTATE;
import network.STOPTYPE;

import playingfield.PlayingField;
import highscore.Highscore;


public class Server extends NetworkParticipant {
    
    private String allColors = "123456789abcdef";
    private int port;
    private int attempts = 0;
    private int maxAttempts;
    private String playerName = "Client04";
    private ServerSocket serverSocket;
    private String key = "";
    
    
    // Server mit Port "port" und
    // Anzahl der Rateversuche "attempts" erstellen
    public Server(int port, int maxAttempts, int colorsLength, int codeLength){
        this.port = port;
        this.maxAttempts = maxAttempts;
        this.colors = allColors.substring(0, colorsLength);
        this.codeLength = codeLength;
        for(int i = 0; i < codeLength; ++i){
        	key += colors.charAt(0);
        }
    }
	
	public void start(){
		startPlayingField(true, "Set code and wait for client");
	}
	
	public void restart(){
            closeWindow();
            start();
	}
    
    private void startServer() {
        NetworkParticipant me = this;
        Runnable runner = new Runnable() {
            public void run(){
                try {
                    String hostname = getMyAddress();
                    gameState = GAMESTATE.SETUP;
                    if(serverSocket != null){
                        serverSocket.close();
                    }
                    serverSocket = new ServerSocket(port);
                    playingField.setStatusText("Waiting for client on " + hostname + ":" + port + " ...");
                    try{
                    	Socket clientSocket = serverSocket.accept();
                        playingField.setStatusText("Client connected");
                        /*if(in != null){
                            in.close();
                        }*/
                        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        
                        /*if(out != null){
                            out.close();
                        }*/
                        out = new OutputStreamWriter(clientSocket.getOutputStream());
                        
                        meThread = new Thread(me);
                        meThread.start();
                    }
                    catch (SocketException e){
                    	System.err.println("Socket was closed before a client has connected");
                    }
                    

                } catch (IOException e) {
                    e.printStackTrace();
                    System.err.println("Accept failed.");
                }
            }
        };
        
        Thread serverThread = new Thread(runner);
        serverThread.start();
        
    }
    
    
    
    protected void executeCommand(String command){
        String[] args = command.split(" ");
        if(args[0].equals("NEWGAME") && gameState != GAMESTATE.INGAME){
			attempts = 0;
            playerName = args[1];
            sendCommand("SETUP " + codeLength + " " + colors);
            sendCommand("GUESS");
			playingField.setStatusText("Game set up, " + playerName + " is guessing now " + (maxAttempts > 0 ? "(attempt " + attempts+1 + " of " + maxAttempts + ")" : ""));
			gameState = GAMESTATE.INGAME;
        }
        else if(args[0].equals("CHECK") && gameState == GAMESTATE.INGAME){
            attempts++;
            String code = args[1];
            String result = checkKey(key, code);
            playingField.addToHistory(code, result);
            sendCommand("RESULT " + result);
                playingField.setStatusText(playerName + " has guessed " + (maxAttempts > 0 ? "(attempt " + attempts + " of " + maxAttempts + ")" : ""));
                if(result.length() == codeLength && !result.contains("W")){
                    sendCommand("GAMEOVER WIN");
                    playingField.setStatusText(playerName + " has won the game with " + attempts + (maxAttempts > 0 ? "of  " + maxAttempts : "") + " attempts");
                    Highscore.get().add(playerName, codeLength, colors.length(), attempts);
                    gameState = GAMESTATE.GAMEOVER;
                }
                else if(maxAttempts > 0 && attempts >= maxAttempts){
                    sendCommand("GAMEOVER LOSE");
                    playingField.setStatusText(playerName + " has lost the game with " + attempts + " of " + maxAttempts + " attempts");
                    gameState = GAMESTATE.GAMEOVER;
                }
        }
		else if(args[0].equals("QUIT")){
                    stop(STOPTYPE.RECEIVED);
		}
		else{
                    System.err.println("Unknown command received!");
		}
    }
    
    public void sendCode(String code){
    	key = code;
        startServer();
    }

    public void stop(STOPTYPE stopType){
        System.out.println("Server: stop called");
        System.out.println("Server: STOPTYPE: " + stopType);
    	stopped = true;
    	if(stopType == STOPTYPE.WINDOWCLOSED){
            sendCommand("QUIT");
    	}
        else if(stopType == STOPTYPE.QUIT){
            sendCommand("QUIT");
            closeWindow();
    	}
        else{
            restart();
        }
    	try{
            //closeIO();
            
            stop = true;
            if(meThread != null){
                meThread.join();
            }
            
    	}
    	catch(InterruptedException e){
    		e.printStackTrace();
    	}
    	
    	
    	
    	try {
    		if(serverSocket != null){
    			serverSocket.close();
    		}
    		if(clientSocket != null && !clientSocket.isClosed()){
                    clientSocket.shutdownOutput();
                    clientSocket.close();
    		}
	    } catch (IOException e) {
	        System.err.println("Server: Shutdown failed.");
	        System.err.println(e.getMessage());
	    }
    }

    private String getMyAddress() {
        String address = "";
        try(final DatagramSocket socket = new DatagramSocket()){
                socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
                address = socket.getLocalAddress().getHostAddress();
                socket.close();
        } catch(Exception exp){}
        return address;
    }


    // Das eingegebene Codewort überprüfen
    public static String checkKey(String original, String toTest){
        String result = "";
        String helper = "";
        for(int i = 0; i < original.length(); i++){
            if(original.charAt(i) == toTest.charAt(i)){
                helper += 'x';
                result += 'B';
            }
            else{
                helper += 'o';
            }
        }
        for(int i = 0; i < original.length(); i++){
            if(helper.charAt(i) != 'x'){
                for(int u = 0; u < toTest.length(); u++){
                    if(helper.charAt(u) != 'x' && original.charAt(i) == toTest.charAt(u)){
                        helper = helper.substring(0, u) + "x" + helper.substring(u+1);
                        result += 'W';
                        break;
                    }
                }
            }
        }
        if(result.length() == 0){
            result = "0";
        }
        //System.out.println("Comparing " + toTest + " to " + original + " -> " + result);
        return result;
    }
    
    
    
    
    
    
}
// vim: ts=4 sta sw=4 et ai
