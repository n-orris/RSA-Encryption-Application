package model;


import model.CipherObj;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SealedObject;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;


public class CipherTest {

    CipherObj testObj;
    private final String testString = "test 323lfdsn";
    File myObj = new File("C:\\Users\\taran\\210 Project\\data\\data.txt"); //test data
    String pubKey;
    String pubKey2;
    String privMod2;
    String privExp2;
    String invalidPub;
    String invalidPrivMod;
    String invalidPrivExp;
    String bbb = "249292008192106653555642295197979900235908923957335763593030527966577820204510150521431125072197" +
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
            "50315712041184033088832657762975771789745949228118971687626498527774568980665235866026815237518124671513" +
            "22027251377";
    String privExp = "773801500960900020366880127258325941970495155103068773464078355935984471884078789590175590446" +
            "6866371147437629873491616536576123193914965559213757799802173283371819138804735294167471763046841584187" +
            "9077432128660821694002629261886719456166965747292176728261037878223715756745484050840832676677114985531" +
            "37365003269156629273488965562197526433770490451362579359854849692492051644496823012788419667083856941033" +
            "04465180822524021599113501976372410335440673890473285646768789093827437342011486158150117070295085266847" +
            "371225022910638428246408971231854925482678287039361709514340857595506171547987055983357037322558707132977" +
            "6833";


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
    void keyGenTest() throws Exception {
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
        CipherObj cObj = new CipherObj();

        assertNull(testObj.createPublicKey(null));
        PublicKey publicKey = testObj.createPublicKey(bbb);
        assertNull(testObj.createPublicKey("Fdsffdsaf"));
        assertNotNull(testObj.createPublicKey(bbb));
        cObj.createPublicKey("dsafwdsds");
        assertNotNull(testObj.encryptText("tttest"));
        assertNull(cObj.encryptText("test"));
    }

    @Test
    void createPrivateKeyTest() throws Exception {
        CipherObj testObj2 = new CipherObj();
        // test key data
        assertNull(testObj.createPrivateKey(null, "33243"));
        PrivateKey privateKey = testObj.createPrivateKey(privMod, privExp);
        assertNull(testObj.createPrivateKey("Fdsffdsaf", "43253"));
        assertNotNull(testObj.createPrivateKey(privMod, privExp));
        testObj.createPublicKey(bbb);
        SealedObject test = testObj.encryptText("Test");
        assertNull(testObj2.decryptText(test));
    }

    @Test
    void getCipherEncryptTest() throws Exception {
        testObj.genKeyPair();
        Cipher cipher = testObj.getCipherEncrypt();
        SealedObject sealedObject = new SealedObject("tt", cipher);
        assertTrue(sealedObject instanceof SealedObject);
    }

    @Test
    void getCipherDecryptTest() throws Exception {
        testObj.genKeyPair();
        SealedObject test = testObj.encryptText("test");
        Cipher cipher = testObj.getCipherDecrypt();
        assertEquals(test.getObject(cipher), "test");

    }
}


