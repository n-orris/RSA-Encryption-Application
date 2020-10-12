package model.ciphertest;

import model.CipherObj;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.CipherOutputStream;
import javax.crypto.SealedObject;
import java.io.File;
import java.security.InvalidAlgorithmParameterException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;


public class CipherTest {

    CipherObj testObj;
    SealedObject ciphertext;
    private String testString = "test 323lfdsn";
    CipherOutputStream cipherOutputStream;
    File myObj = new File("C:\\Users\\taran\\210 Project\\src\\test\\model\\ciphertest\\data.txt"); //test data
    private String pubKey;
    private String privMod;
    private String privExp;
    private String pubKey2;
    private String privMod2;
    private String privExp2;
    private String invalidPub;
    private String invalidPrivMod;
    private String invalidPrivExp;


    @BeforeEach
    void setup() throws Exception {
        testObj = new CipherObj();
        Scanner reader = new Scanner(myObj);
        // test key data
        pubKey = reader.nextLine();
        privMod = reader.nextLine();
        privExp = reader.nextLine();
        pubKey2 = reader.nextLine();
        privMod2 = reader.nextLine();
        privExp2 = reader.nextLine();
        invalidPub = reader.nextLine();
        invalidPrivMod = reader.nextLine();
        invalidPrivExp = reader.nextLine();
    }

    // Having a known output for an encryption would defeat the point
    // this test therefor makes sure the output encrypt is the expected byte size
    @Test
    void encryptionTest() throws Exception {
        testObj.genKeyPair();
        assertEquals(testObj.encryptText(testString).getClass().getSimpleName(), "SealedObject");
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


    @Test
    void createKeyTest() throws Exception {
        //keys imported from ciphertest\data.txt
        //valid key creations
        assertTrue(testObj.createPublicKey(pubKey));
        assertTrue(testObj.createPrivateKey(privMod, privExp));
        // Invalid Test
        assertFalse(testObj.createPublicKey(invalidPub));
        assertFalse(testObj.createPrivateKey(invalidPrivMod, invalidPrivExp));

    }

    // checks to make sure it will only validate keys that are a pair.
    @Test
    void validPairTest() throws Exception {
        //key pairings imported from ciphertest\data.txt
        // valid key pair test
        testObj.createPrivateKey(privMod, privExp);
        testObj.createPublicKey(pubKey);
        PublicKey pubKey = testObj.getPublicKey();
        PrivateKey privKey = testObj.getPrivateKey();
        assertTrue(testObj.validPair(pubKey, privKey));
        // Invalid Key pair
        testObj.createPrivateKey(privMod2, privExp2);
        PrivateKey privKey2 = testObj.getPrivateKey();  //different priv key
        assertFalse(testObj.validPair(pubKey, privKey2));

    }
}




















