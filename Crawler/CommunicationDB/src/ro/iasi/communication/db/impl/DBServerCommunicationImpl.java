package ro.iasi.communication.db.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import ro.iasi.communication.db.api.DBCallback;
import ro.iasi.communication.db.api.DBServerCommunication;
import ro.iasi.communication.db.api.PageWordDTO;
import ro.iasi.communication.db.enums.Operation;

public class DBServerCommunicationImpl implements DBServerCommunication {
	
	public static final int PORT = 8889;
	
	private ServerSocket serverSocket;
	private DBCallback listener;
	
	public DBServerCommunicationImpl() throws IOException {
		this.serverSocket = new ServerSocket();
	}

	@Override
	public void startListening() throws UnknownHostException, IOException, ClassNotFoundException {
		serverSocket.bind(new InetSocketAddress(InetAddress.getByName("0.0.0.0"), DBServerCommunicationImpl.PORT));
		
		while (true) {
			Socket socket = serverSocket.accept();
			
			ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
			Operation operation = (Operation) objectInputStream.readObject();
			
			if (operation == Operation.SEND) {
				listener.pushIndexes((List<PageWordDTO>) objectInputStream.readObject());
			} else if (operation == Operation.GET) {
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
				objectOutputStream.writeObject(listener.getDictionary());
				objectOutputStream.close();
			} else if (operation == Operation.WORD_IDS) {
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
				objectOutputStream.writeObject(listener.getWordIds());
				objectOutputStream.close();
			} else if (operation == Operation.WORD_COUNT) {
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
				objectOutputStream.writeObject(listener.getWordsCount());
				objectOutputStream.close();
			}
			
			objectInputStream.close();
			socket.close();
		}
	}

	@Override
	public void registerListener(DBCallback linksCallback) {
		listener = linksCallback;
	}

}
