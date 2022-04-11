package com.example.fileencoder;

import org.springframework.stereotype.Service;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@Service
public class FileDecoder {

    private final int CHUNK_WITH_HASH_SIZE = 1056;
    private final int CHUNK_SIZE = 1024;

    public boolean decode(byte[] h0, String encodeFilePath) {
        FileInputStream encodedFile = null;
        byte[] hash = Arrays.copyOf(h0, h0.length);
        byte[] chunkWithHash;

        try {
            encodedFile = new FileInputStream(encodeFilePath);
            long fileLength = new File(encodeFilePath).length();

            while (fileLength > 0) {
                chunkWithHash = getBufferSize(fileLength);
                encodedFile.read(chunkWithHash);
                fileLength -= chunkWithHash.length;
                hash = validateChunk(chunkWithHash, hash);
            }
        } catch (IOException e) {
            return false;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } finally {
            closeStream(encodedFile);
        }
        return true;
    }

    private byte[] validateChunk(byte[] chunkWithHash, byte[] hash) throws NoSuchAlgorithmException {
        byte[] chunkHash = HashGenerator.generate(chunkWithHash);
        if (!Arrays.equals(chunkHash, hash)) {
            throw new IllegalArgumentException("File is not valid!");
        }
        byte[] chunk = Arrays.copyOfRange(chunkWithHash, 0, CHUNK_SIZE);
        // do something....
        byte[] currHash = Arrays.copyOfRange(chunkWithHash, CHUNK_SIZE, chunkWithHash.length);
        return currHash;
    }

    private void closeStream(FileInputStream encodedFile) {
        try {
            if (encodedFile != null) {
                encodedFile.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] getBufferSize(long fileLength) {
        return fileLength > CHUNK_WITH_HASH_SIZE ? new byte[CHUNK_WITH_HASH_SIZE] : new byte[(int) fileLength];
    }
}
