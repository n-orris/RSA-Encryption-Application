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



    @BeforeEach
    void setup() throws Exception {
        //All relevant exceptions already tested for cipherobj so not need to test exceptions here
        cipherObj = new CipherObj();
        cipherObj.genKeyPair("RSA");
        account = new Account(cipherObj, "Admin");
    }


    @Test
    void constructorTest() {
        assertNotNull(account.getAccountCipher());
        assertNotNull(account.getAccountCipher().getEncryptedMsgs());
        assertNotNull(account.getId());
    }



    //
    @Test
    void removeEncryptionTest() {
        SealedObject testObject = cipherObj.encryptText("Test object for removeEncrypt");
        int beforeSize = account.getAccountCipher().getEncryptedMsgs().size();
        account.removeEncryption(0);
        int afterSize = account.getAccountCipher().getEncryptedMsgs().size();
        assertEquals(beforeSize - 1, afterSize);

        try {
            account.removeEncryption(10000);
        } catch (Exception e) {
        }
        try {
            SealedObject testObject1 = cipherObj.encryptText("Test object for removeEncrypt");
            account.removeEncryption(0);
        } catch (Exception ee) {
            fail("Tried to remove from null array");
        } //nothing to do here
    }

    @Test
    void newCipherTest() throws Exception {
        CipherObj testC = new CipherObj();
        account.newCipher(testC);
        assertEquals(2, account.getCipherSize());
    }

    @Test
    void removeEncryption() {
        account.getAccountCipher().encryptText("test");
        int before = account.getAccountCipher().getEncryptedMsgs().size();
        account.getAccountCipher().removeEncryption(0);
        assertEquals(before - 1, account.getAccountCipher().getEncryptedMsgs().size());

        try {
            CipherObj cipher2 = new CipherObj();
            account.newCipher(cipher2);
            account.useCipher(2);
            int before1 = account.getAccountCipher().getEncryptedMsgs().size();
            account.getAccountCipher().removeEncryption(0);
            assertEquals(before - 1, account.getAccountCipher().getEncryptedMsgs().size());
            fail("invalid index");
        } catch (Exception e) {
            // should pass
        }

    }


}
