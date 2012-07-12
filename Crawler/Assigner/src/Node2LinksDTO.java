import ro.iasi.communication.api.LinksCallback;
import ro.iasi.communication.api.LinksDTO;

public class Node2LinksDTO implements LinksCallback {

	private Assigner assigner;

	public Node2LinksDTO(Assigner assigner) {
		this.assigner = assigner;
	}

	public LinksDTO getLinks() {
		Node node = assigner.getNextDomain();
		if (!node)
			return null;
		LinksDTO linksDTO = new LinksDTO();
		linksDTO.setUrlRoot(node.getDomain());
		linksDTO.setUrls(node.getPages());
		return linksDTO;
	}

	public void sendLinks(LinksDTO linksDTO) {
		assigner.addLinks(linksDTO.getUrlRoot(), linksDTO.getUrls());
	}

}
