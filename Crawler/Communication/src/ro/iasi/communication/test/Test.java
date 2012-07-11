package ro.iasi.communication.test;

import java.io.IOException;
import java.net.UnknownHostException;

import ro.iasi.communication.api.AssignerCommunication;
import ro.iasi.communication.api.LinksCallback;
import ro.iasi.communication.api.LinksDTO;
import ro.iasi.communication.impl.AssignerCommunicationImpl;

/**
 * 
 * @author David
 *
 * Just a simple test class, no JUnit, sorry ;)
 *
 */
public class Test {

	public static void main(String[] args) throws UnknownHostException, ClassNotFoundException, IOException {
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

	}

}
