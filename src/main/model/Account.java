package model;


import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import javax.crypto.SealedObject;
import java.util.ArrayList;
import java.util.List;

// An account which stores a users information and cypher objects
public class Account implements Writable {
    private String userid;
    public int cipherId;
    private List<CipherObj> userCiphers = new ArrayList<>();


    //EFFECTS: Constructs a new account with 1 cipherobj
    public Account(String id) {
        this.userid = id;
    }

    //MODIFIES: this
    //EFFECTS: adds a new cipher to userciphers
    public void newCipher(CipherObj cipher) {
        userCiphers.add(cipher);
        int size = userCiphers.size();
        userCiphers.get(size - 1).setId(size - 1);
    }


    //MODIFIES: this
    //EFFECTS: Removes the arraylist object at index, catchs IndexOutOfBoundsException
    public void removeEncryption(int index) {
        try {
            userCiphers.get(cipherId).removeEncryption(index);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //MODIFIES: this
    //EFFECTS: Chanegs cipherid
    public void useCipher(int index) {
        this.cipherId = index - 1;
    }


    public CipherObj getAccountCipher() {
        return userCiphers.get(cipherId - 1);
    }

    public int getCipherSize() {
        return userCiphers.size();
    }

    //EFFECTS: Returns encrypted messages
    public List<SealedObject> getEncryptedMsgs() {
        if (userCiphers.size() == 1) {
            return userCiphers.get(0).getEncryptedMsgs();
        }
        return userCiphers.get(cipherId).getEncryptedMsgs();
    }

    public List<CipherObj> getCiphers() {
        return userCiphers;
    }

    public String getId() {
        return userid;
    }

    // Output Json account data to a file
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("userid", userid);
        json.put("Ciphers", ciphersToJson());
        return json;
    }


    //EFFECTS: creates and returns a json array on account data
    private JSONArray ciphersToJson() {
        JSONArray jsonArray = new JSONArray();

        for (CipherObj c : userCiphers) {
            jsonArray.put(c.toJson());
        }
        return jsonArray;
    }
}

