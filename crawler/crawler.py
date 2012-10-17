#!/usr/bin/python2.7

import re
from page import Page
from parser import Parser
from pnode import PNode

class Crawler:

    '''
    input:
        domains: a list of PNode instances
        words: a list of string
    '''
    def __init__(self, domains, words):
        self.internals = domains
        self.words = words
        self.externals = []

    '''
    input:
        domains: a list of PNode instances
    '''
    def add_domains(self, domains):
        self.internals.append(domains)

    def crawl(self):
        p = Parser(self.words)
        while self.internals:
            pnode = self.internals[0]
            for page in pnode.get_pages():
                p.set_url(''.join([pnode.get_domain(), page.get_name()]))
                page.set_wc(p.get_wc())
                links = [''.join(extract(link)) for link in p.get_links()]
                page.set_links(links)
                self.add_links(pnode, links)
            # sending pnode + externals + |internals| - 1 to server
            '''d = {}
            d["domain"] = self.internals.pop(0) # popping out pnode from internals
            d["externals"] = self.externals
            d["stack"] = len(self.internals)
            yield d'''
            yield (self.internals.pop(0), self.externals, len(self.internals))
            self.externals = [] # emptying externals

    def add_links(self, pointer, links):
        for link in links:
            page = Page(link)

            # domain = extract_domain(link)
            domain, link = extract(link)
            if domain == '':
                domain = pointer.get_domain()
            print("domain: %s page: %s" % (domain, link))
            ptr = None
            for internal in self.internals:
                if domain == internal.get_domain():
                    internal.add_page(page)
                    ptr = internal
            if not ptr:
                pnode = None
                for external in self.externals:
                    if external.get_domain() == domain:
                        pnode = external
                        pnode.add_page(page)
                if not pnode:
                    pnode = PNode(domain, page)
                    self.externals.append(pnode)
                pnode.add_referer(pointer)
    
    def get_externals(self):
        return self.externals

    def get_internals(self):
        return self.internals

def extract(link):
    # FIXME: "./" "../../../index.php3" "function.copy" (on julien.odent.net)
    pair = re.sub(r'(https?://)?(([\w]+\.){1}(?!(html|php)$)([A-Za-z\-]+\.?)+)(/?.*)', '\\2 \\6', link).split()
    if len(pair) == 2:
        domain, link = pair
    else:
        s = pair[0].split('.')
        if len(s) == 1:
            domain = ''
            link = s[0]
        elif re.match(r'(html|php)', s[1]):
            domain = ''
            link = pair[0]
        else:
            domain = pair[0]
            link = '/'
    if link[0] != '/':
        link = ''.join(['/', link])
    return (domain, link)
