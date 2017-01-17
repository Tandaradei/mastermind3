package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.JFrame;
import playingfield.PlayingField;

public abstract class NetworkParticipant implements Runnable {
    protected BufferedReader in;
    protected Writer out;
    protected Socket clientSocket;
    protected playingfield.PlayingField playingField;

    protected String colors;
    protected int codeLength;

    public NetworkParticipant(){
    }

    protected void startPlayingField(boolean isServer){
        playingField = new playingfield.PlayingField(isServer, colors, codeLength);
        java.awt.EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.setSize(400, 600);
            frame.setResizable(true);
            frame.add(playingField);
            frame.setVisible(true);
            frame.setTitle((isServer ? "Server" : "Client"));
        });
    }

    public abstract void start();


    protected void sendCommand(String command) {
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

    protected abstract void executeCommand(String command);

    public void run() {
        try{
            while (true) {
                            // Eingabestrom lesen
                String line = in.readLine();
                if (line == null) {
                    break;
                }
                System.out.println("Received: " + line);
                Runnable runner = new Runnable(){
                    public void run(){
                        executeCommand(line);
                    }
                };
                Thread runnerThread = new Thread(runner);
                runnerThread.start();
            }
        } catch(IOException e){
                e.printStackTrace();
        }
    }
}
