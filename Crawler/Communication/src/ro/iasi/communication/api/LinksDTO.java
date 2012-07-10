package ro.iasi.communication.api;

import java.io.Serializable;
import java.util.List;

public class LinksDTO implements Serializable {

	private static final long serialVersionUID = -5291716690774991189L;
	
	private String urlRoot;
	private List<String> urls;
	
	public String getUrlRoot() {
		return urlRoot;
	}
	public void setUrlRoot(String urlRoot) {
		this.urlRoot = urlRoot;
	}
	public List<String> getUrls() {
		return urls;
	}
	public void setUrls(List<String> urls) {
		this.urls = urls;
	}

}
