package ro.iasi.vsm;

import java.util.List;

public class DBInterfaceImpl implements DBInterface {
	public void initializeDocMatches() {
		List<int> docIDs = getDocIDs();
		for (int docID : docIDs) {
			List<int> wordIDs = getWordIDs(docID);
			List<Match> matches = new ArrayList<Match>();
			for (int wordID : wordIDs)
				matches.add(new Match(wordID, getRelationsCount(docID, wordID)));
			DocMatch docMatch = new DocMatch(docID, matches);
			
		}
	}

}
