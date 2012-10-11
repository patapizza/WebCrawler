#!/usr/bin/python2.7

import re
from parser import Parser
from pnode import PNode

class Crawler:

    def __init__(self, domains, words):
        # self.internals = [PNode(domain, "/") for domain in domains]
        self.internals = domains
        self.words = words
        self.externals = []
    
    def crawl(self):
        p = Parser(self.words)
        while self.internals:
            pnode = self.internals[0]
            for page in pnode.get_pages():
                p.set_url(''.join([pnode.get_domain(), page.get_name()]))
                page.set_wc(p.get_wc())
                links = p.get_links()
                page.set_links(links)
                self.add_links(pnode, links)
            # sending pnode + externals + |internals| - 1 to server
            d = {}
            d["domain"] = self.internals.pop(0) # popping out pnode from internals
            d["externals"] = self.externals
            d["stack"] = len(self.internals)
            yield d
            self.externals = [] # emptying externals

    def add_links(self, pointer, links):
        for link in links:
            domain = extract_domain(link)
            ptr = None
            for internal in self.internals:
                if domain == internal.get_domain():
                    internal.add_page(link)
                    ptr = internal
            if not ptr:
                pnode = None
                for external in self.externals:
                    if external.get_domain() == domain:
                        pnode = external
                        pnode.add_page(link)
                if not pnode:
                    pnode = PNode(domain, link)
                    self.externals.append(pnode)
                pnode.add_referer(pointer)
    
    def get_externals(self):
        return self.externals

    def get_internals(self):
        return self.internals

def extract_domain(link):
    return re.sub(r'(https?://)?(([\w]+\.){1}([\w]+\.?)+)/?.*', '\\2', link)
