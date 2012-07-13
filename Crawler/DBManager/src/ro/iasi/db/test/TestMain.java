package ro.iasi.db.test;

import java.io.IOException;
import java.net.UnknownHostException;

import ro.iasi.communication.db.api.DBCrawlerCommunication;
import ro.iasi.communication.db.impl.DBCrawlerCommunicationImpl;

public class TestMain {

	public static void main(String[] args) throws UnknownHostException, ClassNotFoundException, IOException {
		DBCrawlerCommunication communication = new DBCrawlerCommunicationImpl();
		System.out.println(communication.getDictionary().size());
	}

}
