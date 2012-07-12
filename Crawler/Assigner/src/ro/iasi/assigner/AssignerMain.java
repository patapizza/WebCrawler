package ro.iasi.assigner;

import java.io.IOException;

import ro.iasi.communication.api.AssignerCommunication;
import ro.iasi.communication.impl.AssignerCommunicationImpl;

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
