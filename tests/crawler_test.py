#!/usr/bin/python2.7

import crawler
# make sure that ../crawler/ is in $PYTHONPATH

import unittest

class CrawlerTest(unittest.TestCase):

    def setUp(self):
        self.links = ["http://www.google.com/search?q=web+crawler",
	    "publications.csail.mit.edu/library/library.shtml",
	    "https://github.com/patapizza/WebCrawler"]

    def test_get_domain(self):
        self.assertEquals("www.google.com", crawler.get_domain(self.links[0]))
	self.assertEquals("publications.csail.mit.edu", crawler.get_domain(self.links[1]))
	self.assertEquals("github.com", crawler.get_domain(self.links[2]))

if __name__ == '__main__':
    unittest.main()
