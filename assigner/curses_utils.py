import curses
import sys

def createWindows(scrPtr):
    maxln, maxcol = scrPtr.getmaxyx()
        
    winInfos = curses.newwin(2,maxcol,0,0); 
    winInfos.addstr(0, 0, 'Infos:', curses.A_REVERSE)
    winInfos.refresh()
    
    vSizeE = 2*(maxln-2)/3; vSizeC = (maxln-2)/3;
    winEvents = curses.newwin(vSizeE,maxcol,2,0); winEvents.border(0)
    winEvents.setscrreg(2, vSizeE-2); winEvents.scrollok(True);  
    winEvents.addstr(1, 10, 'Events', curses.A_REVERSE)
    winEvents.refresh()    
    
    winCrawlers = curses.newwin(vSizeC,maxcol,vSizeE+2,0); winCrawlers.border(0); 
    winCrawlers.addstr(1, 10, 'Crawlers', curses.A_REVERSE)
    winCrawlers.refresh()      
    
    return winEvents, winCrawlers, winInfos


def printWin(winPtr, data):
    maxln, _ = winPtr.getmaxyx()
    winPtr.scroll(); winPtr.border(0); sys.stdout.flush();
    winPtr.addstr(maxln-2, 2, data); winPtr.refresh()
    
def refreshWinList(winPtr, List, winName) :
    maxln, _ = winPtr.getmaxyx()
    winPtr.erase(); winPtr.border(0)
    winPtr.addstr(1, 10, winName, curses.A_REVERSE); 
    nbC = 0
    for value in List :
        if nbC == maxln-3 and len(List) > nbC :
            winPtr.addstr(nbC+2, 2, '...'); break
        winPtr.addstr(nbC+2, 2, repr(value)); nbC+=1
    winPtr.refresh()

def refreshInfos(winPtr, dom_manager):
    nbDom = len(dom_manager.get_domains()); nbVis = dom_manager.get_nbVisited(); 
    winPtr.erase()
    winPtr.addstr(0, 0, 'Infos:', curses.A_REVERSE)
    winPtr.addstr(0, 8, '#domains:'+str(nbDom))
    if nbDom > 0 :
        winPtr.addstr(0, 28, '#visited:'+str(nbVis)+'('+str((100*nbVis)/nbDom)+'%)')
    
    winPtr.refresh()
