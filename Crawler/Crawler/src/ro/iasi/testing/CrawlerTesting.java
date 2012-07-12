package ro.iasi.testing;

import java.io.IOException;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import ro.iasi.crawler.SimpleCrawler;

public class CrawlerTesting {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SimpleCrawler crawler = new SimpleCrawler();
	crawler.startCrawling();

	}
}
