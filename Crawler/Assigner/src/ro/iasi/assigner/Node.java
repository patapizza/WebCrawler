package ro.iasi.assigner;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class Node {

	private String domain;
	private List<String> pages;
	private Date timestamp;
	private List<String> referers;

	public Node(String domain, String url) {
		this.domain = domain;
		this.pages = new ArrayList<String>();
		this.pages.add(url);
		this.referers = new ArrayList<String>();
		this.timestamp = new Date();
	}

	public void updateTimestamp() {
		timestamp = new Date();
	}

	public String getDomain() {
		return domain;
	}

	public List<String> getReferers() {
		return referers;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void addPage(String url) {
		if (!pages.contains(url))
			pages.add(url);
	}

	public void addReferer(String referer) {
		if (referers.contains(referer))
			return;
		referers.add(referer);
	}

	public List<String> getPages() {
		return pages;
	}

}
