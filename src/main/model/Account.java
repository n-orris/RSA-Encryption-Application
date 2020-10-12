package model;



import javax.crypto.SealedObject;
import java.util.List;

public class Account {
    private String id;
    private CipherObj userCipher;
    private List<SealedObject> encryptedMsgs;

    public Account(CipherObj userCipher,List<SealedObject> encryptedMsgs,String id) {
        this.userCipher = userCipher;
        this.encryptedMsgs = encryptedMsgs;
        this.id = id;
    }

    //MODIFIES: this
    //EFFECTS: add a SealedObject to encryptedMsgs  Arraylist
    public void addEncryption(SealedObject encryptObj) {
        encryptedMsgs.add(encryptObj);
    }

    public void removeEncryption() {

    }


    public CipherObj getAccountCipher() {
        return userCipher;
    }

    public List<SealedObject> getEncryptedMsgs() {
        return encryptedMsgs;
    }

    public String getId() {
        return id;
    }
}
