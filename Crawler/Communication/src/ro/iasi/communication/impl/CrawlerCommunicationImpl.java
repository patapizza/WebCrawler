package ro.iasi.communication.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import ro.iasi.communication.api.CrawlerCommunication;
import ro.iasi.communication.api.LinksDTO;
import ro.iasi.communication.enums.Operation;

public class CrawlerCommunicationImpl implements CrawlerCommunication {

	// TODO externalize
	public static final String IP = "127.0.0.1";
	public static final int PORT = 6668, CONNECTION_TIMEOUT = 10000;

	@Override
	public LinksDTO getLinks() throws UnknownHostException, IOException, ClassNotFoundException {
		Socket socket = createSocket();

		ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
		objectOutputStream.writeObject(Operation.GET);
		
		ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
		LinksDTO linksDTO = (LinksDTO) objectInputStream.readObject();

		objectInputStream.close();
		socket.close();

		return linksDTO;
	}

	@Override
	public void sendLinks(LinksDTO linksDTO) throws UnknownHostException, IOException {
		Socket socket = createSocket();

		ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
		objectOutputStream.writeObject(Operation.SEND);
		objectOutputStream.writeObject(linksDTO);

		objectOutputStream.close();
		socket.close();
	}

	private Socket createSocket() throws UnknownHostException, IOException {
		Socket socket = new Socket();
		socket.connect(new InetSocketAddress(InetAddress.getByName(CrawlerCommunicationImpl.IP), CrawlerCommunicationImpl.PORT),
				CrawlerCommunicationImpl.CONNECTION_TIMEOUT);

		return socket;
	}

}
