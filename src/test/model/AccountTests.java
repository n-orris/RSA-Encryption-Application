package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SealedObject;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.List;

public class AccountTests {
    private CipherObj cipherObj;
    private Account account;
    private List<SealedObject> encryptedMsgs;
    private String id;


    @BeforeEach
    void setup() throws Exception {
        cipherObj = new CipherObj();
        cipherObj.genKeyPair();
        encryptedMsgs = new ArrayList<>();
        account = new Account(cipherObj, encryptedMsgs, "Admin");
    }

    @Test
    void constructorTest() {
        assertNotNull(account.getAccountCipher());
        assertNotNull(account.getEncryptedMsgs());
        assertNotNull(account.getId());
    }

    @Test
    void addEncryptionTest() throws InvalidKeyException, IOException, IllegalBlockSizeException {
        int beforeSize = encryptedMsgs.size();
        SealedObject sealedObject = cipherObj.encryptText("Test");
        account.addEncryption(sealedObject);
        int afterSize = account.getEncryptedMsgs().size();
        //Test for succesful addition of one object
        assertEquals(beforeSize + 1, afterSize);
    }

    @Test
    void removeEncryptionTest() {

        try {
            account.removeEncryption();
        } catch (ArrayIndexOutOfBoundsException ee) {
            fail("Tried to remove from null array");
        } //nothing to do here

        //account.addEncryption(cipherObj.encryptText("add1"));
        //int beforeSize = account.getEncryptedMsgs().size();
        //account.removeEncryption();


    }
}
