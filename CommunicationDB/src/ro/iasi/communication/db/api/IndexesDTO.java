package ro.iasi.communication.db.api;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class IndexesDTO implements Serializable {
	
	private static final long serialVersionUID = 1801705311856265636L;
	
	// string, string, int -> domain, url, wordId
	Map<String, Map<String, List<Integer>>> indexes = new LinkedHashMap<>();

	public Map<String, Map<String, List<Integer>>> getIndexes() {
		return indexes;
	}

	public void setIndexes(Map<String, Map<String, List<Integer>>> indexes) {
		this.indexes = indexes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((indexes == null) ? 0 : indexes.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IndexesDTO other = (IndexesDTO) obj;
		if (indexes == null) {
			if (other.indexes != null)
				return false;
		} else if (!indexes.equals(other.indexes))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "IndexesDTO [indexes=" + indexes + "]";
	} 

}
