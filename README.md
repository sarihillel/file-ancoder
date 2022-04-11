# file-encoder assigment

Project Language/Tools:
Java
Maven
Spring Boot
Junit

Requirments: 
encode / decode file by chunks of 1024 bytes to SHA-256 hash that concat to the prev chunk.

Exceution:
the project contains 4 services:

   * FileEncoder - read from input file by chunks,generate hash by SHA-256 and write to output file.
     The Reading and writing uses by RandomAccessFile with specific pointer to current chunk position in order to enable reading big files effectivitlly.
     The service calculate the new size of output file and writes every chunk and hash exactly to the relevat poisition.

   * FileDecoder - encrypt file by hash of first chunck. this service return boolean result for valiv\invalid file.

   * HashGenerator - generate SHA-256 hashing.

   * HexConverter - convert from\to Hex String.

Unit Testing:
the project's tests written by JUnit.
The tests include multiple options of Junit include using an option of TemporaryFolder for genataring temporary files.
