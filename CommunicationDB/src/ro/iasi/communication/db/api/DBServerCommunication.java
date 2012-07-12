package ro.iasi.communication.db.api;

import java.io.IOException;
import java.net.UnknownHostException;

public interface DBServerCommunication {

	public void registerListener(DBCallback dbCallback);
	public void startListening() throws UnknownHostException, IOException, ClassNotFoundException;
	
}
