package com.example.fileencoder
.tests;

import com.example.fileencoder
.FileDecoder;
import com.example.fileencoder
.HexConverter;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.*;

public class DecodeTest {

    FileDecoder decoder = new FileDecoder();

    @Rule
    public TemporaryFolder temp = new TemporaryFolder();

    @Test
    public void decodeFileSuccess()  {
        byte[] h0 = HexConverter.hexStringToByteArray("9aa157efae6f473505f2e75cdcbb6aa6626f76653431dd5e0d817293f026caaf");
        File file = new File(getClass().getClassLoader().getResource("zero.bin").getFile());
        boolean isValid = decoder.decode(h0, file.getAbsolutePath());
        assertTrue(isValid);
    }

    @Test
    public void fileDoesNotExist() {
        byte[] h0 = HexConverter.hexStringToByteArray("9aa157efae6f473505f2e75cdcbb6aa6626f76653431dd5e0d817293f026caaf");
        boolean isValid = decoder.decode(h0,"noFile.bin");
        assertFalse(isValid);
    }

    @Test
    public void incorrectHashGetIllegalArgumentsException(){
        byte[] h0 = HexConverter.hexStringToByteArray("9aa157efae6f473505f2e75cdcbb6aa6626f76653431dd5e0d817293f026caa0");
        File file = new File(getClass().getClassLoader().getResource("encodedFile.bin").getFile());
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> decoder.decode(h0, file.getAbsolutePath()),
                "Expected doThing() to throw, but it didn't"
        );
        assertTrue(thrown.getMessage().contains("File is not valid"));
    }


}
