#!/usr/bin/python2.7

import re
import urllib2

class Parser:

    def __init__(self, words, url):
        self.words = words
	self.url = url
	self.html = self.fetch_url()
	self.wc = []
	self.links = []

    # return the words counts from document
    def get_wc(self):
        return self.wc

    # return the links from document
    def get_links(self):
        return self.links

    # retrieve words and links in document
    def parse(self):
        self.words = filter(lambda(x): x, re.split('\W+', re.sub(r'<[^>]+>', '', self.html)))
	self.links = re.findall(r'<a [^>]+>[^<]+</a>', self.html)
	self.links = map(lambda(x): re.sub(r'.*(?<!href=[\'"])href=[\'"]([^\'"]+)[\'"].*', '\\1', x), self.links)

    # retrieve the document
    def fetch_url(self):
        sock = urllib2.urlopen(self.url)
	html = sock.read()
	sock.close()
	return html

if __name__ == '__main__':
    p = Parser([], "http://www.google.com")
    p.parse()
    print(p.get_links())
