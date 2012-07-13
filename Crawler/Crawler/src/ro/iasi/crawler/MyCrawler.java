package ro.iasi.crawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import ro.iasi.communication.api.CrawlerCommunication;
import ro.iasi.communication.api.LinksDTO;
import ro.iasi.communication.db.api.DBCrawlerCommunication;
import ro.iasi.communication.db.api.IndexesDTO;
import ro.iasi.communication.db.impl.DBCrawlerCommunicationImpl;
import ro.iasi.communication.impl.CrawlerCommunicationImpl;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class MyCrawler extends WebCrawler {

	private final static Pattern FILTERS = Pattern
			.compile(".*(\\.(css|js|bmp|gif|jpe?g"
					+ "|png|tiff?|mid|mp2|mp3|mp4"
					+ "|wav|avi|mov|mpeg|ram|m4v|pdf"
					+ "|rm|smil|wmv|swf|wma|zip|rar|gz))$");

	private CrawlerCommunication communicationInterface;
	private DBCrawlerCommunication indexDbComm;

	public MyCrawler() {
		super();
		communicationInterface = new CrawlerCommunicationImpl();
		indexDbComm = new DBCrawlerCommunicationImpl();
	}

	/**
	 * You should implement this function to specify whether the given url
	 * should be crawled or not (based on your crawling logic).
	 */
	@Override
	public boolean shouldVisit(WebURL url) {
		String href = url.getURL().toLowerCase();
		return !FILTERS.matcher(href).matches();
	}

	/**
	 * This function is called when a page is fetched and ready to be processed
	 * by your program.
	 */
	@Override
	public void visit(Page page) {
		String url = page.getWebURL().getURL();
		System.out.println("URL: " + url);

		if (page.getParseData() instanceof HtmlParseData) {
			HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
			String text = htmlParseData.getText();

			indexPage(text, url);

			// String html = htmlParseData.getHtml();
			List<WebURL> links = htmlParseData.getOutgoingUrls();
			List<String> stringLinks = new LinkedList<>();
			for (WebURL u : links) {
				stringLinks.add(u.toString());
			}
			try {
				communicationInterface.sendLinks(new LinksDTO(stringLinks));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void indexPage(String data, String pageUrl) {
		StringTokenizer tokenizer = new StringTokenizer(data);
		IndexesDTO indexObject = new IndexesDTO();

		URL url = null;
		try {
			url = new URL(pageUrl);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Map<String, Map<String, List<Integer>>> indexes = new HashMap<String, Map<String, List<Integer>>>();

		while (tokenizer.hasMoreTokens()) {
			Integer wordId = CrawlStarter.getWordID(tokenizer.nextToken());
			if (wordId != null) {
				// try get page
				Map<String, List<Integer>> pages = indexes.get(url.getHost());
				if (pages == null) {
					pages = new HashMap<String, List<Integer>>();
					indexes.put(url.getHost(), pages);
				}
				// try get list of words
				List<Integer> words = pages.get(url.toString());
				if (words == null) {
					words = new LinkedList<Integer>();
					pages.put(url.toString(), words);
				}
				words.add(wordId);
			}
		}

		indexObject.setIndexes(indexes);
		try {
			indexDbComm.pushIndexes(indexObject);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
