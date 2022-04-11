package com.example.fileencoder.tests;

import com.example.fileencoder
.FileEncoder;
import com.example.fileencoder
.HashGenerator;
import com.example.fileencoder
.HexConverter;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.*;


public class EncodeTest {

    FileEncoder encoder = new FileEncoder();

    @Rule
    public TemporaryFolder folder= new TemporaryFolder();

    @Test
    public void encodeFileChunks_equalsHashCode() throws IOException, NoSuchAlgorithmException {
        File file = new File(getClass().getClassLoader().getResource("zero.bin").getFile());
        File tempFile  = folder.newFile("encodedFile.bin");
        byte[] h0 = encoder.encode(file.getAbsolutePath(), tempFile.getAbsolutePath());
        assertEquals("e6e19106304b9665184e957d5dbf58ad36c55b52bf8d993dae4340c96bd8e81f", HexConverter.toHexString(h0));
    }

    @Test
    public void fileSize10240bytes_exactlyPerChunks() throws IOException, NoSuchAlgorithmException {
        File file = new File(getClass().getClassLoader().getResource("file10240Bytes.bin").getFile());
        File tempFile = folder.newFile("encodedFile.bin");
        byte[] h0 = encoder.encode(file.getAbsolutePath(), tempFile.getAbsolutePath());
        assertEquals("9aa157efae6f473505f2e75cdcbb6aa6626f76653431dd5e0d817293f026caaf", HexConverter.toHexString(h0));
    }

    @Test
    public void fileSize600bytes_notEncodedFile() throws IOException, NoSuchAlgorithmException {
        File file = new File(getClass().getClassLoader().getResource("file600Bytes.bin").getFile());
        File tempFile = folder.newFile("encodedFile.bin");
        byte[] h0 = encoder.encode(file.getAbsolutePath(), tempFile.getAbsolutePath());
        assertEquals("9823d2c283e143bb3cb34600e68cc6dd45108be593d0099f64a008677e13fe4d", HexConverter.toHexString(h0));
        assertEquals(file.length(), tempFile.length());
    }

    @Test
    public void fileDoesNotExist() throws IOException, NoSuchAlgorithmException {
        assertNull(encoder.encode("noFile.bin", "encodedFile.bin"));
    }

    @Test
    public void encodeEncodedFile_equalsHashCode() throws IOException, NoSuchAlgorithmException {
        File file = new File(getClass().getClassLoader().getResource("zero.bin").getFile());
        File tempFile  = folder.newFile("encodedFile.bin");
        encoder.encode(file.getAbsolutePath(), tempFile.getAbsolutePath());
        FileInputStream stream = new FileInputStream(tempFile);
        byte[] hash = HashGenerator.generate(stream.readAllBytes());
        assertEquals("3857d5a53a726b96fe0de7f1b7bd0239c56aae4a5f9b9a75428cff6f850aeb56", HexConverter.toHexString(hash));
        stream.close();
    }
}