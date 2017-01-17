
package network.server;

import java.net.*;
import java.io.*;
import java.util.Random;

import network.NetworkParticipant;
import playingfield.PlayingField;


public class Server extends NetworkParticipant {
    
    private String allColors = "123456789abcdef";
    private int port;
    private int attempts;
    private String playerName = "Client04";
    private ServerSocket serverSocket;
    private String key = "";
    
    
    // Server mit Port "port" und
    // Anzahl der Rateversuche "attempts" erstellen
    public Server(int port, int attempts, int colorsLength, int codeLength){
        this.port = port;
        this.attempts = attempts;
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
                    startPlayingField(true);
                    String hostname = getMyAddress();
                    serverSocket = new ServerSocket(port);
                    System.out.println("Auf Client an "+hostname+":"+port+" warten ...");
                    try{
                    	Socket clientSocket = serverSocket.accept();
                    	System.out.println("Server: Verbindung hergestellt!");

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
            playerName = args[1];
            sendCommand("SETUP " + codeLength + " " + colors);
            sendCommand("GUESS");
        }
        if(args[0].equals("CHECK")){
            String code = args[1];
            String result = checkKey(code, key);
            playingField.addToHistory(code, result);
            sendCommand("RESULT " + result);
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
    		meThread.join();
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

    public static String getMyAddress() {
        String address = "";
        try(final DatagramSocket socket = new DatagramSocket()){
                socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
                address = socket.getLocalAddress().getHostAddress();
                socket.close();
        } catch(Exception exp){}
        return address;
    }


    // Automatische Wahl des Codeworts
    /*
    public void generateRandomSequence(String argument) {
        sequence = "";

        Random rand = new Random();
        sequenceLength = rand.nextInt((15 - 2) + 1) + 2;

        for (int i = 0; i < sequenceLength; i++) {
            sequence += colors.charAt((int) (Math.random() * 15));
        }
        newgame(argument);
    }
    */

    // Manuelle Wahl des Codeworts
    /*
    public void generateSequence(int sequenceLength, String colors, String argument) {
        this.sequenceLength = sequenceLength;

        for (int i = 0; i < sequenceLength; i++) {
            sequence += colors.charAt((int) (Math.random() * colors.length()));
        }
        newgame(argument);
    }
    */

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
