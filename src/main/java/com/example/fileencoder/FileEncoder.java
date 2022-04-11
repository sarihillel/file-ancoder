package com.example.fileencoder;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.NoSuchAlgorithmException;

public class FileEncoder {
    private final int CHUNK_SIZE = 1024;
    private final int CHUNK_WITH_HASH_SIZE = 1056;

    public byte[] encode(String inputPath, String outputPath) throws IOException, NoSuchAlgorithmException {
        File file = new File(inputPath);

        RandomAccessFile inputFile = null;
        RandomAccessFile encodedFile = null;

        long fileLen = file.length();
        byte[] chunkBuffer = new byte[CHUNK_SIZE];
        byte[] prevHash = null;
        int lastChunkSize = getLastChunkSize(file);
        long outputFileLen = calcDecodedFileLength(file.length());
        long rPointer = fileLen - lastChunkSize;
        long wPointer = outputFileLen - lastChunkSize;

        try {
            inputFile = new RandomAccessFile(file, "r");
            encodedFile = new RandomAccessFile(new File(outputPath), "rw");
            encodedFile.setLength(outputFileLen);

            prevHash = encodeLastChunk(inputFile, encodedFile, rPointer, wPointer, new byte[lastChunkSize]);

            while (rPointer > 0 && wPointer > 0) {
                rPointer -= CHUNK_SIZE;
                wPointer -= CHUNK_WITH_HASH_SIZE;
                prevHash = encodeNextChunk(inputFile, encodedFile, chunkBuffer, rPointer, wPointer, prevHash);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } finally {
            closeStreams(inputFile, encodedFile);
        }
        return prevHash;
    }

    private void closeStreams(RandomAccessFile inputFile, RandomAccessFile encodedFile) throws IOException {
        try {
            if (inputFile != null) {
                inputFile.close();
            }
            if (encodedFile != null) {
                encodedFile.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getLastChunkSize(File file) {
        return file.length() % CHUNK_SIZE > 0 ? (int) file.length() % CHUNK_SIZE : CHUNK_SIZE;
    }

    private long calcDecodedFileLength(long inputFileLength) {
        if (inputFileLength % CHUNK_SIZE == 0)
            return inputFileLength + (inputFileLength / CHUNK_SIZE - 1) * 32;
        return inputFileLength + (inputFileLength / CHUNK_SIZE) * 32;
    }

    private byte[] encodeLastChunk(RandomAccessFile source, RandomAccessFile dest, long rPointer, long wPointer, byte[] chunk) throws IOException, NoSuchAlgorithmException {
        source.seek(rPointer);
        source.read(chunk);
        dest.seek(wPointer);
        dest.write(chunk);
        return HashGenerator.generate(chunk);
    }

    private byte[] encodeNextChunk(RandomAccessFile source, RandomAccessFile dest, byte[] chunk, long rPointer, long wPointer, byte[] prevHash) throws IOException, NoSuchAlgorithmException {
        source.seek(rPointer);
        source.read(chunk);
        dest.seek(wPointer);
        byte[] chunkWithHash = getChunkWithHash(chunk, prevHash);
        dest.write(chunkWithHash);
        return HashGenerator.generate(chunkWithHash);
    }

    private byte[] getChunkWithHash(byte[] chunk, byte[] prevHash) {
        byte[] chunkWithHash = new byte[1056];
        System.arraycopy(chunk, 0, chunkWithHash, 0, chunk.length);
        System.arraycopy(prevHash, 0, chunkWithHash, chunk.length, prevHash.length);
        return chunkWithHash;
    }
}