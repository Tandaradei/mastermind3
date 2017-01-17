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

public abstract class NetworkParticipant implements Runnable {
    protected BufferedReader in;
    protected Writer out;
    protected Socket clientSocket;
    protected PlayingField playingField;
    protected JFrame playingFieldFrame;
    protected boolean stopped = false;
    protected Thread meThread;

    protected String colors;
    protected int codeLength;
    
    protected Queue<String> commands;
    protected Thread commandThread;

    public NetworkParticipant(){
    	commands = new LinkedList<String>();
    }

    protected void startPlayingField(boolean isServer){
        playingField = new PlayingField(isServer, colors, codeLength);
        playingField.setNetParticipant(this);
        java.awt.EventQueue.invokeLater(() -> {
        	playingFieldFrame = new JFrame();
        	playingFieldFrame.addWindowListener(new PlayingFieldListener(this));
        	playingFieldFrame.setSize(400, 600);
        	playingFieldFrame.setResizable(true);
        	playingFieldFrame.add(playingField);
        	playingFieldFrame.setVisible(true);
        	playingFieldFrame.setTitle((isServer ? "Server" : "Client"));
            
        });
    }

    public abstract void start();


    protected void sendCommand(String command) {
        System.out.println("Sending: " + command);
        try{
            if(out != null){
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
            while (in != null) {
                // Eingabestrom lesen
                String line = in.readLine();
                if (line == null || line.equals("")) {
                	System.out.println("null received");
                    break;
                }
                System.out.println("Received: " + line);
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
            	stop(false);
        	}
        } 
        catch(IOException e){
                e.printStackTrace();
        }
    }
    
    protected void closeIO(){
    	try{
    		System.out.println("Closing IO");
    		if(in != null){
    			in.close();
    			in = null;
    			System.out.println("in closed");
    		}
    		if(out != null){
    			out.close();
    			out = null;
    			System.out.println("out closed");
    		}
    		System.out.println("IO closed");
    	} 
    	catch(IOException e){
            e.printStackTrace();
    	}
    }
    
    public abstract void sendCode(String code);
    
    protected void closeWindow(){
    	playingFieldFrame.dispose();
    	System.out.println("Window closed");
    }
    
    public abstract void stop(boolean initiated);
    
}
