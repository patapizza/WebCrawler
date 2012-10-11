#!/usr/bin/python2.7

from socket import *
import thread
import yaml
import curses
import signal
import sys
from curses_utils import *
 
BUFF = 1024
HOST = '127.0.0.1'# must be input parameter @TODO
PORT = 9999 # must be input parameter @TODO

clients = {} # client (crawler) dictionnary: socket -> addr
global serversock


# interrupt signal handler (ctrl+c)
def interrupt_handler(signal, frame): 
    global serversock
    curses.echo() # Ending curses properly
    curses.endwin()
    serversock.close()
    print('Bye.')
    sys.exit(0) # exit

# +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

def gen_response():
    return 'ack from server'

def handler(clientsock, addr, winE, winC):
    global clients
    clients[clientsock] = addr
    refreshWinList(winC, clients.values(), 'Crawlers')
    while 1:
        recv = clientsock.recv(BUFF)
        if not recv:
            clientsock.close()
            printWin(winE, 'Closing connection: ' + repr(addr))
            del clients[clientsock]
            refreshWinList(winC, clients.values(), 'Crawlers')
            break
        data = yaml.load(recv) # YAML reassembly
        printWin(winE, 'Data RECV: ' + repr(data))
        #clientsock.send(gen_response())
        #print 'sent:' + repr(gen_response())


# +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
if __name__=='__main__':
    #def resize_handler(signal, frame): # interrupt signal handler (ctrl+c)
    #    w.erase()
    #    w.addstr(0,0, str(w.getmaxyx()))
    w = curses.initscr()
    w.nodelay(1); curses.curs_set(0)
    signal.signal(signal.SIGINT, interrupt_handler) # signal handler (ctrl+c)
    #signal.signal(signal.SIGWINCH, resize_handler) # signal handler (ctrl+c)
    winE, winC = createWindows(w)

    global serversock
    serversock = socket(AF_INET, SOCK_STREAM) # create TCP server
    serversock.setsockopt(SOL_SOCKET, SO_REUSEADDR, 1)
    serversock.bind((HOST, PORT))
    serversock.listen(5)
    while 1:
        clientsock, addr = serversock.accept()
        printWin(winE, 'Connection from ' +repr(addr))
        thread.start_new_thread(handler, (clientsock, addr, winE, winC))

