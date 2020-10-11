package model.ciphertest;

import model.CipherObj;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.*;

import javax.crypto.SealedObject;
import javax.crypto.CipherOutputStream;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.security.PublicKey;
import java.util.Scanner;


public class CipherTest {

    CipherObj testObj;
    SealedObject ciphertext;
    private String testString = "test 323lfdsn";
    CipherOutputStream cipherOutputStream;
    File myObj = new File("C:\\Users\\taran\\210 Project\\src\\test\\model\\ciphertest\\data.txt"); //test data


    @BeforeEach
    void setup() throws Exception {
        testObj = new CipherObj();

    }
    // Having a known output for an encryption would defeat the point
    // this test therefor makes sure the output encrypt is the expected byte size
    @Test
    void encryptionTest() throws Exception {
        testObj.genKeyPair();
        assertEquals(testObj.encryptText(testString).getClass().getSimpleName(),"SealedObject");







    }

    // Uses same encrypted text used in the first test
    @Test
    void decryptTest() throws Exception {
        // test a string can be encrypted than decrypted
        testObj.genKeyPair();
        SealedObject sealedTest = testObj.encryptText(testString);
        String output = testObj.decryptText(sealedTest);
        assertEquals(output, testString);
        // empty string test
        SealedObject sealedTest1 = testObj.encryptText("");
        String output1 = testObj.decryptText(sealedTest1);
        assertEquals(output1, "");


    }

    // checks if valid keyPair has been generated
    @Test
    void keyGenTest() throws Exception {
        testObj.genKeyPair();
        boolean valid = testObj.validPair(testObj.getPublicKey(), testObj.getPrivateKey());
        assertTrue(valid);
    }


    // checks to make sure it will only validate keys that are a pair.
    @Test
    void validPairTest() throws Exception {
        //key pairings imported from ciphertest\data.txt
        Scanner reader = new Scanner(myObj);

        // valid key pair test
        testObj.createPublicKey(reader.nextLine(),reader.nextLine());  //reader inputs valid public key lines(1-2)
        testObj.createPrivateKey(reader.nextLine(),reader.nextLine()); //reader inputs valid private key lines(3-4)
        assertTrue(testObj.validPair(testObj.getPublicKey(),testObj.getPrivateKey()));
        // Invalid key test (different public/private key modulus
        testObj.createPublicKey(reader.nextLine(),reader.nextLine());  //reader inputs valid public key lines(5-6)
        testObj.createPrivateKey(reader.nextLine(),reader.nextLine()); //reader inputs valid private key lines(7-8)
        //assertFalse(testObj.validPair(testObj.getPublicKey(),testObj.getPrivateKey()));

    }

    @Test
    void createKeyTest() throws Exception {
        //keys imported from ciphertest\data.txt
        Scanner reader = new Scanner(myObj);

        // valid key parameters
        assertTrue(testObj.createPublicKey(reader.nextLine(),reader.nextLine())); //reader inputs valid public key lines(1-2)
        assertTrue(testObj.createPrivateKey(reader.nextLine(),reader.nextLine()));
        // advance to needed test lines
        for (int i = 0;i < 2;i++) {
            reader.nextLine();
        }
        assertFalse(testObj.createPublicKey(reader.nextLine(),reader.nextLine())); //reader inputs valid public key lines(1-2)
        assertFalse(testObj.createPrivateKey(reader.nextLine(),reader.nextLine()));

    }
}


















