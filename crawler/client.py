#!/usr/bin/python2.7

import signal
import socket
import sys
import time
import yaml
from crawler import Crawler
from utils import recv_data

HOST, PORT = "localhost", 9999

# signal handler (ctrl+c)
def signal_handler(signal, frame):
    sock.close()
    print("Bye.")
    sys.exit(0)

signal.signal(signal.SIGINT, signal_handler) # ctrl+c handling
sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

try:
    sock.connect((HOST, PORT))
finally:
    # TODO: retrieve wordlist from database
    domains = recv_data(sock)
    print("Received: %s\n" % domains)
    if not domains:
        sock.close()
        print("Connection reset by peer")
        sys.exit(1)
    crawler = Crawler(domains, ["prostipute"])
    for d in crawler.crawl():
        domain,externals,stack = d
        print(d)
        sock.sendall(yaml.dump(d))
        print("Sent: %s\nSent: [" % domain)
        for external in externals:
            print("%s" % external)
        print("]\nSent: %d\n" % stack)
        data = recv_data(sock)
        if not data:
            sock.close()
            print("Connection reset by peer")
            break
        print("Received: %s\n" % data)
        crawler.add_domains(data)
