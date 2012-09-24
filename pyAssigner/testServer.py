from socket import *
import thread
import yaml
import curses
import signal
import sys
 
BUFF = 1024
HOST = '127.0.0.1'# must be input parameter @TODO
PORT = 9999 # must be input parameter @TODO

 



def createWindows():
    winEvents = curses.newwin(10,60,0,0); winEvents.border(0)
    winEvents.setscrreg(2, 8); winEvents.scrollok(True);  
    winEvents.addstr(1, 10, '- Events -'); winEvents.refresh()    
    
    winCrawlers = curses.newwin(6,60,12,0); winCrawlers.border(0); 
    winCrawlers.addstr(1, 10, '- Crawlers -');winCrawlers.refresh()      
    
    return winEvents, winCrawlers


def printWin(winPtr, data):
    (maxln, _) = winPtr.getmaxyx()
    winPtr.scroll(); winPtr.border(0);
    winPtr.addstr(maxln-2, 2, repr(data)); winPtr.refresh()

# signal handler (ctrl+c)
def signal_handler(signal, frame): 
    curses.echo() # Ending curses properly
    curses.endwin()
    print 'Bye.'
    sys.exit(0) # exit


# +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

def gen_response():
    return 'ack from server'

def handler(clientsock, addr, winE, winC):
    while 1:
        recv = clientsock.recv(BUFF)
        if not recv:
            clientsock.close()
            print 'closing connection'
            break
        data = yaml.load(recv)
        printWin(winE, 'Data RECV: ' + repr(data))
        #clientsock.send(gen_response())
        #print 'sent:' + repr(gen_response())


# +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

signal.signal(signal.SIGINT, signal_handler) # signal handler (ctrl+c)
myscreen = curses.initscr()
winE, winC = createWindows()

serversock = socket(AF_INET, SOCK_STREAM) # create TCP server
serversock.setsockopt(SOL_SOCKET, SO_REUSEADDR, 1)
serversock.bind((HOST, PORT))
serversock.listen(5)
while 1:
    clientsock, addr = serversock.accept()
    printWin(winE, 'Connection from ' +repr(addr))
    thread.start_new_thread(handler, (clientsock, addr, winE, winC))




