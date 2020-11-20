package persistence;

import model.Account;
import model.CipherObj;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// File Reader for JSON persistence based off of JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonReader {
    private String source = "./data/storeuser.json";

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    //EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Account read() throws Exception {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseAccount(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private Account parseAccount(JSONObject jsonObject) throws Exception {
        String userId = jsonObject.getString("userid");
        JSONArray jsonArray = jsonObject.getJSONArray("Ciphers");
        JSONObject first = (JSONObject) jsonArray.get(0);
        CipherObj ciph = new CipherObj();
//        ciph.genKeyPair("RSA");
        Account ac = new Account(userId);
        addCiphers(ac, jsonObject);
        return ac;
    }

    // MODIFIES: wr
    // EFFECTS: parses thingies from JSON object and adds them to workroom
    private void addCiphers(Account wr, JSONObject jsonObject) throws Exception {
        JSONArray jsonArray = jsonObject.getJSONArray("Ciphers");
        for (Object json : jsonArray) {
            JSONObject nextThingy = (JSONObject) json;
            addCipher(wr, nextThingy);
        }
    }

    // MODIFIES: wr
    // EFFECTS: parses thingy from JSON object and adds it to workroom
    private void addCipher(Account ac, JSONObject jsonObject) throws Exception {
        CipherObj cipherObj = new CipherObj();
        int id = jsonObject.getInt("id");
        int idCipher = jsonObject.getInt("id");
        String privateModulus = null;
        String privExp = null;


        if (jsonObject.getString("private").contains("SunRsaSign")) {
            privateModulus = jsonObject.getString("private").substring(68, 685);
            privExp = jsonObject.getString("private").substring(706);
        } else {
            privateModulus = jsonObject.getString("private").substring(57, 674);
            privExp = jsonObject.getString("private").substring(695, 1311);
        }
        String pub = jsonObject.getString("public");

        cipherObj.createPublicKey(pub.substring(56, 673));
        cipherObj.createPrivateKey(privateModulus, privExp);
        cipherObj.validPair(cipherObj.getPublicKey(), cipherObj.getPrivateKey());
        cipherObj.setId(id);
        //String messages = jsonObject.getJSONArray("messages");
        ac.newCipher(cipherObj);

    }

}
