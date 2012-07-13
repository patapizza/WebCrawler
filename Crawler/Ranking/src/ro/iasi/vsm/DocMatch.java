package ro.iasi.vsm;

public class DocMatch {

	public int docId;
	public List<Match> matches;

	public DocMatch(int docId, List<Match> matches) {
		this.docId = docId;
		this.matches = matches;
	}

}
