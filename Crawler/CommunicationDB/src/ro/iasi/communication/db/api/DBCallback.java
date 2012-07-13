package ro.iasi.communication.db.api;

import java.util.List;
import java.util.Map;

public interface DBCallback {

	public Map<String, Integer> getDictionary();
	public void pushIndexes(List<PageWordDTO> dtos);

	public List<Integer> getWordIds();
	public int getWordsCount();
	public List<DocWordDTO> getDocWordAssociations();
	
}
