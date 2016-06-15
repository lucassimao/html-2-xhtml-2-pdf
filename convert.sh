#!/bin/bash
for f in *.html ; 
do
filename=$(basename "$f")
extension="${filename##*.}"
filename="${filename%.*}"
 
java -jar target/html-2-pdf-1.0-SNAPSHOT-jar-with-dependencies.jar "$f" "$filename.pdf"; 
done

