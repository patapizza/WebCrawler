#!/usr/bin/python2.7

from yaml import load

BUFF = 8192

def recv_data(sock):
    data = ''
    recv = sock.recv(BUFF)
    if not recv:
        return None
    while recv:
        data = ''.join([data, recv])
        recv = sock.recv(BUFF) if len(recv) == BUFF else None
    return load(data)
