package ro.iasi.communication.api;

import java.io.IOException;
import java.net.UnknownHostException;

public interface AssignerCommunication {

	public void startListening() throws UnknownHostException, IOException;
	
}
