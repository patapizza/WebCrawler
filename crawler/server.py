from socket import *
import thread
import yaml
import curses
import signal
import sys

from page import Page
from pnode import PNode
from parser import Parser

BUFF = 1024
HOST = '127.0.0.1'# must be input parameter @TODO
PORT = 9999 # must be input parameter @TODO

clients = {} # client (crawler) dictionnary: socket -> addr
global serversock


def createWindows(scrPtr):
    (maxln, maxcol) = scrPtr.getmaxyx()
    vSizeE = 2*(maxln-2)/3; vSizeC = (maxln-2)/3;
    winEvents = curses.newwin(vSizeE,maxcol,0,0); winEvents.border(0)
    winEvents.setscrreg(2, vSizeE-2); winEvents.scrollok(True);  
    winEvents.addstr(1, 10, 'Events', curses.A_REVERSE)
    winEvents.refresh()    
    
    winCrawlers = curses.newwin(vSizeC,maxcol,vSizeE+1,0); winCrawlers.border(0); 
    winCrawlers.addstr(1, 10, 'Crawlers', curses.A_REVERSE)
    winCrawlers.refresh()      
    
    return winEvents, winCrawlers


def printWin(winPtr, data):
    (maxln, _) = winPtr.getmaxyx()
    winPtr.scroll(); winPtr.border(0);
    winPtr.addstr(maxln-2, 2, data); winPtr.refresh()
    
def refreshWinList(winPtr, List, winName) :
    (maxln, _) = winPtr.getmaxyx()
    winPtr.erase(); winPtr.border(0)
    winPtr.addstr(1, 10, winName, curses.A_REVERSE); 
    nbC = 0
    for value in List :
        if nbC == maxln-3 and len(List) > nbC :
            winPtr.addstr(nbC+2, 2, '...'); break
        winPtr.addstr(nbC+2, 2, repr(value)); nbC+=1
    winPtr.refresh()
    

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
        clientsock.sendall(yaml.dump([]))
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
        #clientsock.sendall(yaml.dump([PNode("http://www.google.com", Page("/")), PNode("http://www.github.com", Page("/"))]))
        clientsock.sendall(yaml.dump([PNode("http://julien.odent.net", Page("/")), PNode("http://www.github.com", Page("/"))]))
        thread.start_new_thread(handler, (clientsock, addr, winE, winC))

