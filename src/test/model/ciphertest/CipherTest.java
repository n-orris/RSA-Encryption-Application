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
    File myObj = new File("C:\\Users\\taran\\210 Project\\src\\test\\model\\ciphertest\\data.txt"); //test data
    String pubKey;
    String privMod;
    String privExp;
    String pubKey2;
    String privMod2;
    String privExp2;
    String invalidPub;
    String invalidPrivMod;
    String invalidPrivExp;


    @BeforeEach
    void setup() throws Exception {
        testObj = new CipherObj();
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
    void keyGenTest()  {
        testObj.genKeyPair();
        boolean valid = testObj.validPair(testObj.getPublicKey(), testObj.getPrivateKey());
        assertTrue(valid);
    }


    // checks to make sure it will only validate keys that are a pair.
    @Test
    void validPairTest() throws Exception {
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
        //key pairings imported from ciphertest\data.txt
        // valid key pair test
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

        testObj.createPublicKey(pubKey);
        assertNotNull(testObj.getPublicKey());
    }

    @Test
    void createPrivateKeyTest() throws Exception {
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

        testObj.createPrivateKey(privMod,privExp);
        assertNotNull(testObj.getPrivateKey());
    }
}




















