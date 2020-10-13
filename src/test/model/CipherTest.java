package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import javax.crypto.Cipher;
import javax.crypto.SealedObject;
import java.io.File;
import java.io.FileNotFoundException;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;


public class CipherTest {

    CipherObj testObj;
    private final String testString = "test 323lfdsn";
    File myObj = new File("C:\\Users\\taran\\210 Project\\data\\data.txt"); //test data
    Scanner reader = new Scanner(myObj);
    // test key data
    String pubKey = reader.nextLine();
    String privMod = reader.nextLine();
    String privExp = reader.nextLine();
    String pubKey2 = reader.nextLine();
    String privMod2 = reader.nextLine();
    String privExp2 = reader.nextLine();
    String invalidPub = reader.nextLine();
    String invalidPrivMod = reader.nextLine();
    String invalidPrivExp = reader.nextLine();

    public CipherTest() throws FileNotFoundException {

    }



    @BeforeEach
    void setup() throws Exception {
        testObj = new CipherObj();
    }

    // Having a known output for an encryption would defeat the point
    // this test therefor makes sure the output encrypt is the expected byte size
    @Test
    void encryptionTest() {
        testObj.genKeyPair();
        assertEquals(testObj.encryptText(testString).getClass().getSimpleName(), "SealedObject");
    }

    // Uses same encrypted text used in the first test
    @Test
    void decryptTest() {
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
    void keyGenTest()  {
        testObj.genKeyPair();
        boolean valid = testObj.validPair(testObj.getPublicKey(), testObj.getPrivateKey());
        assertTrue(valid);
    }


    // checks to make sure it will only validate keys that are a pair.
    @Test
    void validPairTest() throws Exception {
        testObj.createPrivateKey(privMod, privExp);
        testObj.createPublicKey(pubKey);
        PublicKey pubKey1 = testObj.getPublicKey();
        PrivateKey privKey = testObj.getPrivateKey();
        assertTrue(testObj.validPair(pubKey1, privKey));
        // Invalid Key pair
        testObj.createPrivateKey(privMod2, privExp2);
        PrivateKey privKey2 = testObj.getPrivateKey();  //different priv key
        assertFalse(testObj.validPair(pubKey1, privKey2));
    }

    @Test
    void createPublicKeyTest() throws Exception {
        assertNotNull(testObj.createPublicKey(pubKey));
        assertNull(testObj.createPublicKey(invalidPub));
    }

    @Test
    void createPrivateKeyTest() throws Exception {
        PublicKey publicKey = testObj.createPublicKey(pubKey);
        assertNotNull(testObj.createPrivateKey(privMod,privExp));
        assertNull(testObj.createPrivateKey(invalidPrivMod,invalidPrivExp));
        assertNotNull(publicKey);
        assertTrue(publicKey instanceof PublicKey);
        assertTrue(testObj.createPublicKey(pubKey) instanceof PublicKey);
        PublicKey pubkey1 = testObj.createPublicKey(pubKey2);
        testObj.genKeyPair();
        assertTrue(pubkey1 != testObj.getPublicKey());
    }

    @Test
    void getCipherTest() throws InvalidKeyException {
        testObj.genKeyPair();
        assertTrue(testObj.getCipherEncrypt() instanceof Cipher);
        assertTrue(testObj.getCipherDecrypt() instanceof Cipher);
    }
}




















