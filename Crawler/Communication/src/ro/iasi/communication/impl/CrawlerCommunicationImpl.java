package ro.iasi.communication.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import ro.iasi.communication.api.CrawlerCommunication;
import ro.iasi.communication.api.LinksDTO;
import ro.iasi.communication.enums.Operation;

public class CrawlerCommunicationImpl implements CrawlerCommunication {
	
	// TODO externalize
	public static final String IP = "127.0.0.1";
	public static final int PORT = 6666;

	@Override
	public LinksDTO getLinks() throws UnknownHostException, IOException, ClassNotFoundException {
		Socket socket = createSocketForOperation(Operation.GET);
		
		ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
		LinksDTO linksDTO = (LinksDTO) objectInputStream.readObject();
		
		objectInputStream.close();
		socket.close();
		
		return linksDTO;
	}

	@Override
	public void sendLinks(LinksDTO linksDTO) throws UnknownHostException, IOException {
		Socket socket = createSocketForOperation(Operation.SEND);
		
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
		objectOutputStream.writeObject(linksDTO);
		
		objectOutputStream.close();
		socket.close();
	}
	
	private Socket createSocketForOperation(Operation operation) throws UnknownHostException, IOException {
		Socket socket = new Socket(InetAddress.getByName(CrawlerCommunicationImpl.IP), CrawlerCommunicationImpl.PORT);
		
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
		objectOutputStream.writeObject(operation);
		objectOutputStream.close();
		
		return socket;
	}

}
