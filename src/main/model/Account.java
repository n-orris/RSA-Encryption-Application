package model;


import org.json.JSONArray;
import org.json.JSONObject;
import persistence.JsonReader;
import persistence.JsonWriter;
import persistence.Writable;

import javax.crypto.SealedObject;
import java.util.ArrayList;
import java.util.List;

// An account which stores a users information and cypher objects
public class Account implements Writable {
    private String userid;
    private int cipherId;
    private List<CipherObj> userCiphers = new ArrayList<>();
    private List<List<SealedObject>> encryptedMsgs = new ArrayList<>();
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    public Account(CipherObj userCipher, List<SealedObject> encryptedMsgs, String id) {
        this.userCiphers.add(userCipher);
        userCiphers.get(0).setId(1);

        this.encryptedMsgs.add(encryptedMsgs);
        this.userid = id;
        this.cipherId = 1;

    }

    //MODIFIES: this
    //EFFECTS: adds a new cipher to userciphers
    public void newCipher(CipherObj cipher, List<SealedObject> msgs) {
        userCiphers.add(cipher);
        int size = userCiphers.size();
        userCiphers.get(size).setId(size);
    }


    //MODIFIES: this
    //EFFECTS: Add a SealedObject to encryptedMsgs Arraylist
    public void addEncryption(SealedObject encryptObj) {
        encryptedMsgs.get(cipherId).add(encryptObj);
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

    public void useCipher(int index) {
        this.cipherId = index;
    }


    public CipherObj getAccountCipher() {
        return userCiphers.get(cipherId);
    }

    public int getCipherSize() {
        return encryptedMsgs.size();
    }

    public List<SealedObject> getEncryptedMsgs() {
        return encryptedMsgs.get(cipherId);
    }

    public String getId() {
        return userid;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("userid", userid);
        json.put("Ciphers", ciphersToJson());
        return json;
    }

    private JSONArray ciphersToJson() {
        JSONArray jsonArray = new JSONArray();

        for (CipherObj c : userCiphers) {
            jsonArray.put(c.toJson());
        }
        return jsonArray;
    }
}

