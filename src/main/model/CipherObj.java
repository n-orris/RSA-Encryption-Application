package model;

import org.jetbrains.annotations.NotNull;

import javax.crypto.Cipher;
import javax.crypto.SealedObject;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.concurrent.ThreadLocalRandom;

public class CipherObj {

    private static final String ALGO = "RSA"; // encryption algorithm
    KeyPair keyPair;
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");  // Cipher object used to encrypt/decrypt
    private SealedObject encapsulatedMsg; // holds encrypted message

    public CipherObj() throws Exception {

    }


    //MODIFIES: this
    //EFFECTS: Generates an encrypted pair of keys and stores them in keypair,publicKey, and privateKey
    public void genKeyPair() {
        try {
            // generates secure random number
            SecureRandom secRandom = new SecureRandom();
            // Sets the encryption algorithm
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGO);
            // initializes keypairgen with key size and cryptographic strength randomness
            keyPairGenerator.initialize(2048, secRandom);
            // Generates the keyPair and assigns to variables
            keyPair = keyPairGenerator.generateKeyPair();
            publicKey = keyPair.getPublic();
            privateKey = keyPair.getPrivate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Sourced from: https://stackoverflow.com/questions/28204659/how-to-get-public-rsa-key-from-unformatted-string
    // Credit to user @fishi0x01
    //REQUIRES: Valid public key modulus and exponent arguments
    //MODIFIES: this
    //EFFECTS: creates a public key from modulus and exponent args. assigns the key to the publicKey field and returns
    //true if key succesfully created/replaced
    public PublicKey createPublicKey(String stringPublicKey) throws Exception {
        if (stringPublicKey.length() == 617) {
            BigInteger keyInt = new BigInteger(stringPublicKey, 10); // hex base
            BigInteger exponentInt = new BigInteger("65537", 10); // decimal base
            RSAPublicKeySpec keySpeck = new RSAPublicKeySpec(keyInt, exponentInt);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            // Inserts into public key slot
            publicKey = keyFactory.generatePublic(keySpeck);
            return publicKey;
        }
        return null;
    }

    // Sourced from: https://stackoverflow.com/questions/28204659/how-to-get-public-rsa-key-from-unformatted-string
    // Credit to user @fishi0x01
    //REQUIRES: valid private key modulus and exponent
    //MODIFIES: this
    //EFFECTS: creates a private key from modulus and exponent args. assigns the key to the privateKey field and returns
    //true if key succesfully created/replaced
    public PrivateKey createPrivateKey(@NotNull String stringPrivateKey, String privateExponent) throws Exception {
        if (stringPrivateKey.length() == 617 || privateExponent.length() == 617) {
            BigInteger keyInt = new BigInteger(stringPrivateKey, 10); // hex base
            BigInteger exponentInt = new BigInteger(privateExponent, 10); // decimal base
            RSAPrivateKeySpec keySpeck = new RSAPrivateKeySpec(keyInt, exponentInt);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            // Inserts into public key slot
            privateKey = keyFactory.generatePrivate(keySpeck);
            return privateKey;
        }
        return null;
    }


    public PublicKey getPublicKey() {
        return publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public Cipher getCipherEncrypt() throws InvalidKeyException {
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher;
    }

    public Cipher getCipherDecrypt() throws InvalidKeyException {
        cipher.init(Cipher.DECRYPT_MODE,privateKey);
        return cipher;
    }


    //MODIFIES: this
    //EFFECTS: initiates cipher into ENCRYPT_MODE with currently stored publickey, creates a SealedObject with
    //encryption then assigns it to encapsulatedMsg field and returns the object
    public SealedObject encryptText(String text) {
        try {
            // Creates the cipher obj
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            // Add data to the cipher obj
            encapsulatedMsg = new SealedObject(text, cipher);
            return encapsulatedMsg;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    //EFFECTS: initiates cipher into DECRYPT_MODE with currently stored private key, unecrypted the sealed object and
    //stores it in a string variable, returns variable
    public String decryptText(SealedObject sealedText) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            String msg = (String) sealedText.getObject(cipher);

            return msg;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean validPair(PublicKey publicKey, PrivateKey privateKey) {
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
            e.printStackTrace();
        }
        return false;
    }

















}


