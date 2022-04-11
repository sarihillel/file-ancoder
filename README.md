# file-encoder assigment

**Project Language/Tools:**<br />
Java 11<br />
Maven 4.0.0<br />
Spring Boot 2.5.12<br />
Junit 5<br />

**Requirments:**<br />
encode / decode file by chunks of 1024 bytes to SHA-256 hash that concat to the prev chunk.<br />

**Exceution:**<br />
the project contains 4 services:<br />

   * FileEncoder - read from input file by chunks,generate hash by SHA-256 and write to output file.<br />
                   The Reading and writing uses by RandomAccessFile with specific pointer to current chunk position in order to enable reading big files effectivitlly.<br />
                   The service calculate the new size of output file and writes every chunk and hash exactly to the relevat poisition.<br />

   * FileDecoder - encrypt file by hash of first chunck. this service return boolean result for valiv\invalid file.<br />

   * HashGenerator - generate SHA-256 hashing.<br />

   * HexConverter - convert from\to Hex String.<br />

**Unit Testing**:<br />
the project's tests written by JUnit.<br />
The tests include multiple options of Junit include using an option of TemporaryFolder for genataring temporary files.<br />
