#!/bin/bash 
echo $1
export http_proxy=proxy.tuiasi.ro:8080
export use_proxy=on
{ [ $(wget --spider $URL 2>&1 | awk '/Length/ {print $2}') -lt 20971520 ] && wget $URL; } || echo file to big

wget --reject jpg,pdf,png,jpeg,gif,bmp,js,css -p -r  $1
 
