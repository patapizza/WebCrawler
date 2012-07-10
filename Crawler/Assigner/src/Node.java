
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
	private int timestamp;
	
	/**
	 * 
	 */
	private ArrayList<String> referers;
	
	public Node(String domain) {
		
		this.domain = domain;
		
		
		
	}
	
	
}
