#!/usr/bin/python2.7

import re
import robotparser
import urllib2

class Parser:

    def __init__(self, words, robots_url):
        self.words = words
        self.wc = []
        self.links = []
        self.url = None
        self.fetch_robots_file(robots_url)

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

    def fetch_robots_file(self, url):
        self.rp = robotparser.RobotFileParser()
        self.rp.set_url(url)
        self.robots = True
        try:
            self.rp.read()
        except IOError:
            self.robots = False

    # retrieve the document
    def fetch_url(self):
        # TODO: handle 403 forbidden, 404 not found, etc.
        print("fetching: %s" % self.url)
        if self.robots and not self.rp.can_fetch('Sober', self.url):
            return ''
        request = urllib2.Request(self.url)
        request.add_header('User-agent', 'Sober')
        try:
            response = urllib2.urlopen(request)
            html = response.read()
        except urllib2.HTTPError as e:
            print("HTTPError: %d" % e.code)
            html = ''
        else:
            response.close()
        return html

    # feed in a new url to parse
    def set_url(self, url):
        self.url = url
        self.preprocess()
