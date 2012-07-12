package ro.iasi.communication.db.api;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Map;


public interface DBCrawlerCommunication {
	
	// from this map crawler can quicky find word in dictionary and obtain it's id
	public Map<String, Integer> getDictionary() throws UnknownHostException, IOException, ClassNotFoundException;
	
	public void pushIndexes(IndexesDTO indexesDTO) throws UnknownHostException, IOException;

}
