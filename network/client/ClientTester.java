package network.client;

import playingfield.PlayingField;

public class ClientTester {

	public static void main(String[] args){
		String address = args.length > 0 ? args[0] : "141.18.44.38";
		int port = args.length > 0 ? Integer.parseInt(args[1]) : 50004;
		PlayingField playingField = new PlayingField(false, "0123", 4);
		//playingField.setVisible(true);
		Client client = new Client(playingField, address, port);
		client.start();
	}
}
