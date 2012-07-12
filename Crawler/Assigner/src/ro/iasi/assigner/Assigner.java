import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class Assigner {
	
	private Queue<Node> domains;
	
	public Assigner() {
		this.domains = new PriorityQueue<Node>(1000, new Comparator<Node>() {
			@Override
			public int compare(Node n1, Node n2) {
				return (n1.getReferers().size() < n2.getReferers().size()) ? 1 : -1;
			}

		});
	}
	
	public void addLinks(String domain, List<String> links) {
		Node node = null;
		for (node : domains)
			if (node.getDomain().equals(domain))
				break;
		if (!node)
			node = new Node(domain, domain);
		for (String link : links)
			// TODO: regex to only use absolute paths
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
		Date now = new Date(System.currentTimeMillis() - 3600);
		Node domain = null;
		for (domain : domains)
			if (domain.getTimestamp().after(now))
				break;
		if (!domain)
			return null;
		domain.updateTimestamp();
		return domain;
	}

}
