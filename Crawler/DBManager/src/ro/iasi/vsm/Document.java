package ro.iasi.vsm;

public class Document {

	private String domain;
	private String page;
	private List<Tuple> words;

	public Document(String domain, String page, List<Tuple> words) {
		this.domain = domain;
		this.page = page;
		this.words = words;
	}

}
