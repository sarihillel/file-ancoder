# file-encoder assigment

===== Requirments =======
encode / decode file by chunks of 1024 bytes to SHA-256 hash that concat to the prev chunk.

===== Project Language/Tools =====
Java
Maven
Spring Boot
Junit

===== Exceution ==========
the project contains 4 services:

   * FileEncoder - read from input file by chunks,generate hash by SHA-256 and write to output file.
     The Reading and writing uses by RandomAccessFile in order to enable raeding big files  

   * FileDecoder - encrypt file by hash of first chunck. this service return boolean result for valiv\invalid file.

   * HashGenerator - generate SHA-256 hashing.

   * HexConverter - convert from\to Hex String.

===== Unit Testing =========
I tested this project by JUnit test mode.
the tests include multiple options of Junit include using an option of TemporaryFolder for tem genatared files.
