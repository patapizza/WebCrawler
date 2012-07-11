package ro.iasi.communication.api;

public interface LinksCallback {

	public LinksDTO getLinks();
	public void sendLinks(LinksDTO linksDTO);
	
}
