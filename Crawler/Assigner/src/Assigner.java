import java.util.PriorityQueue;


public class Assigner {
	
	PriorityQueue<Node> domains;
	
	
	public void Assigner() {
		
		
		
	}
	
	/**
	 * check the link and increment the reference counter 
	 */
	public void addLink(String domain, String url) {
		for (node : domains)
			if (node.getDomain().equals(domain)) {
				if (node.hasURL(url))
					return;
				node.addURL(url);
				break;
			}
		domains.add(new Node(domain, url));
	}
	
	/**
	 * 
	 * @return
	 */
	public Node getNextDomain() {
		Node domain = (Node) domains.peek();
		domain.updateTimestamp();
		return domain;
	}

	
}
