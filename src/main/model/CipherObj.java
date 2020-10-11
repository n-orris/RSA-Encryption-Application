package model;


import javax.crypto.Cipher;
import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.SchemaOutputResolver;
import java.security.*;

public class CipherObj {

    private static final String ALGO = "RSA";
    private final KeyPairGenerator keyPairGenerator;
    private SecureRandom secRandom = new SecureRandom();
    private KeyPair keyPair;
    private String hexStringKey;
    private Cipher cipher;

    public CipherObj() throws Exception {

        // Sets the encryption algorithm
        keyPairGenerator = KeyPairGenerator.getInstance(ALGO);
        // initializes keypairgen with key size and cryptographic strength randomness
        keyPairGenerator.initialize(2048, secRandom);
        // Generates the keyPair
        keyPair = keyPairGenerator.generateKeyPair();

    }

    public PublicKey getPublicKey() {
        return keyPair.getPublic();
    }

    public PrivateKey getPrivateKey() {
        return keyPair.getPrivate();
    }

    //EFFECTS: Converts key to hexBinary and stores it in hexStringKey to allow console output
    public String stringPublicKey() {
        hexStringKey = DatatypeConverter.printHexBinary(keyPair.getPublic().getEncoded());
        return hexStringKey;
    }

    //EFFECTS: Converts key to hexBinary and stores it in hexStringKey to allow console output
    public String stringPrivateKey() {
        hexStringKey = DatatypeConverter.printHexBinary(keyPair.getPrivate().getEncoded());
        return hexStringKey;
    }


    //REQUIRES
    //MODIFIES
    //EFFECTS
    public byte[] encryptText(String text) throws Exception {
        cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        // Creates the cipher obj
        cipher.init(Cipher.ENCRYPT_MODE,keyPair.getPublic());
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
    public String decryptText(byte[] ciphertext) throws Exception {
        cipher.init(Cipher.DECRYPT_MODE,keyPair.getPrivate());
        //decrypted text
        byte[] plainText = cipher.doFinal(ciphertext);
        return new String(plainText);
    }

}




























