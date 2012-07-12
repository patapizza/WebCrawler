package ro.iasi.db;

import java.io.IOException;
import java.util.Map;

import ro.iasi.communication.db.api.DBCallback;
import ro.iasi.communication.db.api.DBServerCommunication;
import ro.iasi.communication.db.api.IndexesDTO;
import ro.iasi.communication.db.impl.DBServerCommunicationImpl;

public class DBMain {

	public static final int PORT = 8889;

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		DBManager dbManager = new DBManager();
		dbManager.connect();

		DBServerCommunication dbServerCommunication = new DBServerCommunicationImpl();
		dbServerCommunication.registerListener(new DBCallback() {
			
			@Override
			public void pushIndexes(IndexesDTO indexesDTO) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public Map<String, Integer> getDictionary() {
				// TODO Auto-generated method stub
				return null;
			}
		});
		
		dbServerCommunication.startListening();
	}

}
