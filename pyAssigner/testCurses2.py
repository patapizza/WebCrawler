import curses

myscreen = curses.initscr()

myscreen.border(0)
scr1 = curses.newwin(10,30,0,0); scr1.border(0)
scr2 = curses.newpad(40,60); scr2.border(0);

scr1.addstr(2, 2, "Python curses in action!"); scr1.refresh()
scr2.addstr(2, 2, "Pouet"); 
scr2_pos=0; 
scr2.refresh(scr2_pos, 0, 5, 5, 10, 60)

scr2.getch()

scr2.addstr("Blabla\n"); scr2_pos = scr2_pos+1; scr2.refresh(scr2_pos, 0, 5, 5, 10, 60)
scr2.getch()
scr2.addstr("Blabla\n"); scr2_pos = scr2_pos+1; scr2.refresh(scr2_pos, 0, 5, 5, 10, 60)
scr2.getch()
scr2.addstr("Blabla\n"); scr2_pos = scr2_pos+1; scr2.refresh(scr2_pos, 0, 5, 5, 10, 60)
scr2.getch()
scr2.addstr("Blabla\n"); scr2_pos = scr2_pos+1; scr2.refresh(scr2_pos, 0, 5, 5, 10, 60)
scr2.getch()
scr2.addstr("Blabla\n"); scr2_pos = scr2_pos+1; scr2.refresh(scr2_pos, 0, 5, 5, 10, 60)
scr2.getch()
scr2.addstr("Blabla\n"); scr2_pos = scr2_pos+1; scr2.refresh(scr2_pos, 0, 5, 5, 10, 60)
scr2.getch()
scr2.addstr("Blabla\n"); scr2_pos = scr2_pos+1; scr2.refresh(scr2_pos, 0, 5, 5, 10, 60)
scr2.getch()
scr2.addstr("Blabla\n"); scr2_pos = scr2_pos+1; scr2.refresh(scr2_pos, 0, 5, 5, 10, 60)
scr2.getch()
scr2.addstr("Blabla\n"); scr2_pos = scr2_pos+1; scr2.refresh(scr2_pos, 0, 5, 5, 10, 60)
scr2.getch()
scr2.addstr("Blabla\n"); scr2_pos = scr2_pos+1; scr2.refresh(scr2_pos, 0, 5, 5, 10, 60)
scr2.getch()
scr2.addstr("Blabla\n"); scr2_pos = scr2_pos+1; scr2.refresh(scr2_pos, 0, 5, 5, 10, 60)
scr2.getch()
scr2.addstr("Blabla\n"); scr2_pos = scr2_pos+1; scr2.refresh(scr2_pos, 0, 5, 5, 10, 60)
scr2.getch()


# Ending curses
curses.echo()
curses.endwin()
