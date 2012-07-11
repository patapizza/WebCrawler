import java.util.Date;
import java.util.ArrayList;

public class Node {
	
	private String domain; 
	private ArrayList<String> pages;
	private int referenceCounter;
	private Date timestamp;
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
