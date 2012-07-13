package ro.iasi.vsm;

import java.util.List;

public class DocMatch {

	public int docId;
	public List<Match> matches; // select 

	public DocMatch(int docId, List<Match> matches) {
		this.docId = docId;
		this.matches = matches;
	}

}
