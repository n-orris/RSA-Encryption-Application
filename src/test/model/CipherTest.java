package model;


import exceptions.InvalidKeyPairException;
import exceptions.PrivateKeyException;
import exceptions.PublicKeyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

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

    String invalidPub = "17250996A64411591914539129084015705832284460398449270610431346579736693972224842801244" +
            "830918811503224924767850315558126854728329135498341443650291972504356345600828837678531646034371829" +
            "6371954269979477529251575253327565679488473976851457133804662674062905594732456851873808205007432128" +
            "484268830299539762727274686617461753605492350254093920059992869533607124087568435493504495168703003769" +
            "2469281864262350251552219968228813262309071705493496733573345649689319076725809161376012952520912045357" +
            "12794934251754854160812632841115537694273901696651807964258180283984789519088519678898350830291269234762" +
            "9528387788010141323829A";


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

        try {
            assertEquals(testObj.encryptText(testString).getClass().getSimpleName(), "SealedObject");
        } catch (PublicKeyException e) {
            fail("valid key to encrypt");
        }

        try {
            invalidCipher.encryptText(testString);
            fail("Invalid key, should throw exception");
        } catch (PublicKeyException e) {
            //PASS
        }

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
            invalidCipher.genKeyPair("r");
            boolean invalid = testObj.validPair(invalidCipher.getPublicKey(), invalidCipher.getPrivateKey());
            assertFalse(invalid);
            fail("Invalid pair, should throw exception");
        } catch (Exception e) {
            //PASS
        }

        try {
            testObj.genKeyPair("RSA");
            boolean valid = testObj.validPair(testObj.getPublicKey(), testObj.getPrivateKey());
            assertTrue(valid);
        } catch (Exception e) {
            fail("Valid keypair, exception not expected");
        }
    }








    // checks to make sure it will only validate keys that are a pair.
    @Test
    void validPairTest() throws PublicKeyException, PrivateKeyException {


        testObj.genKeyPair("RSA");
        try {
            testObj.validPair(invalidCipher.getPublicKey(), testObj.getPrivateKey());
            fail("Invalid public Key, expection expected");
        } catch (Exception e) {
            //PASS

        }
        try {
            testObj.validPair(testObj.getPublicKey(), invalidCipher.getPrivateKey());
            fail("Invalid private key, expection expected");
        } catch (Exception e) {
            // PASS
        }
        // valid key pair test
        try {

            PublicKey pubKey = testObj.getPublicKey();
            PrivateKey privKey = testObj.getPrivateKey();

            assertTrue(testObj.validPair(pubKey, privKey));
        } catch (InvalidKeyPairException e) {
            fail("Valid pair, no exception expected");
        }
        // Invalid Key pair
        try {
            PublicKey pubKey2 = invalidCipher.getPublicKey();
            PrivateKey privKey2 = testObj.getPrivateKey();
            assertFalse(testObj.validPair(pubKey2, privKey2));
            fail("Should throw expection");
        } catch (InvalidKeyPairException e) {
            //PASS
        }
    }

    @Test
    void createPublicKeyTest() throws PublicKeyException {
        try {
            invalidCipher.createPublicKey("fdsf4d");
            fail("Invalid public key string, exception should have been thrown ");
        } catch (PublicKeyException e) {
            //Pass
        }
        try {
            testObj.createPublicKey(pubkey1);
        } catch (PublicKeyException e) {
            fail("valid public key string, no expected exception ");
        }

        // Valid key test
        assertNotNull(testObj.createPublicKey(pubkey1));
        // Invalid/incorrect format string input

        // invalid key creation of correct length
        try {
            invalidCipher.createPublicKey(invalidPub);

            // tests that key that should be valid can encrypt
            assertNotNull(testObj.encryptText("tttest"));
            fail("Correct length but invalid key should throw exception");

        } catch (PublicKeyException e) {
            //Pass
        }
    }

    @Test
    void createPrivateKeyTest() throws PrivateKeyException, NoSuchPaddingException, NoSuchAlgorithmException {

        // Invalid  length modulus & exponent tests
        try {
            invalidCipher.createPrivateKey("32424352346544445", privExp);
            fail("Invalid private Modulus, should throw exception");
        } catch (Exception e) {
            //Pass
        }
        try {
            testObj.createPrivateKey(privMod, "4343533344344334");
            fail("Invalid Private key modulus or exponent");
        } catch (Exception e) {
            //Pass
        }

        try {
            testObj.createPrivateKey(privMod,privExp);
        } catch (PrivateKeyException e) {
            fail("valid key, exception not expected");
        }

    }

    @Test
    void getCipherEncryptTest() throws Exception {
        testObj.genKeyPair("RSA");

        try {
            invalidCipher.getCipherEncrypt();
            fail("No public key, exception expected");
        } catch (PublicKeyException e) {
            //PASS

        }

        try {
            testObj.getCipherEncrypt();
        } catch (Exception e) {
            fail("valid public and cipher should be active");
        }


        Cipher cipher = testObj.getCipherEncrypt();
        SealedObject sealedObject = new SealedObject("tt", cipher);
        assertTrue(sealedObject instanceof SealedObject);
    }


    @Test
    void getCipherDecryptTest() throws Exception {
        testObj.genKeyPair("RSA");
        try {
            invalidCipher.getCipherDecrypt();
            fail("No private key, exception expected");
        } catch (Exception e) {
            //PASS
        }
        try {
            testObj.getCipherDecrypt();
            //PASS
        } catch (Exception e) {
            fail("Should have valid private key");
        }

        testObj.genKeyPair("RSA");
        SealedObject test = testObj.encryptText("test");
        Cipher cipher = testObj.getCipherDecrypt();
        assertEquals(test.getObject(cipher), "test");
    }

    @Test
    void getterSetterTests() throws PublicKeyException {
        testObj.genKeyPair("RSA");
        SealedObject obj = testObj.encryptText("test");
        int before = testObj.getEncryptedMsgs().size();
        testObj.removeEncryption(0);
        assertEquals(testObj.getEncryptedMsgs().size(), 0);
    }
}




















