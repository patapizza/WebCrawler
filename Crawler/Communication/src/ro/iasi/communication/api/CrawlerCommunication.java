package ro.iasi.communication.api;

import java.io.IOException;
import java.net.UnknownHostException;

public interface CrawlerCommunication {
	
	public void sendLinks(LinksDTO linksDTO) throws UnknownHostException, IOException;
	public LinksDTO getLinks() throws UnknownHostException, IOException, ClassNotFoundException;

}
