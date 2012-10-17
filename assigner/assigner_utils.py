#!/usr/bin/python2.7

from pnode import PNode
from time import time

class DomManager :

    def __init__(self, domains):
        # dictionnary of tuples :  key:pnode.get_domain() , value:[pnode,new?]
        self.domains = domains 
    
    def update_domain(self, domain, update_timestamp=True) :
        if domain.get_domain() in  self.domains.keys() : # if domain already in dict
            curr_domain, _ = self.domains[domain.get_domain()]
            for page in domain.get_pages() :
                curr_domain.add_page(page) 
            for ref in domain.get_referers() :
                curr_domain.add_referer(ref)
            if update_timestamp : curr_domain.update_timestamp()
            self.domains[domain.get_domain()] = [domain, False] # domain is not new anymore
        else  :                                         # if domain not in dict
            self.domains[domain.get_domain()] = [domain, True]
        
    def update_externals(self, externals) :
        for domain in externals :
            update_domain(domain, update_timestamp=False)
    
    # currently : select the first domain not yet visited, or the last recent one
    def select_domain() :
        oldest = time()
        isnew = False
        domain = None
        for dom, new in self.domains.values() :
            if new : 
                domain = dom
                isnew = True
                break
            if dom.get_timestamp() < oldest :
                oldest = dom.get_timestamp()
                domain = dom
        if not isnew :
            domain.update_timestamp()        
        return domain
        
