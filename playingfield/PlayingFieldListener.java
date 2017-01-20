package playingfield;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import network.NetworkParticipant;
import network.STOPTYPE;

public class PlayingFieldListener implements WindowListener {

	private NetworkParticipant netParticipant;
	
	public PlayingFieldListener(NetworkParticipant netParticipant){
		this.netParticipant = netParticipant;
	}
	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("windowClosed called on PlayingField");
		netParticipant.stop(STOPTYPE.WINDOWCLOSED);
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("windowClosing called on PlayingField");
		netParticipant.stop(STOPTYPE.WINDOWCLOSED);
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
}
