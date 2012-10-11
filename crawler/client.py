#!/usr/bin/python2.7

import signal
import socket
import sys
import time
import yaml
from crawler import Crawler

HOST, PORT = "localhost", 9999

def recv_data():
    data = ''
    recv = sock.recv(8192)
    if not recv:
        return None
    while recv:
        data = ''.join([data, recv])
        recv = sock.recv(8192) if len(recv) == 8192 else None
    return yaml.load(data)

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
    domains = recv_data()
    if not domains:
        sock.close()
        print("Connection closed by peer")
        sys.exit(1)
    print("Domains: %s\n" % domains)
    crawler = Crawler(domains, [])
    for domain in crawler.crawl():
        sock.sendall(yaml.dump(domain))
        print("Sent: %s\nSent: [" % domain["domain"])
        for external in domain["externals"]:
            print("%s" % external)
        print("]\nSent: %d\n" % domain["stack"])
        data = recv_data()
        if not data:
            sock.close()
            print("Connection closed by peer")
            break
        print("Received: %s\n" % data)
