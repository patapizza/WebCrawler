import java.util.PriorityQueue;

public class Assigner {
	
	private PriorityQueue<Node> domains;
	
	public Assigner() {
		this.domains = new PriorityQueue<Node>();
	}
	
	public void addLinks(String domain, ArrayList<String> links) {
		Node node = null;
		for (node : domains)
			if (node.getDomain().equals(domain))
				break;
		if (!node)
			node = new Node(domain, domain);
		for (String link : links)
			// TODO: regex
			if (link.substring(0, domain.length()).equals(domain))
				node.addPage(link);
			else {
				Node toAdd = new Node(getDomain(link), link);
				toAdd.addReferer(domain);
				domains.add(toAdd);
			}
		domains.add(node);
	}
	
	public Node getNextDomain() {
		Node domain = (Node) domains.peek();
		domain.updateTimestamp();
		return domain;
	}

}
