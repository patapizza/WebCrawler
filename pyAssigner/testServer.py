from socket import *
import thread
import yaml
 
BUFF = 1024
HOST = '127.0.0.1'# must be input parameter @TODO
PORT = 9999 # must be input parameter @TODO

def gen_response():
    return 'ack from server'
 
def handler(clientsock, addr):
    while 1:
        recv = clientsock.recv(BUFF)
        if not recv:
            clientsock.close()
            print 'closing connection'
            break
        data = yaml.load(recv)
        print 'data:' + repr(data)
        clientsock.send(gen_response())
        print 'sent:' + repr(gen_response())
 
if __name__=='__main__':
    ADDR = (HOST, PORT)
    serversock = socket(AF_INET, SOCK_STREAM)
    serversock.setsockopt(SOL_SOCKET, SO_REUSEADDR, 1)
    serversock.bind(ADDR)
    serversock.listen(5)
    while 1:
        print 'waiting for connection...'
        clientsock, addr = serversock.accept()
        print '...connected from:', addr
        thread.start_new_thread(handler, (clientsock, addr))
