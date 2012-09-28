#!/usr/bin/python2.7

import heapq
import re

class Crawler:

    def __init__(self):
        self.pqueue = []
    
    def get_next_domain(self):
        pass

    def add_links(self, domain, links):
        pointer = None
        for pnode in self.pqueue:
	    if pnode.get_domain() == domain:
	        pointer = pnode
		break
	if pointer is None:
	    pointer = PNode(domain, "index.html")
	for link in links:
	    if get_domain(link) == domain:
	        pointer.add_page(link)
	    else:
	        pnode = PNode(get_domain(link), link)
		pnode.add_referer(domain)
		heapq.heappush(pqueue, (42, pnode))
	heapq.heappush(pqueue, (42, pointer))

def get_domain(link):
    return re.sub(r'(https?://)?(([\w]+\.){1}([\w]+\.?)+)/?.*', '\\2', link)
