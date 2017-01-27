package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.JFrame;
import playingfield.PlayingField;
import playingfield.PlayingFieldListener;

/**
* Base class for Server and Client
*
*/

public abstract class NetworkParticipant implements Runnable {
    protected BufferedReader in;
    protected Writer out;
    protected Socket clientSocket;
    protected PlayingField playingField;
    protected JFrame playingFieldFrame;
    protected boolean stopped = false;
    protected boolean stop = false;
    protected Thread meThread;

    protected String colors;
    protected int codeLength;
	protected GAMESTATE gameState = GAMESTATE.NOTSTARTED;
    
    protected Queue<String> commands;
    protected Thread commandThread;

    public NetworkParticipant(){
    	commands = new LinkedList<String>();
    }

    protected void startPlayingField(boolean isServer, String text){
        playingField = new PlayingField(isServer, colors, codeLength);
        playingField.setStatusText(text);
        playingField.setNetParticipant(this);
        java.awt.EventQueue.invokeLater(() -> {
            playingFieldFrame = new JFrame();
            playingFieldFrame.addWindowListener(new PlayingFieldListener(this));
            //playingFieldFrame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
            playingFieldFrame.setSize(400, 600);
            playingFieldFrame.setResizable(true);
            playingFieldFrame.add(playingField);
            playingFieldFrame.setVisible(true);
            playingFieldFrame.setTitle((isServer ? "Server" : "Client"));
            
        });
    }

    public abstract void start();
	
    public abstract void restart();
	
	protected void resetPlayingField(){
		playingField.reset();
	}



    protected void sendCommand(String command) {
        try{
            if(out != null){
                System.out.println("Sending: " + command);
                out.write(String.format("%s%n", command));
                out.flush();
            }

        } catch(IOException e){
                e.printStackTrace();
        }
    }

    protected void executeNextCommand(){
    	if(commands.size() > 0){
            try{
                if(commandThread != null && commandThread.isAlive()){
                    commandThread.join();
                }
                Runnable runner = new Runnable(){
                    public void run(){
                        executeCommand(commands.poll());
                    }
                };
                commandThread = new Thread(runner);
                commandThread.start();
            }
            catch(InterruptedException e){
                    e.printStackTrace();
            }
    		
    		
    	}
    }
    
    protected abstract void executeCommand(String command);

    public void run() {
        try{
            while (in != null && !stop) {
                // Eingabestrom lesen
                String line = in != null? in.readLine() : null;
                System.out.println("Received: " + line);
                if (line == null || line.equals("")) {
                    break;
                }
                
                commands.add(line);
                Runnable runner = new Runnable(){
                    public void run(){
                        executeNextCommand();
                    }
                };
                Thread runnerThread = new Thread(runner);
                runnerThread.start();
            }
            if(!stopped){
                stop(STOPTYPE.RECEIVED);
            }
        } 
        catch(IOException e){
            //stop(STOPTYPE.RECEIVED);
        }
    }
    
    protected void closeIO(){
    	try{
            if(in != null){
                    in.close();
                    in = null;
            }
            if(out != null){
                    out.close();
                    out = null;
            }
    	} 
    	catch(IOException e){
            e.printStackTrace();
    	}
    }
    
    public abstract void sendCode(String code);
    
    protected void closeWindow(){
    	playingFieldFrame.dispose();
    }
    
    public abstract void stop(STOPTYPE stopType);
    
}
