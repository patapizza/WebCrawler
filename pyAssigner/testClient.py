import socket
import sys
import time

HOST, PORT = "localhost", 9999
data = " ".join(sys.argv[1:])

# Create a socket (SOCK_STREAM means a TCP socket)
sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

try:
    # Connect to server and send data
    sock.connect((HOST, PORT))    
finally:
    while 1 :
        sock.sendall(data + "\n")
        received = sock.recv(1024)    
        time.sleep(1)
        print "Sent: " + data
        print "Received: " + received
