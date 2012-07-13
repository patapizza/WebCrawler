package ro.iasi.communication.db.api;

public class PageWordDTO {

	String domain;
	String URL;
	String title;
	
	Integer word;
	
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getWord() {
		return word;
	}
	public void setWord(Integer word) {
		this.word = word;
	}

}
