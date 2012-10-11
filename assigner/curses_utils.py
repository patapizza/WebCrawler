import curses


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
    
