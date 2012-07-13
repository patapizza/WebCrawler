package ro.iasi.communication.db.api;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

public interface DBRankerCommunication {

	public List<Integer> getWordIds() throws UnknownHostException, IOException, ClassNotFoundException;
	public int getWordsCount() throws IOException;
	
}
