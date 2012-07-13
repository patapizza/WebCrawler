package ro.iasi.db;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import ro.iasi.communication.db.api.DBCallback;
import ro.iasi.communication.db.api.DBServerCommunication;
import ro.iasi.communication.db.api.DocWordDTO;
import ro.iasi.communication.db.api.IndexesDTO;
import ro.iasi.communication.db.api.PageWordDTO;
import ro.iasi.communication.db.impl.DBServerCommunicationImpl;

public class DBMain {

	public static final int PORT = 8889;

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		final DBManager dbManager = new DBManager();
		dbManager.connect();

		DBServerCommunication dbServerCommunication = new DBServerCommunicationImpl();
		dbServerCommunication.registerListener(new DBCallback() {
			
			@Override
			public void pushIndexes(List<PageWordDTO> dtos) {
				dbManager.storeIndexes(dtos);
			}
			
			@Override
			public Map<String, Integer> getDictionary() {
				return dbManager.getDictionary();
			}

			@Override
			public List<Integer> getWordIds() {
				return dbManager.getWordIds();
			}

			@Override
			public int getWordsCount() {
				return dbManager.getWordsCount();
			}

			@Override
			public List<DocWordDTO> getDocWordAssociations() {
				return dbManager.getDocWordAssociations();
			}
		});
		
		dbServerCommunication.startListening();
	}

}
