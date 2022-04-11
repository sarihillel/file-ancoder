# file-encoder assigment

Project Language/Tools:<br />
Java 11__
Maven 4.0.0__
Spring Boot 2.5.12__
Junit 5__

Requirments:_ 
encode / decode file by chunks of 1024 bytes to SHA-256 hash that concat to the prev chunk._

Exceution:_
the project contains 4 services:_

   * FileEncoder - read from input file by chunks,generate hash by SHA-256 and write to output file._
                   The Reading and writing uses by RandomAccessFile with specific pointer to current chunk position in order to enable reading big files effectivitlly._
                   The service calculate the new size of output file and writes every chunk and hash exactly to the relevat poisition._

   * FileDecoder - encrypt file by hash of first chunck. this service return boolean result for valiv\invalid file._

   * HashGenerator - generate SHA-256 hashing._

   * HexConverter - convert from\to Hex String._

Unit Testing:_
the project's tests written by JUnit._
The tests include multiple options of Junit include using an option of TemporaryFolder for genataring temporary files._
