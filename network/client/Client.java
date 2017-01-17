package network.client;

import java.net.*;

import network.NetworkParticipant;
import playingfield.PlayingField;

import java.io.*;
import javax.swing.JFrame;

public class Client extends NetworkParticipant {

    private String address = "127.0.0.1";
    private int port = 50004;

    public Client(String address, int port){

        this.address = address;
        this.port = port;
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
                        System.out.println("Client: Verbindung hergestellt!");
                     // Eingabestrom

                        //startPlayingField(false);
                        sendCommand("NEWGAME Client04");
                        Thread netThread = new Thread(me);
                        netThread.start();
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

    protected void executeCommand(String command){
        String[] args = command.split(" ");
        if(args[0].equals("SETUP")){
            colors = args[1];
            codeLength = Integer.parseInt(args[2]);
            startPlayingField(false);
        }
    }
    
}
