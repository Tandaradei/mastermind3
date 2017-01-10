
package server;

import java.net.*;
import java.io.*;
import java.util.Random;

class Server {
    
    private String sequence = "";
    private int sequenceLength = 4;
    private String colors = "123456789abcdef";
    private int port;
    private Socket socket;
    private int attempts;
    private String playerName = "Client04";
    
    
    // Server mit Port "port" und
    // Anzahl der Rateversuche "attempts" erstellen
    public Server(int port, int attempts){
        this.port = port;
        this.attempts = attempts;
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
    public void generateRandomSequence(String argument) {
        sequence = "";

        Random rand = new Random();
        sequenceLength = rand.nextInt((15 - 2) + 1) + 2;

        for (int i = 0; i < sequenceLength; i++) {
            sequence += colors.charAt((int) (Math.random() * 15));
        }
        newgame(argument);
    }


    // Manuelle Wahl des Codeworts
    public void generateSequence(int sequenceLength, String colors, String argument) {
        this.sequenceLength = sequenceLength;

        for (int i = 0; i < sequenceLength; i++) {
            sequence += colors.charAt((int) (Math.random() * colors.length()));
        }
        newgame(argument);
    }


    // Neues Spiel starten
    public String newgame(String argument) {
        playerName = argument;

        return "SETUP " + sequenceLength + " " + sequence;
    }

    // Das eingegebene Codewort überprüfen
    public static String checkKey(String original, String toTest){
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
    
    
    public static void main (String [] args) throws IOException {
        int port = 50000;
        String hostname = getMyAddress();

        // Server-Socket erzeugen und an Port 50000 binden
        ServerSocket ss = new ServerSocket(50000);

        // Auf eine Client-Verbindung warten und akzeptieren
        System.out.println("Auf Client an "+hostname+":"+port+" warten ...");
        Socket s = ss.accept();
        System.out.println("Verbindung hergestellt!");

        // Eingabestrom
        BufferedReader in =
            new BufferedReader(new InputStreamReader(s.getInputStream()));

        // Ausgabestrom
        Writer out = new OutputStreamWriter(s.getOutputStream());

        // Eingabestrom für Servereingabe
        BufferedReader usr =
                new BufferedReader(new InputStreamReader(System.in));

        // Abwechselnd Stream einlesen bzw. schreiben
        // Abbruch bei EOF (Str+D/Strg+Z) oder Leerzeichen
        while (true) {

            // Eingabestrom lesen
            String line = in.readLine();
            if (line == null) break;
            System.out.println("Client: " + line);

            // Servereingabe (User)
            System.out.print("Server #: ");
            line = usr.readLine();
            if (line == null || line.equals("")) break;

            // Ausgabestrom schreiben
            out.write(String.format("%s%n", line));
            out.flush();
        }

        s.shutdownOutput();
        System.out.println("Verbindung beendet!");
    }
}
// vim: ts=4 sta sw=4 et ai
