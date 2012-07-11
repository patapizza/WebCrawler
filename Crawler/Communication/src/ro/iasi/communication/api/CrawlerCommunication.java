package ro.iasi.communication.api;

public interface CrawlerCommunication {
	
	public void sendLinks(LinksDTO linksDTO);
	public LinksDTO getLinks();

}
