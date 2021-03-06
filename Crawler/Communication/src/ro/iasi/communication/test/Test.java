package ro.iasi.communication.test;

import java.io.IOException;
import java.net.UnknownHostException;

import ro.iasi.communication.api.AssignerCommunication;
import ro.iasi.communication.api.CrawlerCommunication;
import ro.iasi.communication.api.LinksCallback;
import ro.iasi.communication.api.LinksDTO;
import ro.iasi.communication.impl.AssignerCommunicationImpl;
import ro.iasi.communication.impl.CrawlerCommunicationImpl;

/**
 * 
 * @author David
 *
 * Just a simple test class, no JUnit, sorry ;)
 *
 */
public class Test {

	public static void main(String[] args) throws UnknownHostException, ClassNotFoundException, IOException, InterruptedException {
		// server side
		Thread assigner = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					AssignerCommunication assignerCommunication = new AssignerCommunicationImpl();
					assignerCommunication.registerListener(new LinksCallback() {
						
						@Override
						public void sendLinks(LinksDTO linksDTO) {
							System.out.println(linksDTO);
						}
						
						@Override
						public LinksDTO getLinks() {
							LinksDTO linksDTO = new LinksDTO();
							linksDTO.setUrlRoot("google.com");
							linksDTO.getUrls().add("google.com/pepa");
							linksDTO.getUrls().add("google.com/franta");
							return linksDTO;
						}
					});
					assignerCommunication.startListening();
				} catch (Exception ex) {
					System.out.println("test server exception: " + ex + "\n");
					ex.printStackTrace();
				}
			}
		});
		assigner.start();
		
		// client side test 1
		CrawlerCommunication crawlerCommunication = new CrawlerCommunicationImpl();
		System.out.println(crawlerCommunication.getLinks());
		
		// client side test 2
		LinksDTO linksDTO = new LinksDTO();
		linksDTO.setUrlRoot("google.ro");
		linksDTO.getUrls().add("google.ro/aaa");
		linksDTO.getUrls().add("google.ro/bbb");
		
		crawlerCommunication.sendLinks(linksDTO);
		
		Thread.sleep(10000);
		System.exit(0);
	}

}
