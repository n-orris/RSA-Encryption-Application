package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class AccountTests {
    private CipherObj cipherObj;
    private Account account;
    private List<SealedObject> encryptedMsgs;


    @BeforeEach
    void setup() throws Exception {
        //All relevant exceptions already tested for cipherobj so not need to test exceptions here
        cipherObj = new CipherObj();
        cipherObj.genKeyPair("RSA");
        encryptedMsgs = new ArrayList<>();
        account = new Account(cipherObj, "Admin");
    }


    @Test
    void constructorTest() {
        assertNotNull(account.getAccountCipher());
        assertNotNull(account.getEncryptedMsgs());
        assertNotNull(account.getId());
    }

    @Test
    void addEncryptionTest() {
        int beforeSize = encryptedMsgs.size();
        SealedObject sealedObject = cipherObj.encryptText("Test");
        account.addEncryption(sealedObject);
        int afterSize = account.getEncryptedMsgs().size();
        //Test for succesful addition of one object
        assertEquals(beforeSize + 1, afterSize);
    }

    //
    @Test
    void removeEncryptionTest() {
        SealedObject testObject = cipherObj.encryptText("Test object for removeEncrypt");
        account.addEncryption(testObject);
        int beforeSize = account.getEncryptedMsgs().size();
        account.removeEncryption(0);
        int afterSize = account.getEncryptedMsgs().size();
        assertEquals(beforeSize - 1, afterSize);

        try {
            account.removeEncryption(10000);
        } catch (Exception e) {
        }
        try {
            SealedObject testObject1 = cipherObj.encryptText("Test object for removeEncrypt");
            account.addEncryption(testObject1);
            account.removeEncryption(0);
        } catch (Exception ee) {
            fail("Tried to remove from null array");
        } //nothing to do here
    }

    @Test
    void newCipherTest() throws Exception {
        CipherObj testC = new CipherObj();
    }
}
