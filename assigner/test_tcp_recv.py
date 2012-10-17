#!/usr/bin/python2.7
from socket import *
 
BUFF = 8096
HOST = '127.0.0.1'# must be input parameter @TODO
PORT = 9999 # must be input parameter @TODO




serversock = socket(AF_INET, SOCK_STREAM) # create TCP server
serversock.setsockopt(SOL_SOCKET, SO_REUSEADDR, 1)
serversock.bind((HOST, PORT))
serversock.listen(5)

clientsock, addr = serversock.accept()
print 'Connection from ' +repr(addr)
print ' '

print 'Receiving file'
data = ''
recv = clientsock.recv(1024)
bytes = 0
if not recv:
    print 'Error'
    quit()
while recv:    
    bytes += len(recv)
    
    data = ''.join([data, recv])
    recv = clientsock.recv(1024) if len(recv) == 1024 else None
    
clientsock.send('OK')
print 'Received : ' + str(bytes) + ' bytes'
print 'Writing file.'
f = open('received', 'w')
f.write(data)
f.close()
