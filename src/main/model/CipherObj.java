package model;

import exceptions.InvalidKeyPairException;
import exceptions.PrivateKeyException;
import exceptions.PublicKeyException;
import org.json.JSONObject;
import persistence.Writable;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.ThreadLocalRandom;


// holds all encryption keys and methods
public class CipherObj implements Writable {

    private KeyPair keyPair;
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");// Cipher object used to encrypt/decrypt
    private SealedObject encapsulatedMsg; // holds encrypted message
    private int cipherId;
    private List<SealedObject> msgs; // list of encrypted msgs


    public CipherObj() throws NoSuchAlgorithmException, NoSuchPaddingException {
        this.cipherId = 1;
        msgs = new ArrayList<>();
    }


    //MODIFIES: this
    //EFFECTS: Generates an encrypted pair of keys and stores them in keypair,publicKey, and privateKey
    public void genKeyPair(String algorithm) {
        try {
            // generates secure random number
            SecureRandom secRandom = new SecureRandom();
            // Sets the encryption algorithm
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
            // initializes keypairgen with key size and cryptographic strength randomness
            keyPairGenerator.initialize(2048, secRandom);
            // Generates the keyPair and assigns to variables
            keyPair = keyPairGenerator.generateKeyPair();
            publicKey = keyPair.getPublic();
            privateKey = keyPair.getPrivate();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }


    // Sourced from: https://stackoverflow.com/questions/28204659/how-to-get-public-rsa-key-from-unformatted-string
    // Credit to user @fishi0x01
    //REQUIRES: Valid public key modulus and exponent arguments
    //MODIFIES: this
    //EFFECTS: creates a public key from modulus and exponent args. assigns the key to the publicKey field and returns
    //true if key succesfully created/replaced
    public PublicKey createPublicKey(String stringPublicKey) throws PublicKeyException {
        try {
            if (stringPublicKey.length() == 617) {
                BigInteger keyInt = new BigInteger(stringPublicKey, 10); // hex base
                BigInteger exponentInt = new BigInteger("65537", 10); // decimal base
                RSAPublicKeySpec keySpeck = new RSAPublicKeySpec(keyInt, exponentInt);
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                // Inserts into public key slot
                publicKey = keyFactory.generatePublic(keySpeck);
                return publicKey;
            } else {
                throw new PublicKeyException("Invalid key string length");
            }
        } catch (NumberFormatException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new PublicKeyException("Inproperly formatted public key");
        }
    }

    //SETS Id for the cipher object
    public void setId(int id) {
        this.cipherId = id;
    }

    // Sourced from: https://stackoverflow.com/questions/28204659/how-to-get-public-rsa-key-from-unformatted-string
    // Credit to user @fishi0x01
    //REQUIRES: valid private key modulus and exponent
    //MODIFIES: this
    //EFFECTS: creates a private key from modulus and exponent args. assigns the key to the privateKey field and returns
    //true if key succesfully created/replaced
    public PrivateKey createPrivateKey(String stringPrivateKey, String privateExponent) throws PrivateKeyException {

        try {
            if ((stringPrivateKey.length() == 617 || stringPrivateKey.length() == 616)
                    && (privateExponent.length() == 617 || privateExponent.length() == 616)) {
                BigInteger keyInt = new BigInteger(stringPrivateKey, 10); // hex base
                BigInteger exponentInt = new BigInteger(privateExponent, 10); // decimal base
                RSAPrivateKeySpec keySpeck = new RSAPrivateKeySpec(keyInt, exponentInt);
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                // Inserts into public key slot
                privateKey = keyFactory.generatePrivate(keySpeck);
                return privateKey;

            } else {
                throw new PrivateKeyException("Invalid key string length");
            }
        } catch (NumberFormatException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new PrivateKeyException("Inproperly formatted private key");
        }
    }


    // EFFECTS: returns the public key
    public PublicKey getPublicKey() {
        return publicKey;
    }
    //EFFECTS: returns the private key

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    // Adds a sealed object to the lsit
    public void addEncryption(SealedObject s) {
        msgs.add(s);
    }

    // removes sealed object at index in list
    public void removeEncryption(int index) {
        msgs.remove(index);
    }

    // gets all sealedobjects in list
    public List<SealedObject> getEncryptedMsgs() {
        return msgs;
    }

    //returns the cipher in encrypt mode, if invalid public key returns null
    public Cipher getCipherEncrypt() throws PublicKeyException {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return cipher;
        } catch (InvalidKeyException e) {
            throw new PublicKeyException("Cipher cannot be initiated with invalid public key");
        }
    }

    //EFFECTS: retrieves the cipher in decrypt mode, if invalid private key returns null
    public Cipher getCipherDecrypt() throws PrivateKeyException {
        try {

            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return cipher;
        } catch (InvalidKeyException e) {
            throw new PrivateKeyException("Cipher cannot be initiated with invalid public key");
        }
    }


    //MODIFIES: this
    //EFFECTS: initiates cipher into ENCRYPT_MODE with currently stored publickey, creates a SealedObject with
    //encryption then assigns it to encapsulatedMsg field and returns the object
    public SealedObject encryptText(String text) throws PublicKeyException {

        try {
            // Creates the cipher obj
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            // Add data to the cipher obj
            encapsulatedMsg = new SealedObject(text, cipher);
            msgs.add(encapsulatedMsg);
            return encapsulatedMsg;

        } catch (Exception e) {
            throw new PublicKeyException("Invalid public key, cannot encrypt message");
        }
    }

    //EFFECTS: initiates cipher into DECRYPT_MODE with currently stored private key, unecrypts the sealed object and
    //return the plaintext string
    public String decryptText(SealedObject sealedText) throws PrivateKeyException {

        try {
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            String msg = (String) sealedText.getObject(privateKey);

            return msg;
        } catch (Exception e) {
            throw new PrivateKeyException("Invalid private key. cannot decrypt message");
        }
    }


    // solution sourced from https://stackoverflow.com/questions/49426844/how-to-validate-a-public-and-private-key-pair-in-java
    // @user Peter Walser

    // EFFECTS: returns true if keypair is valid, false otherwise
    public boolean validPair(PublicKey publicKey, PrivateKey privateKey) throws InvalidKeyPairException {
        try {
            // create a challenge
            byte[] challenge = new byte[10000];
            ThreadLocalRandom.current().nextBytes(challenge);

            // sign using the private key
            Signature sig = Signature.getInstance("SHA256withRSA");
            sig.initSign(privateKey);
            sig.update(challenge);
            byte[] signature = sig.sign();

            // verify signature using the public key
            sig.initVerify(publicKey);
            sig.update(challenge);
            return sig.verify(signature);
        } catch (Exception e) {
            throw new InvalidKeyPairException("Public & private key are not a valid pair");
        }
    }

    // EFFECTS: returns a json object of the Cipher
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("id", cipherId);
        json.put("keyPair", keyPair);
        json.put("public", publicKey);
        json.put("private", privateKey);
        json.put("cipher", cipher);
        return json;
    }

}






