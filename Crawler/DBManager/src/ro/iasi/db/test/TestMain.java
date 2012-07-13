package ro.iasi.db.test;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import ro.iasi.communication.db.api.DBCrawlerCommunication;
import ro.iasi.communication.db.api.IndexesDTO;
import ro.iasi.communication.db.impl.DBCrawlerCommunicationImpl;

public class TestMain {

	public static void main(String[] args) throws UnknownHostException, ClassNotFoundException, IOException {
		DBCrawlerCommunication communication = new DBCrawlerCommunicationImpl();
		
		Map<String, Integer> dictionary = communication.getDictionary();
		System.out.println(dictionary.size());
		
		Map<String, Map<String, List<Integer>>> indexes = new LinkedHashMap<>();
		List<Integer> wordIds = new ArrayList<>();
		wordIds.add(dictionary.get("dog"));
		Map<String, List<Integer>> urls = new LinkedHashMap<>();
		urls.put("google.com/assadasd", wordIds);
		indexes.put("google.com", urls);
		
		IndexesDTO indexesDTO = new IndexesDTO();
		indexesDTO.setIndexes(indexes);
		
//		communication.pushIndexes(indexesDTO);
	}

}
