#!/usr/bin/python2.7

from time import time

class PNode:

    def __init__(self, domain, page):
        self.domain = domain
	self.pages = [page]
	self.referers = []
	self.timestamp = time.time()

    def get_domain(self):
        return self.domain

    def get_referers(self):
        return self.referers

    def get_timestamp(self):
        return self.timestamp

    def add_page(self, page):
        if not page in set(self.pages):
            self.pages.append(page)

    def add_referer(self, referer):
        if not referer in set(self.referers):
	    self.referers.append(referer)

    def update_timestamp(self):
        self.timestamp = time.time()

