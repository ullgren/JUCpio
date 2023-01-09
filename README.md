# Java Util CPIO

##  What is JUCpio ?

JUCpio stands for Java Util CPIO and is a set of classes (a API) for reading and writing cpio files.

It is designed to be similar to [java.util.zip](http://java.sun.com/j2se/1.5.0/docs/api/java/util/zip/package-summary.html).

The primary reason I started this project was to learn more about the CPIO file format. The API is currently in a very early stage of development.

## Features

### Implemented

* Gives a interface to extract data from cpio files.
* Reads cpio files with binary header.


### Features Requests / Bugs

* Handle ASCII style headers.
* Make a better solution that doesn't use `MappedByteBuffe`.

### References

* [java.util.zip](http://java.sun.com/j2se/1.5.0/docs/api/java/util/zip/package-summary.html)
* [format of cpio archives](http://www.mkssoftware.com/docs/man4/cpio.4.asp)
* [HP-UX 11i Version 1.5 Reference Volume 5, Section 4 > c > cpio(4)](http://docs.hp.com/en/B9106-90011/cpio.4.html)
