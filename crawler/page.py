#!/usr/bin/python2.7

class Page:

    def __init__(self, name):
        self.name = name
	self.wc = []

    def get_name(self):
        return self.name

    def get_links(self):
        return self.links

    def set_links(self, links):
        self.links = links

    def get_wc(self):
        return wc

    def set_wc(self, wc):
        self.wc = wc
