package model;

import org.junit.jupiter.api.BeforeEach;
import model.CipherObj;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;



public class CipherTest {

    CipherObj cipherObj;
    public byte[] ciphertest;
    private String testString = "test 323lfdsn";

    @BeforeEach
    void setup() throws Exception {
        cipherObj = new CipherObj();
        cipherObj.genKeyPair();
        ciphertest = cipherObj.encryptText(testString);
    }

    // Having a known output for an encryption would defeat the point
    // this test therefor makes sure the output encrypt is the expected byte size
    @Test
    void byteSizeTest() throws Exception {



    }

    // Uses same encrypted text used in the first test
    @Test
    void decryptTest() throws Exception {
        byte[] result = cipherObj.decryptText(ciphertest);
        assertEquals(result,testString);

    }
}
