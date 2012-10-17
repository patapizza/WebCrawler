#!/usr/bin/python2.7

from pnode import PNode
from time import time
import yaml
import os.path

MAX_REMOTE_LIST_SIZE = 10 # number of domains that a remote crawler should manage
SAVE_FILE = 'save.sober'

def get_domains_for_remote()
    if len(self.domains) >= MAX_REMOTE_LIST_SIZE :
        return [select_domain() for i in range(MAX_REMOTE_LIST_SIZE - remote_list_size)]
    else : 
        return [select_domain()]

def process_data(data, domain_manager) :
    domain, externals, remote_list_size = data
    domain_manager.update_domain(domain)
    domain_manager.update_externals(externals)
    save_domains(domain_manager.get_domains())  

def save_domains(domains) :
    f = open(SAVE_FILE,'w')
    f.write(yaml.dump(domains))
    f.close


class DomManager :

    def __init__(self):
        # initializes the domains dictionnary
        # dictionnary:  key:pnode.get_domain() , value:[pnode,new?]
        self.domains = {}
        if os.path.exists(SAVE_FILE) :
            f = open(SAVE_FILE,'r')
            self.domains = yaml.load(f.read())
            f.close()        
            if not isinstance(self.domains, dict) :
                print('Save file corrupted. Creating new file.', sys.stderr)
                self.domains = {} 
    
    
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
        if len(self.domains) == 0 : # if domains dict empty
            return PNode("http://julien.odent.net", Page("/"))
            
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
    
    def get_domains() :
        return self.domains
