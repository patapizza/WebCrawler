#!/usr/bin/python2.7

import sys
import yaml
import networkx as nx
import matplotlib.pyplot as plt

if len(sys.argv) != 2 : 
    print 'Usage: python draw.py SOBER_SAVE_FILE'
    quit()

f = open(sys.argv[1],'r')
save = yaml.load(f.read())
f.close()

G = nx.Graph()

for domain,new in save.values() :
    for r in domain.get_referers() :
        G.add_edge(domain.get_domain(),r.get_domain())

nx.draw(G)
plt.show()
