#!/bin/bash
echo edit "$1"
sed -ie 's/\<br\>/\ /g' "$1" 
sed -ie 's/\<\/br\>/\ /g' "$1"
