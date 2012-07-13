package ro.iasi.communication.db.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import ro.iasi.communication.db.api.DBRankerCommunication;
import ro.iasi.communication.db.enums.Operation;

public class DBRankerCommunicationImpl implements DBRankerCommunication {

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
	public List<Integer> getWordIds() throws UnknownHostException, IOException, ClassNotFoundException {
		Socket socket = createSocket();

		ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
		objectOutputStream.writeObject(Operation.WORD_IDS);

		ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
		List<Integer> ids = (List<Integer>) objectInputStream.readObject();

		objectInputStream.close();
		socket.close();

		return ids;
	}

	@Override
	public int getWordsCount() {
		// TODO Auto-generated method stub
		return 0;
	}

}
