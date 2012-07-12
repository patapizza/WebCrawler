package ro.iasi.communication.db.api;

import java.util.Map;

public interface DBCallback {

	public Map<String, Integer> getDictionary();
	public void pushIndexes(IndexesDTO indexesDTO);

}
