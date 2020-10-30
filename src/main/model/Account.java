package model;


import javax.crypto.SealedObject;
import java.util.ArrayList;
import java.util.List;

// An account which stores a users information and cypher objects
public class Account {
    private String id;
    private List<CipherObj> userCiphers = new ArrayList<>();
    private List<SealedObject> encryptedMsgs;

    public Account(CipherObj userCipher, List<SealedObject> encryptedMsgs, String id) {
        this.userCiphers.add(userCipher);
        this.encryptedMsgs = encryptedMsgs;
        this.id = id;
    }

    //MODIFIES: this
    //EFFECTS: Add a SealedObject to encryptedMsgs Arraylist
    public void addEncryption(SealedObject encryptObj) {
        encryptedMsgs.add(encryptObj);
    }

    //MODIFIES: this
    //EFFECTS: Removes the arraylist object at index, catchs IndexOutOfBoundsException
    public void removeEncryption(int index) {
        try {
            encryptedMsgs.remove(index);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public List<CipherObj> getAccountCipher() {
        return userCiphers;
    }

    public List<SealedObject> getEncryptedMsgs() {
        return encryptedMsgs;
    }

    public String getId() {
        return id;
    }
}

