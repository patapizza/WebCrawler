#!/usr/bin/python2.7

import re
import urllib2

class Parser:

    def __init__(self, words):
        self.words = words
	self.wc = []
	self.links = []
	self.url = None

    # retrieve words and links from document, count the word occurrences
    def preprocess(self):
        if not self.url:
	    return False
        html = self.fetch_url()
        tokens = filter(lambda(x): x, re.split('\W+', re.sub(r'<[^>]+>', '', html)))
	self.links = map(lambda(x): re.sub(r'.*(?<!href=[\'"])href=[\'"]([^\'"]+)[\'"].*', '\\1', x), re.findall(r'<a [^>]+>[^<]+</a>', html))
	self.wc = [tokens.count(w) for w in self.words]

    # return the word counts from document
    def get_wc(self):
        return self.wc

    # return the links from document
    def get_links(self):
        return self.links

    # retrieve the document
    def fetch_url(self):
        sock = urllib2.urlopen(self.url)
	html = sock.read()
	sock.close()
	return html

    # feed in a new url to parse
    def set_url(self, url):
        self.url = url
	self.preprocess()

if __name__ == '__main__':
    p = Parser(["google", "a"])
    p.set_url("http://www.google.com")
    print(p.get_links())
    print(p.get_wc())
