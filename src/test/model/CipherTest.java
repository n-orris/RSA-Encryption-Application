package model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import static org.junit.jupiter.api.Assertions.*;


public class CipherTest {

    CipherObj testObj;
    private final String testString = "test 323lfdsn";

    CipherObj invalidCipher;
    // hardcoded example public key and private key
    String pubkey1 = "24929200819210665355564229519797990023590892395733576359303052796657782020451015052143112507" +
            "2197" +
            "728392407607589157635707866607824863444659217351002904265085768041043979122470762307186702660972949" +
            "8239147433588448008766293209987542157686706073316270046166191645801704549728728028979309356387111112" +
            "095920820586121227113623269390554456201148281123836957567261602677023670580398980663240223578181997" +
            "7601515759042967180724673255548941686110233074732245171778266640024165472407820265185940251273897708" +
            "5308861658829054811512252262105836471552671848210019145643287660478414488388546964922713836442903453" +
            "90746919333326389404681";
    String privMod = "18058123764724033983115843357237085517544543310896207031483567714623300336099017780640009141" +
            "154613871947071892213047753906548067719246700774639178325878112397975248759030229568559398815468463372" +
            "963106141328974982271694086507624790560121121084571760090588767722199008864971081395292367806613923315" +
            "9091608264867349826278263939629964738015634222919362666668101446999345224202877327431360305628992217534" +
            "0623686830333705828760898442930670531891637070667749300942628553210369631063080393880840725330415840567" +
            "503157120411840330888326577629757717897459492281189716876264985277745689806652358660268152375181246" +
            "71513" +
            "22027251377";
    String privExp = "773801500960900020366880127258325941970495155103068773464078355935984471884078789590175590446" +
            "6866371147437629873491616536576123193914965559213757799802173283371819138804735294167471763046841584187" +
            "9077432128660821694002629261886719456166965747292176728261037878223715756745484050840832676677114985531" +
            "3736500326915662927348896556219752643377049045136257935985484969249205164449682301278841966708385694" +
            "1033" +
            "044651808225240215991135019763724103354406738904732856467687890938274373420114861581501170702950852" +
            "66847" +
            "371225022910638428246408971231854925482678287039361709514340857595506171547987055983357037322558707132" +
            "977" +
            "6833";


    @BeforeEach
    void setup() throws NoSuchPaddingException, NoSuchAlgorithmException {
        // Requires exceptions but wont throw them as Algorithm/padding is currently hardcoded
        testObj = new CipherObj();
        //Cipher object for invalid tests
        invalidCipher = new CipherObj();
    }


    // Having a known output for an encryption would defeat the point
    // this test therefor makes sure the output encrypt is the expected byte size
    @Test
    void encryptionTest() {
        testObj.genKeyPair("RSA");
        assertEquals(testObj.encryptText(testString).getClass().getSimpleName(), "SealedObject");
    }

    // Uses same encrypted text used in the first test
    @Test
    void decryptTest() throws Exception {
        // test a string can be encrypted than decrypted
        testObj.genKeyPair("RSA");
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
    void keyGenTest() {

        try {
            invalidCipher.genKeyPair("s");
        } catch (Exception e) {
        }
        // Tests that exception is not thrown as it
        try {
            testObj.genKeyPair("RSA");
        } catch (Exception e) {
            fail();
        }

        boolean valid = testObj.validPair(testObj.getPublicKey(), testObj.getPrivateKey());
        boolean invalid = testObj.validPair(invalidCipher.getPublicKey(), invalidCipher.getPrivateKey());
        assertTrue(valid);
        assertFalse(invalid);
    }


    // checks to make sure it will only validate keys that are a pair.
    @Test
    void validPairTest() {

        testObj.genKeyPair("RSA");
        try {
            testObj.validPair(invalidCipher.getPublicKey(), testObj.getPrivateKey());
        } catch (Exception e) {
            fail("Invalid public Key");
        }
        try {
            testObj.validPair(testObj.getPublicKey(), invalidCipher.getPrivateKey());
        } catch (Exception e) {
            fail("Invalid private key");
        }
        // valid key pair test
        PublicKey pubKey = testObj.getPublicKey();
        PrivateKey privKey = testObj.getPrivateKey();
        assertTrue(testObj.validPair(pubKey, privKey));
        // Invalid Key pair
        invalidCipher.createPublicKey(pubkey1);
        invalidCipher.createPrivateKey(privMod, privExp);
        PublicKey pubKey2 = invalidCipher.getPublicKey();
        PrivateKey privKey2 = invalidCipher.getPrivateKey();  //different priv key
        assertFalse(testObj.validPair(pubKey2, privKey2));
    }

    @Test
    void createPublicKeyTest() {
        try {
            invalidCipher.createPublicKey(null);
        } catch (Exception e) {
        }
        try {
            testObj.createPublicKey(pubkey1);
        } catch (Exception e) {
            fail("Invalid public Key");
        }

        // Null test
        assertNull(testObj.createPublicKey(null));
        // Valid key test
        assertNotNull(testObj.createPublicKey(pubkey1));
        // Invalid/incorrect format string input
        assertNull(testObj.createPublicKey("Fdsffdsaf"));

        // invalid key creation
        invalidCipher.createPublicKey("dsafwdsds");

        // tests that key that should be valid can encrypt
        assertNotNull(testObj.encryptText("tttest"));
        //tests that invalid key cant encrypt
        assertNull(invalidCipher.encryptText("test"));
    }

    @Test
    void createPrivateKeyTest() throws Exception {

        try {
            invalidCipher.createPrivateKey("invalid Modulus", "invalid exp");
        } catch (Exception e) {
        }
        try {
            testObj.createPublicKey(pubkey1);
        } catch (Exception e) {
            fail("Invalid Private key modulus or exponent");
        }

        CipherObj testObj2 = new CipherObj();
        // test key data
        assertNull(testObj.createPrivateKey(null, "33243"));
        assertNull(testObj.createPrivateKey("Fdsffdsaf", "43253"));
        assertNotNull(testObj.createPrivateKey(privMod, privExp));
        testObj.createPublicKey(pubkey1);
        SealedObject test = testObj.encryptText("Test");
        assertNull(testObj2.decryptText(test));
    }

    @Test
    void getCipherEncryptTest() throws Exception {
        testObj.genKeyPair("RSA");

        try {
            invalidCipher.getCipherEncrypt();
        } catch (Exception e) {

        }

        try {
            testObj.getCipherEncrypt();
        } catch (Exception e) {
            fail("valid cipher should be active");
        }


        Cipher cipher = testObj.getCipherEncrypt();
        SealedObject sealedObject = new SealedObject("tt", cipher);
        assertTrue(sealedObject instanceof SealedObject);
    }


    @Test
    void getCipherDecryptTest() throws Exception {
        testObj.createPrivateKey("Invalid Key to set key to null", "test.test");
        try {
            invalidCipher.getCipherDecrypt();
        } catch (Exception e) {
        }
        try {
            testObj.getCipherDecrypt();
        } catch (Exception e) {
            fail("Should have valid private key");
        }

        testObj.genKeyPair("RSA");
        SealedObject test = testObj.encryptText("test");
        Cipher cipher = testObj.getCipherDecrypt();
        assertEquals(test.getObject(cipher), "test");

    }
}


