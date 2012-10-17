#!/usr/bin/python2.7

from time import time

class PNode:

    '''
    input:
        domain - a string
        page - a Page object
    '''
    def __init__(self, domain, page):
        self.domain = domain
        self.pages = [page]
        self.referers = []
        self.timestamp = time()

    def __str__(self):
        return self.domain

    def get_domain(self):
        return self.domain

    def get_referers(self):
        return self.referers

    def get_pages(self):
        return self.pages

    def get_timestamp(self):
        return self.timestamp

    def add_page(self, page):
        pages = [p.get_name() for p in self.pages]
        if not page.get_name() in set(pages):
            self.pages.append(page)

    def add_referer(self, referer):
        referers = [r.get_domain() for r in self.referers]
        if not referer.get_domain() in set(referers):
            self.referers.append(referer)

    def update_timestamp(self):
        self.timestamp = time()

