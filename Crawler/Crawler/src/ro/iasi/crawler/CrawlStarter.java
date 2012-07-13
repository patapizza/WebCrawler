package ro.iasi.crawler;

import java.util.Map;

import ro.iasi.communication.db.impl.DBCrawlerCommunicationImpl;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class CrawlStarter {
	private static Map<String, Integer> dictionary;
	public static int getWordID(String word){
		return dictionary.get(word);
	}
	
    public static void main(String[] args) throws Exception {
    	DBCrawlerCommunicationImpl wordDbCommunicationImpl=new DBCrawlerCommunicationImpl();
    	CrawlStarter.dictionary=wordDbCommunicationImpl.getDictionary();
    	
    	
           String crawlStorageFolder = "/tmp/crawling";
            int numberOfCrawlers = 3;
            
            System.setProperty("http.proxyHost", "proxy.tuiasi.ro");
    		System.setProperty("http.proxyPort", "8080");

            CrawlConfig config = new CrawlConfig();
            config.setCrawlStorageFolder(crawlStorageFolder);
            config.setMaxDepthOfCrawling(1);

            /*
             * Instantiate the controller for this crawl.
             */
            PageFetcher pageFetcher = new PageFetcher(config);
            RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
            RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
            CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

            /*
             * For each crawl, you need to add some seed urls. These are the first
             * URLs that are fetched and then the crawler starts following links
             * which are found in these pages
             */
            controller.addSeed("http://192.168.243.80/riw");

            /*
             * Start the crawl. This is a blocking operation, meaning that your code
             * will reach the line after this only when crawling is finished.
             */
            controller.start(MyCrawler.class, numberOfCrawlers);    
    }
}
