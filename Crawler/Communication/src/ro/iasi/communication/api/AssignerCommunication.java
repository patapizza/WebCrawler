package ro.iasi.communication.api;

import java.io.IOException;
import java.net.UnknownHostException;

public interface AssignerCommunication {

	public void registerListener(LinksCallback linksCallback);
	public void startListening() throws UnknownHostException, IOException, ClassNotFoundException;
	
}
