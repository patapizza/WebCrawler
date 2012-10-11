#!/usr/bin/python2.7

from crawler import Crawler
# make sure that ../crawler/ is in $PYTHONPATH

import unittest

class CrawlerTest(unittest.TestCase):

    def setUp(self):
        self.links = ["http://www.google.com/search?q=web+crawler",
        "publications.csail.mit.edu/library/library.shtml",
        "https://github.com/patapizza/WebCrawler"]
        self.domains = ["http://github.com", "http://publications.csail.mit.edu"]
        self.words = ["issue", "available", "publication", "request", "whatever"]

    def test_get_domain(self):
        self.assertEquals("www.google.com", crawler.extract_domain(self.links[0]))
        self.assertEquals("publications.csail.mit.edu", crawler.extract_domain(self.links[1]))
        self.assertEquals("github.com", crawler.extract_domain(self.links[2]))
    
    def test_crawling(self):
        c = Crawler(self.domains, self.words)
        c.crawl()
        self.assertEquals(2, len(c.get_internals()))
        for external in c.get_externals():
            print(external.get_domain())

if __name__ == '__main__':
    unittest.main()
