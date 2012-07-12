package ro.iasi.assigner;

import java.io.IOException;
import java.net.UnknownHostException;

import ro.iasi.communication.api.AssignerCommunication;
import ro.iasi.communication.api.CrawlerCommunication;
import ro.iasi.communication.api.LinksCallback;
import ro.iasi.communication.api.LinksDTO;
import ro.iasi.communication.impl.AssignerCommunicationImpl;
import ro.iasi.communication.impl.CrawlerCommunicationImpl;

public class AssignerMain {

	/**
	 * @param args
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args) throws IOException,
			ClassNotFoundException {
		// server side
		Thread assigner = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					AssignerCommunication assignerCommunication = new AssignerCommunicationImpl();
					assignerCommunication.registerListener(new Assigner());
					assignerCommunication.startListening();
				} catch (Exception ex) {
					System.out.println("test server exception: " + ex + "\n");
					ex.printStackTrace();
				}
			}
		});
		assigner.start();
	}
}
