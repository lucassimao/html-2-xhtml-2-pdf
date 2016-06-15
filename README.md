# html-2-xhtml-2-pdf

Simple project showing how to convert a HTML not well formatted file
to XHTML and after that, to PDF

This project uses:
    - flying-saucer-pdf
    - itext
    - jsoup


# How to build

- Download and install maven
- run the following commands in the project source dir:
    - mvn compile && mvn assembly:single
    - java -jar target/html-2-pdf-1.0-SNAPSHOT-jar-with-dependencies.jar page.html file.pdf
    - convert.sh file is used for batch conversion
