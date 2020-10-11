package model;


import javax.crypto.Cipher;
import javax.xml.bind.DatatypeConverter;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

public class CipherObj {

    private static final String ALGO = "RSA";
    private KeyPairGenerator keyPairGenerator;
    private SecureRandom secRandom = new SecureRandom();
    private KeyPair keyPair;
    private String hexStringKey;
    private Cipher cipher;
    private PublicKey publicKey;
    private PrivateKey privateKey;

    public CipherObj() throws Exception {

    }


    //MODIFIES: initializes and generates a pair of public and private keys
    //EFFECTS:
    public void genKeyPair() throws NoSuchAlgorithmException {
        // Sets the encryption algorithm
        keyPairGenerator = KeyPairGenerator.getInstance(ALGO);
        // initializes keypairgen with key size and cryptographic strength randomness
        keyPairGenerator.initialize(2048, secRandom);
        // Generates the keyPair
        keyPair = keyPairGenerator.generateKeyPair();
        publicKey = keyPair.getPublic();
        privateKey = keyPair.getPrivate();
    }

    // Sourced from: https://stackoverflow.com/questions/28204659/how-to-get-public-rsa-key-from-unformatted-string
    // Credit to user @fishi0x01
    public void createPublicKey(String stringPublicKey,String publicExponent) throws Exception {
        try {
            // for key modulus and exponent values as hex and decimal string respectively

            BigInteger keyInt = new BigInteger(stringPublicKey, 10); // hex base
            BigInteger exponentInt = new BigInteger(publicExponent, 10); // decimal base

            RSAPublicKeySpec keySpeck = new RSAPublicKeySpec(keyInt, exponentInt);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            // Inserts into public key slot
            publicKey = keyFactory.generatePublic(keySpeck);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createPrivateKey(String stringPrivateKey,String privateExponent) throws Exception {
        try {
            // for key modulus and exponent values as hex and decimal string respectively

            BigInteger keyInt = new BigInteger(stringPrivateKey, 10); // hex base
            BigInteger exponentInt = new BigInteger(privateExponent, 10); // decimal base

            RSAPrivateKeySpec keySpeck = new RSAPrivateKeySpec(keyInt, exponentInt);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            // Inserts into public key slot
            privateKey = keyFactory.generatePrivate(keySpeck);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public PublicKey getPublicKey() {
        return publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    //REQUIRES
    //MODIFIES
    //EFFECTS
    public byte[] encryptText(String text) throws Exception {
        cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        // Creates the cipher obj
        cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
        // Add data to the cipher obj
        byte[] msg = text.getBytes();
        cipher.update(msg);
        // Completes the encryption
        byte[] ciphertext = cipher.doFinal();
        return ciphertext;

    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS:
    public byte[] decryptText(byte[] ciphertext) throws Exception {
        cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
        //decrypted text
        byte[] hexKey = cipher.doFinal(ciphertext);
        return hexKey;
    }





}




























