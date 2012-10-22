#!/usr/bin/python2.7

from pnode import PNode
from page import Page
from time import time
import yaml
import os.path

MAX_REMOTE_LIST_SIZE = 100 # number of domains that a remote crawler should manage
SAVE_FILE = 'sober.sav'


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
    
    def update_domain(self, domain, is_external=False) :
        if domain.get_domain() in  self.domains.keys() : # if domain already in dict
            curr_domain, _ = self.domains[domain.get_domain()]
            for page in domain.get_pages() :
                curr_domain.add_page(page) 
            for ref in domain.get_referers() :
                curr_domain.add_referer(ref)
            if is_external : curr_domain.update_timestamp()
            self.domains[domain.get_domain()] = [domain, False] # domain is not new anymore
        else  :                                         # if domain not in dict
            self.domains[domain.get_domain()] = [domain, is_external] # set new to True only for externals
        
    def update_externals(self, externals) :
        for domain in externals :
            self.update_domain(domain, is_external=True)
    
    # currently : select the first domain not yet visited, or the last recent one
    def select_domain(self) :
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
    
    def get_domains(self) :
        return self.domains

    def get_nbVisited(self) :
        self.nbNewDomains = 0
        for dom, new in self.domains.values() :
            if new : self.nbNewDomains += 1
        return len(self.domains) - self.nbNewDomains
        
    def get_domains_for_remote(self, remote_list_size) :
        if len(self.domains) >= MAX_REMOTE_LIST_SIZE :
            return [self.select_domain() for i in range(MAX_REMOTE_LIST_SIZE - remote_list_size)]
        else : 
            return [self.select_domain()]

    def process_data(self, data) :
        domain, externals, _ = data
        self.update_domain(domain)
        self.update_externals(externals)
        self.save_domains()  

    def save_domains(self) :
        f = open(SAVE_FILE,'w')
        f.write(yaml.dump(self.domains))
        f.close
