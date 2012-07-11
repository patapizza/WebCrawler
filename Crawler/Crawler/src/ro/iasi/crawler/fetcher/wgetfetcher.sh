#!/bin/bash 
echo $1
export http_proxy=proxy.tuiasi.ro:8080
export use_proxy=on
wget --reject jpg,pdf,png,jpeg,gif,bmp,js,css -p -r  $1
 
