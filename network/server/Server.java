
package network.server;

import java.net.*;
import java.io.*;
import java.util.Random;

import network.NetworkParticipant;
import playingfield.PlayingField;


public class Server extends NetworkParticipant {
    
    private String allColors = "123456789abcdef";
    private int port;
    private int attempts = 1;
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
    
    public void start () {
        NetworkParticipant me = this;
        Runnable runner = new Runnable() {
            public void run(){
                try {
                    String hostname = getMyAddress();
					startPlayingField(true, "Auf Client an "+hostname+":"+port+" warten ...");
					System.out.println("Auf Client an "+hostname+":"+port+" warten ...");
                    serverSocket = new ServerSocket(port);
                    try{
                    	Socket clientSocket = serverSocket.accept();
                    	System.out.println("Server: Connected!");
						playingField.setStatusText("Client connected");
                        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                        // Ausgabestrom
                        out = new OutputStreamWriter(clientSocket.getOutputStream());

                        //startPlayingField(true);
                        
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
        if(args[0].equals("NEWGAME")){
			attempts = 1;
            playerName = args[1];
            sendCommand("SETUP " + codeLength + " " + colors);
            sendCommand("GUESS");
			playingField.setStatusText("Game set up, " + playerName + " is guessing now " + (maxAttempts > 0 ? "(attempt " + attempts + " of " + maxAttempts + ")" : ""));
        }
        else if(args[0].equals("CHECK")){
			attempts++;
            String code = args[1];
            String result = checkKey(code, key);
            playingField.addToHistory(code, result);
            sendCommand("RESULT " + result);
			playingField.setStatusText(playerName + " has guessed " + (maxAttempts > 0 ? "(attempt " + attempts + " of " + maxAttempts + ")" : ""));
			if(result.length() == codeLength && !result.contains("W")){
				sendCommand("GAMEOVER WIN");
				playingField.setStatusText(playerName + " has won the game with " + attempts + (maxAttempts > 0 ? "of  " + maxAttempts : "") + " attempts");
			}
			else if(maxAttempts > 0 && attempts > maxAttempts){
				sendCommand("GAMEOVER LOSE");
				playingField.setStatusText(playerName + " has lost the game with " + attempts + " of  " + maxAttempts + " attempts");
			}
        }
		else{
			System.err.println("Unknow command received!");
		}
    }
    
    public void sendCode(String code){
    	key = code;
    }

    public void stop(boolean initiated){
    	stopped = true;
    	if(initiated){
    		sendCommand("");
    	}
    	
    	try{
    		closeIO();
			if(meThread != null){
				meThread.join();
			}
    	}
    	catch(InterruptedException e){
    		e.printStackTrace();
    	}
    	
    	System.out.println("Server: stop called!");
    	
    	
    	try {
    		System.out.println("Server: Stopping connection!");
    		if(serverSocket != null){
    			serverSocket.close();
    			System.out.println("Server: ServerSocket closed!");
    		}
    		if(clientSocket != null && !clientSocket.isClosed()){
				clientSocket.shutdownOutput();
    			clientSocket.close();
    		}
    		System.out.println("Server: Connection stopped!");
	    } catch (IOException e) {
	        System.err.println("Server: Shutdown failed.");
	        e.printStackTrace();
	    }
    	if(!initiated){
    		start();
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
    private String checkKey(String original, String toTest){
        String result = "";
        for(int i = 0; i < original.length(); i++){
            if(original.charAt(i) == toTest.charAt(i)){
                String newMyKey = original.substring(0, i) + 'x' + original.substring(i+1);
                String newToTest = toTest.substring(0, i) + 'x' + toTest.substring(i+1);
                original = newMyKey;
                toTest = newToTest;
                result += 'B';
            }
        }
        for(int i = 0; i < original.length(); i++){
            if(original.charAt(i) != 'x'){
                for(int u = 0; u < toTest.length(); u++){
                    if(original.charAt(i) == toTest.charAt(u)){
                        String newMyKey = original.substring(0, i) + 'x' + original.substring(i+1);
                        String newToTest = toTest.substring(0, u) + 'x' + toTest.substring(u+1);
                        original = newMyKey;
                        toTest = newToTest;
                        result += 'W';
                        break;
                    }
                }
            }
        }
        if(result.length() > 0){
            return result;
        }
        return "0";
    }
    
    
    
    
    
    
}
// vim: ts=4 sta sw=4 et ai
