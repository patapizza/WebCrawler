package ro.iasi.communication.db;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class DBMain {
	
	public static final int PORT = 8889;

	public static void main(String[] args) throws IOException {
		DBManager dbManager = new DBManager();
		dbManager.connect();
		
		ServerSocket serverSocket = new ServerSocket();
		serverSocket.bind(new InetSocketAddress(InetAddress.getByName("0.0.0.0"), DBMain.PORT));

		while (true) {
			Socket socket = serverSocket.accept();

			ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
//			Operation operation = (Operation) objectInputStream.readObject();
//
//			if (operation == Operation.SEND) {
//				listener.sendLinks((LinksDTO) objectInputStream.readObject());
//			} else if (operation == Operation.GET) {
//				ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
//				objectOutputStream.writeObject(listener.getLinks());
//				objectOutputStream.close();
//			}
//
//			objectInputStream.close();
			socket.close();
		}
	}

}
