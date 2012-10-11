#!/usr/bin/python2.7

import socket
import sys
import time
import yaml
import signal

HOST, PORT = "localhost", 9999



# signal handler (ctrl+c)
def signal_handler(signal, frame): 
    sock.close()
    print 'Bye.'
    sys.exit(0) # exit



signal.signal(signal.SIGINT, signal_handler) # signal handler (ctrl+c)
# Create a socket (SOCK_STREAM means a TCP socket)
sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

try:
    # Connect to server and send data
    sock.connect((HOST, PORT))    
finally:
    n = 1
    while 1 : 
        time.sleep(3)
        data = 'coucou '+str(n)
        sock.sendall(yaml.dump(data) + "\n")
        print "Sent: " + data        
        n += 1
        #received = sock.recv(1024)   
