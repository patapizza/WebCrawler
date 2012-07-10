package ro.iasi.communication.impl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;

import ro.iasi.communication.api.AssignerCommunication;

public class AssignerCommunicationImpl implements AssignerCommunication {
	
	public static final int PORT = 6666;
	
	private ServerSocket serverSocket;
	
	public AssignerCommunicationImpl() throws IOException {
		this.serverSocket = new ServerSocket();
	}

	@Override
	public void startListening() throws UnknownHostException, IOException {
		serverSocket.bind(new InetSocketAddress(InetAddress.getByName("0.0.0.0"), AssignerCommunicationImpl.PORT));
		// TODO
	}

}
