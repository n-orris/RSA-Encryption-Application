package model;

import static org.junit.jupiter.api.Assertions.*;
import model.CipherObj;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import model.Account;

import javax.crypto.SealedObject;
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
        account = new Account(cipherObj,encryptedMsgs,"Admin");
    }

    @Test
    void constructorTest() {
        assertNotNull(account.getAccountCipher());
        assertNotNull(account.getEncryptedMsgs());
        assertNotNull(account.getId());
    }

    @Test
    void addEncryptionTest() {
        if (encryptedMsgs == null) {
            encryptedMsgs.add(cipherObj.encryptText("test"));
            assertEquals(encryptedMsgs.size(),1);
        } else {
            int beforeSize = encryptedMsgs.size();
            encryptedMsgs.add(cipherObj.encryptText("another test"));
            assertTrue(beforeSize + 1 == encryptedMsgs.size());
        }
    }

    @Test
    void removeEncryptionTest() {

    }


}
