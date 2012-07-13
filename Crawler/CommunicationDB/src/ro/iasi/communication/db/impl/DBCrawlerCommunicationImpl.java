package ro.iasi.communication.db.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

import ro.iasi.communication.db.api.DBCrawlerCommunication;
import ro.iasi.communication.db.api.IndexesDTO;
import ro.iasi.communication.db.api.PageWordDTO;
import ro.iasi.communication.db.enums.Operation;

public class DBCrawlerCommunicationImpl implements DBCrawlerCommunication {

	// TODO externalize
	public static final String IP = "127.0.0.1";
	public static final int PORT = 8889, CONNECTION_TIMEOUT = 10000;

	private Socket createSocket() throws UnknownHostException, IOException {
		Socket socket = new Socket();
		socket.connect(new InetSocketAddress(InetAddress.getByName(DBCrawlerCommunicationImpl.IP), DBCrawlerCommunicationImpl.PORT),
				DBCrawlerCommunicationImpl.CONNECTION_TIMEOUT);

		return socket;
	}

	@Override
	public Map<String, Integer> getDictionary() throws UnknownHostException, IOException, ClassNotFoundException {
		Socket socket = createSocket();

		ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
		objectOutputStream.writeObject(Operation.GET);

		ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
		Map<String, Integer> dictionary = (Map<String, Integer>) objectInputStream.readObject();

		objectInputStream.close();
		socket.close();

		return dictionary;
	}

	@Override
	public void pushIndexes(List<PageWordDTO> dtos) throws UnknownHostException, IOException {
		Socket socket = createSocket();

		ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
		objectOutputStream.writeObject(Operation.SEND);
		objectOutputStream.writeObject(dtos);

		objectOutputStream.close();
		socket.close();
	}

}
