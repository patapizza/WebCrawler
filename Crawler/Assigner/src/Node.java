import java.util.Date;

public class Node {
	
	/**
	 * current domain
	 */
	private String domain; 
	
	/**
	 * pages to crawl
	 */
	private ArrayList<String> pages;
	
	/**
	 * number of references from referers
	 */
	private int referenceCounter;
	
	/**
	 * timestamp of the current domain
	 */
	private Date timestamp;

	/**
	 * 
	 */
	private ArrayList<String> referers;

	public Node(String domain, String url) {
		this.domain = domain;
		this.pages = new ArrayList<String>();
		this.pages.add(url);
		this.referers = new ArrayList<String>();
		this.referenceCounter = 0;
		this.timestamp = new Date();
	}

	public void updateTimestamp() {
		timestamp = new Date();
	}

	public String getDomain() {
		return domain;
	}

	public boolean hasPage(String url) {
		return pages.contains(url);
	}

	public void addPage(String url) {
		if (!pages.contains(url))
			pages.add(url);
	}

	public void addReferer(String referer) {
		if (referers.contains(referer))
			return;
		referers.add(referer);
		++referenceCounter;
	}
	
	
}
