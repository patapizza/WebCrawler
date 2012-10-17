#!/usr/bin/python2.7
import socket
import sys

HOST, PORT = "localhost", 9999

sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

if len(sys.argv) != 2 : 
    print 'Usage: python test_tcp_send.py FILE'
    quit()

try:
    # Connect to server and send data
    sock.connect((HOST, PORT))    
finally:
    print 'Reading file...'
    f = open(sys.argv[1], 'r')
    data = f.read()
    f.close()
    
    print 'Sending file ...'
    sock.sendall(data)
    received = sock.recv(1024)   
    print received
