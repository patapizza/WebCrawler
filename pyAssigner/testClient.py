import socket
import sys
import time
import yaml

HOST, PORT = "localhost", 9999

# Create a socket (SOCK_STREAM means a TCP socket)
sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

try:
    # Connect to server and send data
    sock.connect((HOST, PORT))    
finally:
    n = 1
    while 1 : 
        time.sleep(1)
        data = 'coucou '+str(n)
        sock.sendall(yaml.dump(data) + "\n")
        print "Sent: " + data        
        n += 1
        #received = sock.recv(1024)   
