#!/usr/bin/python2.7

from pnode import PNode
from time import time

class DomManager :

    def __init__(self, domains):
        # dictionnary of tuples :  key:pnode.get_domain() , value:[pnode,new?]
        self.domains = domains 
    
    def update_domain(self, domain, update_timestamp=True) :
        if domain.get_domain() in  self.domains.keys() : # if domain already in dict
            curr_domain = self.domains[domain.get_domain()]
            for page in domain.get_pages() :
                curr_domain.add_page(page) 
            for ref in domain.get_referers() :
                curr_domain.add_referer(ref)
            if update_timestamp : curr_domain.update_timestamp()
        else  :                                         # if domain not in dict
            self.domains[domain.get_domain()] = [domain, ]
        
    def update_externals(self, externals)
        for domain in externals :
            update_domain(domain, False)
    
    
    def select_domain() :
        oldest = time()
        domain = None
        for dom, new in domains.values()
            if new : 
                domain = dom
                break
            if dom.get_timestamp() < oldest :
                oldest = dom.get_timestamp()
                domain = dom
        
