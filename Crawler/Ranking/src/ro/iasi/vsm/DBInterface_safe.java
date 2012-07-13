package ro.iasi.vsm;

import java.util.List;

public interface DBInterface {
	public List<int> getWordIDs(int docID);
	public int getWordsCount();
	public List<int> getDocIDs();
	public int getRelationsCount(int docID, int wordID);
}
