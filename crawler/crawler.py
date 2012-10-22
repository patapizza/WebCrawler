#!/usr/bin/python2.7

import re
from page import Page
from parser import Parser
from pnode import PNode
from time import time

CRAWLING_DELAY = 2

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
        self.internals.extend(domains)

    def crawl(self):
        while self.internals:
            timeout = False
            pnode = self.internals.pop(0)
            p = Parser(self.words, ''.join([pnode.get_domain(), '/robots.txt']))
            for page in pnode.get_pages():
                if page.get_wc():
                    continue # skipping pages already parsed
                if pnode.get_timestamp() >= time() - CRAWLING_DELAY:
                    self.internals.append(pnode)
                    timeout = True
                    break
                p.set_url(''.join([pnode.get_domain(), page.get_name()]))
                pnode.update_timestamp()
                page.set_wc(p.get_wc())
                links = [''.join(extract(link)) for link in p.get_links()]
                page.set_links(links)
                self.add_links(pnode, links)            
            # sending pnode + externals + |internals| - 1 to server
            if not timeout :
                yield (pnode, self.externals, len(self.internals))
                self.externals = [] # emptying externals

    def add_links(self, pointer, links):
        for link in links:
            domain, link = extract(link)
            if domain == '':
                domain = pointer.get_domain()
            if not re.match(r'https?://', domain):
                domain = ''.join(['http://', domain])
            print("domain: %s page: %s" % (domain, link))
            page = Page(link)
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
    pair = re.sub(r'(https?://)?(([\w]+\.){1}(?!(html|php|py)$)([A-Za-z\-]+\.?)+)(/?.*)', '\\2 \\6', link).split()
    if len(pair) == 2:
        domain, link = pair
    else:
        s = pair[0].split('.')
        if len(s) == 1:
            domain = ''
            link = s[0]
        elif re.match(r'(html|php|py)', s[1]):
            domain = ''
            link = pair[0]
        else:
            domain = pair[0]
            link = '/'
    if link[0] != '/':
        link = ''.join(['/', link])
    return (domain, link)
