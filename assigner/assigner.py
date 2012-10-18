#!/usr/bin/python2.7

from socket import *
from multiprocessing import Process, Lock
import yaml
import curses
import signal
import sys
from curses_utils import *
from assigner_utils import *
from utils import recv_data
 
BUFF = 8096
HOST = '127.0.0.1'# must be input parameter @TODO
PORT = 9999 # must be input parameter @TODO

clients = {} # client (crawler) dictionnary: socket -> addr
global serversock
global domain_manager 
mutex = Lock()

# +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

def handler(clientsock, addr, winE, winC):
    global clients, domain_manager    
    with mutex : 
        clients[clientsock] = addr    
        refreshWinList(winC, clients.values(), 'Crawlers')
        clientsock.send(yaml.dump(domain_manager.get_domains_for_remote(0)))
    while 1:
        data = recv_data(clientsock)
        if not data:
            clientsock.close()
            with mutex: 
                printWin(winE, 'Closing connection: ' + repr(addr))
                del clients[clientsock]
                refreshWinList(winC, clients.values(), 'Crawlers')
            break
        with mutex : 
            printWin(winE, 'Data RECV: '+ data[0].get_domain())
            domain_manager.process_data(data)
            response = domain_manager.get_domains_for_remote(data[2])
        clientsock.send(yaml.dump(response))
        #print 'sent:' + repr(gen_response())


# +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
if __name__=='__main__':
    global serversock, domain_manager
    
    print 'Initializing DomManager ...'
    domain_manager = DomManager()
    print 'Ok'
        
    #def resize_handler(signal, frame): # interrupt signal handler (ctrl+c)
    #    w.erase()
    #    w.addstr(0,0, str(w.getmaxyx()))
    w = curses.initscr()
    w.nodelay(1); curses.curs_set(0)
    #signal.signal(signal.SIGWINCH, resize_handler) # signal handler (ctrl+c)
    winE, winC = createWindows(w)
        
    # interrupt signal handler (ctrl+c)
    def interrupt_handler(signal, frame): 
        global serversock
        curses.echo() # Ending curses properly
        curses.endwin()
        serversock.close()
        print('Bye.')
        sys.exit(0) # exit    
    signal.signal(signal.SIGINT, interrupt_handler) # signal handler (ctrl+c)
    
    # starting server     
    serversock = socket(AF_INET, SOCK_STREAM) # create TCP server
    serversock.setsockopt(SOL_SOCKET, SO_REUSEADDR, 1)
    serversock.bind((HOST, PORT))
    serversock.listen(5)
    while 1:
        clientsock, addr = serversock.accept()
        printWin(winE, 'Connection from ' +repr(addr))
        p = Process(target = handler, args = (clientsock, addr, winE, winC))
        p.start()
        


